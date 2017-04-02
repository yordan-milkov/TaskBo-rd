import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { DialogService } from 'aurelia-dialog';
import { ConnectionManager } from './ConnectionManager';
import { Task } from './task';
import { Confirm } from './confirmModal';

@inject(Router, ConnectionManager, DialogService)

export class EditTask extends Task
{
    protected dlg: DialogService;
    constructor(router: Router, connect: ConnectionManager, dlg: DialogService)
    {
        super(router, connect);
        this.dlg = dlg;
    }

    attached()
    {
        console.log('editTask:attached');
    }

    activate(params: any)
    {
        super.activate(params);
    }

    saveTask()
    {
        this.task.users = '';
        this.assigniees.forEach(assignie =>
        {
            for( let user of this.groupUsers )
            {
                if ( assignie === user.name )
                {
                    this.task.users += user.UID + ',';
                    break;
                }
            }
        });
        this.connect.updateTaskData(this.task);
        
        this.checkboxes.forEach(element =>
        {
            this.connect.updateCheckData(element);
        });
        this.endTask();
    }

    endTask()
    {
        this.router.navigate('task/' + this.task.issueUID)
    }

    removeAssignie(userName: any)
    {
        this.dlg.open({ viewModel: Confirm, model: 'Do you want to remove this user as asignee: ' + userName })
            .then(response =>
            {
                if (!response.wasCancelled) {
                    let index = this.assigniees.findIndex(element => element === userName );
                    this.assigniees.splice(index, 1);
                }
            });
    }

    removeCheck(checkData: any)
    {
        this.dlg.open({ viewModel: Confirm, model: 'Do you want to delete this check box:' + checkData.name })
            .then(response =>
            {
                if (!response.wasCancelled) {
                    let index = this.checkboxes.findIndex(element => element.checkUID === checkData.checkUID);
                    this.checkboxes.splice(index, 1);
                    this.connect.deleteCheck(checkData.checkUID);
                }
            });
    }
}