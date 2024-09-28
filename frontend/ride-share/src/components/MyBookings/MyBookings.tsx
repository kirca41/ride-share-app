import { Box, Checkbox, FormControlLabel, List, Theme, useMediaQuery } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useEffect, useState } from "react";
import { BookingResponse } from "../../interfaces/response/BookingResponse";
import { BookingService } from "../../services/bookingService";
import BookingListItem from "../BookingListItem/BookingListItem";

const MyBookings: React.FC = () => {

    const [bookings, setBookings] = useState<BookingResponse[]>([]);
    const [includePast, setIncludePast] = useState(false);
    const isSmallScreen = useMediaQuery((theme: Theme) => theme.breakpoints.down('sm'));


    useEffect(() => {
        fetchBookingsForActiveUser();
    }, [includePast]);

    const fetchBookingsForActiveUser = async () => {
        const bookingsResponse = await BookingService.getAllForActiveUser(includePast);
        setBookings(bookingsResponse.data);
    }

    const onCancel = async (id: number) => {
        const response = await BookingService.cancel(id);
        if (response.status === 200) {
            enqueueSnackbar('Successfully saved', {
                variant: 'success',
                autoHideDuration: 3000
            });
            fetchBookingsForActiveUser();
        }
    }

    const renderedBookings = bookings.map(booking => {
        return <BookingListItem key={booking.id} booking={booking} isSmallScreen={isSmallScreen} isMyBookingsView={true} onApprove={() => {}} onDecline={() => {}} onCancel={onCancel} />
    });

    let noBookingsYet;
    if (bookings.length === 0) {
        noBookingsYet = 'You have not made any bookings yet.'
    }

    return <Box component="div" display="flex" flexDirection="column" gap={3} sx={{ maxWidth: '100vw', padding: '2%' }}>
        <FormControlLabel sx={{ justifyContent: "flex-end" }}
            control={
                <Checkbox
                    checked={includePast}
                    onChange={(e) => setIncludePast(e.target.checked)}
                    name="includePast"
                />
            }
            label="Include Past Bookings"
        />
        <List sx={{ width: isSmallScreen ? "100%" : "60%", margin: "0 auto" }}>
            {renderedBookings}
            {noBookingsYet}
        </List>
    </Box>
}

export default MyBookings;