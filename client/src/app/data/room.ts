import {Player} from './player';

export interface Room {
  id: string;
  owner: Player;
  players: Player[];
}
