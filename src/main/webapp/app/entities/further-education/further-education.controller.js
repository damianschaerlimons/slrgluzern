(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('FurtherEducationController', FurtherEducationController);

    FurtherEducationController.$inject = ['$scope', '$state', 'FurtherEducation'];

    function FurtherEducationController ($scope, $state, FurtherEducation) {
        var vm = this;
        
        vm.furtherEducations = [];

        loadAll();

        function loadAll() {
            FurtherEducation.query(function(result) {
                vm.furtherEducations = result;
            });
        }
    }
})();
