package mk.ukim.finki.rideshare.web.request;

public record CreateMessageRequest(
        String content,
        Long chatId,
        Long senderId
) {
}
