(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationDialogController', EducationDialogController);

    EducationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Education', 'Member', 'Educationtype'];

    function EducationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Education, Member, Educationtype) {
        var vm = this;

        vm.education = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.members = Member.query();
        vm.educationtypes = Educationtype.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.education.id !== null) {
                Education.update(vm.education, onSaveSuccess, onSaveError);
            } else {
                Education.save(vm.education, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:educationUpdate', result);
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
