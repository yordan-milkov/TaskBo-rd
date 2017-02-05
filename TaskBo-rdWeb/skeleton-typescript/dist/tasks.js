var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-dependency-injection", "aurelia-router"], function (require, exports, aurelia_dependency_injection_1, aurelia_router_1) {
    "use strict";
    var Tasks = (function () {
        function Tasks(router) {
            this.router = router;
            this.router.configure(function (config) {
                config.map([
                    { route: 'task/:id', name: 'task', moduleId: 'task', nav: false, title: 'View task', href: 'task' },
                    { route: 'edit/:id', name: 'editTask', moduleId: 'editTask', nav: false, title: 'Edit task', href: 'editTrask' }
                ]);
                return config;
            });
        }
        Tasks.prototype.attached = function () {
            console.log('tasks:attached');
        };
        Tasks.prototype.activate = function (params) {
            console.log(params);
            this.heading = 'Group with UID: ' + params.id;
        };
        Tasks.prototype.navigateToTask = function (params) {
            this.router.navigate('task/' + params);
        };
        return Tasks;
    }());
    Tasks = __decorate([
        aurelia_dependency_injection_1.inject(aurelia_router_1.Router),
        __metadata("design:paramtypes", [Object])
    ], Tasks);
    exports.Tasks = Tasks;
    function getTasks(id) {
    }
});

//# sourceMappingURL=tasks.js.map
