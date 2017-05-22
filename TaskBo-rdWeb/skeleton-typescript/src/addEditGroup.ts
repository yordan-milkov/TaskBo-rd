import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { DialogService } from 'aurelia-dialog';
import { ConnectionManager } from './ConnectionManager';
import { Confirm } from './confirmModal';

@inject(Router, ConnectionManager, DialogService)
export class AddEditGroup
{
    protected router: Router;
    protected connect: ConnectionManager;
    protected dlg: DialogService;
    group: any;
    protected groupUsers: any;
    protected modifyUsers: Map<string,number>;
    protected allUsers: any;
    protected selected: any;
    protected isNew : boolean;
    constructor(router: Router, connect: ConnectionManager, dlg: DialogService)
    {
        this.router = router;
        this.connect = connect;
        this.groupUsers = [];
        this.modifyUsers = new Map<string,number>();
        this.dlg = dlg;
        this.isNew  = false;
    }

    attached()
    {
        console.log('task:attached');
    }

    activate(params: any)
    {
        this.connect
            .getAllUsers()
            .then((data) => {
                this.allUsers = JSON.parse(data.response);
                console.log( this.allUsers );
            });
            console.log(params)
        if ( params.id !== 'new' )
        {
            this.connect.getGroupData(params.id)
            .then((data) => {
                console.log( data.response );
                this.group = JSON.parse(data.response);
                console.log( this.group );
            });

            this.connect.getUsersByGroup(params.id)
                .then(
                (data: any) =>
                {
                    this.groupUsers  = JSON.parse(data.response);

                });
        }
        else
        {
            this.isNew  = true;
        }
    }

    addUser()
    {
        if ( this.selected != null )
        {   
            let found = false;
            this.groupUsers.forEach(user =>
            {
                if (user.UID === this.selected.UID) {
                    found = true;
                }
            });

            if (found === false) {
                this.groupUsers.push(this.selected);
                let key = this.selected.UID;
                if ( this.modifyUsers.get( key ) === 0 )
                {
                    this.modifyUsers.set( key, 2 );
                }
                else
                {
                    this.modifyUsers.set( key, 1 );
                }
            }
        }
    }

    removeUser(userName: any)
    {
        this.dlg.open({ viewModel: Confirm, model: 'Do you want to remove this user from the group: ' + userName.name })
            .whenClosed(response =>
            {
                if (!response.wasCancelled) {
                    let index = this.groupUsers.findIndex(element => element === userName);
                    let key = this.groupUsers[index].UID;
                    if ( this.modifyUsers.get( key ) === 1 )
                    {
                        this.modifyUsers.set( key, 2 );
                    }
                    else
                    {
                        this.modifyUsers.set( key, 0 );
                    }
                    this.groupUsers.splice(index, 1);
                }
            });
    }
    
    endEdit()
    {
        this.router.navigate('home');
    }

    modifyUsersInGroup()
    {
        this.modifyUsers.forEach((value, key, map) =>
        {
            if ( value === 0 || value === 1 )
            {
                this.connect.updateUserInGroup( this.group.UID, key, value );
            }
        });
    }

    saveGroup()
    {
        var promise :any;
        if ( this.isNew )
        {
            promise = this.connect.createGroup( this.group.name, this.group.description );
        }
        else
        {
            promise = this.connect.updateGroupData( this.group.UID, this.group.name, this.group.description );
        }
        
        promise.then(
        (data: any) =>
        {
            if ( this.isNew )
            {
                this.group.UID  = JSON.parse(data.response);
                console.log(this.group.UID)
            }
            this.modifyUsersInGroup();
            this.endEdit();
        });
    }
}
