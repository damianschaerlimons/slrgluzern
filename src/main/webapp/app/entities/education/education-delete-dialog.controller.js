(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationDeleteController',EducationDeleteController);

    EducationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Education'];

    function EducationDeleteController($uibModalInstance, entity, Education) {
        var vm = this;

        vm.education = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Education.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
