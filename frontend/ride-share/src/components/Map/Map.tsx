import type { Map as LeafletMap, Marker as LeafletMarker } from 'leaflet';
import { useEffect, useMemo, useRef } from "react";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import 'leaflet/dist/leaflet.css';

interface MapProps {
    latitude: number;
    longitude: number;
    handleMarkerDragEnd: (latitude: number, longitude: number) => void
}

export const Map = ({ latitude, longitude, handleMarkerDragEnd }: MapProps) => {

    const mapRef = useRef<LeafletMap | null>(null);
    const markerRef = useRef<LeafletMarker | null>(null);

    const eventHandlers = useMemo(
        () => ({
          dragend() {
            const marker = markerRef.current
            if (marker != null) {
                handleMarkerDragEnd(marker.getLatLng().lat, marker.getLatLng().lng);
            }
          },
        }),
        [],
      )

    useEffect(() => {
        if (mapRef.current && latitude && longitude) {
            mapRef.current.flyTo([latitude, longitude]);
        }
    }, [latitude, longitude]);

    return <MapContainer
        ref={mapRef}
        center={[latitude, longitude]}
        zoom={17}
        scrollWheelZoom
        style={{ height: "300px" }}
    >
       <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
       {latitude && longitude && <Marker draggable ref={markerRef} position={[latitude, longitude]} eventHandlers={eventHandlers} />}
    </MapContainer>;
}