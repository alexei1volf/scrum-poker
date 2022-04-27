import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {RoomService} from './room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  rooms: Observable<string>;

  constructor(private roomService: RoomService) { }

  ngOnInit(): void {
    this.rooms = this.roomService.listAndListen();
  }

}
