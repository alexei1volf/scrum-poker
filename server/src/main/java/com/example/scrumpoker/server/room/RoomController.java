package com.example.scrumpoker.server.room;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;

@Controller
@MessageMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepository roomRepository;

    @MessageMapping("")
    public Flux<ByteBuffer> listAndListen() {
        return roomRepository.listAndListen()
                .map(MessageMapper::mapToRoomEventBuffer);
    }

}
