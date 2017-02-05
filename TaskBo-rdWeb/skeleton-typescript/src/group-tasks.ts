import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { ConnectionManager } from './ConnectionManager';

@inject(Router, ConnectionManager)
export class Tasks
{
    private router: Router;
    private connection: ConnectionManager;
    groupData: any;
    tasks: any[]; //received array of group's tasks
    constructor(router, connection)
    {
        this.connection = connection;
        this.router = router;
        this.router.configure((config: RouterConfiguration) =>
        {
            config.map([
                { route: 'task/:id', name: 'task', moduleId: 'task', nav: false, title: 'View task', href: 'task' },
                { route: 'edit/:id', name: 'editTask', moduleId: 'editTask', nav: false, title: 'Edit task', href: 'editTrask' }
            ]);
            return config;
        });
    }

  /*  attached()
    {
        console.log('tasks:attached');
        console.log(this.tasks);
    }*/

    activate(params: any)
    {
        console.log(params)
        this.connection.getTasksByGroup(params.id)
            .then(
            (data: any) =>
            {
                this.tasks = JSON.parse(data.response);
        console.log(this.tasks);
            }
            );
        console.log(this.tasks);
        // this.heading = 'Group with UID: ' + ;
    }

    navigateToTask(params)
    {
        this.router.navigate('task/' + params)
    }
}

function getTasks(id)
{

}