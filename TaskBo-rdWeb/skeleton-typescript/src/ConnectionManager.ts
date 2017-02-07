import { HttpClient } from 'aurelia-http-client';

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
        this.socket.binaryType = 'arraybuffer';
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
        this.socket.onmessage = event => this.handleMessage(event.data);
        this.socket.onerror = error => console.log(error);
    }

    loginUser()
    {

        console.log('user loag: ');
        if (this.socket.readyState === WebSocket.OPEN) {
            this.sendStringMassage('putki');
            this.sendStringMassage(JSON.stringify({ "user": "test", "password": "test" }));
        }
    }

    private sendStringMassage(msg: string)
    {
        this.socket.binaryType;
        //var encodedObject = unescape(encodeURIComponent(JSON.stringify(myObject)));
        this.socket.send(msg + '\n');
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

export class ConnectionManager
{
    private httpClient: any;

    constructor()
    {
        this.httpClient = new HttpClient()
            .configure((x: any) =>
            {
                x.withBaseUrl('http://192.168.0.201/TaskBo-rdWebServer/php/')
                x.withHeader("Content-type", " application/x-www-form-urlencoded");
            });
    }

    login(uid: string, password: string): Promise<any>
    {
        return this.httpClient.post('login.php', `UID=${uid}&password=${password}`);
    }

    getGroupsByUID(UserID: string): any
    {
       return this.httpClient.createRequest('user-groups.php')
            .asGet()
            .withParams({ 'UID': UserID })
            .send();
    }

    getTasksByGroup(groupID: string) : any
    {
        return this.httpClient.createRequest('group-data.php')
            .asGet()
            .withParams({ 'groupUID': groupID })
            .send();
    }

    getUsersByGroup(groupID: string) : any
    {
        return this.httpClient.createRequest('group-users.php')//todo
            .asGet()
            .withParams({ 'groupUID': groupID })
            .send();
    }

    getTaskData(taskID: string) : any
    {
        return this.httpClient.createRequest('issue-data.php')
            .asGet()
            .withParams({ 'issueUID': taskID })
            .send();
    }
    
    getTaskChecks(taskID: string) : any
    {
        return this.httpClient.createRequest('issue-checks.php') //to do
            .asGet()
            .withParams({ 'issueUID': taskID })
            .send();
    }

    updateCheckState(isChecked: boolean, checkUID: string)
    {
        return this.httpClient.post('issue-checks-finish.php', `isFinished=${isChecked?1:0}&checkUID=${checkUID}`);
    }
}