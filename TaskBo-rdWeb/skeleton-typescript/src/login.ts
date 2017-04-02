import {Aurelia} from 'aurelia-framework';
import { inject } from 'aurelia-dependency-injection';
import { ConnectionManager } from './ConnectionManager';

@inject(Aurelia, ConnectionManager)
export class Login
{
    private aurelia: Aurelia;
    private connection: ConnectionManager;

    isLoggedIn: boolean;
    username: string = "test";
    password: string = "test";

    constructor(aurelia: Aurelia, connectionManager: ConnectionManager)
    {
        this.aurelia = aurelia;
        this.connection = connectionManager;
        this.isLoggedIn = false;
    }

    login()
    {
        this.connection
            .login(this.username, this.password)
            .then((data) => {
                let userData = JSON.parse(data.response);
                localStorage.setItem( 'UID', userData.UID );
                this.aurelia.setRoot('app');
            });
    }
}