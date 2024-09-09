import axiosConfig from "../config/axiosConfig";
import { CreateRideRequest } from "../interfaces/request/CreateRideRequest";
import { RideResponse } from "../interfaces/response/RideResponse";

const path = 'rides';

const search = async (origin?: string, destination?: string, date?: string, seats?: number) => {
    return axiosConfig.get<RideResponse[]>(path, {
        params: {
            origin,
            destination,
            date,
            seats
        }
    });
}

const getById = async (id: number) => {
    return axiosConfig.get<RideResponse>(`${path}/${id}`);
}

const create = async (request: CreateRideRequest) => {
    return axiosConfig.post(path, request);
}

export { search, getById, create };
