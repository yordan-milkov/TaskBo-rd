var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "aurelia-framework", "jquery", "jquery-ui"], function (require, exports, aurelia_framework_1) {
    "use strict";
    var Datepicker = (function () {
        function Datepicker(Element) {
            this.id = '';
            this.name = '';
            this.options = {};
            this.element = Element;
            if (!this.id && this.name) {
                this.id = this.name;
            }
            if (!this.name && this.id) {
                this.name = this.id;
            }
        }
        Datepicker.prototype.attached = function () {
            var dateFormat = "mm/dd/yy", from = $("#from")
                .datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 1
            })
                .on("change", function (e) {
                to.datepicker("option", "minDate", getDate(this));
            }), to = $("#to").datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 1
            })
                .on("change", function (e) {
                from.datepicker("option", "maxDate", getDate(this));
            });
            function getDate(element) {
                var date;
                try {
                    date = $.datepicker.parseDate(dateFormat, element.value);
                }
                catch (error) {
                    date = null;
                }
                return date;
            }
        };
        Datepicker.prototype.detached = function () {
            $("#" + this.id).datepicker('destroy').off('change');
        };
        return Datepicker;
    }());
    __decorate([
        aurelia_framework_1.bindable,
        __metadata("design:type", Object)
    ], Datepicker.prototype, "id", void 0);
    __decorate([
        aurelia_framework_1.bindable,
        __metadata("design:type", Object)
    ], Datepicker.prototype, "name", void 0);
    __decorate([
        aurelia_framework_1.bindable,
        __metadata("design:type", Object)
    ], Datepicker.prototype, "options", void 0);
    Datepicker = __decorate([
        aurelia_framework_1.customElement('datepicker'),
        aurelia_framework_1.inject(aurelia_framework_1.DOM.Element),
        __metadata("design:paramtypes", [Object])
    ], Datepicker);
    exports.Datepicker = Datepicker;
});

//# sourceMappingURL=datepicker.js.map
