(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationtypeDetailController', EducationtypeDetailController);

    EducationtypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Educationtype'];

    function EducationtypeDetailController($scope, $rootScope, $stateParams, entity, Educationtype) {
        var vm = this;

        vm.educationtype = entity;

        var unsubscribe = $rootScope.$on('slrgApp:educationtypeUpdate', function(event, result) {
            vm.educationtype = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
