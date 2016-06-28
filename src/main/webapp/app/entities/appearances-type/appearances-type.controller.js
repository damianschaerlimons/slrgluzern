(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesTypeController', AppearancesTypeController);

    AppearancesTypeController.$inject = ['$scope', '$state', 'AppearancesType'];

    function AppearancesTypeController ($scope, $state, AppearancesType) {
        var vm = this;
        
        vm.appearancesTypes = [];

        loadAll();

        function loadAll() {
            AppearancesType.query(function(result) {
                vm.appearancesTypes = result;
            });
        }
    }
})();
