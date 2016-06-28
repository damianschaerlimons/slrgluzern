(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MembertypeController', MembertypeController);

    MembertypeController.$inject = ['$scope', '$state', 'Membertype'];

    function MembertypeController ($scope, $state, Membertype) {
        var vm = this;
        
        vm.membertypes = [];

        loadAll();

        function loadAll() {
            Membertype.query(function(result) {
                vm.membertypes = result;
            });
        }
    }
})();
