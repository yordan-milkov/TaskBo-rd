import { inject } from 'aurelia-dependency-injection';
import { ConnectionManager } from './ConnectionManager';
import {Aurelia} from 'aurelia-framework';
import {ValidationControllerFactory, ValidationRules, ValidationController  } from 'aurelia-validation';

@inject(Aurelia, ConnectionManager, ValidationControllerFactory)
export class Register
{
    private aurelia: Aurelia;
    protected connect: ConnectionManager;
    protected controller: ValidationController;
    heading: string;
    user: any;
    constructor(aurelia: Aurelia, connect: ConnectionManager, controll: ValidationControllerFactory)
    {
        this.connect = connect;
        this.aurelia = aurelia
        this.controller =  controll.createForCurrentScope();
        this.user   = new Object();
        this.user.newPass      = new String()
        this.user.repeatPass   = new String()
        this.user.UID          = new String()
    }

    attached()
    {
        //console.log('task:attached');
    }

    activate(params: any)
    {
        this.user.UID   = localStorage.getItem( 'UID' );
    }

    updateData()
    {
        this.connect.updateProfileData( this.user )
        localStorage.setItem( 'mail', this.user.mail );
        localStorage.setItem( 'name', this.user.name );
        localStorage.setItem( 'GSM', this.user.GSM );
    }

    saveProfile()
    {
        this.controller.reset()
        if ( String(this.user.newPass) === String(this.user.repeatPass) && String(this.user.newPass) )
        {
            this.connect.createProfile( this.user )
            .then(
                (data: any) =>
                {
                    if ( data.response === 'done' )
                    {
                        this.updateData();
                        this.aurelia.setRoot('app');
                    }
                    else
                    {
                        console.log( data )
                    }
                }
            );
        }
        else
        {
            this.controller.addError('New password doen\'t match.', 'password')
            console.log( 'new password doen\'t match' )
        }
    }

    endEdit()
    {
        this.aurelia.setRoot('login');
    }
}
