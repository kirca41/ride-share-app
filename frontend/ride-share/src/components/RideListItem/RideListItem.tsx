import { Box, Button, Card, CardActions, CardContent, Chip, Divider, Typography } from "@mui/material";
import { RideResponse } from "../../interfaces/response/RideResponse";
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import BoltIcon from '@mui/icons-material/Bolt';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import HomeIcon from '@mui/icons-material/Home';
import LuggageIcon from '@mui/icons-material/Luggage';
import { Link } from 'react-router-dom';

const RideListItem = ({ ride, isMyRidesView }: { ride: RideResponse, isMyRidesView: boolean }) => {
    const isDoorToDoorChip = ride.isDoorToDoor ? <Chip icon={<HomeIcon />} label="Door-to-Door" /> : '';
    const isInstantBookingEnabledChip = ride.isInstantBookingEnabled ? <Chip icon={<BoltIcon />} label="Instant Booking Enabled" /> : '';
    const hasLuggageSpaceChip = ride.hasLuggageSpace ? <Chip icon={<LuggageIcon />} label="Has Luggage Space" /> : '';


    return (
        <Card variant="outlined" sx={{ maxWidth: '100vw', height: 'fit-content' }} key={ride.id}>
            <CardContent>
                <Typography variant="h5" component="div" display="flex" alignItems="center">
                    {ride.origin} <ArrowForwardIcon /> {ride.destination}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    {ride.departureDate} {ride.departureTime}
                </Typography>
                <Typography sx={{ fontStyle: 'italic' }} color="text.secondary">
                    {ride.isDepartureTimeFlexible && 'Departure time is flexible'}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    Price: {ride.price}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    Total capacity: {ride.capacity}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    Seats left: {ride.seatsLeft}
                </Typography>
                <Box display="flex" flexWrap="wrap" gap={2}>
                    {isDoorToDoorChip}
                    {isInstantBookingEnabledChip}
                    {hasLuggageSpaceChip}
                </Box>
            </CardContent>
            <Divider />
            {!isMyRidesView && <CardActions>
                <Button variant="contained" size="small" startIcon={<ChatBubbleIcon />}>Send Message</Button>
                {ride.isInstantBookingEnabled && <Link to={`book-ride/${ride.id}`}><Button variant="contained" size="small" color="success">Book</Button></Link>}
                {!ride.isInstantBookingEnabled && <Link to={`book-ride/${ride.id}`}><Button variant="contained" size="small" color="success">Request</Button></Link>}
                <Box component="div" display="flex" justifyContent="flex-end" flexGrow={1}>
                    <Button variant="text">{ride.providerFullName}</Button>
                </Box>
            </CardActions>}
            {isMyRidesView && <CardActions>
                <Link to={`rides/${ride.id}/bookings`}><Button variant="contained" size="small" color="success">View bookings</Button></Link>
            </CardActions>}
        </Card>
    );
}

export default RideListItem;