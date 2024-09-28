package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Ride;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, Long>,
        JpaSpecificationExecutor<Ride> {

    Optional<Ride> findByUuid(UUID uuid);

    Long countByProviderAndIsCanceledIsTrueAndDepartureDateTimeBetween(User provider, ZonedDateTime from, ZonedDateTime to);
}
