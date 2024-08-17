package mk.ukim.finki.rideshare.web.converter;

import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.web.response.BookingResponse;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getStatus().getName(),
                booking.getStatus().getPrettyName(),
                booking.getBookedBy().getId(),
                booking.getBookedBy().getUsername(),
                booking.getRide().getId()
        );
    }
}