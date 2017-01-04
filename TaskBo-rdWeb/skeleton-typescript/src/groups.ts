import { inject } from 'aurelia-framework';
import { Router } from 'aurelia-router';
import { GROUPS_FROM_SERVER } from './data';
import { DataService } from './ConnectionManager';

@inject(Router, DataService)
export class Groups
{
  public heading = 'Welcome to TaskBo-rd!';
  private router: Router;
  private dataService: DataService;
  public groups: any;

  constructor(router: Router, dataService: DataService)
  {
    this.router = router;
    this.dataService = dataService;
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
    this.dataService.connect();
    this.dataService.loginUser();
  }

}
