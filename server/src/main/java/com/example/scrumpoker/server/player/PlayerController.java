package com.example.scrumpoker.server.player;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@MessageMapping("player")
@RequiredArgsConstructor
@Log
public class PlayerController {

    private final PlayerRepository playerRepository;

    @MessageMapping("login")
    public String login(@Payload String username) {
        log.info("login for username: " + username);
        String id = UUID.randomUUID().toString();
        Player player = new Player(id, username);
        playerRepository.register(player);
        return id;
    }

}
