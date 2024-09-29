package mk.ukim.finki.rideshare.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.Message;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.MessageRepository;
import mk.ukim.finki.rideshare.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> findAllByChat(Chat chat) {
        return messageRepository.findAllByChatOrderByTimestampDesc(chat);
    }

    @Override
    public Message create(String content, User sender, Chat chat) {
        return messageRepository.save(
                new Message(
                    ZonedDateTime.now(ZoneId.systemDefault()),
                    content,
                    chat,
                    sender
                )
        );
    }

    @Override
    public Message getLastMessageForChat(Chat chat) {
        return messageRepository.findFirstByChatOrderByTimestampDesc(chat)
                .orElse(null);
    }
}
