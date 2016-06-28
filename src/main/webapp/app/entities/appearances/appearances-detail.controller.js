(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesDetailController', AppearancesDetailController);

    AppearancesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Appearances', 'Member', 'AppearancesType'];

    function AppearancesDetailController($scope, $rootScope, $stateParams, entity, Appearances, Member, AppearancesType) {
        var vm = this;

        vm.appearances = entity;

        var unsubscribe = $rootScope.$on('slrgApp:appearancesUpdate', function(event, result) {
            vm.appearances = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
