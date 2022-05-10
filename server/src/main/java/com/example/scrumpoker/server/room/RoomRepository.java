package com.example.scrumpoker.server.room;

import com.example.scrumpoker.server.player.Player;
import com.example.scrumpoker.server.player.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.example.scrumpoker.server.player.PlayerEvent.Type.DISCONNECTED;
import static com.example.scrumpoker.server.room.RoomEvent.Type.*;
import static com.example.scrumpoker.server.utils.SinksSupport.RETRY_NON_SERIALIZED;

@Service
public class RoomRepository {

    final ConcurrentMap<String, Room> allRooms = new ConcurrentHashMap<>();
    final Sinks.Many<RoomEvent> roomUpdates = Sinks.many().multicast().directBestEffort();

    public RoomRepository(PlayerRepository playerRepository) {
        playerRepository.listAndListen().subscribe(pe -> {
            if (pe.getType() == DISCONNECTED) {
                remove(pe.getPlayer());
            }
        });

        Player alex = new Player(UUID.randomUUID().toString(), "Alex");
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId, alex);
        allRooms.put(roomId, room);
    }

    public Flux<RoomEvent> listAndListen() {
        return Flux.fromIterable(allRooms.values())
                .map(room -> RoomEvent.of(room, ADDED))
                .concatWith(roomUpdates.asFlux());
    }

    public void findAndJoin(String roomId, Player player) {

        var room = allRooms.get(roomId);

        if (room != null) {
            room.join(player);

            roomUpdates.emitNext(RoomEvent.of(room, UPDATED), RETRY_NON_SERIALIZED);
        }

        throw new IllegalStateException("Room " + roomId + " does not exist");

    }

    public void findAndLeave(String roomId, Player player) {
        var room = allRooms.get(roomId);

        if (room != null) {
            room.leave(player);
            roomUpdates
                    .emitNext(RoomEvent.of(allRooms.get(roomId), UPDATED), RETRY_NON_SERIALIZED);
        }

        throw new IllegalStateException("Room " + roomId + " does not exist");
    }

    public void findAndStart(String roomId, Player player) {
        var room = allRooms.remove(roomId);

        if (room != null) {
            room.start(player);
            roomUpdates.emitNext(RoomEvent.of(room, REMOVED), RETRY_NON_SERIALIZED);
            return;
        }

        throw new IllegalStateException("Room " + roomId + " does not exist");
    }

    public void add(Room room) {
        if (allRooms.put(room.id(), room) == null) {
            roomUpdates.emitNext(RoomEvent.of(room, ADDED), RETRY_NON_SERIALIZED);
        }
    }

    public void update(Room room) {
        if (allRooms.replace(room.id(), room) != null) {
            roomUpdates.emitNext(RoomEvent.of(room, UPDATED), RETRY_NON_SERIALIZED);
        }
    }

    public void remove(String roomId) {
        var room = allRooms.remove(roomId);

        if (room != null) {
            roomUpdates.emitNext(RoomEvent.of(room, REMOVED), RETRY_NON_SERIALIZED);
        }
    }

    void remove(Player player) {
        allRooms.values().forEach(room -> {
            if (room.owner().getId().equals(player.getId())) {
                allRooms.remove(room.id());
                roomUpdates.emitNext(RoomEvent.of(room, REMOVED), RETRY_NON_SERIALIZED);
            } else if (room.players().stream().anyMatch(p -> p.getId().equals(player.getId()))) {
                room.leave(player);
                roomUpdates.emitNext(RoomEvent.of(room, UPDATED), RETRY_NON_SERIALIZED);
            }
        });
    }
}
