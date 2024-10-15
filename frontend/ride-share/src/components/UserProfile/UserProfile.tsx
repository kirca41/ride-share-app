import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"
import { UserResponse } from "../../interfaces/response/UserResponse";
import { Avatar, Card, CardContent, Collapse, Divider, Grid, IconButton, List, ListItem, ListItemText, Rating, Typography } from "@mui/material";
import { UserService } from "../../services/userService";
import React from "react";
import { ExpandLess, ExpandMore } from "@mui/icons-material";
import { RatingResponse } from "../../interfaces/response/RatingResponse";
import { RatingService } from "../../services/ratingService";
import { RideService } from "../../services/rideService";
import { BookingService } from "../../services/bookingService";

const UserProfile: React.FC = () => {

    const [user, setUser] = useState<UserResponse | null>(null);
    const [ratings, setRatings] = useState<RatingResponse[]>([]);
    const [openIndexes, setOpenIndexes] = useState<number[]>([]);
    const [rideCancellations, setRideCancellations] = useState<number>(0);
    const [bookingCancellations, setBookingCancellations] = useState<number>(0);
    const { userId } = useParams();

    useEffect(() => {
        fetchUser();
        fetchRatings();
        fetchRideCancellations();
        fetchBookingCancellations();
    }, [userId]);

    const handleToggle = (index: number) => {
        setOpenIndexes(prevState =>
            prevState.includes(index)
                ? prevState.filter(i => i !== index)
                : [...prevState, index]
        );
    };

    const fetchUser = async () => {
        const userResponse = await UserService.getById(Number(userId));
        setUser(userResponse.data);
    }

    const fetchRatings = async () => {
        const userRatingsResponse = await RatingService.getAllForProvider(Number(userId));
        setRatings(userRatingsResponse.data);
    }

    const fetchRideCancellations = async () => {
        const cancellationsResponse = await RideService.getNumberOfCancellationsByProviderInTheLastMonth(Number(userId));
        setRideCancellations(cancellationsResponse.data);
    }

    const fetchBookingCancellations = async () => {
        const bookingCancellations = await BookingService.getNumberOfCancellationsByBookedByInTheLastMonth(Number(userId));
        setBookingCancellations(bookingCancellations.data);
    }

    const getAverageRating = () => {
        return ratings.map(rating => rating.value).reduce((prev, curr) => prev + curr, 0) / ratings.length;
    }

    const renderedRatings = ratings.map((rating, index) => (
        <React.Fragment key={index}>
            <ListItem>
                <ListItemText 
                    primary={<span><span>{rating.ratedBy.fullName}</span> <Rating name="read-only" value={rating.value} readOnly  sx={{ verticalAlign: 'text-bottom' }}/></span>}
                    secondary={`Ride on ${rating.ride.departureDate} from ${rating.ride.origin} to ${rating.ride.destination}`}
                />
                {rating.comment && (
                    <IconButton onClick={() => handleToggle(index)}>
                        {openIndexes.includes(index) ? <ExpandLess /> : <ExpandMore />}
                    </IconButton>
                )}
            </ListItem>
            <Collapse in={openIndexes.includes(index)} timeout="auto" unmountOnExit>
                <Typography variant="body2" color="text.secondary" sx={{ ml: 4, mr: 4 }}>
                    {rating.comment}
                </Typography>
            </Collapse>
        </React.Fragment>
    ));

    return (
        user && <Card sx={{ maxWidth: 350, margin: 'auto', mt: 5 }}>
            <CardContent>
                <Grid container spacing={2} alignItems="center">
                    <Grid item>
                        <Avatar
                            alt={user.fullName}
                            sx={{ width: 80, height: 80 }}
                        />
                    </Grid>
                    <Grid item>
                        <Typography variant="h5" component="div">
                            {user.fullName}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {user.mobileNumber}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Joined on: {user.joinedOnDate}
                        </Typography>
                    </Grid>
                </Grid>
                <Typography variant="subtitle2" sx={{ mt: 3 }}>
                    Ride cancellations in the last month: {rideCancellations}
                </Typography>
                <Typography variant="subtitle2" sx={{ mt: 3 }}>
                    Booking cancellations in the last month: {bookingCancellations}
                </Typography>
                <Typography variant="h6" sx={{ mt: 3 }} display="flex" justifyContent="space-between">
                    <span>Driver Ratings</span>
                    <span>Average: {getAverageRating()}</span>
                </Typography>
                <List>
                    {renderedRatings}
                </List>
            </CardContent>
        </Card>
    );
}

export default UserProfile;