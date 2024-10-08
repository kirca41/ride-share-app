package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.service.BookingService;
import mk.ukim.finki.rideshare.service.UserService;
import mk.ukim.finki.rideshare.web.converter.BookingConverter;
import mk.ukim.finki.rideshare.web.request.CreateBookingRequest;
import mk.ukim.finki.rideshare.web.response.BookingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingConverter bookingConverter;
    private final UserService userService;

    @PostMapping
    public BookingResponse create(@RequestBody CreateBookingRequest createBookingRequest) {
        return bookingConverter.toResponse(
                bookingService.create(createBookingRequest.rideId(), createBookingRequest.seatsToBook())
        );
    }

    @PutMapping("/{id}/approve")
    public BookingResponse approve(@PathVariable Long id) {
        return bookingConverter.toResponse(bookingService.approve(id));
    }

    @PutMapping("/{id}/cancel")
    public BookingResponse cancel(@PathVariable Long id) {
        return bookingConverter.toResponse(bookingService.cancel(id));
    }

    @PutMapping("/{id}/decline")
    public BookingResponse decline(@PathVariable Long id) {
        return bookingConverter.toResponse(bookingService.decline(id));
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

    @GetMapping("/{bookedById}/booked-by-cancellations")
    public Long getNumberOfCancellationsByBookedByInTheLastMonth(@PathVariable Long bookedById) {
        User bookedBy = userService.getById(bookedById);

        return bookingService.getNumberOfCancellationsByBookedByInTheLastMonth(bookedBy);
    }
}
