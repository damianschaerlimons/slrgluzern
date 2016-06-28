(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesDeleteController',AppearancesDeleteController);

    AppearancesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Appearances'];

    function AppearancesDeleteController($uibModalInstance, entity, Appearances) {
        var vm = this;

        vm.appearances = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Appearances.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
