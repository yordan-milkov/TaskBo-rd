export class EditTask
{
    heading: string;
    task: any[]; //received task id
    constructor()
    {
    }

    attached()
    {
        console.log('editTask:attached');
    }

    activate(params: any)
    {
        console.log(params)
        this.heading = 'Edit task with UID: ' + params;
        this.task = params; //TODO: server call for tasks by group UID
    }
}