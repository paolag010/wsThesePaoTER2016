(function (ng) {
    var mod = ng.module('employeeModule', ['CrudModule', 'companyModule', 'departmentModule']);

    mod.constant('employee.context', 'employees');
    
    mod.constant('employeeModel', [{
            name: 'name',
            displayName: 'Name',
            type: 'String',
            order: 1
        }, {
            name: 'salary',
            displayName: 'Salary',
            type: 'Integer',
            order: 2
        }, {
            name: 'taxes',
            displayName: 'Taxes',
            type: 'Computed',
            order: 4,
            compute: function (record) {
                return record.salary * 0.1;
            }
        }, {
            name: 'address',
            displayName: 'Address',
            type: 'String',
            order: 5
        }, {
            name: 'company',
            displayName: 'Company',
            type: 'Computed',
            order: 6,
            compute: function (record) {
                return record.company;
            }
        }, {
            name: 'department',
            displayName: 'Department',
            type: 'Computed',
            order: 7,
            compute: function (record) {
                return record.department;
            }
        }]);
})(window.angular);