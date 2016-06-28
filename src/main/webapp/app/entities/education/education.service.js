(function() {
    'use strict';
    angular
        .module('slrgApp')
        .factory('Education', Education);

    Education.$inject = ['$resource', 'DateUtils'];

    function Education ($resource, DateUtils) {
        var resourceUrl =  'api/educations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.valid = DateUtils.convertLocalDateFromServer(data.valid);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.valid = DateUtils.convertLocalDateToServer(data.valid);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.valid = DateUtils.convertLocalDateToServer(data.valid);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
