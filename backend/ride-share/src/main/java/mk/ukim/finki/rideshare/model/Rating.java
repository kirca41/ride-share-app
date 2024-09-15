package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rating")
public class Rating extends BaseEntity {

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "rated_by")
    private User ratedBy;
}
