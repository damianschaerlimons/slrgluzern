(function() {
    'use strict';
    angular
        .module('slrgApp')
        .factory('Member', Member);

    Member.$inject = ['$resource', 'DateUtils'];

    function Member ($resource, DateUtils) {
        var resourceUrl =  'api/members/:id/:type';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthday = DateUtils.convertLocalDateFromServer(data.birthday);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.birthday = DateUtils.convertLocalDateToServer(data.birthday);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.birthday = DateUtils.convertLocalDateToServer(data.birthday);
                    return angular.toJson(data);
                }
                 },
             'education': {
                 method: 'GET',
                 isArray: true,
 //                transformRequest: function (data) {
 //                                    data.birthday = DateUtils.convertLocalDateToServer(data.birthday);
 //                                    return angular.toJson(data);
 //                                },
                 params: {
                     type: 'educations'
                 }
             },
              'appearances': {
                  method: 'GET',
                  isArray: true,
  //                transformRequest: function (data) {
  //                                    data.birthday = DateUtils.convertLocalDateToServer(data.birthday);
  //                                    return angular.toJson(data);
  //                                },
                  params: {
                      type: 'appearances'
                  }
             },
               'furtheredu': {
                   method: 'GET',
                   isArray: true,
   //                transformRequest: function (data) {
   //                                    data.birthday = DateUtils.convertLocalDateToServer(data.birthday);
   //                                    return angular.toJson(data);
   //                                },
                   params: {
                       type: 'furtheredu'
                   }
              },
             'assessment': {
                 method: 'GET',
                 isArray: true,
 //                transformRequest: function (data) {
 //                                    data.birthday = DateUtils.convertLocalDateToServer(data.birthday);
 //                                    return angular.toJson(data);
 //                                },
                 params: {
                     type: 'assessments'
                 }
            },
            'search' : {

                method: 'GET',
                isArray: true,
                params: {
                    type: 'search'
                }
            },
            'export': {
                method: 'GET',
                params: {
                    type: 'export'
                }
            }

        });
    }
})();
