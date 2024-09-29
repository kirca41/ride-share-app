package mk.ukim.finki.rideshare.web.response;

import java.util.UUID;

public record ChatResponse(
        Long id,
        UUID uuid,
        Long participant1Id,
        String participant1FullName,
        Long participant2Id,
        String participant2FullName,
        MessageResponse lastMessage
) {
}
