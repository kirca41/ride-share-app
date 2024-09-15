package mk.ukim.finki.rideshare.repository;

import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatOrderByTimestampDesc(Chat chat);
}
