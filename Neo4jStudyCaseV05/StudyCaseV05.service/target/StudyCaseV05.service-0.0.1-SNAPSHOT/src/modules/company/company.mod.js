(function (ng) {
    var mod = ng.module('companyModule', ['CrudModule']);

    mod.constant('company.context', 'companies');

    mod.constant('company.skipMock', false);

    mod.constant('companyModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            order: 1
        }]);
})(window.angular);