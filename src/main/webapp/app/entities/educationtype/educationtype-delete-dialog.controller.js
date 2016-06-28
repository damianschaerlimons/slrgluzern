(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationtypeDeleteController',EducationtypeDeleteController);

    EducationtypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Educationtype'];

    function EducationtypeDeleteController($uibModalInstance, entity, Educationtype) {
        var vm = this;

        vm.educationtype = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Educationtype.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
