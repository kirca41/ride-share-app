package mk.ukim.finki.rideshare.web.api;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.web.converter.RideConverter;
import mk.ukim.finki.rideshare.web.request.CreateRideRequest;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rides")
@AllArgsConstructor
public class RideController {

    private final RideService rideService;
    private final RideConverter rideConverter;

    @GetMapping
    public List<RideResponse> search(@RequestParam(required = false) String origin,
                                     @RequestParam(required = false) String destination,
                                     @RequestParam(required = false) LocalDate date,
                                     @RequestParam(required = false) Integer seats) {
        return rideService.search(origin, destination, date, seats).stream().map(rideConverter::toResponse).toList();
    }

    @PostMapping
    public RideResponse create(@RequestBody CreateRideRequest createRideRequest) {
        Ride ride = rideService.create(
                createRideRequest.origin(),
                createRideRequest.originLatitude(),
                createRideRequest.originLongitude(),
                createRideRequest.destination(),
                createRideRequest.destinationLatitude(),
                createRideRequest.destinationLongitude(),
                createRideRequest.isDoorToDoor(),
                createRideRequest.departureTime(),
                createRideRequest.isDepartureTimeFlexible(),
                createRideRequest.price(),
                createRideRequest.hasLuggageSpace(),
                createRideRequest.capacity(),
                createRideRequest.isInstantBookingEnabled()
        );

        return rideConverter.toResponse(ride);
    }

}
