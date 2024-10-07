import { Autocomplete, Button, Checkbox, FormControl, FormControlLabel, FormHelperText, Grid, TextField, Typography } from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import dayjs, { Dayjs } from 'dayjs';
import { debounce } from 'lodash';
import { enqueueSnackbar } from 'notistack';
import React, { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { LocationSelectOption } from '../../interfaces/LocationSelectOption';
import { CreateRideRequest } from '../../interfaces/request/CreateRideRequest';
import { searchLocation } from '../../services/osmService';
import { Map } from '../Map/Map';
import { RideService } from '../../services/rideService';
import { RidePriceStatisticsResponse } from '../../interfaces/response/RidePriceStatisticsResponse';

interface PublishRideFormData {
    origin: LocationSelectOption | null,
    destination: LocationSelectOption | null,
    isDoorToDoor: boolean,
    departureTime: dayjs.Dayjs | null,
    isDepartureTimeFlexible: boolean,
    price: number | null,
    hasLuggageSpace: boolean,
    capacity: number,
    isInstantBookingEnabled: boolean,
}

const PublishRide: React.FC = () => {
    const [formData, setFormData] = useState<PublishRideFormData>({
        origin: null,
        destination: null,
        isDoorToDoor: true,
        departureTime: dayjs().add(1, 'hour'),
        isDepartureTimeFlexible: false,
        price: null,
        hasLuggageSpace: false,
        capacity: 1,
        isInstantBookingEnabled: false,
    });
    const [originOptions, setOriginOptions] = useState<LocationSelectOption[]>([]);
    const [destinationOptions, setDestinationOptions] = useState<LocationSelectOption[]>([]);
    const [ridePriceStatistics, setRidePriceStatistics] = useState<RidePriceStatisticsResponse | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchRidePriceStatistics();
    }, [formData.origin, formData.destination]);

    const fetchRidePriceStatistics = async () => {
        if (formData.origin != null && formData.destination != null) {
            const response = await RideService.getPriceStatisticsForRide(formData.origin.value, formData.destination.value);
            setRidePriceStatistics(response.data);
        }
    }

    const debouncedSearchOriginOptions = useMemo(
        () =>
            debounce(async (term: string) => {
                const options = await searchLocation(term);
                setOriginOptions(options);
            }, 500),
        []
    );

    const debouncedSearchDestinationOptions = useMemo(
        () =>
            debounce(async (term: string) => {
                const options = await searchLocation(term);
                setDestinationOptions(options);
            }, 500),
        []
    );

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.checked,
        });
    };

    const handleDateChange = (date: Dayjs | null) => {
        if (date) {
            setFormData({
                ...formData,
                departureTime: date,
                isDepartureTimeFlexible: false
            });
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        await RideService.create(formDataToCreateRideRequest());
        enqueueSnackbar('Ride successfully published', {
            variant: 'success',
            autoHideDuration: 3000
        });
        navigate('/');
    };

    const formDataToCreateRideRequest = (): CreateRideRequest => {
        return {
            origin: formData.origin?.value!!,
            originLatitude: formData.origin?.latitude!!,
            originLongitude: formData.origin?.longitude!!,
            destination: formData.destination?.value!!,
            destinationLatitude: formData.destination?.latitude!!,
            destinationLongitude: formData.destination?.longitude!!,
            isDoorToDoor: formData.isDoorToDoor,
            departureTime: formData.departureTime?.format('YYYY-MM-DDTHH:mm:ss'),
            isDepartureTimeFlexible: formData.isDepartureTimeFlexible,
            price: formData.price!,
            hasLuggageSpace: formData.hasLuggageSpace,
            capacity: formData.capacity,
            isInstantBookingEnabled: formData.isInstantBookingEnabled
        }
    }

    const handleMarkerDragEndOrigin = (latitude: number, longitude: number) => {
        setFormData({
            ...formData,
            origin: {
                ...formData.origin!,
                latitude,
                longitude
            }
        });
    }

    const handleMarkerDragEndDestination = (latitude: number, longitude: number) => {
        setFormData({
            ...formData,
            destination: {
                ...formData.destination!,
                latitude,
                longitude
            }
        });
    }

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Typography variant="h3" component="div" display="flex" justifyContent="center" color="primary" sx={{ mt: 3 }}>
                Publish a ride
            </Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2} sx={{
                    '@media (min-width: 600px)': {
                        width: "50%",
                        margin: "0 auto"
                    }
                }}>
                    <Grid item xs={12}>
                        <Autocomplete
                            value={formData.origin}
                            onChange={(_, newValue) => setFormData({ ...formData, origin: newValue })}
                            onInputChange={(_, inputValue) => {
                                setFormData({ ...formData, origin: null });
                                inputValue && debouncedSearchOriginOptions(inputValue);
                            }}
                            options={originOptions}
                            getOptionLabel={(option) => option.label}
                            renderInput={(params) => <TextField {...params} label="Origin" variant="outlined" required />}
                            sx={{ flexGrow: 1 }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        {!formData.isDoorToDoor && formData.origin &&
                            <Map 
                                latitude={formData.origin.latitude}
                                longitude={formData.origin.longitude}
                                draggable={true}
                                handleMarkerDragEnd={handleMarkerDragEndOrigin} 
                            />
                        }
                    </Grid>
                    <Grid item xs={12}>
                        <Autocomplete
                            value={formData.destination}
                            onChange={(_, newValue) => setFormData({ ...formData, destination: newValue })}
                            onInputChange={(_, inputValue) => {
                                setFormData({ ...formData, destination: null });
                                inputValue && debouncedSearchDestinationOptions(inputValue);
                            }}
                            options={destinationOptions}
                            getOptionLabel={(option) => option.label}
                            renderInput={(params) => <TextField {...params} label="Destination" variant="outlined" required />}
                            sx={{ flexGrow: 1 }}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        {!formData.isDoorToDoor && formData.destination &&
                            <Map 
                                latitude={formData.destination.latitude}
                                longitude={formData.destination.longitude}
                                draggable={true}
                                handleMarkerDragEnd={handleMarkerDragEndDestination} 
                            />
                        }
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={formData.isDoorToDoor}
                                    onChange={handleCheckboxChange}
                                    name="isDoorToDoor"
                                />
                            }
                            label="Door-to-Door"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <DateTimePicker
                            label="Departure Time"
                            value={formData.departureTime}
                            onChange={handleDateChange}
                            disablePast
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={formData.isDepartureTimeFlexible}
                                    onChange={handleCheckboxChange}
                                    name="isDepartureTimeFlexible"
                                />
                            }
                            label="Flexible Departure Time"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl fullWidth>
                            <TextField
                                label="Price"
                                name="price"
                                value={formData.price}
                                onChange={handleChange}
                                type="number"
                                required
                            />
                            {ridePriceStatistics && ridePriceStatistics.min && 
                            ridePriceStatistics.max && ridePriceStatistics.average && 
                            <FormHelperText>{`Suggested price range for this route: ${ridePriceStatistics.min} - ${ridePriceStatistics.max}. Average price: ${Math.round(ridePriceStatistics.average)}`}</FormHelperText>}
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={formData.hasLuggageSpace}
                                    onChange={handleCheckboxChange}
                                    name="hasLuggageSpace"
                                />
                            }
                            label="Luggage Space Available"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Capacity"
                            name="capacity"
                            value={formData.capacity}
                            onChange={handleChange}
                            type="number"
                            required
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={formData.isInstantBookingEnabled}
                                    onChange={handleCheckboxChange}
                                    name="isInstantBookingEnabled"
                                />
                            }
                            label="Enable Instant Booking"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="success" fullWidth type="submit">
                            Publish Ride
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </LocalizationProvider>
    );
};

export default PublishRide;
