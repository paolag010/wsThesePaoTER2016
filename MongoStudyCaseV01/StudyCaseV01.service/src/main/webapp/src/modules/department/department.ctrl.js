(function (angular) {
    var departmentModule = angular.module('departmentModule');

    departmentModule.controller('departmentCtrl', ['$scope', 'departmentService','companyService', 'departmentModel', function ($scope, departmentSvc, companySvc, model) {
            
            $scope.model = model;
            departmentSvc.extendCtrl(this, $scope);
            this.fetchRecords();
            var getCompanyName = function(record){
                for (var i in $scope.companyRecords) {
                    if ($scope.companyRecords[i].id === record.company) {
                        return $scope.companyRecords[i].name;
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
        }]);
})(window.angular);