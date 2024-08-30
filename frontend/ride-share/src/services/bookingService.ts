import axiosConfig from "../config/axiosConfig";
import { CreateBookingRequest } from "../interfaces/request/CreateBookingRequest";
import { BookingResponse } from "../interfaces/response/BookingResponse";

const path = 'bookings';

const createBooking = (request: CreateBookingRequest) => {
    return axiosConfig.post<BookingResponse>(path, request);
}

export { createBooking };