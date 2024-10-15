package mk.ukim.finki.rideshare.repository.specification;

import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class RideSpecification {

    private static final String RIDE_ORIGIN_FIELD = "origin";
    private static final String RIDE_DESTINATION_FIELD = "destination";
    private static final String RIDE_DEPARTURE_DATE_TIME = "departureDateTime";
    private static final String RIDE_PROVIDER = "provider";

    private static final String RIDE_IS_CANCELED = "isCanceled";

    public static Specification<Ride> hasOriginLikeAndDestinationLikeAndDepartureDateTimeAndNotIsCancelled(String origin, String destination, LocalDate departureDateTime) {
        return SpecificationFactory.chainAndSpecifications(
                List.of(
                        SpecificationFactory.like(RIDE_ORIGIN_FIELD, origin),
                        SpecificationFactory.like(RIDE_DESTINATION_FIELD, destination),
                        SpecificationFactory.dateEquals(RIDE_DEPARTURE_DATE_TIME, departureDateTime, ZoneId.systemDefault()),
                        SpecificationFactory.equalsValue(RIDE_IS_CANCELED, false)
                )
        );
    }

    public static Specification<Ride> hasOriginLikeAndDestinationLike(String origin, String destination) {
        return SpecificationFactory.chainAndSpecifications(
                List.of(
                        SpecificationFactory.like(RIDE_ORIGIN_FIELD, origin),
                        SpecificationFactory.like(RIDE_DESTINATION_FIELD, destination)
                )
        );
    }

    public static Specification<Ride> providerEqualsAndDepartureTimeGreaterThan(User provider, LocalDate date) {
        return SpecificationFactory.chainAndSpecifications(
                List.of(
                        SpecificationFactory.equalsValue(RIDE_PROVIDER, provider),
                        SpecificationFactory.dateGreaterThan(RIDE_DEPARTURE_DATE_TIME, date, ZoneId.systemDefault())
                )
        );
    }
}
