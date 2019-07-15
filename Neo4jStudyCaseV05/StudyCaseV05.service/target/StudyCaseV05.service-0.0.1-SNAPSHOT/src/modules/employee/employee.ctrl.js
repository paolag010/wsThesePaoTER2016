(function (angular) {
    var employeeModule = angular.module('employeeModule');

    employeeModule.controller('employeeCtrl', ['$scope', 'employeeService','companyService','departmentService', 'employeeModel', function ($scope, employeeSvc, companySvc, departmentSvc, model) {
            
            $scope.model = model;
            employeeSvc.extendCtrl(this, $scope);
            this.fetchRecords();
            this.getTaxesSalary = function (employee) {
                return (employee.salary) * 1;
            };
            var getCompanyName = function(record){
                for (var i in $scope.companyRecords) {
                    if ($scope.companyRecords[i].id === record.company) {
                        return $scope.companyRecords[i].name;
                    }
                }
                return;
            };
            var getDepartmentName = function(record){
                for (var i in $scope.departmentRecords) {
                    if ($scope.departmentRecords[i].id === record.department) {
                        return $scope.departmentRecords[i].name;
                    }
                }
                return;
            };            
            companySvc.fetchRecords().then(function(data){
                $scope.companyRecords = data.plain();
                for(var i in model){
                    if(model.hasOwnProperty(i) && model[i].name === 'company'){
                        model[i].compute = getCompanyName;
                        return;
                    }
                }
            });
            departmentSvc.fetchRecords().then(function(data){
                $scope.departmentRecords = data.plain();
                for(var i in model){
                    if(model.hasOwnProperty(i) && model[i].name === 'department'){
                        model[i].compute = getDepartmentName;
                        return;
                    }
                }
            });
            
        }]);
})(window.angular);