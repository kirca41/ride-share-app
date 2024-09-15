package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.service.ChatService;
import mk.ukim.finki.rideshare.service.MessageService;
import mk.ukim.finki.rideshare.service.UserService;
import mk.ukim.finki.rideshare.web.converter.MessageConverter;
import mk.ukim.finki.rideshare.web.request.CreateMessageRequest;
import mk.ukim.finki.rideshare.web.response.MessageResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final UserService userService;
    private final MessageConverter messageConverter;

    @GetMapping("/chat/{chatUuid}")
    public List<MessageResponse> getAllByChatUuid(@PathVariable UUID chatUuid) {
        Chat chat = chatService.getByUuid(chatUuid);
        return messageService
                .findAllByChat(chat)
                .stream()
                .map(messageConverter::toResponse)
                .toList();
    }

    @MessageMapping("/chat/{otherParticipantId}")
    @SendTo("/topic/{otherParticipantId}")
    public MessageResponse create(@DestinationVariable String otherParticipantId, @Payload CreateMessageRequest request) {
        User sender = userService.getById(request.senderId());
        Chat chat = chatService.getById(request.chatId());

        return messageConverter.toResponse(messageService.create(request.content(), sender, chat));
    }
}
