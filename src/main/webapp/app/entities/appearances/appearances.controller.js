(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AppearancesController', AppearancesController);

    AppearancesController.$inject = ['$scope', '$state', 'Appearances'];

    function AppearancesController ($scope, $state, Appearances) {
        var vm = this;
        
        vm.appearances = [];

        loadAll();

        function loadAll() {
            Appearances.query(function(result) {
                vm.appearances = result;
            });
        }
    }
})();
