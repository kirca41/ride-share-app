import { Box, Button, Card, CardActions, CardContent, Chip, Divider, Typography } from "@mui/material";
import { RideResponse } from "../../interfaces/response/RideResponse";
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import BoltIcon from '@mui/icons-material/Bolt';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import HomeIcon from '@mui/icons-material/Home';
import LuggageIcon from '@mui/icons-material/Luggage';
import PlaceIcon from '@mui/icons-material/Place';
import { Link, useNavigate } from 'react-router-dom';
import { ChatService } from "../../services/chatService";
import dayjs from "dayjs";
import LocationPreviewDialog from "../LocationPreviewDialog/LocationPreviewDialog";
import { useState } from "react";

interface RideListItemProps {
    ride: RideResponse;
    isMyRidesView: boolean;
    onCancel: (id: number) => void;
}

const RideListItem = ({ ride, isMyRidesView, onCancel }: RideListItemProps) => {
    const [meetingPointDialogOpen, setMeetingPointDialogOpen] = useState(false);
    const [dropOffPointDialogOpen, setDropOffPointDialogOpen] = useState(false);

    const isDoorToDoorChip = ride.isDoorToDoor ? <Chip icon={<HomeIcon />} label="Door-to-Door" /> : '';
    const isInstantBookingEnabledChip = ride.isInstantBookingEnabled ? <Chip icon={<BoltIcon />} label="Instant Booking Enabled" /> : '';
    const hasLuggageSpaceChip = ride.hasLuggageSpace ? <Chip icon={<LuggageIcon />} label="Has Luggage Space" /> : '';
    const rideDepartureDateTime = dayjs(ride.departureDate + ride.departureTime, "YYYY-MM-DD HH:mm");
    const shouldShowCancelButton = rideDepartureDateTime.isAfter(dayjs().add(1, 'day')) && !ride.isCanceled;
    const navigate = useNavigate();

    const openChat = async (providerId: number) => {
        const { data } = await ChatService.getOrCreate(providerId);
        navigate(`/chat/${data.uuid}`);
    }

    const handleMeetingPointDialogOpenClick = () => {
        setMeetingPointDialogOpen(true);
    }
    
    const handleDropOffPointDialogOpenClick = () => {
        setDropOffPointDialogOpen(true);
    }

    return (
        <>
            <Card 
                variant="outlined"
                sx={{ 
                    maxWidth: '100vw',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'flex-end'
                }} 
                    key={ride.id}
            >
                <CardContent sx={{ flexGrow: 1 }}>
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
                    {!ride.isDoorToDoor && <Box sx={{ mb: 1.5 }} color="text.secondary" display="flex" gap={2}>
                        <Chip color="info" icon={<PlaceIcon />} label="View meeting point" onClick={handleMeetingPointDialogOpenClick} />
                        <Chip color="info" icon={<PlaceIcon />} label="View drop off point" onClick={handleDropOffPointDialogOpenClick} />
                    </Box>}
                    <Box display="flex" flexWrap="wrap" gap={2}>
                        {isDoorToDoorChip}
                        {isInstantBookingEnabledChip}
                        {hasLuggageSpaceChip}
                    </Box>
                </CardContent>
                <Divider />
                {!isMyRidesView && <CardActions>
                    <Button onClick={() => openChat(ride.providerId)} variant="contained" size="small" startIcon={<ChatBubbleIcon />}>Chat</Button>
                    {ride.isInstantBookingEnabled && <Link to={`/book-ride/${ride.id}`}><Button variant="contained" size="small" color="success">Book</Button></Link>}
                    {!ride.isInstantBookingEnabled && <Link to={`/book-ride/${ride.id}`}><Button variant="contained" size="small" color="success">Request</Button></Link>}
                    <Box component="div" display="flex" justifyContent="flex-end" flexGrow={1}>
                        <Link to={`/user-profile/${ride.providerId}`}><Button variant="text">{ride.providerFullName}</Button></Link>
                    </Box>
                </CardActions>}
                {isMyRidesView && <CardActions>
                    <Link to={`/rides/${ride.id}/bookings`}><Button variant="contained" size="small" color="success">View bookings</Button></Link>
                    {shouldShowCancelButton && <Button variant="contained" size="small" color="error" onClick={() => onCancel(ride.id)}>Cancel</Button>}
                </CardActions>}
            </Card>
            <LocationPreviewDialog
                title="Meeting point"
                latitude={ride.originLatitude}
                longitude={ride.originLongitude}
                open={meetingPointDialogOpen}
                onClose={() => setMeetingPointDialogOpen(false)}
            />
            <LocationPreviewDialog
                title="Drop off point"
                latitude={ride.destinationLatitude}
                longitude={ride.destinationLongitude}
                open={dropOffPointDialogOpen}
                onClose={() => setDropOffPointDialogOpen(false)}
            />
        </>
    );
}

export default RideListItem;