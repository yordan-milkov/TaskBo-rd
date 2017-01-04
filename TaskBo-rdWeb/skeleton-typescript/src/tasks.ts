import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { GROUPS_FROM_SERVER } from './data';

@inject(Router)
export class Tasks
{
    private router: Router;
    heading: string;
    tasks: any[]; //received array of group's tasks
    constructor(router)
    {
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

    attached()
    {
        console.log('tasks:attached');
    }

    activate(params: any)
    {
        console.log(params)
        this.heading = 'Group with UID: ' + params.id;
        this.tasks = getTasks(params.id); //TODO: server call for tasks by group UID
    }

    navigateToTask(params)
    {
        this.router.navigate('task/' + params)
    }
}

function getTasks(id)
{
    for (let i = 0; i < GROUPS_FROM_SERVER.length; i++) {
        if (GROUPS_FROM_SERVER[i].uid === id) {
            return GROUPS_FROM_SERVER[i].tasks;
        }
    }
}