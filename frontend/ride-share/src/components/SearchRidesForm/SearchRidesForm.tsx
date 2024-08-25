import React, { useMemo, useState } from 'react';
import { TextField, Button, Box, Autocomplete } from '@mui/material';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs, { Dayjs } from 'dayjs';
import { LocationSelectOption } from '../../interfaces/LocationSelectOption';
import { searchLocation } from '../../services/osmService';
import { debounce } from 'lodash';

const SearchRidesForm: React.FC = ({ onSearchRidesFormSubmit }) => {
    const [origin, setOrigin] = useState<LocationSelectOption | null>(null);
    const [destination, setDestination] = useState<LocationSelectOption | null>(null);
    const [date, setDate] = useState<Dayjs | null>(dayjs(new Date()));
    const [seats, setSeats] = useState<number>(1);
    const [originOptions, setOriginOptions] = useState<LocationSelectOption[]>([]);
    const [destinationOptions, setDestinationOptions] = useState<LocationSelectOption[]>([]);

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
        onSearchRidesFormSubmit(origin?.value, destination?.value, date, seats);
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
                onChange={(_, newValue) => setOrigin(newValue)}
                onInputChange={(_, inputValue) => {
                    setOrigin(null);
                    inputValue && debouncedSearchOriginOptions(inputValue);
                }}
                options={originOptions}
                getOptionLabel={(option) => option.label}
                renderInput={(params) => <TextField {...params} label="Origin" variant="outlined" required />}
                sx={{ flexGrow: 1 }}
            />

            <Autocomplete
                value={destination}
                onChange={(_, newValue) => setDestination(newValue)}
                onInputChange={(_, inputValue) => {
                    setDestination(null);
                    inputValue && debouncedSearchDestinationOptions(inputValue);
                }}
                options={destinationOptions}
                getOptionLabel={(option) => option.label}
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