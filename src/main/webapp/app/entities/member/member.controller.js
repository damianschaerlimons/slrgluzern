(function() {
    'use strict';

    angular
        .module('slrgApp')
        .controller('MemberController', MemberController);

    MemberController.$inject = ['$scope', '$state', 'Member'];

    function MemberController ($scope, $state, Member) {
        var vm = this;

        vm.members = [];

        vm.filter = {
            text: '',
            aqua: false,
            skipper: false,
            boat: false,
            rescue: false,
            full: true
        };
        vm.download = download;


        function download () {
            var blob = new Blob([document.getElementById('exportable').innerHTML], {
                type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8" });

                saveAs(blob, "Report.xls");
        }

        loadAll();

        function loadAll() {
            Member.query(function(result) {
                vm.members = result;
            });
        }
    }
})();
