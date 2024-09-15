package mk.ukim.finki.rideshare.web.response;

public record MessageResponse(
        Long id,
        String content,
        String dateSent,
        String timeSent,
        Long senderId,
        String senderFullName
) {
}
