package mk.ukim.finki.rideshare.web.api;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.web.converter.BookingConverter;
import mk.ukim.finki.rideshare.web.request.CreateBookingRequest;
import mk.ukim.finki.rideshare.web.response.BookingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingConverter bookingConverter;

    @PostMapping
    public BookingResponse create(@RequestBody CreateBookingRequest createBookingRequest) {
        return bookingConverter.toResponse(
                bookingService.create(createBookingRequest.rideId(), createBookingRequest.seatsToBook())
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

    @GetMapping("/rides/{rideId}")
    public List<BookingResponse> getAllForRide(@PathVariable Long rideId) {
        return bookingService.getAllForRide(rideId)
                .stream().map(bookingConverter::toResponse)
                .toList();
    }

    @GetMapping
    public List<BookingResponse> getAllForActiveUser(@RequestParam(required = false, defaultValue = "false") Boolean includePast) {
        return bookingService.getAllForActiveUser(includePast)
                .stream()
                .sorted(Comparator.comparing(Booking::getBookedAt))
                .map(bookingConverter::toResponse)
                .toList();
    }
}
