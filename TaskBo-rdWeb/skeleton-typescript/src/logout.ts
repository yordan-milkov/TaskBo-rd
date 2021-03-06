import {Aurelia} from 'aurelia-framework';
import { inject } from 'aurelia-dependency-injection';
import { Router, RouterConfiguration } from 'aurelia-router';

@inject(Aurelia, Router)
export class Logout
{
    private aurelia: Aurelia;
    private router: Router;

    constructor(aurelia: Aurelia, router: Router)
    {
        this.aurelia = aurelia;
        this.router = router;
    }

    public attached()
    {
        localStorage.clear();
        this.router.navigate('/');
    }

    public detached () {
        location.reload();          
    }
}