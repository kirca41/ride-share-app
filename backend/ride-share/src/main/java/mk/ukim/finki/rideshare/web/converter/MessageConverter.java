package mk.ukim.finki.rideshare.web.converter;

import mk.ukim.finki.rideshare.model.Message;
import mk.ukim.finki.rideshare.web.response.MessageResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class MessageConverter {

    public MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getTimestamp().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                message.getTimestamp().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                message.getSender().getId(),
                message.getSender().getFullName());
    }
}
