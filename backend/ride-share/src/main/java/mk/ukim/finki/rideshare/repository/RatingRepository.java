package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Rating;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByRideAndRatedBy(Ride ride, User ratedBy);
}
