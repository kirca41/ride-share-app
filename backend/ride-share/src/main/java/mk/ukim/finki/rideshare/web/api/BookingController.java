package mk.ukim.finki.rideshare.web.api;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.web.converter.BookingConverter;
import mk.ukim.finki.rideshare.web.response.BookingResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingConverter bookingConverter;

    @PostMapping("/ride/{rideId}")
    public BookingResponse create(@PathVariable Long rideId) {
        return bookingConverter.toResponse(
                bookingService.create(rideId)
        );
    }

    @PutMapping("/{id}/approve")
    public BookingResponse approve(@PathVariable Long id) {
        return bookingConverter.toResponse(
                bookingService.updateStatus(id, ApplicationConstants.BOOKING_STATUS_APPROVED)
        );
    }

    @PutMapping("/{id}/cancel")
    public BookingResponse cancel(@PathVariable Long id) {
        return bookingConverter.toResponse(
                bookingService.updateStatus(id, ApplicationConstants.BOOKING_STATUS_CANCELED)
        );
    }

    @PutMapping("/{id}/decline")
    public BookingResponse decline(@PathVariable Long id) {
        return bookingConverter.toResponse(
                bookingService.updateStatus(id, ApplicationConstants.BOOKING_STATUS_DECLINED)
        );
    }
}
