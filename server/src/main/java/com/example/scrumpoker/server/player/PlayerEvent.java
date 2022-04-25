package com.example.scrumpoker.server.player;

import lombok.Value;

@Value(staticConstructor = "of")
public class PlayerEvent {

    Player player;
    Type type;

    public enum Type {
        CONNECTED,
        DISCONNECTED
    }
}
