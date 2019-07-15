(function(ng){
    var mod = ng.module('companyMasterModule');
    
    mod.service('companyMasterService', ['masterUtils', 'companyMasterModule.context', function(utils, ctx){
            this.url = ctx;
            utils.extendService(this);
            
            this.getMostPopulatedEmployees = function () {
                return this.api.customGET('mostPopulatedEmployees');
            };
            
    }]);
})(window.angular);