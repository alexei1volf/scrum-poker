import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  BufferEncoders,
  encodeCompositeMetadata,
  encodeRoute,
  MESSAGE_RSOCKET_COMPOSITE_METADATA,
  MESSAGE_RSOCKET_ROUTING,
  RSocketClient
} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  client;

  constructor() {
  }

  public listAndListen(): Observable<string> {
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
          onComplete: () => console.log('login complete'),
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

    return new Observable();
  }

  private urlFromLocation(): string {
    const port = '9000';
    const isSecure = window.location.protocol === 'https:';
    const hostname = window.location.hostname;
    return `${isSecure ? 'wss' : 'ws'}://${hostname}:${port}/rsocket`;
  }

}
