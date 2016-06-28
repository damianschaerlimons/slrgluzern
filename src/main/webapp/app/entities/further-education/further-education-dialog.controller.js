(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('FurtherEducationDialogController', FurtherEducationDialogController);

    FurtherEducationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FurtherEducation', 'Member'];

    function FurtherEducationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FurtherEducation, Member) {
        var vm = this;

        vm.furtherEducation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.members = Member.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.furtherEducation.id !== null) {
                FurtherEducation.update(vm.furtherEducation, onSaveSuccess, onSaveError);
            } else {
                FurtherEducation.save(vm.furtherEducation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:furtherEducationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
