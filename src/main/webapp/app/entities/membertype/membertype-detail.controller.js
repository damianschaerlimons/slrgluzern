(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MembertypeDetailController', MembertypeDetailController);

    MembertypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Membertype'];

    function MembertypeDetailController($scope, $rootScope, $stateParams, entity, Membertype) {
        var vm = this;

        vm.membertype = entity;

        var unsubscribe = $rootScope.$on('slrgApp:membertypeUpdate', function(event, result) {
            vm.membertype = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
