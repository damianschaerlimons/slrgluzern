(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('FurtherEducationDetailController', FurtherEducationDetailController);

    FurtherEducationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FurtherEducation', 'Member'];

    function FurtherEducationDetailController($scope, $rootScope, $stateParams, entity, FurtherEducation, Member) {
        var vm = this;

        vm.furtherEducation = entity;

        var unsubscribe = $rootScope.$on('slrgApp:furtherEducationUpdate', function(event, result) {
            vm.furtherEducation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
