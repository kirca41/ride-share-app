package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Rating;
import mk.ukim.finki.rideshare.web.response.RatingResponse;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RatingConverter {

    private final RideConverter rideConverter;
    private final UserConverter userConverter;

    public RatingResponse toResponse(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getValue(),
                rating.getComment(),
                rideConverter.toResponse(rating.getRide()),
                userConverter.toResponse(rating.getRatedBy()));
    }
}
