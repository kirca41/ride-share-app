export interface RideResponse {
    id : number;
    origin: string;
    originLatitude: number;
    originLongitude: number;
    destination: string;
    destinationLatitude: number;
    destinationLongitude: number;
    isDoorToDoor: boolean;
    departureDate: string;
    departureTime: string;
    isDepartureTimeFlexible: boolean;
    price: number;
    hasLuggageSpace: boolean;
    capacity: number;
    seatsLeft: number;
    isInstantBookingEnabled: boolean;
    providerFullName: string;
}