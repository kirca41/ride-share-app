package mk.ukim.finki.rideshare.repository.specification;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class BookingSpecification {

    private static final String BOOKING_BOOKED_BY_FIELD = "bookedBy";
    private static final String BOOKING_BOOKED_AT_FIELD = "bookedAt";

    public static Specification<Booking> bookedByEqualsAndBookedAtGreaterThan(User bookedBy, LocalDate date) {
        return SpecificationFactory.chainAndSpecifications(
                List.of(
                        SpecificationFactory.equalsValue(BOOKING_BOOKED_BY_FIELD, bookedBy),
                        SpecificationFactory.dateGreaterThan(BOOKING_BOOKED_AT_FIELD, date, ZoneId.systemDefault())
                )
        );
    }
}
