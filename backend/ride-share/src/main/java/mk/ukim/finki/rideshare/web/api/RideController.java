package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.service.RideService;
import mk.ukim.finki.rideshare.web.converter.RideConverter;
import mk.ukim.finki.rideshare.web.request.CreateRideRequest;
import mk.ukim.finki.rideshare.web.response.RidePriceStatisticsResponse;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;
    private final RideConverter rideConverter;

    @GetMapping("/search")
    public List<RideResponse> search(@RequestParam(required = false) String origin,
                                     @RequestParam(required = false) String destination,
                                     @RequestParam(required = false) LocalDate date,
                                     @RequestParam(required = false) Integer seats,
                                     @RequestParam(value = "sortBy", required = false, defaultValue = "departureDateTime") String sortBy,
                                     @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        return rideService.search(origin, destination, date, seats, sort).stream().map(rideConverter::toResponse).toList();
    }

    @GetMapping
    public List<RideResponse> getAllForActiveUser(@RequestParam(required = false, defaultValue = "false") Boolean includePast) {
        return rideService.getAllForActiveUser(includePast)
                .stream().map(rideConverter::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public RideResponse getById(@PathVariable Long id) {
        return rideConverter.toResponse(rideService.getById(id));
    }

    @GetMapping("/uuid/{uuid}")
    public RideResponse getByUuid(@PathVariable UUID uuid) {
        return rideConverter.toResponse(rideService.getByUuid(uuid));
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
                createRideRequest.departureTime().atZone(ZoneId.systemDefault()),
                createRideRequest.isDepartureTimeFlexible(),
                createRideRequest.price(),
                createRideRequest.hasLuggageSpace(),
                createRideRequest.capacity(),
                createRideRequest.isInstantBookingEnabled()
        );

        return rideConverter.toResponse(ride);
    }

    @GetMapping("/recommended-price")
    public RidePriceStatisticsResponse getPriceStatisticsForRide(@RequestParam(defaultValue = "") String origin,
                                                                 @RequestParam(defaultValue = "") String destination) {
        List<Ride> rides = rideService.getAllByOriginLikeAndDestinationLike(origin, destination);
        DoubleSummaryStatistics stats = rides.stream().mapToDouble(Ride::getPrice).summaryStatistics();

        return new RidePriceStatisticsResponse(
                stats.getCount() > 0 ? stats.getMin() : null,
                stats.getCount() > 0 ? stats.getMax() : null,
                stats.getCount() > 0 ? stats.getAverage() : null
        );
    }

    @PutMapping("/{id}/cancel")
    public RideResponse cancel(@PathVariable Long id) {
        return rideConverter.toResponse(rideService.cancel(id));
    }

}
