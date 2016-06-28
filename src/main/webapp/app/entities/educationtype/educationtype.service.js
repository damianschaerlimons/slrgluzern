(function() {
    'use strict';
    angular
        .module('slrgApp')
        .factory('Educationtype', Educationtype);

    Educationtype.$inject = ['$resource'];

    function Educationtype ($resource) {
        var resourceUrl =  'api/educationtypes/:id';

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
