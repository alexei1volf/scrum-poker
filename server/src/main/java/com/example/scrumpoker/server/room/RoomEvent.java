package com.example.scrumpoker.server.room;

import lombok.Value;

@Value(staticConstructor = "of")
public class RoomEvent {

    Room room;
    Type type;

    enum Type {
        ADDED,
        UPDATED,
        REMOVED
    }
}
