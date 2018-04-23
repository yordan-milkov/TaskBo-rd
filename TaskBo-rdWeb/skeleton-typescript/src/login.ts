import {Aurelia} from 'aurelia-framework';
import { inject } from 'aurelia-dependency-injection';
import { ConnectionManager } from './ConnectionManager';

@inject(Aurelia, ConnectionManager)
export class Login
{
    private aurelia: Aurelia;
    private connection: ConnectionManager;

    username: string = "";
    password: string = "";
    newUser: string = "";

    constructor(aurelia: Aurelia, connectionManager: ConnectionManager)
    {
        this.aurelia = aurelia;
        this.connection = connectionManager;
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
                localStorage.setItem( 'key', this.password );
                this.aurelia.setRoot( 'app' );
            });
    }

    register()
    {
        if ( this.newUser )
        {
            this.connection.checkUser(this.newUser)
                .then((data) => {
                    if ( data.response === 'Register!' )
                    {
                        localStorage.setItem( 'UID', this.newUser );
                        this.aurelia.setRoot( 'register' );
                    }
                    else
                    {
                        console.log( 'name exists' )
                        console.log( data )
                    }
                    // let userData = JSON.parse(data.response);
                    // localStorage.setItem( 'mail', userData.mail );
                    // localStorage.setItem( 'name', userData.name );
                    // localStorage.setItem( 'GSM', userData.GSM );
                    // this.aurelia.setRoot('app');
                });
        }
        else{
            console.log( 'set name' )
        }
    }
}