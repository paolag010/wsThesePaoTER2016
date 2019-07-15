(function (ng) {

    var mainApp = ng.module('mainApp', ['ngRoute', 'employeeModule', 'companyModule','companyMasterModule','departmentModule']);

    mainApp.config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/employee', {
                templateUrl: 'src/modules/employee/employee.tpl.html',
                controller: 'employeeCtrl',
                controllerAs: 'employeeCtrl'
            }).when('/company/master', {
                templateUrl: 'src/modules/company/master/company.master.tpl.html',
                controller: 'companyMasterCtrl',
                controllerAs: 'companyCtrl'
            }).when('/department', {
                templateUrl: 'src/modules/department/department.tpl.html',
                controller: 'departmentCtrl',
                controllerAs: 'departmentCtrl'
            }).otherwise('/');
        }]);
})(window.angular);