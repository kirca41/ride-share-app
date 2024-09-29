package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.Message;
import mk.ukim.finki.rideshare.model.User;

import java.util.List;

public interface MessageService {

    List<Message> findAllByChat(Chat chat);

    Message create(String content, User sender, Chat chat);

    Message getLastMessageForChat(Chat chat);
}
