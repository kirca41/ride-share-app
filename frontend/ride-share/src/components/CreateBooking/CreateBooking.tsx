import { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { getById } from "../../services/rideService";
import { RideResponse } from "../../interfaces/response/RideResponse";
import { Box, Button, Card, CardActions, CardContent, CardHeader, Divider, TextField, Typography } from "@mui/material";
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import BoltIcon from '@mui/icons-material/Bolt';
import HomeIcon from '@mui/icons-material/Home';
import LuggageIcon from '@mui/icons-material/Luggage';
import { createBooking } from "../../services/bookingService";
import { CreateBookingRequest } from "../../interfaces/request/CreateBookingRequest";

const CreateBooking: React.FC = () => {

    const [searchParams] = useSearchParams();
    const { rideId } = useParams();
    const navigate = useNavigate();
    const [seats, setSeats] = useState(1);
    const [ride, setRide] = useState<RideResponse | null>(null);

    useEffect(() => {
        const seatsParam = searchParams.get('seats');
        if (seatsParam)
            setSeats(Number(seatsParam));

        if (rideId) {
            fetchRideById();
        }
    }, [searchParams]);

    const fetchRideById = async () => {
        const rideResponse = await getById(Number(rideId));
        setRide(rideResponse.data);
    }

    const onBookClick = async () => {
        if (ride) {
            const request: CreateBookingRequest = {
                rideId: ride.id,
                seatsToBook: seats
            }
            const bookingResponse = await createBooking(request);

            // TODO: handle case when you have already booked for this ride or you are also the provider

            navigate('../');
        }
    }

    const submitButtonLabel = ride?.isInstantBookingEnabled ? 'Book' : 'Request';
    const bookingRequestDisclaimer = !ride?.isInstantBookingEnabled ? 
        `Submitting this form will not instanly confirm your seats on the selected ride. 
        Instead, your requested will be reviewed by the provider after which
        you will receive an email with the outcome of your request.`
        : '';

    return <Box sx={{
        '@media (min-width: 600px)': {
            display: "flex",
            justifyContent: "center",
            width: "60%",
            margin: "0 auto"
        }
    }}>
        {ride && <Card>
            <CardHeader title="Booking Info" titleTypographyProps={{ variant: "h3", component: "div", align: "center", color: "primary" }} />
            <CardContent>
                <Box display="flex" flexDirection="column" gap={1}>
                    <Typography variant="h6" component="div" display="flex" alignItems="center">
                        <AccessTimeIcon /> {ride.departureDate} {ride.departureTime}
                    </Typography>
                    <Divider />
                    <Typography variant="h6" component="div" display="flex" alignItems="center">
                        From: {ride.origin}
                    </Typography>
                    <Typography variant="h6" component="div" display="flex" alignItems="center">
                        To: {ride.destination}
                    </Typography>
                    <Typography variant="h6" component="div" display="flex" alignItems="center">
                        Total capacity: {ride.capacity}
                    </Typography>
                    <Typography variant="h6" component="div" display="flex" alignItems="center">
                        Seats left: {ride.seatsLeft > 0 ? ride.seatsLeft : 'No seats left for this ride'}
                    </Typography>
                    <Typography variant="h6" component="div" display="flex" alignItems="center">
                        Price: {ride.price}
                    </Typography>
                    <Divider />
                    <Typography variant="subtitle1" component="div" display="flex" alignItems="center" gap={1}>
                        <HomeIcon /> Door-to-Door
                    </Typography>
                    <Typography variant="subtitle1" component="div" display="flex" alignItems="center" gap={1}>
                        <BoltIcon /> Instant Booking Enabled
                    </Typography>
                    <Typography variant="subtitle1" component="div" display="flex" alignItems="center" gap={1}>
                        <LuggageIcon /> Has Luggage Space
                    </Typography>
                    <Divider />
                    <TextField
                        label="Seats to book"
                        variant="outlined"
                        type="number"
                        value={seats}
                        onChange={(e) => setSeats(Number(e.target.value))}
                        InputProps={{ inputProps: { min: 1, max: ride.seatsLeft } }}
                        required
                    />
                </Box>
            </CardContent>
            <CardActions sx={{ flexDirection: "column" }}>
                <Button size="small" variant="contained" color="success" fullWidth onClick={onBookClick}>
                    {submitButtonLabel}
                </Button>
                <Typography variant="subtitle2" style={{ fontStyle: 'italic' }}>
                    {bookingRequestDisclaimer}
                </Typography>
            </CardActions>
        </Card>}
    </Box>
}

export default CreateBooking;