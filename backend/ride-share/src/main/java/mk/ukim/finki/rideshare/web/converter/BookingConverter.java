package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.web.response.BookingResponse;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookingConverter {

    private final RideConverter rideConverter;

    public BookingResponse toResponse(Booking booking) {
        RideResponse rideResponse = rideConverter.toResponse(booking.getRide());

        return new BookingResponse(
                booking.getId(),
                booking.getStatus().getName(),
                booking.getStatus().getPrettyName(),
                booking.getBookedBy().getId(),
                booking.getBookedBy().getUsername(),
                rideResponse
        );
    }
}