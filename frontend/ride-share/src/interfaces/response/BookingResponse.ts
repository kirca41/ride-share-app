import { RideResponse } from "./RideResponse";

export interface BookingResponse {
    id: number;
    statusName: string;
    statusPrettyName: string;
    bookedById: number;
    bookedByUsername: string;
    ride: RideResponse;
}