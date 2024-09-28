import { List, useMediaQuery } from "@mui/material";
import { enqueueSnackbar } from "notistack";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BookingResponse } from "../../interfaces/response/BookingResponse";
import { BookingService } from "../../services/bookingService";
import BookingListItem from "../BookingListItem/BookingListItem";

const RideBookingsList: React.FC = () => {

    const [bookings, setBookings] = useState<BookingResponse[]>([]);
    const { rideId } = useParams();
    const isSmallScreen = useMediaQuery((theme: any) => theme.breakpoints.down('sm'));


    useEffect(() => {
        fetchBookingsForRide();
    }, [rideId]);

    const fetchBookingsForRide = async () => {
        const bookingsResponse = await BookingService.getAllForRide(Number(rideId));
        setBookings(bookingsResponse.data);
    }

    const onApprove = async (id: number) => {
        const response = await BookingService.approve(id);
        if (response.status === 200) {
            enqueueSnackbar('Successfully saved', {
                variant: 'success',
                autoHideDuration: 3000
            });
            fetchBookingsForRide();
        }
    }

    const onDecline = async (id: number) => {
        const response = await BookingService.decline(id);
        if (response.status === 200) {
            enqueueSnackbar('Successfully saved', {
                variant: 'success',
                autoHideDuration: 3000
            });
            fetchBookingsForRide();
        }
    }

    let noBookingsForRideYet;
    if (bookings.length === 0) {
        noBookingsForRideYet = 'There are no bookings for this ride yet.'
    }

    const renderedBookings = bookings.map(booking => {
        return <BookingListItem key={booking.id} booking={booking} isSmallScreen={isSmallScreen} isMyBookingsView={false} onApprove={onApprove} onDecline={onDecline} onCancel={() => {}} />
    });

    return <List sx={{ width: isSmallScreen ? "100%" : "60%", margin: "0 auto" }}>
        {renderedBookings}
        {noBookingsForRideYet}
    </List>
}

export default RideBookingsList;