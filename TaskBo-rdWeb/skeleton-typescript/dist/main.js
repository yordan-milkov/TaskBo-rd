define(["require", "exports", "aurelia-framework", "./login", "bootstrap"], function (require, exports, aurelia_framework_1, login_1) {
    "use strict";
    function configure(aurelia) {
        aurelia.use
            .standardConfiguration()
            .developmentLogging();
        console.log(aurelia_framework_1.DOM);
        aurelia.start().then(function (a) {
            if (aurelia.container.get(login_1.Login).isLoggedIn) {
                a.setRoot('app');
            }
            else {
                a.setRoot('login');
            }
        });
    }
    exports.configure = configure;
});

//# sourceMappingURL=main.js.map
