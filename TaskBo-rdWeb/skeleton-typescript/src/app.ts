import { Router, RouterConfiguration } from 'aurelia-router';

export class App {
  public router: Router;

  public configureRouter(config: RouterConfiguration, router: Router) {
    config.title = 'TaskBo-rd';
    config.map([
      { route: ['', 'home'], name: 'home', moduleId: 'home', nav: true, title: 'Home'  },
      { route: 'group-tasks/:id', name: 'group-tasks', moduleId: 'group-tasks', nav: false, title: 'View tasks in group', href: 'group-tasks' }
    ]);

    this.router = router;
  }
}
