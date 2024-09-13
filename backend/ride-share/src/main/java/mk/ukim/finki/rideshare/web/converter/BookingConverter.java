package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.web.response.BookingResponse;
import mk.ukim.finki.rideshare.web.response.RideResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class BookingConverter {

    private final RideConverter rideConverter;

    public BookingResponse toResponse(Booking booking) {
        RideResponse rideResponse = rideConverter.toResponse(booking.getRide());

        return new BookingResponse(
                booking.getId(),
                booking.getBookedAt().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                booking.getBookedAt().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                booking.getStatus().getName(),
                booking.getStatus().getPrettyName(),
                booking.getBookedBy().getId(),
                booking.getBookedBy().getUsername(),
                rideResponse
        );
    }
}