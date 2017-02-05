var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-dependency-injection", "aurelia-router", "./ConnectionManager"], function (require, exports, aurelia_dependency_injection_1, aurelia_router_1, ConnectionManager_1) {
    "use strict";
    var Tasks = (function () {
        function Tasks(router, connection) {
            this.connection = connection;
            this.router = router;
            this.router.configure(function (config) {
                config.map([
                    { route: 'task/:id', name: 'task', moduleId: 'task', nav: false, title: 'View task', href: 'task' },
                    { route: 'edit/:id', name: 'editTask', moduleId: 'editTask', nav: false, title: 'Edit task', href: 'editTrask' }
                ]);
                return config;
            });
        }
        Tasks.prototype.activate = function (params) {
            var _this = this;
            console.log(params);
            this.connection.getTasksByGroup(params.id)
                .then(function (data) {
                _this.tasks = JSON.parse(data.response);
                console.log(_this.tasks);
            });
            console.log(this.tasks);
        };
        Tasks.prototype.navigateToTask = function (params) {
            this.router.navigate('task/' + params);
        };
        return Tasks;
    }());
    Tasks = __decorate([
        aurelia_dependency_injection_1.inject(aurelia_router_1.Router, ConnectionManager_1.ConnectionManager),
        __metadata("design:paramtypes", [Object, Object])
    ], Tasks);
    exports.Tasks = Tasks;
    function getTasks(id) {
    }
});

//# sourceMappingURL=group-tasks.js.map
