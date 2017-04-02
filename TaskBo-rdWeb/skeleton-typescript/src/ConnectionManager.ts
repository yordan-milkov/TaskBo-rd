import { HttpClient } from 'aurelia-http-client';

export class ConnectionManager
{
    private httpClient: any;

    constructor()
    {
        this.httpClient = new HttpClient()
            .configure((x: any) =>
            {
                x.withBaseUrl('http://192.168.0.201/TaskBo-rdWebServer/php/');
                x.withHeader("Content-type", " application/x-www-form-urlencoded");
            });
    }

    login(uid: string, password: string): Promise<any>
    {
        return this.httpClient.post('login.php', `UID=${uid}&password=${password}`);
    }

    getGroupsByUID(UserID: string): any
    {
       return this.httpClient.createRequest('user-groups.php')
            .asGet()
            .withParams({ 'UID': UserID })
            .send();
    }

    getTasksByGroup(groupID: string) : any
    {
        return this.httpClient.createRequest('group-data.php')
            .asGet()
            .withParams({ 'groupUID': groupID })
            .send();
    }

    getUsersByGroup(groupID: string) : any
    {
        return this.httpClient.createRequest('group-users.php')
            .asGet()
            .withParams({ 'groupUID': groupID })
            .send();
    }

    getTaskData(taskID: string) : any
    {
        return this.httpClient.createRequest('issue-data.php')
            .asGet()
            .withParams({ 'issueUID': taskID })
            .send();
    }
    
    getTaskChecks(taskID: string) : any
    {
        return this.httpClient.createRequest('issue-checks.php')
            .asGet()
            .withParams({ 'issueUID': taskID })
            .send();
    }

    updateCheckState(isChecked: boolean, checkUID: string)
    {
        return this.httpClient.post('issue-checks-finish.php', `isFinished=${isChecked?1:0}&checkUID=${checkUID}`);
    }

    updateTaskData(taskData: any)
    {
        console.log(taskData.name);
        taskData.isFinished = taskData.isFinished ? 1 : 0;
        return this.httpClient.post('issue-data-update.php',
            `name=${taskData.name}&description=${taskData.description}&users=${taskData.users}&issueUID=${taskData.issueUID}`
            );
    }

    addCheck(checkData: any)
    {
        console.log(checkData.issueUID);
        return this.httpClient.post('issue-checks-update.php',
            `name=${checkData.name}&issueUID=${checkData.issueUID}`
            );
    }

    updateCheckData(checkData: any)
    {
        console.log( checkData.name );
        return this.httpClient.post('issue-checks-update.php',
            `name=${checkData.name}&checkUID=${checkData.checkUID}`
            );
    }
    
    deleteCheck(checkUID: any)
    {
        return this.httpClient.post('issue-checks-update.php',
            `checkUID=${checkUID}`
            );
    }
}