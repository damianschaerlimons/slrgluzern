(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('SectionController', SectionController);

    SectionController.$inject = ['$scope', '$state', 'Section'];

    function SectionController ($scope, $state, Section) {
        var vm = this;
        
        vm.sections = [];

        loadAll();

        function loadAll() {
            Section.query(function(result) {
                vm.sections = result;
            });
        }
    }
})();
