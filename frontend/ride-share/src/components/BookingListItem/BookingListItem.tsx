import { Button, Grid, ListItem, ListItemText, Typography } from "@mui/material"
import { BookingResponse } from "../../interfaces/response/BookingResponse"
import { BookingStatus } from "../../interfaces/enums/BookingStatus";
import dayjs from "dayjs";

interface BookingListItemProps {
    booking: BookingResponse;
    isSmallScreen: boolean;
    isMyBookingsView: boolean;
    onApprove: (id: number) => void;
    onDecline: (id: number) => void;
    onCancel: (id: number) => void;
}

const BookingListItem: React.FC<BookingListItemProps> = ({ booking, isSmallScreen, isMyBookingsView, onApprove, onDecline, onCancel }: BookingListItemProps) => {

    const { ride } = booking;
    const bookingDepartureTimeDetails = (ride.departureDate && ride.departureTime) ? `${ride.departureDate} ${ride.departureTime}`
        : 'Flexible departure time';
    const secondaryListItemText = !isMyBookingsView ? `Booked by: ${booking.bookedByUsername} ${booking.bookedOnDate} ${booking.bookedOnTime}` 
        : `Provided by: ${ride.providerFullName}`;
    const rideDepartureDateTime = dayjs(ride.departureDate + ride.departureTime, "YYYY-MM-DD HH:mm");
    const shouldShowActionButtons = rideDepartureDateTime.isAfter(dayjs());

    return <ListItem key={booking.id} divider>
        <Grid container direction={isSmallScreen ? 'column' : 'row'} spacing={2} alignItems="center">
            <Grid item xs={12} sm={8}>
                <ListItemText
                    primary={<Typography variant="h6">{`${booking.ride.origin} - ${booking.ride.destination} ${bookingDepartureTimeDetails}`}</Typography>}
                    secondary={secondaryListItemText}
                />
            </Grid>
            <Grid item xs={12} sm={4}>
                {(shouldShowActionButtons && booking.statusName === BookingStatus.NEW && !isMyBookingsView) ? (
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
                ) : (<Grid item xs={12} sm={4}><Typography variant="h6" align={isSmallScreen ? 'left' : 'right'} color="primary" sx={{ textAlign: 'left' }}>
                    {booking.statusPrettyName}
                </Typography></Grid>
                )}
                {shouldShowActionButtons && booking.statusName !== BookingStatus.DECLINED && booking.statusName !== BookingStatus.CANCELED && isMyBookingsView && <Grid item xs={12} sm={4}>
                        <Button
                            variant="contained"
                            color="error"
                            fullWidth={isSmallScreen}
                            onClick={() => onCancel(booking.id)}
                        >
                            Cancel
                        </Button>
                    </Grid>}
            </Grid>
        </Grid>
    </ListItem>
}

export default BookingListItem;