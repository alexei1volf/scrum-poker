package com.example.scrumpoker.server.player;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.example.scrumpoker.server.player.PlayerEvent.Type.CONNECTED;
import static com.example.scrumpoker.server.player.PlayerEvent.Type.DISCONNECTED;
import static com.example.scrumpoker.server.utils.SinksSupport.RETRY_NON_SERIALIZED;

@Service
public class PlayerRepository {

    final ConcurrentMap<String, Player> allPlayers = new ConcurrentHashMap<>();
    final Sinks.Many<PlayerEvent> playersUpdates = Sinks.many().multicast().directBestEffort();

    public Flux<PlayerEvent> listAndListen() {
        return Flux.fromIterable(allPlayers.values())
                .map(p -> PlayerEvent.of(p, CONNECTED))
                .concatWith(playersUpdates.asFlux());
    }

    public void register(Player player) {
        if (allPlayers.put(player.getId(), player) == null) {
            playersUpdates.emitNext(PlayerEvent.of(player, CONNECTED), RETRY_NON_SERIALIZED);
        }
    }

    public void disconnect(String id) {
        final Player player = allPlayers.remove(id);
        if (player != null) {
            playersUpdates.emitNext(PlayerEvent.of(player, DISCONNECTED), RETRY_NON_SERIALIZED);
        }
    }

    public Player find(String id) {
        return allPlayers.get(id);
    }
}
