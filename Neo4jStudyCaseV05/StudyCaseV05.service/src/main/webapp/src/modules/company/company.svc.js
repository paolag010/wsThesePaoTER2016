(function (ng) {
    var mod = ng.module('companyModule');

    mod.service('companyService', ['CRUDBase', 'company.context', function (CRUDBase, ctx) {
            this.url = ctx;
            CRUDBase.extendService(this);
            
            /*
            this.getMostPopulatedEmployees = function () {
                return this.api.customGET('mostPopulatedEmployees');
            };
            */
            
        }]);
})(window.angular);
