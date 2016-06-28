(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('FurtherEducationDeleteController',FurtherEducationDeleteController);

    FurtherEducationDeleteController.$inject = ['$uibModalInstance', 'entity', 'FurtherEducation'];

    function FurtherEducationDeleteController($uibModalInstance, entity, FurtherEducation) {
        var vm = this;

        vm.furtherEducation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FurtherEducation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
