package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "booking_request")
public class BookingRequest extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "status_id")
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
}
