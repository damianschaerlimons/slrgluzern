(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AssessmentDeleteController',AssessmentDeleteController);

    AssessmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Assessment'];

    function AssessmentDeleteController($uibModalInstance, entity, Assessment) {
        var vm = this;

        vm.assessment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Assessment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
