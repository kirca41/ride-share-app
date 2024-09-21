import { Button, Grid, Rating, TextField, Typography } from "@mui/material";
import React, { FormEvent, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { RideResponse } from "../../interfaces/response/RideResponse";
import { RideService } from "../../services/rideService";
import { CreateRatingRequest } from "../../interfaces/request/CreateRatingRequest";
import { RatingService } from "../../services/ratingService";
import { enqueueSnackbar } from "notistack";

interface RideRatingFormData {
    id: number | null;
    value: number;
    comment: string;
}

const RideRating: React.FC = () => {

    const [ride, setRide] = useState<RideResponse>();
    const [formData, setFormData] = useState<RideRatingFormData>({
        id: null,
        value: 0,
        comment: ''
    });
    const { rideUuid } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, [rideUuid]);

    const fetchData = async () => {
        if (rideUuid) {
            const rideResponse = await RideService.getByUuid(rideUuid);
            setRide(rideResponse.data);     

            if (rideResponse.data) {
                const ratingResponse = await RatingService.getByRideId(rideResponse.data.id);
                if (ratingResponse.data) {
                    setFormData({
                        id: ratingResponse.data.id,
                        value: ratingResponse.data.value,
                        comment: ratingResponse.data.comment
                    });
                }
            }
        }
    }

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (ride) {
            const request = {
                rideId: ride.id,
                ...formData
            } as CreateRatingRequest;
            await RatingService.createOrUpdate(request);
            enqueueSnackbar('Successfully saved. Thank you for your feedback.', {
                variant: 'success',
                autoHideDuration: 3000
            });
            navigate('/');
        }
    }

    return <form onSubmit={handleSubmit}>
        <Grid container spacing={2} sx={{
            padding: '2%',
            '@media (min-width: 600px)': {
                width: "50%",
                margin: "0 auto"
            }
        }}>
            <Grid item xs={12}>
                <Typography component="legend">Rating</Typography>
                <Rating
                    name="value"
                    value={formData.value}
                    onChange={(_, newValue) => setFormData({ ...formData, value: newValue ? newValue : 0 })}
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    fullWidth
                    multiline
                    label="Comment"
                    name="comment"
                    value={formData.comment}
                    onChange={(event) => setFormData({ ...formData, comment: event.target.value })}
                    type="text"
                    required
                />
            </Grid>
            <Grid item xs={12}>
                <Button variant="contained" color="success" fullWidth type="submit">
                    Submit
                </Button>
            </Grid>
        </Grid>
    </form>
}

export default RideRating;