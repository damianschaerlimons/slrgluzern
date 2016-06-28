(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MembertypeDeleteController',MembertypeDeleteController);

    MembertypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Membertype'];

    function MembertypeDeleteController($uibModalInstance, entity, Membertype) {
        var vm = this;

        vm.membertype = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Membertype.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
