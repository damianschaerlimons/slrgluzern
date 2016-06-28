(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationController', EducationController);

    EducationController.$inject = ['$scope', '$state', 'Education'];

    function EducationController ($scope, $state, Education) {
        var vm = this;
        
        vm.educations = [];

        loadAll();

        function loadAll() {
            Education.query(function(result) {
                vm.educations = result;
            });
        }
    }
})();
