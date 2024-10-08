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
@Table(name = "booking")
public class Booking extends BaseEntity {

    @Column(name = "seats_booked")
    private Integer seatsBooked;

    @Column(name = "booked_at")
    private ZonedDateTime bookedAt;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "booked_by")
    private User bookedBy;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
}
