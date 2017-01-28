define(["require", "exports"], function (require, exports) {
    "use strict";
    var EditTask = (function () {
        function EditTask() {
        }
        EditTask.prototype.attached = function () {
            console.log('editTask:attached');
        };
        EditTask.prototype.activate = function (params) {
            console.log(params);
            this.heading = 'Edit task with UID: ' + params;
            this.task = params;
        };
        return EditTask;
    }());
    exports.EditTask = EditTask;
});

//# sourceMappingURL=editTask.js.map
