// import "jquery";
// import "jquery-ui";
import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';

@inject(Router, ConnectionManager)
export class Task
{
    private router: Router;
    private connect: ConnectionManager;
    heading: string;
    task: any; //received task
    checkboxes: any;
    constructor(router: Router, connect: ConnectionManager)
    {
        this.router = router;
        this.connect = connect; 
    }

    attached()
    {
        //console.log('task:attached');
    }

    activate(params: any)
    {
        console.log(params);
        console.log(params.id);
        
        this.connect.getTaskData( params.id )
        .then(
        (data: any) =>
        {
            this.task = JSON.parse(data.response)[0];
            this.heading = 'Task: ' + this.task.name;
            console.log(this.task);
        }
        );

        this.connect.getTaskChecks( params.id )
        .then(
        (data: any) =>
        {
            this.checkboxes = JSON.parse(data.response);
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
            console.log(data)
        }
        )
    }

    editTask(params)
    {
        this.router.navigate('edit/' + params)
    }
}
