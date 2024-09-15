package mk.ukim.finki.rideshare.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.repository.ChatRepository;
import mk.ukim.finki.rideshare.service.ChatService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat getOrCreate(User participant1, User participant2) {
        return chatRepository.findByParticipants(participant1, participant2)
                .orElseGet(() -> chatRepository.save(new Chat(UUID.randomUUID(), participant1, participant2)));
    }

    @Override
    public Chat getById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new RideShareServerException("Chat with id %d not found".formatted(id)));
    }

    @Override
    public Chat getByUuid(UUID uuid) {
        return chatRepository.findByUuid(uuid)
                .orElseThrow(() -> new RideShareServerException("Chat with uuid %s not found".formatted(uuid)));
    }
}
