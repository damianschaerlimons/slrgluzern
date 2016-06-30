(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AssessmentDialogController', AssessmentDialogController);

    AssessmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Assessment', 'Member'];

    function AssessmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Assessment, Member) {
        var vm = this;

        vm.assessment = entity;
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
            if (vm.assessment.id !== null) {
                Assessment.update(vm.assessment, onSaveSuccess, onSaveError);
            } else {
                Assessment.save(vm.assessment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:assessmentUpdate', result);
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
