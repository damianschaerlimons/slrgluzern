(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('AssessmentDetailController', AssessmentDetailController);

    AssessmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Assessment', 'Member'];

    function AssessmentDetailController($scope, $rootScope, $stateParams, entity, Assessment, Member) {
        var vm = this;

        vm.assessment = entity;

        var unsubscribe = $rootScope.$on('slrgApp:assessmentUpdate', function(event, result) {
            vm.assessment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
