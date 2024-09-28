import axios from "../config/axiosConfig";
import { CreateBookingRequest } from "../interfaces/request/CreateBookingRequest";
import { BookingResponse } from "../interfaces/response/BookingResponse";

const path = 'bookings';

export const BookingService = {
    createBooking: async (request: CreateBookingRequest) => {
        return axios.post<BookingResponse>(path, request);
    },
    getAllForRide: async (rideId: number) => {
        return axios.get<BookingResponse[]>(`${path}/rides/${rideId}`);
    },
    getAllForActiveUser: async (includePast: boolean) => {
        return axios.get<BookingResponse[]>(path, {
            params: { includePast }
        });
    },
    approve: async (id: number) => {
        return axios.put<BookingResponse>(`${path}/${id}/approve`);
    },
    decline: async (id: number) => {
        return axios.put<BookingResponse>(`${path}/${id}/decline`);
    },
    cancel: async (id: number) => {
        return axios.put<BookingResponse>(`${path}/${id}/cancel`);
    },
    getNumberOfCancellationsByBookedByInTheLastMonth: async (bookedById: number) => {
        return axios.get<number>(`${path}/${bookedById}/booked-by-cancellations`);
    }
}