import { RideResponse } from "./RideResponse";
import { UserResponse } from "./UserResponse";

export interface RatingResponse {
    id: number;
    value: number;
    comment: string;
    ride: RideResponse;
    ratedBy: UserResponse;
}