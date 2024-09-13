import { Box, Checkbox, FormControlLabel, List, useMediaQuery } from "@mui/material";
import { useEffect, useState } from "react";
import { BookingResponse } from "../../interfaces/response/BookingResponse";
import { getAllForActiveUser } from "../../services/bookingService";
import BookingListItem from "../BookingListItem/BookingListItem";

const MyBookings: React.FC = () => {

    const [bookings, setBookings] = useState<BookingResponse[]>([]);
    const [includePast, setIncludePast] = useState(false);
    const isSmallScreen = useMediaQuery((theme: any) => theme.breakpoints.down('sm'));


    useEffect(() => {
        fetchBookingsForActiveUser();
    }, [includePast]);

    const fetchBookingsForActiveUser = async () => {
        const bookingsResponse = await getAllForActiveUser(includePast);
        setBookings(bookingsResponse.data);
    }

    const renderedBookings = bookings.map(booking => {
        return <BookingListItem booking={booking} isSmallScreen={isSmallScreen} isMyBookingsView={false} onApprove={() => {}} onDecline={() => {}} />
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
            label="Include Past Rides"
        />
        <List sx={{ width: isSmallScreen ? "100%" : "60%", margin: "0 auto" }}>
            {renderedBookings}
            {noBookingsYet}
        </List>
    </Box>
}

export default MyBookings;