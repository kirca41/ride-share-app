import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import BoltIcon from '@mui/icons-material/Bolt';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import HomeIcon from '@mui/icons-material/Home';
import LuggageIcon from '@mui/icons-material/Luggage';
import { Box, Button, Card, CardActions, CardContent, Chip, Divider, Typography } from "@mui/material";
import { useState } from "react";
import { RideResponse } from "../../interfaces/response/RideResponse";
import { search } from "../../services/rideService";
import SearchRidesForm from "../SearchRidesForm/SearchRidesForm";

export function RideList() {

    const [rides, setRides] = useState<RideResponse[]>([]);

    const onSearchRidesFormSubmit = async (origin?: string, destination?: string, date?: string, seats?: number) => {
        const ridesResponse = await search(origin, destination, date, seats);
        setRides(ridesResponse.data);
    }

    const renderedRides = rides.map(ride => {
        const isDoorToDoorChip = ride.isDoorToDoor ? <Chip icon={<HomeIcon />} label="Door-to-Door" /> : '';
        const isInstantBookingEnabledChip = ride.isInstantBookingEnabled ? <Chip icon={<BoltIcon />} label="Instant Booking Enabled" /> : '';
        const hasLuggageSpaceChip = ride.hasLuggageSpace ? <Chip icon={<LuggageIcon />} label="Has Luggage Space" /> : '';

        return (
            <Card sx={{ maxWidth: '100vw', height: 'fit-content' }}>
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
                <CardActions>
                    <Button variant="contained" size="small" startIcon={<ChatBubbleIcon />}>Send Message</Button>
                    {ride.isInstantBookingEnabled && <Button variant="contained" size="small" color="success">Book</Button>}
                    {!ride.isInstantBookingEnabled && <Button variant="contained" size="small" color="success">Request</Button>}
                    <Box component="div" display="flex" justifyContent="flex-end" flexGrow={1}>
                        <Button variant="text">{ride.providerFullName}</Button>
                    </Box>
                </CardActions>
            </Card>
        );
    });

    return <Box component="div" display="flex" flexDirection="column" gap={3} sx={{ maxWidth: '100vw', overflow: 'hidden' }}>
        <SearchRidesForm onSearchRidesFormSubmit={onSearchRidesFormSubmit} />
        <Box component="div" display="flex" gap={3} height="100vh" sx={{
            '@media (max-width: 600px)': {
                flexDirection: 'column',
                alignItems: 'center',
                gap: 1,
            }
        }}>{renderedRides}</Box>
    </Box>
}