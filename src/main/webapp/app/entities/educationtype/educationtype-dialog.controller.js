(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationtypeDialogController', EducationtypeDialogController);

    EducationtypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Educationtype'];

    function EducationtypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Educationtype) {
        var vm = this;

        vm.educationtype = entity;
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
            if (vm.educationtype.id !== null) {
                Educationtype.update(vm.educationtype, onSaveSuccess, onSaveError);
            } else {
                Educationtype.save(vm.educationtype, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:educationtypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
