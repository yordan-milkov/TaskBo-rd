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
    protected selected: any;
    protected isNew: boolean;

    constructor(router: Router, connect: ConnectionManager, dlg: DialogService)
    {
        super(router, connect);
        this.dlg = dlg;
        this.isNew = false;
    }

    attached()
    {
        console.log('editTask:attached');
    }

    activate(params: any)
    {
        let newStr = params.id.substring(0, 3);
        if (newStr === 'new') {
            this.task = new Object();
            this.task.groupUID = params.id.substring(3);
            this.isNew = true;
            this.task.users = localStorage.getItem('UID') + ','
            console.log(this.task);
            super.fillGroupUsers(this.task.groupUID);
        }
        else {
            super.activate(params);
        }
    }

    saveTask()
    {
        this.task.users = '';
        this.assigniees.forEach(assignie =>
        {
            for (let user of this.groupUsers) {
                if (assignie === user.name) {
                    this.task.users += user.UID + ',';
                    break;
                }
            }
        });

        let promise: any;
        if (this.isNew) {
            promise = this.connect.createTaskData(this.task);
        }
        else {
            promise = this.connect.updateTaskData(this.task);
        }

        promise.then(
            (data: any) =>
            {
                if (this.isNew) {
                    this.task.issueUID  = JSON.parse(data.response);
                }
                else {
                    this.checkboxes.forEach(element =>
                    {
                        this.connect.updateCheckData(element);
                    });
                }

                this.endTask();
            });
    }

    endTask()
    {
        if (this.task.issueUID) {
            this.router.navigate('task/' + this.task.issueUID)
        }
        else {
            this.router.navigate('group-tasks/' + this.task.groupUID);
        }
    }

    removeAssignie(userName: any)
    {
        this.dlg.open({ viewModel: Confirm, model: 'Do you want to remove this user as asignee: ' + userName })
            .whenClosed(response =>
            {
                console.log(response);
                if (!response.wasCancelled) {
                    let index = this.assigniees.findIndex(element => element === userName);
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

    addUser()
    {
        if (this.selected != null) {
            let found = false;
            this.assigniees.forEach(assignie =>
            {
                if (assignie === this.selected.name) {
                    found = true;
                }
            });

            if (found === false) {
                this.assigniees.push(this.selected.name);
            }
        }
    }
}