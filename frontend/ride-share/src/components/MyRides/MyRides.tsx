import { Box, Checkbox, FormControlLabel } from "@mui/material";
import { useEffect, useState } from "react";
import { RideResponse } from "../../interfaces/response/RideResponse";
import { getAllForActiveUser } from "../../services/rideService";
import RideListItem from "../RideListItem/RideListItem";

const MyRides: React.FC = () => {

    const [includePast, setIncludePast] = useState(false);
    const [rides, setRides] = useState<RideResponse[]>([]);

    useEffect(() => {
        fetchMyRides();
    }, [includePast])

    const fetchMyRides = async () => {
        const ridesResponse = await getAllForActiveUser(includePast);
        setRides(ridesResponse.data);
    }

    const renderedRides = rides.map(ride => {
        return <RideListItem ride={ride} isMyRidesView />
    });

    return <Box component="div" display="flex" flexDirection="column" gap={3} sx={{ maxWidth: '100vw', padding: '2%' }}>
        <FormControlLabel
            control={
                <Checkbox
                    checked={includePast}
                    onChange={(e) => setIncludePast(e.target.checked)}
                    name="includePast"
                />
            }
            label="Include Past Rides"
        />
        <Box component="div" display="flex" gap={3} height="100vh" flexWrap="wrap" sx={{
            '@media (max-width: 600px)': {
                flexDirection: 'row',
                alignItems: 'center',
                gap: 1
            }
        }}>
            {renderedRides}
            {renderedRides.length === 0 && 'You have no past rides yet.'}
        </Box>
    </Box>
}

export default MyRides;