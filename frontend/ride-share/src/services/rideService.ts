import axiosConfig from "../config/axiosConfig";
import { CreateRideRequest } from "../interfaces/request/CreateRideRequest";
import { RidePriceStatisticsResponse } from "../interfaces/response/RidePriceStatisticsResponse";
import { RideResponse } from "../interfaces/response/RideResponse";

const path = 'rides';

export const RideService = {
    search: async (origin?: string, destination?: string, date?: string, seats?: number, sortBy?: string, sortDirection?: string) => {
        return axiosConfig.get<RideResponse[]>(`${path}/search`, {
            params: {
                origin,
                destination,
                date,
                seats,
                sortBy,
                sortDirection
            }
        });
    },
    getAllForActiveUser: async (includePast: boolean) => {
        return axiosConfig.get<RideResponse[]>(path, {
            params: { includePast }
        });
    },
    getById: async (id: number) => {
        return axiosConfig.get<RideResponse>(`${path}/${id}`);
    },
    getByUuid: async(uuid: string) => {
        return axiosConfig.get<RideResponse>(`${path}/uuid/${uuid}`);
    },
    create: async (request: CreateRideRequest) => {
        return axiosConfig.post(path, request);
    },
    getPriceStatisticsForRide: async (origin: string, destination: string) => {
        return axiosConfig.get<RidePriceStatisticsResponse>(`${path}/recommended-price`, { params: { origin, destination }});
    }
}
