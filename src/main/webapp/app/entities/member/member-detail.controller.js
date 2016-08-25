(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MemberDetailController', MemberDetailController);

    MemberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Member', 'Membertype', 'Section'];

    function MemberDetailController($scope, $rootScope, $stateParams, entity, Member, Membertype, Section) {
        var vm = this;

        vm.member = entity;
        vm.checkDate = checkDate;
                vm.checkEqual= checkEqual;


        var unsubscribe = $rootScope.$on('slrgApp:memberUpdate', function(event, result) {
            vm.member = result;
        });

          console.log(entity);
        Member.education({id: entity.id},function(data){
           vm.educations = data;
        });

        Member.appearances({id: entity.id}, function(data){
         console.log(data);
           vm.appearances = data;
        });

        Member.furtheredu({id: entity.id}, function(data){
           vm.furtherEducations = data;
        });

         Member.assessment({id: entity.id}, function(data){
           vm.assessments = data;
         });


        function checkDate(date) {
            var now = new Date();
            var year = now.getFullYear();

            var dateYear = new Date(date).getFullYear();

            if(year < dateYear){
                return true;
            }

            if(year > dateYear){
                return false;
            }
            return null;
        }

        function checkEqual(date) {
                    var now = new Date();
                    var year = now.getFullYear();

                    var dateYear = new Date(date).getFullYear();

                    if(year == dateYear){
                        return true;
                    }
                    return false;
                }

        $scope.$on('$destroy', unsubscribe);
    }
})();
