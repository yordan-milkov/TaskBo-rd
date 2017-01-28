var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-dependency-injection", "aurelia-router", "./data"], function (require, exports, aurelia_dependency_injection_1, aurelia_router_1, data_1) {
    "use strict";
    var Task = (function () {
        function Task(router) {
            this.router = router;
        }
        Task.prototype.attached = function () {
            console.log('task:attached');
        };
        Task.prototype.activate = function (params) {
            console.log(params);
            this.heading = 'Task with UID: ' + params.id;
            this.task = getTask(params.id);
        };
        Task.prototype.editTask = function (params) {
            this.router.navigate('edit/' + params);
        };
        return Task;
    }());
    Task = __decorate([
        aurelia_dependency_injection_1.inject(aurelia_router_1.Router),
        __metadata("design:paramtypes", [aurelia_router_1.Router])
    ], Task);
    exports.Task = Task;
    function getTask(taskId) {
        for (var i = 0; i < data_1.TASKS_FROM_SERVER.length; i++) {
            if (data_1.TASKS_FROM_SERVER[i].id === taskId) {
                return data_1.TASKS_FROM_SERVER[i];
            }
        }
    }
});

//# sourceMappingURL=task.js.map
