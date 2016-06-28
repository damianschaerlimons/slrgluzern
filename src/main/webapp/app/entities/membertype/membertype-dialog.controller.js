(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MembertypeDialogController', MembertypeDialogController);

    MembertypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Membertype'];

    function MembertypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Membertype) {
        var vm = this;

        vm.membertype = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.membertype.id !== null) {
                Membertype.update(vm.membertype, onSaveSuccess, onSaveError);
            } else {
                Membertype.save(vm.membertype, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:membertypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
