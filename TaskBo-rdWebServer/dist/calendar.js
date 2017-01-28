define(["require", "exports", "jquery", "jquery-ui"], function (require, exports) {
    "use strict";
    var Tasks = (function () {
        function Tasks() {
            this.heading = 'DATE PICKER';
        }
        Tasks.prototype.attached = function () {
            $("#from")
                .datepicker()
                .on("change", function (e) {
                console.log(getDate(this));
            });
            $("#to")
                .datepicker()
                .on("change", function (e) {
                console.log(getDate(this));
            });
            function getDate(element) {
                var date;
                try {
                    date = $.datepicker.parseDate("mm/dd/yy", element.value);
                }
                catch (error) {
                    date = null;
                }
                return date;
            }
            console.log('edit:attached');
        };
        Tasks.prototype.activate = function (uid) {
            console.log(uid);
        };
        return Tasks;
    }());
    exports.Tasks = Tasks;
});

//# sourceMappingURL=calendar.js.map
