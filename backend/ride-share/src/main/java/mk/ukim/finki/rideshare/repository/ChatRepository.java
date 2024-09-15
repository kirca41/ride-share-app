package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("""
    select c from Chat c
    where c.participant1 = :participant1 and c.participant2 = :participant2
    or c.participant1 = :participant2 and c.participant2 = :participant1
   """)
    Optional<Chat> findByParticipants(User participant1, User participant2);

    Optional<Chat> findByUuid(UUID uuid);
}
