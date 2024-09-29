package mk.ukim.finki.rideshare.web.converter;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.Message;
import mk.ukim.finki.rideshare.web.response.ChatResponse;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatConverter {

    private final MessageConverter messageConverter;

    public ChatResponse toResponse(Chat chat, Message lastMessage) {
        return new ChatResponse(
                chat.getId(),
                chat.getUuid(),
                chat.getParticipant1().getId(),
                chat.getParticipant1().getFullName(),
                chat.getParticipant2().getId(),
                chat.getParticipant2().getFullName(),
                messageConverter.toResponse(lastMessage));
    }

    public ChatResponse toResponse(Chat chat) {
        return new ChatResponse(
                chat.getId(),
                chat.getUuid(),
                chat.getParticipant1().getId(),
                chat.getParticipant1().getFullName(),
                chat.getParticipant2().getId(),
                chat.getParticipant2().getFullName(),
                null);
    }
}
