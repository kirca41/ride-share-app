package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "booking_request_status")
public class BookingRequestStatus extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "pretty_name")
    private String prettyName;
}
