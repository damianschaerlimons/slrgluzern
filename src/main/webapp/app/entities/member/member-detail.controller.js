(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MemberDetailController', MemberDetailController);

    MemberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Member', 'Membertype', 'Section'];

    function MemberDetailController($scope, $rootScope, $stateParams, entity, Member, Membertype, Section) {
        var vm = this;

        vm.member = entity;

        var unsubscribe = $rootScope.$on('slrgApp:memberUpdate', function(event, result) {
            vm.member = result;
        });

          console.log(entity);
        Member.education({id: entity.id},function(data){
           vm.educations = data;
        });

        Member.appearances({id: entity.id}, function(data){
           vm.appearances = data;
        });

        Member.furtheredu({id: entity.id}, function(data){
           vm.furtherEducations = data;
        });

         Member.assessment({id: entity.id}, function(data){
           vm.assessments = data;
         });


        $scope.$on('$destroy', unsubscribe);
    }
})();
