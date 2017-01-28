define(["require", "exports"], function (require, exports) {
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
});

//# sourceMappingURL=ConnectionManager.js.map
