import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';
import { Register } from './register';
import {Aurelia} from 'aurelia-framework';

@inject(Aurelia, Router, ConnectionManager)
export class EditProfile extends Register
{
    protected router: Router;

    constructor(aurelia: Aurelia, router: Router, connect: ConnectionManager)
    {
        super(aurelia, connect);
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
                            console.log( data )
                        }
                    }
                );
                
            }
        }
        else
        {
            console.log( 'new password doen\'t match' )
        }
    }

    endEdit()
    {
        this.router.navigate('home');
    }
}
