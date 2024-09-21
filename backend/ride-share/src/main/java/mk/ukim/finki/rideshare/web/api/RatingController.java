package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.service.RatingService;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import mk.ukim.finki.rideshare.web.converter.RatingConverter;
import mk.ukim.finki.rideshare.web.request.CreateRatingRequest;
import mk.ukim.finki.rideshare.web.response.RatingResponse;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final RatingConverter ratingConverter;
    private final RideService rideService;
    private final AuthHelperService authHelperService;

    @GetMapping("/ride/{rideId}")
    public RatingResponse getByRideId(@PathVariable Long rideId) {
        Ride ride = rideService.getById(rideId);
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RideShareServerException("No active user found"));

        return ratingService.getByRideAndRatedBy(ride, activeUser)
                .map(ratingConverter::toResponse)
                .orElse(null);
    }

    @PostMapping
    public RatingResponse createOrUpdate(@RequestBody CreateRatingRequest request) {
        Ride ride = rideService.getById(request.rideId());
        User activeUser = authHelperService.getActiveUser()
                .orElseThrow(() -> new RideShareServerException("You have to be logged in to leave a rating"));

        return ratingConverter.toResponse(
                ratingService.createOrUpdate(request.id(), request.value(), request.comment(), ride, activeUser));
    }
}
