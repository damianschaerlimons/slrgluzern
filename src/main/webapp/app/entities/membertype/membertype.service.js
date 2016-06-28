(function() {
    'use strict';
    angular
        .module('slrgApp')
        .factory('Membertype', Membertype);

    Membertype.$inject = ['$resource'];

    function Membertype ($resource) {
        var resourceUrl =  'api/membertypes/:id';

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
