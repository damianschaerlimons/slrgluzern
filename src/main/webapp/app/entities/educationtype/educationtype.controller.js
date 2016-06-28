(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationtypeController', EducationtypeController);

    EducationtypeController.$inject = ['$scope', '$state', 'Educationtype'];

    function EducationtypeController ($scope, $state, Educationtype) {
        var vm = this;
        
        vm.educationtypes = [];

        loadAll();

        function loadAll() {
            Educationtype.query(function(result) {
                vm.educationtypes = result;
            });
        }
    }
})();
