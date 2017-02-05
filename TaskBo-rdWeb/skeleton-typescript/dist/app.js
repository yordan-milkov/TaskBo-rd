define(["require", "exports"], function (require, exports) {
    "use strict";
    var App = (function () {
        function App() {
        }
        App.prototype.configureRouter = function (config, router) {
            config.title = 'TaskBo-rd';
            config.map([
                { route: ['', 'home'], name: 'home', moduleId: 'home', nav: true, title: 'Home' },
                { route: 'group-tasks/:id', name: 'group-tasks', moduleId: 'group-tasks', nav: false, title: 'View tasks in group', href: 'group-tasks' }
            ]);
            this.router = router;
        };
        return App;
    }());
    exports.App = App;
});

//# sourceMappingURL=app.js.map
