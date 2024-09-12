package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Boolean existsByStatusAndRideAndBookedBy(BookingStatus bookingStatus, Ride ride, User user);

    List<Booking> findAllByRideAndStatus(Ride ride, BookingStatus status);

    List<Booking> findAllByRideOrderByStatus(Ride ride);
}