(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('SectionDetailController', SectionDetailController);

    SectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Section'];

    function SectionDetailController($scope, $rootScope, $stateParams, entity, Section) {
        var vm = this;

        vm.section = entity;

        var unsubscribe = $rootScope.$on('slrgApp:sectionUpdate', function(event, result) {
            vm.section = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
