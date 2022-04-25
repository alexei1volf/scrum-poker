package com.example.scrumpoker.server.player;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Player {
    final private String id;
    final private String name;
}
