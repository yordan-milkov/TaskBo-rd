import { inject } from 'aurelia-framework';
import { Router } from 'aurelia-router';
import { DataService, ConnectionManager } from './ConnectionManager';

@inject(Router, DataService, ConnectionManager)
export class Home
{
  public heading = 'Welcome to TaskBo-rd!';
  private router: Router;
  // private dataService: DataService;
  private connection: ConnectionManager;
  public groups: any;

  constructor(router: Router, dataService: DataService, connectionManager: ConnectionManager)
  {
    this.router = router;
    this.connection = connectionManager;
    // this.dataService = dataService;

  }

  public activate()
  {
      this.connection.getGroupsByUID('test')
      .then(
      (data: any) =>
      {
        let result = JSON.parse(data.response);
        console.log(result);
        this.groups = result;
      }
      );
     console.log(this.groups);
  }

  public navigateToTasks(uid: string): void
  {
    console.log(uid);
    this.router.navigate('group-tasks/' + uid);
  }

  addGroup()
  {
    // alert('add group!');
    // this.dataService.connect();

    // this.connection.login('test', 'test').then((responseData) => {
    //  console.log(responseData);
    // });

  }

}
