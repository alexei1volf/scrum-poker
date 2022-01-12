package com.example.scrumpoker.server.game;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class GameService {

    Flux<GameEvent> getGameEvents(String gameId) {
        return Flux
                .fromStream(Stream.generate(() -> new GameEvent(gameId + " : " + Instant.now())))
                .delayElements(Duration.ofSeconds(1));
    }
}
