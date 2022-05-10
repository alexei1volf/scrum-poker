import {Room} from './room';

export interface RoomEvent {
  room: Room;
  type: Type;
}

enum Type {
  ADDED,
  UPDATED,
  REMOVED
}
