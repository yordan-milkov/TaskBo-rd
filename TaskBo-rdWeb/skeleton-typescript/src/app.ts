import { Router, RouterConfiguration } from 'aurelia-router';

export class App {
  public router: Router;

  public configureRouter(config: RouterConfiguration, router: Router) {
    config.title = 'TaskBo-rd';
    config.map([
      { route: ['', 'home'], name: 'home', moduleId: 'home', nav: true, title: 'Home'  },
      { route: 'profile', name: 'profile', moduleId: 'profile', nav: true, title: 'Profile', href: 'profile' },
      { route: 'editProfile', name: 'editProfile', moduleId: 'editProfile', nav: false, title: 'Edit Profile', href: 'editProfile' },
      { route: 'addEditGroup/:id', name: 'addEditGroup', moduleId: 'addEditGroup', nav: false, title: 'Edit Group', href: 'addEditGroup' },
      { route: 'group-tasks/:id', name: 'group-tasks', moduleId: 'group-tasks', nav: false, title: 'Tasks by group', href: 'group-tasks' },
      { route: 'task/:id', name: 'task', moduleId: 'task', nav: false, title: 'View task', href: 'task' },
      { route: 'edit/:id', name: 'editTask', moduleId: 'editTask', nav: false, title: 'Edit task', href: 'editTrask' }
    ]);

    this.router = router;
  }
}
