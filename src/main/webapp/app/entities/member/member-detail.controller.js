(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MemberDetailController', MemberDetailController);

    MemberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Member', 'Membertype'];

    function MemberDetailController($scope, $rootScope, $stateParams, entity, Member, Membertype) {
        var vm = this;

        vm.member = entity;
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


        var unsubscribe = $rootScope.$on('slrgApp:memberUpdate', function(event, result) {
            vm.member = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
