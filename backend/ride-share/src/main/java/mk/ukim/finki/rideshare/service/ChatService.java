package mk.ukim.finki.rideshare.service;

import mk.ukim.finki.rideshare.model.Chat;
import mk.ukim.finki.rideshare.model.User;

import java.util.UUID;

public interface ChatService {

    Chat getOrCreate(User participant1, User participant2);

    Chat getById(Long id);

    Chat getByUuid(UUID uuid);
}
