package mk.ukim.finki.rideshare.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.config.ApplicationConstants;
import mk.ukim.finki.rideshare.model.Booking;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.repository.BookingRepository;
import mk.ukim.finki.rideshare.service.BookingRideManagingService;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingRideManagingServiceImpl implements BookingRideManagingService {

    private final BookingRepository bookingRepository;
    private final BookingStatusService bookingStatusService;

    @Override
    public List<Booking> getAllByRideAndStatus(Ride ride, BookingStatus bookingStatus) {
        return bookingRepository.findAllByRideAndStatus(ride, bookingStatus);
    }

    @Override
    public List<Booking> getAllByRideAndStatusApproved(Ride ride) {
        return getAllByRideAndStatus(ride, bookingStatusService.findByName(ApplicationConstants.BOOKING_STATUS_APPROVED));
    }
}
