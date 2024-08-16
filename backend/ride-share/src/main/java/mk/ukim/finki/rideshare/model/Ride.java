package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

import java.time.ZonedDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Ride extends BaseEntity {

    @Column(name = "origin_latitude")
    private Double originLatitude;

    @Column(name = "origin_longitude")
    private Double originLongitude;

    @Column(name = "destination_latitude")
    private Double destinationLatitude;

    @Column(name = "destination_longitude")
    private Double destinationLongitude;

    @Column(name = "is_door_to_door")
    private Boolean isDoorToDoor;

    @Column(name = "departure_time")
    private ZonedDateTime departureTime;

    @Column(name = "is_departure_time_flexible")
    private Boolean isDepartureTimeFlexible;

    @Column(name = "price")
    private Double price;

    @Column(name = "has_luggage_space")
    private Boolean hasLuggageSpace;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "is_instant_booking_enabled")
    private Boolean isInstantBookingEnabled;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;
}
