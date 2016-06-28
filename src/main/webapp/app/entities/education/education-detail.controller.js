(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationDetailController', EducationDetailController);

    EducationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Education', 'Member', 'Educationtype'];

    function EducationDetailController($scope, $rootScope, $stateParams, entity, Education, Member, Educationtype) {
        var vm = this;

        vm.education = entity;

        var unsubscribe = $rootScope.$on('slrgApp:educationUpdate', function(event, result) {
            vm.education = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
