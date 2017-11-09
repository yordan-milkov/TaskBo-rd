// import "jquery";
// import "jquery-ui";

// export class Tasks {
//     heading: string;
//     constructor() {
//         this.heading = 'DATE PICKER';
//     }

//     attached() {
//         (<any>$("#from"))
//             .datepicker()
//             .on("change", function (e) {
//                 console.log(getDate(this));
//             });

//        (<any>$("#to"))
//             .datepicker()
//             .on("change", function (e) {
//                 console.log(getDate(this));
//             });

//         function getDate(element) {
//             var date;
//             try {
//                 date = (<any>$).datepicker.parseDate("mm/dd/yy", element.value);
//             } catch (error) {
//                 date = null;
//             }

//             return date;
//         }
//         console.log('edit:attached');
//     }

//     activate(uid: string) {
//         console.log(uid)
//     }
// }