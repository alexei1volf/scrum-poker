package com.example.scrumpoker.server.room;

import com.example.scrumpoker.data.Player;
import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;

public class MessageMapper {

    static ByteBuffer mapToRoomEventBuffer(com.example.scrumpoker.server.room.RoomEvent roomEvent) {
        final FlatBufferBuilder builder = new FlatBufferBuilder();
        com.example.scrumpoker.data.RoomEvent.finishRoomEventBuffer(builder,
                com.example.scrumpoker.data.RoomEvent.createRoomEvent(
                        builder,
                        (byte) roomEvent.getType().ordinal(),
                        builder.createString(roomEvent.getRoom().id()),
                        Player.createPlayer(
                                builder,
                                builder.createString(roomEvent.getRoom().owner().getId()),
                                builder.createString(roomEvent.getRoom().owner().getName())
                        ),
                        com.example.scrumpoker.data.RoomEvent.createPlayersVector(
                                builder,
                                roomEvent.getRoom()
                                        .players()
                                        .stream()
                                        .mapToInt(p -> Player.createPlayer(
                                                builder,
                                                builder.createString(p.getId()),
                                                builder.createString(p.getName())
                                        ))
                                        .toArray()
                        )
                )
        );
        return builder.dataBuffer().position(builder.dataBuffer().capacity() - builder.offset());
    }
}
