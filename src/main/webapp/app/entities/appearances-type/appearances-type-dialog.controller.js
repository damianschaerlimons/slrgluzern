(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesTypeDialogController', AppearancesTypeDialogController);

    AppearancesTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AppearancesType'];

    function AppearancesTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AppearancesType) {
        var vm = this;

        vm.appearancesType = entity;
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
            if (vm.appearancesType.id !== null) {
                AppearancesType.update(vm.appearancesType, onSaveSuccess, onSaveError);
            } else {
                AppearancesType.save(vm.appearancesType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:appearancesTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
