export interface CreateRatingRequest {
    id: number | null;
    rideId: number;
    value: number;
    comment: string;
}