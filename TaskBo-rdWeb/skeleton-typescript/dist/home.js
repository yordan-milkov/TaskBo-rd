var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-framework", "aurelia-router", "./ConnectionManager"], function (require, exports, aurelia_framework_1, aurelia_router_1, ConnectionManager_1) {
    "use strict";
    var Home = (function () {
        function Home(router, dataService, connectionManager) {
            this.heading = 'Welcome to TaskBo-rd!';
            this.router = router;
            this.connection = connectionManager;
        }
        Home.prototype.activate = function () {
            var _this = this;
            this.connection.getGroupsByUID('test')
                .then(function (data) {
                var result = JSON.parse(data.response);
                console.log(result);
                _this.groups = result;
            });
            console.log(this.groups);
        };
        Home.prototype.navigateToTasks = function (uid) {
            console.log(uid);
            this.router.navigate('group-tasks/' + uid);
        };
        Home.prototype.addGroup = function () {
        };
        return Home;
    }());
    Home = __decorate([
        aurelia_framework_1.inject(aurelia_router_1.Router, ConnectionManager_1.DataService, ConnectionManager_1.ConnectionManager),
        __metadata("design:paramtypes", [aurelia_router_1.Router, ConnectionManager_1.DataService, ConnectionManager_1.ConnectionManager])
    ], Home);
    exports.Home = Home;
});

//# sourceMappingURL=home.js.map
