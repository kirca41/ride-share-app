package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
