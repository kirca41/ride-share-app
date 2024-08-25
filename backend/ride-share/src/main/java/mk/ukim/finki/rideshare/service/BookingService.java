package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;

import java.util.List;

public interface BookingService {

    Booking getById(Long bookingId);

    Booking create(Long rideId);

    Boolean existsWithStatusApprovedAndRideAndUser(Ride ride, User user);

    Booking updateStatus(Long bookingId, String newStatusName);

    List<Booking> getAllByRideAndStatus(Ride ride, BookingStatus bookingStatus);
}