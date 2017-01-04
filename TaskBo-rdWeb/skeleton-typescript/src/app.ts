import { Router, RouterConfiguration } from 'aurelia-router';

export class App {
  public router: Router;

  public configureRouter(config: RouterConfiguration, router: Router) {
    config.title = 'TaskBo-rd';
    config.map([
      { route: ['', 'groups'], name: 'groups', moduleId: 'groups', nav: true, title: 'Groups'  },
      { route: 'tasks/:id', name: 'tasks', moduleId: 'tasks', nav: false, title: 'View tasks in group', href: 'tasks' }
    ]);

    this.router = router;
  }
}
