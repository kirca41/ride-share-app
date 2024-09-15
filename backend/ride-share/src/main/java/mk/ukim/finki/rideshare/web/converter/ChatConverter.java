package mk.ukim.finki.rideshare.web.converter;

import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.web.response.ChatResponse;
import org.springframework.stereotype.Component;

@Component
public class ChatConverter {

    public ChatResponse toResponse(Chat chat) {
        return new ChatResponse(
                chat.getId(),
                chat.getUuid(),
                chat.getParticipant1().getId(),
                chat.getParticipant1().getFullName(),
                chat.getParticipant2().getId(),
                chat.getParticipant2().getFullName());
    }
}
