package mk.ukim.finki.rideshare.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "booking_status")
public class BookingStatus extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "pretty_name")
    private String prettyName;
}
