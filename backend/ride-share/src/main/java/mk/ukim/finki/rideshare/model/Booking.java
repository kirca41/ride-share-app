package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking extends BaseEntity {

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
