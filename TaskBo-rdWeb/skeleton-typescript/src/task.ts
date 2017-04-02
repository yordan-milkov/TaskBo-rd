// import "jquery";
// import "jquery-ui";
import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';

@inject(Router, ConnectionManager)
export class Task
{
    protected router: Router;
    protected connect: ConnectionManager;
    heading: string;
    task: any; //received task
    protected checkboxes: any;
    protected assigniees: Array<string>;
    protected groupUsers: any;
    private newCheck: any;
    constructor(router: Router, connect: ConnectionManager)
    {
        this.router = router;
        this.connect = connect;
        this.newCheck = '';
        this.assigniees = [];
    }

    attached()
    {
        //console.log('task:attached');
    }

    getTaskData(taskID)
    {
        this.connect.getTaskData(taskID)
            .then(
            (data: any) =>
            {
                this.task = JSON.parse(data.response)[0];
                this.heading = 'Task: ' + this.task.name;
                this.task.isResolved = this.task.isResolved == 1 ? true : false;
                console.log(this.task);

                this.connect.getUsersByGroup(this.task.groupUID)
                .then(
                (data: any) =>
                {
                    this.groupUsers  = JSON.parse(data.response);
                    this.fillAssignieList();
                });
            }
            );
    }

    activate(params: any)
    {
        console.log(params);
        console.log(params.id);

        this.getTaskData(params.id);

        this.connect.getTaskChecks(params.id)
            .then(
            (data: any) =>
            {
                this.checkboxes = JSON.parse(data.response);
                for (let item of this.checkboxes) {
                    item.isFinished = item.isFinished == 1 ? true : false;
                }
                console.log(this.checkboxes);
            }
            );
    }

    onCheckboxChanged(isChecked: boolean, checkUID: string)
    {
        this.connect.updateCheckState(isChecked, checkUID)
            .then(
            (data: any) =>
            {
                console.log(data);
            }
            )
    }

    addCheck()
    {
        if (this.newCheck !== '') {
            console.log(this.newCheck);
            let newItem = {
                name: this.newCheck,
                isFinished: false,
                issueUID: this.task.issueUID };
            this.checkboxes.push(newItem);
            this.connect.addCheck( newItem );
            this.newCheck = '';
            console.log(this.checkboxes)
        }
    }

    editTask(params)
    {
        this.router.navigate('edit/' + params)
    }

    fillAssignieList()
    {
        let userList = this.task.users.split( "," )
        for( let item of userList )
        {
            for( let user of this.groupUsers )
            {
                if ( item === user.UID )
                {
                    this.assigniees.push( user.name );
                    break;
                }
            }
        }
    }
}
