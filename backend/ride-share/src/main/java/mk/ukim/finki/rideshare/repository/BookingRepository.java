package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.ZonedDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>,
        JpaSpecificationExecutor<Booking> {

    Boolean existsByRideAndBookedBy(Ride ride, User user);

    Boolean existsByRideAndBookedByAndStatus(Ride ride, User user, BookingStatus status);

    List<Booking> findAllByRideAndStatus(Ride ride, BookingStatus status);

    List<Booking> findAllByRideOrderByStatus(Ride ride);

    List<Booking> findAllByStatusAndRide_DepartureDateTimeBefore(BookingStatus status, ZonedDateTime referenceDateTime);

    Long countByStatusAndBookedByAndBookedAtBetween(BookingStatus status, User bookedBy, ZonedDateTime from, ZonedDateTime to);
}