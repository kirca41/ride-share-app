import { RideResponse } from "./RideResponse";

export interface BookingResponse {
    id: number;
    bookedOnDate: string;
    bookedOnTime: string;
    statusName: string;
    statusPrettyName: string;
    bookedById: number;
    bookedByUsername: string;
    ride: RideResponse;
}