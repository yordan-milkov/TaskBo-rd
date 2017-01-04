// import "jquery";
// import "jquery-ui";
import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';
import { TASKS_FROM_SERVER } from './data';

@inject(Router)
export class Task
{
    private router: Router;
    heading: string;
    task: any; //received task
    constructor(router: Router)
    {
        this.router = router;
    }

    attached()
    {
        console.log('task:attached');
    }

    activate(params: any)
    {
        console.log(params)
        this.heading = 'Task with UID: ' + params.id;
        this.task = getTask(params.id); //TODO: server call for task
    }

    editTask(params)
    {
        this.router.navigate('edit/' + params)
    }
}

function getTask(taskId)
{
    for (let i = 0; i < TASKS_FROM_SERVER.length; i++) {
        if (TASKS_FROM_SERVER[i].id === taskId) {
            return TASKS_FROM_SERVER[i];
        }
    }
}