package mk.ukim.finki.rideshare.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Rating;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.RatingRepository;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import mk.ukim.finki.rideshare.service.RatingService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final BookingService bookingService;
    private final BookingStatusService bookingStatusService;

    @Override
    public Optional<Rating> getByRideAndRatedBy(Ride ride, User ratedBy) {
        return ratingRepository.findByRideAndRatedBy(ride, ratedBy);
    }

    @Override
    public Rating createOrUpdate(Long id, Double value, String comment, Ride ride, User ratedBy) {
        if (!bookingService.existsByRideAndBookedByAndStatus(
                ride, ratedBy,
                bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED)))
            throw new RideShareServerException("You must have a booking for this ride to leave a rating");

        if (id != null) {
            return update(id, value, comment);
        } else {
            return create(value, comment, ride, ratedBy);
        }
    }

    private Rating create(Double value, String comment, Ride ride, User ratedBy) {
        return ratingRepository.save(new Rating(value, comment, ride, ratedBy));
    }

    private Rating update(Long id, Double value, String comment) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RideShareServerException("Rating with id %d not found".formatted(id)));
        rating.setValue(value);
        rating.setComment(comment);
        return ratingRepository.save(rating);
    }
}
