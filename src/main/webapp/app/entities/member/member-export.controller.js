(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MemberExportController', MemberExportController);

    MemberExportController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity' ,'Member', 'Membertype', 'Section'];

    function MemberExportController ($timeout, $scope, $stateParams, $uibModalInstance, entity,Member, Membertype, Section) {
        var vm = this;

        vm.clear = clear;
        vm.members = Member.query();
        vm.member = entity;
        vm.Member = Member;
        vm.membertypes = Membertype.query();
        vm.sections = Section.query();
        vm.search = search;
        vm.exportMembers = exportMembers;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function exportMembers () {
           vm.Member.export(vm.member);
        }

        function search () {
        console.log("search");
            vm.members = vm.Member.search(vm.member);
        }

        function onSaveSuccess (result) {
            $scope.$emit('slrgApp:memberUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

//        vm.datePickerOpenStatus.birthday = false;
//
//        function openCalendar (date) {
//            vm.datePickerOpenStatus[date] = true;
//        }
    }
})();
