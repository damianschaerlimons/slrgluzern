(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('EducationController', EducationController);

    EducationController.$inject = ['$scope', '$state', 'Education'];

    function EducationController ($scope, $state, Education) {
        var vm = this;

        vm.educations = [];
        vm.checkDate = checkDate;
        vm.checkEqual= checkEqual;

        loadAll();

        function checkDate(date) {
            var now = new Date();
            var year = now.getFullYear();

            var dateYear = new Date(date).getFullYear();

            if(year < dateYear){
                return true;
            }

            if(year > dateYear){
                return false;
            }


            return null;
        }


        function checkEqual(date) {
                    var now = new Date();
                    var year = now.getFullYear();

                    var dateYear = new Date(date).getFullYear();

                    if(year == dateYear){
                        return true;
                    }


                    return false;
                }

        function loadAll() {
            Education.query(function(result) {
                vm.educations = result;
            });
        }
    }
})();
