import { Autocomplete, Box, Button, TextField } from '@mui/material';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs, { Dayjs } from 'dayjs';
import { debounce } from 'lodash';
import React, { useEffect, useMemo, useState } from 'react';
import { LocationSelectOption } from '../../interfaces/LocationSelectOption';
import { searchLocation } from '../../services/osmService';
import { useLocation, useNavigate } from 'react-router-dom';

interface SearchRidesFormProps {
    onSearchRidesFormSubmit: (origin?: string, destination?: string, date?: string, seats?: number) => void
}

const SearchRidesForm: React.FC<SearchRidesFormProps> = ({ onSearchRidesFormSubmit }: SearchRidesFormProps) => {
    const [origin, setOrigin] = useState<string | LocationSelectOption | null>(null);
    const [destination, setDestination] = useState<string | LocationSelectOption | null>(null);
    const [date, setDate] = useState<Dayjs | null>(dayjs(new Date()));
    const [seats, setSeats] = useState<number>(1);
    const [originOptions, setOriginOptions] = useState<LocationSelectOption[]>([]);
    const [destinationOptions, setDestinationOptions] = useState<LocationSelectOption[]>([]);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const originParam = queryParams.get('origin');
        const destinationParam = queryParams.get('destination');
        const dateParam = queryParams.get('date');
        const seatsParam = queryParams.get('seats');

        if (originParam) {
            setOrigin({ label: originParam, value: originParam });
        }
        if (destinationParam) {
            setDestination({ label: destinationParam, value: destinationParam });
        }
        if (dateParam) {
            setDate(dayjs(dateParam));
        }
        if (seatsParam) {
            setSeats(Number(seatsParam));
        }
    }, []);

    useEffect(() => {
        const queryParams = new URLSearchParams();        

        if (origin) {
            queryParams.set('origin', typeof origin === 'string' ? origin : origin.value);
        }
        if (destination) {
            queryParams.set('destination', typeof destination === 'string' ? destination : destination.value);
        }
        if (date) queryParams.set('date', date.format('YYYY-MM-DD'));
        if (seats) queryParams.set('seats', seats.toString());

        navigate(`?${queryParams.toString()}`, { replace: true });
    }, [origin, destination, date, seats, navigate]);

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


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const originValue = typeof origin === 'string' ? origin : origin?.value;
        const destinationValue = typeof destination === 'string' ? destination : destination?.value;
        onSearchRidesFormSubmit(originValue, destinationValue, date?.format('YYYY-MM-DD'), seats);
    };

    return (
        <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{
                display: 'flex',
                flexDirection: 'column',
                maxWidth: '100vw',
                gap: 2,
                marginTop: '2%',
                '@media (min-width: 600px)': {
                    flexDirection: 'row',
                    alignItems: 'center',
                    gap: 3,
                }
            }}
        >

            <Autocomplete
                value={origin}
                freeSolo
                onChange={(_, newValue) => {
                    if (typeof newValue === 'string') {
                        setOrigin({ label: newValue, value: newValue });
                    } else if (newValue) {
                        setOrigin(newValue);
                    } else {
                        setOrigin(null);
                    }
                }}
                onInputChange={(_, inputValue) => {
                    setOrigin({ label: inputValue, value: inputValue });
                    inputValue && debouncedSearchOriginOptions(inputValue);
                }}
                options={originOptions}
                getOptionLabel={(option) => {
                    if (typeof option === 'string') {
                        return option;
                    } else if (option) {
                        return option.label
                    } else {
                        return '';
                    }
                }}
                renderInput={(params) => <TextField {...params} label="Origin" variant="outlined" required />}
                sx={{ flexGrow: 1 }}
            />

            <Autocomplete
                value={destination}
                freeSolo
                onChange={(_, newValue) => {
                    if (typeof newValue === 'string') {
                      setDestination({ label: newValue, value: newValue });
                    } else if (newValue) {
                      setDestination(newValue);
                    } else {
                      setDestination(null);
                    }
                  }}
                  onInputChange={(_, inputValue) => {
                    setDestination({ label: inputValue, value: inputValue });
                    inputValue && debouncedSearchDestinationOptions(inputValue);
                  }}
                options={destinationOptions}
                getOptionLabel={(option) => {
                    if (typeof option === 'string') {
                        return option;
                    } else if (option) {
                        return option.label
                    } else {
                        return '';
                    }
                }}
                renderInput={(params) => <TextField {...params} label="Destination" variant="outlined" required />}
                sx={{ flexGrow: 1 }}
            />

            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                    label="Date"
                    value={date}
                    onChange={(newValue) => setDate(newValue)}
                />
            </LocalizationProvider>

            <TextField
                label="Seats"
                variant="outlined"
                type="number"
                value={seats}
                onChange={(e) => setSeats(Number(e.target.value))}
                InputProps={{ inputProps: { min: 1 } }}
                required
            />

            <Button type="submit" variant="contained" color="primary">
                Search
            </Button>
        </Box>
    );
};

export default SearchRidesForm;