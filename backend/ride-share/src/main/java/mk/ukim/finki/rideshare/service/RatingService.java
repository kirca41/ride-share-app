package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Rating;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    Optional<Rating> getByRideAndRatedBy(Ride ride, User ratedBy);

    Rating createOrUpdate(Long id, Double value, String comment, Ride ride, User ratedBy);

    List<Rating> getAllForProvider(User provider);

}
