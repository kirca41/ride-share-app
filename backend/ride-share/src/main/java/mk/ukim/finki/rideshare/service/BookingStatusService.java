package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.BookingStatus;

public interface BookingStatusService {

    BookingStatus findByName(String name);
}