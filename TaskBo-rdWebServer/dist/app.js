define(["require", "exports"], function (require, exports) {
    "use strict";
    var App = (function () {
        function App() {
        }
        App.prototype.configureRouter = function (config, router) {
            config.title = 'TaskBo-rd';
            config.map([
                { route: ['', 'groups'], name: 'groups', moduleId: 'groups', nav: true, title: 'Groups' },
                { route: 'tasks/:id', name: 'tasks', moduleId: 'tasks', nav: false, title: 'View tasks in group', href: 'tasks' }
            ]);
            this.router = router;
        };
        return App;
    }());
    exports.App = App;
});

//# sourceMappingURL=app.js.map
