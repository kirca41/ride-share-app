package mk.ukim.finki.rideshare.repository.specification;

import mk.ukim.finki.rideshare.model.Ride;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RideSpecification {

    private static final String RIDE_ORIGIN_FIELD = "origin";
    private static final String RIDE_DESTINATION_FIELD = "destination";

    public static Specification<Ride> hasOriginLikeAndDestinationLike(String origin, String destination) {
        return SpecificationFactory.chainAndSpecifications(
                List.of(
                        SpecificationFactory.like(RIDE_ORIGIN_FIELD, origin),
                        SpecificationFactory.like(RIDE_DESTINATION_FIELD, destination)
                )
        );
    }
}
