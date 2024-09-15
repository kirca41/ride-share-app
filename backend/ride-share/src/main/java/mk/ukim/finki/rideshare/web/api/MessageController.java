package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.service.ChatService;
import mk.ukim.finki.rideshare.service.MessageService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import mk.ukim.finki.rideshare.web.converter.MessageConverter;
import mk.ukim.finki.rideshare.web.request.CreateMessageRequest;
import mk.ukim.finki.rideshare.web.response.MessageResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final AuthHelperService authHelperService;
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

    @PostMapping
    public MessageResponse create(@RequestBody CreateMessageRequest request) {
        User sender = authHelperService.getActiveUser()
                .orElseThrow(() -> new RideShareServerException("No active user found"));
        Chat chat = chatService.getById(request.chatId());

        return messageConverter.toResponse(messageService.create(request.content(), sender, chat));
    }
}
