define(["require", "exports", "aurelia-http-client"], function (require, exports, aurelia_http_client_1) {
    "use strict";
    var DataService = (function () {
        function DataService() {
        }
        DataService.prototype.activate = function () {
        };
        DataService.prototype.connect = function () {
            var _this = this;
            this.socket = new WebSocket("ws://localhost:4444");
            this.socket.binaryType = 'arraybuffer';
            this.socket.onopen = function (event) {
                console.log('websocket connected');
                _this.loginUser();
            };
            this.socket.onmessage = function (event) { return _this.handleMessage(event.data); };
            this.socket.onerror = function (error) { return console.log(error); };
        };
        DataService.prototype.loginUser = function () {
            console.log('user loag: ');
            if (this.socket.readyState === WebSocket.OPEN) {
                this.sendStringMassage('putki');
                this.sendStringMassage(JSON.stringify({ "user": "test", "password": "test" }));
            }
        };
        DataService.prototype.sendStringMassage = function (msg) {
            this.socket.binaryType;
            this.socket.send(msg + '\n');
        };
        DataService.prototype.handleMessage = function (data) {
            console.log('websocket message: ');
            console.log(data);
        };
        DataService.prototype.addstate = function () {
        };
        return DataService;
    }());
    exports.DataService = DataService;
    var ConnectionManager = (function () {
        function ConnectionManager() {
            this.httpClient = new aurelia_http_client_1.HttpClient()
                .configure(function (x) {
                x.withBaseUrl('http://192.168.0.201/TaskBo-rdWebServer/php/');
                x.withHeader("Content-type", " application/x-www-form-urlencoded");
            });
        }
        ConnectionManager.prototype.login = function (uid, password) {
            return this.httpClient.post('login.php', "UID=" + uid + "&password=" + password);
        };
        ConnectionManager.prototype.getGroupsByUID = function (UserID) {
            return this.httpClient.createRequest('user-groups.php')
                .asGet()
                .withParams({ 'UID': UserID })
                .send();
        };
        ConnectionManager.prototype.getTasksByGroup = function (groupID) {
            return this.httpClient.createRequest('group-data.php')
                .asGet()
                .withParams({ 'groupID': groupID })
                .send();
        };
        return ConnectionManager;
    }());
    exports.ConnectionManager = ConnectionManager;
});

//# sourceMappingURL=ConnectionManager.js.map
