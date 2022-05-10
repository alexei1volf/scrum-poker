import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {RoomService} from './room.service';
import {Room} from '../data/room';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  rooms: Observable<Room[]>;

  constructor(private roomService: RoomService) { }

  ngOnInit(): void {
    this.rooms = this.roomService.getRooms();
  }

}
