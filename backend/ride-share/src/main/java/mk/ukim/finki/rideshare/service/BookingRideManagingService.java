package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;

import java.util.List;

public interface BookingRideManagingService {

    List<Booking> getAllByRideAndStatus(Ride ride, BookingStatus bookingStatus);

    List<Booking> getAllByRideAndStatusApproved(Ride ride);
}
