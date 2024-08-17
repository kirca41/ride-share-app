package mk.ukim.finki.rideshare.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.rideshare.model.BookingStatus;
import mk.ukim.finki.rideshare.repository.BookingStatusRepository;
import mk.ukim.finki.rideshare.service.BookingStatusService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingStatusServiceImpl implements BookingStatusService {

    private final BookingStatusRepository repository;
    @Override
    public BookingStatus findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }
}