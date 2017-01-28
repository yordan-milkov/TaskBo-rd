export class DataService
{
    private socket: WebSocket;

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
        this.socket.binaryType  = 'arraybuffer';
        this.socket.onopen = event =>
        {
            console.log('websocket connected')
            this.loginUser();
            // this.eventAggregator.subscribe(WebsocketClient.USERS_EVENT, users => {
            //     this.users = new Set<Object>(users);
            // });
            // this.eventAggregator.publish(WebsocketClient.CONNECTED_EVENT, this.socket);
            // resolve(this);
        };
        this.socket.onmessage   = event => this.handleMessage(event.data);
        this.socket.onerror     = error => console.log(error);
    }

    loginUser() {
        
        console.log('user loag: ');
        if ( this.socket.readyState === WebSocket.OPEN )
        {
            this.sendStringMassage('putki');
            this.sendStringMassage(JSON.stringify({"user": "test", "password": "test"}));
        }
    }

    private sendStringMassage(msg: string)
    {
        this.socket.binaryType;
        //var encodedObject = unescape(encodeURIComponent(JSON.stringify(myObject)));
        this.socket.send(msg+'\n');
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