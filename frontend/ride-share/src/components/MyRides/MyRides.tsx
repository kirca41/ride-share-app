import { Box, Checkbox, FormControlLabel, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { RideResponse } from "../../interfaces/response/RideResponse";
import RideListItem from "../RideListItem/RideListItem";
import { Link } from "react-router-dom";
import { RideService } from "../../services/rideService";
import { enqueueSnackbar } from "notistack";

const MyRides: React.FC = () => {

    const [includePast, setIncludePast] = useState(false);
    const [rides, setRides] = useState<RideResponse[]>([]);

    useEffect(() => {
        fetchMyRides();
    }, [includePast])

    const fetchMyRides = async () => {
        const ridesResponse = await RideService.getAllForActiveUser(includePast);
        setRides(ridesResponse.data);
    }

    const onCancel = async (id: number) => {
        const _ = await RideService.cancel(id);
        enqueueSnackbar('Successfully saved', {
            variant: 'success',
            autoHideDuration: 3000
        });
        fetchMyRides();
    }

    const renderedRides = rides.map(ride => {
        return <RideListItem key={ride.id} ride={ride} isMyRidesView onCancel={onCancel} />
    });

    let noPublishedRidesYet;
    if (renderedRides.length === 0) {
        noPublishedRidesYet = <Typography component="div" variant="body1">
            You have no published rides yet. To publish you first ride click <Link to="/publish-ride" style={{ textDecoration: "none", color: "#1976d2" }}>here</Link>.
        </Typography>
    }

    return <Box component="div" display="flex" flexDirection="column" gap={3} sx={{ maxWidth: '100vw', padding: '2%' }}>
        <FormControlLabel sx={{ justifyContent: "flex-end" }}
            control={
                <Checkbox
                    checked={includePast}
                    onChange={(e) => setIncludePast(e.target.checked)}
                    name="includePast"
                />
            }
            label="Include Past Rides"
        />
        <Box component="div" display="flex" gap={3} flexWrap="wrap" sx={{
            '@media (max-width: 600px)': {
                flexDirection: 'row',
                alignItems: 'center',
                gap: 1
            },
            alignItems: 'stretch',
            margin: "0 auto"
        }}>
            {renderedRides}
            {noPublishedRidesYet}
        </Box>
    </Box>
}

export default MyRides;