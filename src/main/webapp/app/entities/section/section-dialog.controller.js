(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('SectionDialogController', SectionDialogController);

    SectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Section'];

    function SectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Section) {
        var vm = this;

        vm.section = entity;
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
            if (vm.section.id !== null) {
                Section.update(vm.section, onSaveSuccess, onSaveError);
            } else {
                Section.save(vm.section, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:sectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
