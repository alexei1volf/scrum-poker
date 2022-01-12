package com.example.scrumpoker.server.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final SubscribableChannel subscribableChannel;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
        this.subscribableChannel = MessageChannels.publishSubscribe().get();
    }

    @GetMapping(value = "/{gameId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<GameEvent> getGameEvents(@PathVariable String gameId) {
        return Flux.create(sink -> {
            MessageHandler handler = message -> sink.next(new GameEvent(gameId));
            sink.onCancel(() -> subscribableChannel.unsubscribe(handler));
            subscribableChannel.subscribe(handler);
        }, FluxSink.OverflowStrategy.LATEST);
    }


    @GetMapping(value = "/{gameId}/send")
    String publish(@PathVariable String gameId) {
        subscribableChannel.send(new GenericMessage<>(gameId));
        return "ok";
    }


}
