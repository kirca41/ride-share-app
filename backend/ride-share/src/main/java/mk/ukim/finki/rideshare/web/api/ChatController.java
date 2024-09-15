package mk.ukim.finki.rideshare.web.api;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.rideshare.model.User;
import mk.ukim.finki.rideshare.service.ChatService;
import mk.ukim.finki.rideshare.service.UserService;
import mk.ukim.finki.rideshare.service.exception.RideShareServerException;
import mk.ukim.finki.rideshare.service.helper.AuthHelperService;
import mk.ukim.finki.rideshare.web.converter.ChatConverter;
import mk.ukim.finki.rideshare.web.response.ChatResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final AuthHelperService authHelperService;
    private final ChatConverter chatConverter;

    @GetMapping("/{uuid}")
    public ChatResponse getByUuid(@PathVariable UUID uuid) {
        return chatConverter.toResponse(chatService.getByUuid(uuid));
    }

    @PutMapping("/{otherParticipantId}")
    public ChatResponse getOrCreate(@PathVariable Long otherParticipantId) {
        User activeUserParticipant = authHelperService
                .getActiveUser()
                .orElseThrow(() -> new RideShareServerException("No active user"));
        User otherParticipant = userService.getById(otherParticipantId);

        return chatConverter.toResponse(chatService.getOrCreate(activeUserParticipant, otherParticipant));
    }
}
