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
        this.heading = 'Group with UID: ' + this.groupUID;

        this.connection.getTasksByGroup(this.groupUID)
            .then(
            (data: any) =>
            {
                this.tasks = JSON.parse(data.response);
                this.sortResolve();

                this.connection.getGroupData(this.groupUID)
                .then((data) => {
                    let group = JSON.parse(data.response);
                    this.heading = 'Group: ' + group.name;
                });
                console.log(this.tasks);
            }
            );
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
    sortUID()
    {
        this.tasks.sort(
            (a: any, b: any) =>
            {
                return ( a.issueUID > b.issueUID ) ? 1 : 0;
            }
        )
    }
    sortName()
    {
        this.tasks.sort(
            (a: any, b: any) =>
            {
                return ( a.name < b.name ) ? 1 : 0;
            }
        )
    }
    
    sortDesc()
    {
        this.tasks.sort(
            (a: any, b: any) =>
            {
                return ( a.description < b.description ) ? 1 : 0;
            }
        )
    }

    sortResolve()
    {
        this.tasks.sort(
            (a: any, b: any) =>
            {
                return ( a.isResolved > b.isResolved ) ? 1 : 0;
            }
        )
    }

    getColor(isResolved :any) : string
    {
        return isResolved == 0 ? 'sucess' : 'danger'
    }

    getFlag(isResolved:any) : string
    {
        return isResolved == 0 ? 'flag-checkered' : 'flag'
    }
    resolveIssue(task :any)
    {
        console.log(task)
        task.isResolved = ( task.isResolved == 0 )
        this.connection.updateResolveState( task.isResolved, task.issueUID );
    }
}
