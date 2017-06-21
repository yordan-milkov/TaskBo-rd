import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';

@inject(Router, ConnectionManager)
export class Tasks
{
    private router: Router;
    private connection: ConnectionManager;
    groupUID: any;
    tasks: any[]; //received array of group's tasks
    heading: string;
    constructor(router, connection)
    {
        this.connection = connection;
        this.router = router;
    }

    /*  attached()
      {
          console.log('tasks:attached');
          console.log(this.tasks);
      }*/

    activate(params: any)
    {
        console.log(params)
        this.groupUID = params.id;

        this.connection.getTasksByGroup(this.groupUID)
            .then(
            (data: any) =>
            {
                this.tasks = JSON.parse(data.response);
                console.log(this.tasks);
            }
            );
        console.log(this.tasks);
        this.heading = 'Group with UID: ' + this.groupUID;
    }

    navigateToTask(params)
    {
        this.router.navigate('task/' + params)
    }

    editTask(issueUID)
    {
        this.router.navigate('edit/' + issueUID)
    }

    addTask()
    {
        console.log(this.groupUID);
        this.router.navigate('edit/new' + this.groupUID);
    }
}
