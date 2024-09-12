import { Box } from "@mui/material";
import { useState } from "react";
import { RideResponse } from "../../interfaces/response/RideResponse";
import { search } from "../../services/rideService";
import SearchRidesForm from "../SearchRidesForm/SearchRidesForm";
import RideListItem from '../RideListItem/RideListItem';

const RideList: React.FC = () => {

    const [rides, setRides] = useState<RideResponse[]>([]);

    const onSearchRidesFormSubmit = async (origin?: string, destination?: string, date?: string, seats?: number) => {
        const ridesResponse = await search(origin, destination, date, seats);
        setRides(ridesResponse.data);
    }

    const renderedRides = rides.map(ride => {
        return <RideListItem key={ride.id} ride={ride} isMyRidesView={false} />
    });

    return <Box component="div" display="flex" flexDirection="column" gap={3} sx={{ maxWidth: '100vw', overflow: 'hidden' }}>
        <SearchRidesForm onSearchRidesFormSubmit={onSearchRidesFormSubmit} />
        <Box component="div" display="flex" gap={3} height="100vh" sx={{
            '@media (max-width: 600px)': {
                flexDirection: 'column',
                alignItems: 'center',
                gap: 1,
            }
        }}>
            {renderedRides}
            {renderedRides.length === 0 && 'No rides were found in the system for the given criteria.'}
        </Box>
    </Box>
}

export default RideList;