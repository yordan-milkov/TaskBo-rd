import {Aurelia} from 'aurelia-framework';
import { inject } from 'aurelia-dependency-injection';
import { ConnectionManager } from './ConnectionManager';
import {ValidationControllerFactory, ValidationRules, ValidationController  } from 'aurelia-validation';

@inject(Aurelia, ConnectionManager, ValidationControllerFactory)
export class Login
{
    private aurelia: Aurelia;
    private connection: ConnectionManager;
    private controller: ValidationController;
    username: string = "";
    password: string = "";
    newUser: string = "";

    constructor(aurelia: Aurelia, connectionManager: ConnectionManager, validator: ValidationControllerFactory)
    {
        this.aurelia = aurelia;
        this.connection = connectionManager;
        this.controller =  validator.createForCurrentScope();
        // ValidationRules
        //     .ensure('newUser').required()
        //     .withMessage('New user name must be provided.')
        //     .on(this);
        localStorage.clear();
    }

    login()
    {
        this.controller.reset();
        this.connection
            .login(this.username, this.password)
            .then((data) => {
                console.log(data)
                if ( data.response === 'password' )
                {
                    this.controller.addError('Wrong password.', 'password')
                }
                else if ( data.response === 'user' ) {
                    this.controller.addError('User does not exists.', 'username')
                }
                else
                {
                    let userData = JSON.parse(data.response);
                    localStorage.setItem( 'UID', userData.UID );
                    localStorage.setItem( 'mail', userData.mail );
                    localStorage.setItem( 'name', userData.name );
                    localStorage.setItem( 'GSM', userData.GSM );
                    localStorage.setItem( 'key', this.password );
                    this.aurelia.setRoot( 'app' );
                }
            });
            console.log(this.controller.errors)
    }

    register()
    {
        this.controller.reset();
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
                        this.controller.addError('Username already exists', 'newUser')
                    }
                });
        }
        else{
            this.controller.addError('New user name must be provided.', 'newUser')
        }
    }
}