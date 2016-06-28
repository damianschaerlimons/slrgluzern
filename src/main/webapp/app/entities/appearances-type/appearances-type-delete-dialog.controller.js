(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesTypeDeleteController',AppearancesTypeDeleteController);

    AppearancesTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'AppearancesType'];

    function AppearancesTypeDeleteController($uibModalInstance, entity, AppearancesType) {
        var vm = this;

        vm.appearancesType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AppearancesType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
