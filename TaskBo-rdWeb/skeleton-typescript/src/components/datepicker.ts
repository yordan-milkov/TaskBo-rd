// import { customElement, bindable, inject, DOM } from 'aurelia-framework';
// import "jquery";
// import "jquery-ui";

// @customElement('datepicker')
// @inject(DOM.Element)
// export class Datepicker {
//     @bindable id = '';
//     @bindable name = '';
//     @bindable options = {};
//     element;

//     constructor(Element) {
//         this.element = Element;

//         if (!this.id && this.name) {
//             this.id = this.name;
//         }

//         if (!this.name && this.id) {
//             this.name = this.id;
//         }
//     }

//     attached() {

//         var dateFormat = "mm/dd/yy",
//             from = (<any>$("#from"))
//                 .datepicker({
//                     defaultDate: "+1w",
//                     changeMonth: true,
//                     numberOfMonths: 1
//                 })
//                 .on("change", function (e) {
//                     to.datepicker("option", "minDate", getDate(this));
//                     // console.log(getDate(this));
//                 }),

//             to = (<any>$("#to")).datepicker({
//                 defaultDate: "+1w",
//                 changeMonth: true,
//                 numberOfMonths: 1
//             })
//                 .on("change", function (e) {
//                     from.datepicker("option", "maxDate", getDate(this));

//                 });

//         function getDate(element) {
//             var date;
//             try {
//                 date = (<any>$).datepicker.parseDate(dateFormat, element.value);
//             } catch (error) {
//                 date = null;
//             }

//             return date;
//         }
//     }

//     detached() {
//         (<any>$(`#${this.id}`)).datepicker('destroy').off('change');
//     }
// }