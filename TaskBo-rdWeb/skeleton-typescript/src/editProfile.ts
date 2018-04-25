import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';
import { Register } from './register';
import {Aurelia} from 'aurelia-framework';
import {ValidationControllerFactory, ValidationRules, ValidationController  } from 'aurelia-validation';

@inject(Aurelia, Router, ConnectionManager,ValidationControllerFactory)
export class EditProfile extends Register
{
    protected router: Router;

    constructor(aurelia: Aurelia, router: Router, connect: ConnectionManager,  validate:ValidationControllerFactory)
    {
        super(aurelia, connect, validate);
        this.router = router;
        // this.router = router;
        // this.connect = connect;
        // this.user   = new Object();
        this.user.oldPass      = new String()
        // this.user.newPass      = new String()
        // this.user.repeatPass   = new String()
        // this.user.UID          = new String()
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
        if ( ! this.user.UID )
        {
            console.log('new user');
        }
    }

    // updateData()
    // {
    //     this.connect.updateProfileData( this.user )
    //     localStorage.setItem( 'mail', this.user.mail );
    //     localStorage.setItem( 'name', this.user.name );
    //     localStorage.setItem( 'GSM', this.user.GSM );
    //     this.router.navigate('profile');
    // }

    updateProfile()
    {
        this.controller.reset();
        if ( String(this.user.newPass) === String(this.user.repeatPass) )
        {
            if ( ! String(this.user.newPass) )
            {
                this.updateData()
                this.router.navigate('profile');
            }
            else
            {
                this.connect.updateProfilePassword( this.user )
                .then(
                    (data: any) =>
                    {
                        if ( data.response === 'done' )
                        {
                            this.updateData();
                            this.router.navigate('profile');
                        }
                        else
                        {
                            this.controller.addError('Old password is wrong.', 'password')
                            console.log( data )
                        }
                    }
                );
                
            }
        }
        else
        {
            this.controller.addError('New password doen\'t match.', 'password')
        }
    }

    endEdit()
    {
        this.router.navigate('home');
    }
}
