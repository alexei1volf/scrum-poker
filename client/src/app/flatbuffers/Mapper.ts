import {RoomEvent} from './generated/room-event';
import {EventType} from './generated/event-type';
import {ByteBuffer} from 'flatbuffers/js/byte-buffer';

export class Mapper {

  public static extractRoom(roomEventBuffer): {eventType, room} {
    const dataBuf = ByteBuffer.allocate(roomEventBuffer.data);
    const event = RoomEvent.getRootAsRoomEvent(dataBuf);
    const eventType = event.type() === EventType.Added ? 'Added' : event.type() === EventType.Updated ? 'Updated' : 'Removed';
    const roomId = event.id();
    const players = [...new Array(event.playersLength()).keys()]
      .map(i => {
        const player = event.players(i);
        return {
          id: player.id(),
          name: player.name(),
        };
      });
    const playerOwner = event.owner();
    const owner = {
      id: playerOwner.id(),
      name: playerOwner.name(),
    };
    const room = {
      id: roomId,
      owner: owner,
      players: players
    };
    return {eventType, room};
  }

}
