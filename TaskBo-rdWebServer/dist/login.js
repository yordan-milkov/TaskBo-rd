var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-framework", "aurelia-dependency-injection", "./ConnectionManager"], function (require, exports, aurelia_framework_1, aurelia_dependency_injection_1, ConnectionManager_1) {
    "use strict";
    var Login = (function () {
        function Login(aurelia, connectionManager) {
            this.username = "test";
            this.password = "test";
            this.aurelia = aurelia;
            this.connection = connectionManager;
            this.isLoggedIn = false;
        }
        Login.prototype.login = function () {
            var _this = this;
            this.connection
                .login(this.username, this.password)
                .then(function (data) {
                console.log(data);
                _this.aurelia.setRoot('app');
            });
        };
        return Login;
    }());
    Login = __decorate([
        aurelia_dependency_injection_1.inject(aurelia_framework_1.Aurelia, ConnectionManager_1.ConnectionManager),
        __metadata("design:paramtypes", [aurelia_framework_1.Aurelia, ConnectionManager_1.ConnectionManager])
    ], Login);
    exports.Login = Login;
});

//# sourceMappingURL=login.js.map
