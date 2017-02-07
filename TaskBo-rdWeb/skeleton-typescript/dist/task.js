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
    var Task = (function () {
        function Task(router, connect) {
            this.router = router;
            this.connect = connect;
        }
        Task.prototype.attached = function () {
        };
        Task.prototype.activate = function (params) {
            var _this = this;
            console.log(params);
            console.log(params.id);
            this.connect.getTaskData(params.id)
                .then(function (data) {
                _this.task = JSON.parse(data.response)[0];
                _this.heading = 'Task: ' + _this.task.name;
                console.log(_this.task);
            });
            this.connect.getTaskChecks(params.id)
                .then(function (data) {
                _this.checkboxes = JSON.parse(data.response);
                console.log(_this.checkboxes);
            });
        };
        Task.prototype.onCheckboxChanged = function (isChecked, checkUID) {
            this.connect.updateCheckState(isChecked, checkUID)
                .then(function (data) {
                console.log(data);
            });
        };
        Task.prototype.editTask = function (params) {
            this.router.navigate('edit/' + params);
        };
        return Task;
    }());
    Task = __decorate([
        aurelia_dependency_injection_1.inject(aurelia_router_1.Router, ConnectionManager_1.ConnectionManager),
        __metadata("design:paramtypes", [aurelia_router_1.Router, ConnectionManager_1.ConnectionManager])
    ], Task);
    exports.Task = Task;
});

//# sourceMappingURL=task.js.map
