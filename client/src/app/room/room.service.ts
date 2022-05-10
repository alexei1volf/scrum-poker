import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {
  BufferEncoders,
  encodeCompositeMetadata,
  encodeRoute,
  MESSAGE_RSOCKET_COMPOSITE_METADATA,
  MESSAGE_RSOCKET_ROUTING,
  RSocketClient
} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';
import {Mapper} from '../flatbuffers/Mapper';
import {Room} from '../data/room';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  public rooms$: BehaviorSubject<Room[]> = new BehaviorSubject([]);

  private client;
  private rooms: Room[] = [];

  constructor() {
  }

  public getRooms(): Observable<Room[]> {
    this.listAndListen();
    return this.rooms$;
  }

  private listAndListen(): void {
    this.client = new RSocketClient({
      setup: {
        keepAlive: 30000,
        lifetime: 90000,
        dataMimeType: 'text/plain',
        metadataMimeType: MESSAGE_RSOCKET_COMPOSITE_METADATA.string,
      },
      transport: new RSocketWebSocketClient({
          url: this.urlFromLocation()
        },
        BufferEncoders
      )
    });

    this.client.connect().subscribe({
      onComplete: (rsocket) => {
        rsocket.requestResponse({
          metadata: encodeCompositeMetadata([
            [MESSAGE_RSOCKET_ROUTING, encodeRoute('player.login')]
          ]),
          data: Buffer.from('username')
        }).subscribe({
          onComplete: () => {
            rsocket.requestStream({
              metadata: encodeCompositeMetadata([
                [MESSAGE_RSOCKET_ROUTING, encodeRoute('room')],
              ]),
            }).subscribe({
              onSubscribe: (s) => {
                s.request(2147483642);
              },
              onNext: eventBuf => {
                console.log('room event is handled');
                this.handleRoomEvent(Mapper.extractRoom(eventBuf));
              },
              onError: error => {
                console.log('rooms list and listen error:: ' + error);
              },
              onComplete: () => {
                console.log('rooms list and listen is completed');
              }
            });
          },
          onError: error => {
            console.log('Connection has been closed due to:: ' + error);
          }
        });
      },
      onError: error => {
        console.log('Connection has been refused due to:: ' + error);
      },
      onSubscribe: cancel => {
        /* call cancel() to abort */
      }
    });
  }

  private urlFromLocation(): string {
    const port = '9000';
    const isSecure = window.location.protocol === 'https:';
    const hostname = window.location.hostname;
    return `${isSecure ? 'wss' : 'ws'}://${hostname}:${port}/rsocket`;
  }

  private handleRoomEvent({eventType, room}): void {
    if (eventType === 'Added') {
      this.rooms = [room, ...this.rooms];
    } else if (eventType === 'Updated') {
      this.rooms = [room, ...this.rooms.filter(r => r.id !== room.id)];
    } else {
      this.rooms = this.rooms.filter(r => r.id !== room.id);
    }
    this.rooms$.next(this.rooms);
  }

}
