//import { io } from 'socket.io';
// var socket = io.connect( 'http://localhost:3000' );

export class DataService
{
    private socket: WebSocket;
    private connected: boolean;

    constructor() { }

    activate()
    {
        // socket.on( 'users',   // <- only works once (when loading the page) but doesn't listen after
        //     function ( userlist ) {
        //         this.users = userlist;
        //     }.bind( this ) );
    }

    connect(): void
    {
        this.socket = new WebSocket("ws://localhost:4444");
        this.socket.onopen = event =>
        {
            this.connected = true;
            console.log('websocket connected')
            this.loginUser();
            // this.eventAggregator.subscribe(WebsocketClient.USERS_EVENT, users => {
            //     this.users = new Set<Object>(users);
            // });
            // this.eventAggregator.publish(WebsocketClient.CONNECTED_EVENT, this.socket);
            // resolve(this);
        };
        this.socket.onclose = event =>
        {
            console.log('ebi sa!');
            this.connected = false;
        };
        this.socket.onmessage = event =>
        {
            console.log('sybstenie');
            this.handleMessage(event.data);
        };
        this.socket.onerror = error => 
        {
            console.log('mamata');
            console.log(error);
        };
    }

    loginUser() {
        
        console.log('user loag: ');
        if ( this.connected )
        {
            this.socket.send({"user": "test", "password": "test"});
        }
    }

    private handleMessage(data: any): void
    {
        console.log('websocket message: ');
        console.log(data);
    }

    addstate()
    {
        // socket.emit('add state', this.newstate); // <- works flawless
        // this.newstate = '';
    }
}