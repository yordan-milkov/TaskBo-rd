import { inject } from 'aurelia-framework';
import { Router } from 'aurelia-router';
import { GROUPS_FROM_SERVER } from './data';
import { DataService, ConnectionManager } from './ConnectionManager';

@inject(Router, DataService, ConnectionManager)
export class Groups
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
    this.groups = GROUPS_FROM_SERVER; //TODO: server call for all group ids
  }

  public navigateToTasks(uid: string): void
  {
    console.log(uid);
    this.router.navigate('tasks/' + uid);
  }

  addGroup()
  {
    // alert('add group!');
    // this.dataService.connect();
   // this.connection.getGroupByUID('test');
   this.connection.login('test', 'test').then((responseData) => {
     console.log(responseData);
   });
   
  }

}
