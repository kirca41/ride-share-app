import { Box } from "@mui/material";
import { useState } from "react";
import { RideResponse } from "../../interfaces/response/RideResponse";
import SearchRidesForm from "../SearchRidesForm/SearchRidesForm";
import RideListItem from '../RideListItem/RideListItem';
import { RideService } from "../../services/rideService";

const RideList: React.FC = () => {

    const [rides, setRides] = useState<RideResponse[]>([]);

    const onSearchRidesFormSubmit = async (origin?: string, destination?: string, date?: string, seats?: number, sortBy?: string, sortDirection?: string) => {
        const ridesResponse = await RideService.search(origin, destination, date, seats, sortBy, sortDirection);
        setRides(ridesResponse.data);
    }

    const renderedRides = rides.map(ride => {
        return <RideListItem key={ride.id} ride={ride} isMyRidesView={false} onCancel={() => {}} />
    });

    return <Box component="div" display="flex" flexDirection="column" gap={3} sx={{ maxWidth: '100vw', overflow: 'hidden', padding: '2%' }}>
        <SearchRidesForm onSearchRidesFormSubmit={onSearchRidesFormSubmit} />
        <Box component="div" display="flex" gap={3} sx={{
            '@media (max-width: 600px)': {
                flexDirection: 'column',
                alignItems: 'center',
                gap: 1,
            },
            alignItems: 'stretch'
        }}>
            {renderedRides}
            {renderedRides.length === 0 && 'No rides were found in the system for the given criteria.'}
        </Box>
    </Box>
}

export default RideList;