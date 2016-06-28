(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesDialogController', AppearancesDialogController);

    AppearancesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Appearances', 'Member', 'AppearancesType'];

    function AppearancesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Appearances, Member, AppearancesType) {
        var vm = this;

        vm.appearances = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.members = Member.query();
        vm.appearancestypes = AppearancesType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.appearances.id !== null) {
                Appearances.update(vm.appearances, onSaveSuccess, onSaveError);
            } else {
                Appearances.save(vm.appearances, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:appearancesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.valid = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
