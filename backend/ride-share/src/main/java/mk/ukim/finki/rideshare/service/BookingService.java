package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;

public interface BookingService {

    Booking getById(Long bookingId);

    Booking create(Long rideId, Integer seatsToBook);

    Boolean existsWithStatusApprovedAndRideAndUser(Ride ride, User user);

    Booking updateStatus(Long bookingId, String newStatusName);
}