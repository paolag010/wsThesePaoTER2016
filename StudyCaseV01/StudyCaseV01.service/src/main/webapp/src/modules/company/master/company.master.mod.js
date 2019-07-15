(function (ng) {
    var mod = ng.module('companyMasterModule', ['companyModule', 'employeeModule', 'departmentModule', 'masterModule']);

    mod.constant('companyMasterModule.context', 'companies/master');
})(window.angular);