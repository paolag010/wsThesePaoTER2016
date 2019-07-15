(function (ng) {
    var mod = ng.module('departmentModule', ['CrudModule', 'companyModule']);

    mod.constant('department.context', 'departments');
    
    mod.constant('departmentModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            order: 1
        }, {
            name: 'company',
            displayName: 'Company',
            type: 'Computed',
            order: 2,
            compute: function (record) {
                return record.company;
            }
        }]);
})(window.angular);