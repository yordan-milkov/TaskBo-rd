import {Aurelia} from 'aurelia-framework';
import { inject } from 'aurelia-dependency-injection';
import { ConnectionManager } from './ConnectionManager';

@inject(Aurelia, ConnectionManager)
export class Login
{
    private aurelia: Aurelia;
    private connection: ConnectionManager;

    isLoggedIn: boolean;
    username: string = "";
    password: string = "";

    constructor(aurelia: Aurelia, connectionManager: ConnectionManager)
    {
        this.aurelia = aurelia;
        this.connection = connectionManager;
        this.isLoggedIn = false;
        localStorage.clear();
    }

    login()
    {
        this.connection
            .login(this.username, this.password)
            .then((data) => {
                let userData = JSON.parse(data.response);
                localStorage.setItem( 'UID', userData.UID );
                localStorage.setItem( 'mail', userData.mail );
                localStorage.setItem( 'name', userData.name );
                localStorage.setItem( 'GSM', userData.GSM );
                this.aurelia.setRoot('app');
            });
    }
}