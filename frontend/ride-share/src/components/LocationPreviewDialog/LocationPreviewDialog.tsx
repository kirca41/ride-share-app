import { Dialog, DialogTitle } from "@mui/material";
import { Map } from '../Map/Map';

interface LocationPreviewDialogProps {
    title: string;
    latitude: number;
    longitude: number;
    open: boolean;
    onClose: () => void;
}

const LocationPreviewDialog = ({ title, latitude, longitude, open, onClose }: LocationPreviewDialogProps) => {

    return (
        <Dialog open={open} fullWidth maxWidth="xl" onClose={() => onClose()}>
            <DialogTitle>{title}</DialogTitle>
            <Map
                latitude={latitude} 
                longitude={longitude}
                draggable={false}
                handleMarkerDragEnd={() => {}}
            />
        </Dialog>
    );
}

export default LocationPreviewDialog;