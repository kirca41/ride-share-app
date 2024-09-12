import axiosConfig from "../config/axiosConfig";
import { CreateBookingRequest } from "../interfaces/request/CreateBookingRequest";
import { BookingResponse } from "../interfaces/response/BookingResponse";

const path = 'bookings';

const createBooking = async (request: CreateBookingRequest) => {
    return axiosConfig.post<BookingResponse>(path, request);
}

const getAllForRide = async (rideId: number) => {
    return axiosConfig.get<BookingResponse[]>(`${path}/rides/${rideId}`);
}

const approve = async (id: number) => {
    return axiosConfig.put<BookingResponse>(`${path}/${id}/approve`);
}

const decline = async (id: number) => {
    return axiosConfig.put<BookingResponse>(`${path}/${id}/decline`);
}

export { createBooking, getAllForRide, approve, decline };