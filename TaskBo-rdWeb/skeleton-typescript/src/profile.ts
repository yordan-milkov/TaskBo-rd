import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';

@inject(Router, ConnectionManager)
export class Task
{
    protected router: Router;
    protected connect: ConnectionManager;
    heading: string;
    user: any;
    constructor(router: Router, connect: ConnectionManager)
    {
        this.router = router;
        this.connect = connect;
        this.user   = new Object();
    }

    attached()
    {
        //console.log('task:attached');
    }

    activate(params: any)
    {
        this.user.name  = localStorage.getItem( 'name' );
        this.user.UID   = localStorage.getItem( 'UID' );
        this.user.mail  = localStorage.getItem( 'mail' );
        this.user.GSM   = localStorage.getItem( 'GSM' );
    }
}
