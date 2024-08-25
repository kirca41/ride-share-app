import axiosConfig from "../config/axiosConfig";
import { RideResponse } from "../interfaces/response/RideResponse";

const path = 'rides';

export const search = async (origin?: string, destination?: string, date?: string, seats?: number) => {
    return axiosConfig.get<RideResponse[]>(path, {
        params: {
            origin,
            destination,
            date,
            seats
        }
    });
}
