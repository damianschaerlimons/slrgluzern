(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesTypeDetailController', AppearancesTypeDetailController);

    AppearancesTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'AppearancesType'];

    function AppearancesTypeDetailController($scope, $rootScope, $stateParams, entity, AppearancesType) {
        var vm = this;

        vm.appearancesType = entity;

        var unsubscribe = $rootScope.$on('slrgApp:appearancesTypeUpdate', function(event, result) {
            vm.appearancesType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
