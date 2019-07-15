(function (ng) {
    var mod = ng.module('departmentModule');

    mod.service('departmentService', ['CRUDBase', 'department.context', function (CRUDBase, ctx) {
            this.url = ctx;
            CRUDBase.extendService(this);
        }]);
})(window.angular);
