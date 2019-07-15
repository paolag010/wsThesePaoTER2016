(function (ng) {
    var mod = ng.module('employeeModule');

    mod.service('employeeService', ['CRUDBase', 'employee.context', function (CRUDBase, ctx) {
            this.url = ctx;
            CRUDBase.extendService(this);
        }]);
})(window.angular);
