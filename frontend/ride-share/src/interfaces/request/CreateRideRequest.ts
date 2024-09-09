export interface CreateRideRequest {
    origin: string;
    originLatitude: number;
    originLongitude: number;
    destination: string;
    destinationLatitude: number;
    destinationLongitude: number;
    isDoorToDoor: boolean;
    departureTime?: string;
    isDepartureTimeFlexible: boolean;
    price: number;
    hasLuggageSpace: boolean;
    capacity: number;
    isInstantBookingEnabled: boolean;
}