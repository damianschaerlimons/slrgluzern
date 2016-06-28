(function() {
    'use strict';
    angular
        .module('slrgApp')
        .factory('AppearancesType', AppearancesType);

    AppearancesType.$inject = ['$resource'];

    function AppearancesType ($resource) {
        var resourceUrl =  'api/appearances-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
