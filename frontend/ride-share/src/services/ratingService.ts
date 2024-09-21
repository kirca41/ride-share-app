import axios from "../config/axiosConfig";
import { CreateRatingRequest } from "../interfaces/request/CreateRatingRequest";
import { RatingResponse } from "../interfaces/response/RatingResponse";

const path = '/ratings'

export const RatingService = {
    getByRideId: async (rideId: number) => {
        return axios.get<RatingResponse>(`${path}/ride/${rideId}`);
    },
    createOrUpdate: async (request: CreateRatingRequest) => {
        return axios.post<RatingResponse>(path, request);
    }
}