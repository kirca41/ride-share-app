package mk.ukim.finki.rideshare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mk.ukim.finki.rideshare.model.base.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "rating")
public class Rating extends BaseEntity {

    @Column(name = "value")
    private Double value;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "rated_by")
    private User ratedBy;
}
