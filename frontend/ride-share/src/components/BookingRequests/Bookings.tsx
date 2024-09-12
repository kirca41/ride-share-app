import { Button, Grid, List, ListItem, ListItemText, Typography, useMediaQuery } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BookingResponse } from "../../interfaces/response/BookingResponse";
import { approve, decline, getAllForRide } from "../../services/bookingService";

enum BookingStatus {
    NEW = "NEW",
    APPROVED = "APPROVED",
    CANCELED = "CANCELED",
    DECLINED = "DECLINED"
}

const Bookings: React.FC = () => {

    const [bookings, setBookings] = useState<BookingResponse[]>([]);
    const { rideId } = useParams();
    const isSmallScreen = useMediaQuery((theme: any) => theme.breakpoints.down('sm'));


    useEffect(() => {
        fetchBookingsForRide();
    }, [rideId]);

    const fetchBookingsForRide = async () => {
        const bookingsResponse = await getAllForRide(Number(rideId));
        setBookings(bookingsResponse.data);
    }

    const onApprove = async (id: number) => {
        const response = await approve(id);
        if (response.status === 200)
            fetchBookingsForRide();
    }

    const onDecline = async (id: number) => {
        const response = await decline(id);
        if (response.status === 200)
            fetchBookingsForRide();
    }

    const renderedBookings = bookings.map(booking => {
        const { ride } = booking;
        const bookingDepartureTimeDetails = (ride.departureDate && ride.departureTime) ? `${ride.departureDate} ${ride.departureTime}`
            : 'Flexible departure time';

        return (
            <ListItem key={booking.id} divider>
                <Grid container direction={isSmallScreen ? 'column' : 'row'} spacing={2} alignItems="center">
                    <Grid item xs={12} sm={8}>
                        <ListItemText
                            primary={<Typography variant="h6">{`${booking.ride.origin} - ${booking.ride.destination} ${bookingDepartureTimeDetails}`}</Typography>}
                            secondary={booking.bookedByUsername}
                        />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        {booking.statusName === BookingStatus.NEW ? (
                            <Grid container spacing={1} justifyContent={isSmallScreen ? 'flex-start' : 'flex-end'}>
                                <Grid item>
                                    <Button
                                        variant="contained"
                                        color="success"
                                        fullWidth={isSmallScreen}
                                        onClick={() => onApprove(booking.id)}
                                    >
                                        Approve
                                    </Button>
                                </Grid>
                                <Grid item>
                                    <Button
                                        variant="contained"
                                        color="error"
                                        fullWidth={isSmallScreen}
                                        onClick={() => onDecline(booking.id)}
                                    >
                                        Decline
                                    </Button>
                                </Grid>
                            </Grid>
                        ) : (
                            <Typography variant="h6" align={isSmallScreen ? 'left' : 'right'} color="primary">
                                {booking.statusPrettyName}
                            </Typography>
                        )}
                    </Grid>
                </Grid>
            </ListItem>
        )
    });

    return <List sx={{ width: isSmallScreen ? "100%" : "60%", margin: "0 auto" }}>
        {renderedBookings}
    </List>
}

export default Bookings;