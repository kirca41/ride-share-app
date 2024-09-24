package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;

import java.time.ZonedDateTime;
import java.util.List;

public interface BookingService {

    Booking getById(Long bookingId);

    Booking create(Long rideId, Integer seatsToBook);

    Boolean existsByRideAndBookedBy(Ride ride, User bookedBy);

    Boolean existsByRideAndBookedByAndStatus(Ride ride, User bookedBy, BookingStatus bookingStatus);

    Booking approve(Long bookingId);

    Booking cancel(Long bookingId);

    Booking decline(Long bookingId);

    Booking updateStatus(Booking booking, String newStatusName);

    List<Booking> getAllForRide(Long rideId);

    List<Booking> getAllForActiveUser(Boolean includePast);

    List<Booking> getAllByStatusAndRideDepartureTimeBefore(BookingStatus status, ZonedDateTime referenceDateTime);
}