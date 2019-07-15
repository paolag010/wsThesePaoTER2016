(function (ng) {
    var companyModule = ng.module('companyModule');

    companyModule.controller('companyCtrl', ['$scope', 'companyService', 'companyModel', function ($scope, svc, model) {
            $scope.model = model;
            svc.extendCtrl(this, $scope);
            this.fetchRecords();
            
            /*
            this.getMostPopulatedEmployees = function () {
                svc.getMostPopulatedEmployees().then(function (data) {
                    alert('The most populated company (number employees) is ' + data.name);
                }, function () {
                    alert('There are no companies with population on server');
                });
            };
            
            var self = this;
            this.globalActions.push({
                name: 'mostPopulatedEmployees',
                displayName: 'Most Populated Employees',
                icon: 'star',
                fn: function () {
                    self.getMostPopulatedEmployees();
                },
                show: function () {
                    return true;
                }
            });
            */
            
        }]);
})(window.angular);