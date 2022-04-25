package com.example.scrumpoker.server.room;

import com.example.scrumpoker.server.player.Player;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RequiredArgsConstructor
public class Room {

    final String id;
    final Player owner;

    final CopyOnWriteArraySet<Player> players = new CopyOnWriteArraySet<>();

    public String id() {
        return id;
    }

    public Player owner() {
        return owner;
    }

    public Set<Player> players() {
        return players;
    }

    public void start(Player player) {
        if (!player.getId().equals(owner.getId())) {
            throw new IllegalStateException("Only owner can start the game");
        }

        //GameClient.create(players); // TODO: 4/25/2022 create game
    }

    public void join(Player player) {
        if (players.size() < 4) {
            if (players.add(player)) {
                return;
            }
            throw new IllegalStateException("Player already joined");
        }

        throw new IllegalStateException("The Room is full");
    }

    public void leave(Player player) {
        if (players.remove(player)) {
            return;
        }

        throw new IllegalStateException("Player already left");
    }
}
