var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-framework", "aurelia-router", "./data", "./ConnectionManager"], function (require, exports, aurelia_framework_1, aurelia_router_1, data_1, ConnectionManager_1) {
    "use strict";
    var Groups = (function () {
        function Groups(router, dataService, connectionManager) {
            this.heading = 'Welcome to TaskBo-rd!';
            this.router = router;
            this.connection = connectionManager;
            this.groups = data_1.GROUPS_FROM_SERVER;
        }
        Groups.prototype.navigateToTasks = function (uid) {
            console.log(uid);
            this.router.navigate('tasks/' + uid);
        };
        Groups.prototype.addGroup = function () {
            this.connection.login('test', 'test').then(function (responseData) {
                console.log(responseData);
            });
        };
        return Groups;
    }());
    Groups = __decorate([
        aurelia_framework_1.inject(aurelia_router_1.Router, ConnectionManager_1.DataService, ConnectionManager_1.ConnectionManager),
        __metadata("design:paramtypes", [aurelia_router_1.Router, ConnectionManager_1.DataService, ConnectionManager_1.ConnectionManager])
    ], Groups);
    exports.Groups = Groups;
});

//# sourceMappingURL=groups.js.map
