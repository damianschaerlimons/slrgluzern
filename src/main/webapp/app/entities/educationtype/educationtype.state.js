(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('educationtype', {
            parent: 'entity',
            url: '/educationtype',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.educationtype.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/educationtype/educationtypes.html',
                    controller: 'EducationtypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('educationtype');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('educationtype-detail', {
            parent: 'entity',
            url: '/educationtype/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.educationtype.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/educationtype/educationtype-detail.html',
                    controller: 'EducationtypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('educationtype');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Educationtype', function($stateParams, Educationtype) {
                    return Educationtype.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('educationtype.new', {
            parent: 'educationtype',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educationtype/educationtype-dialog.html',
                    controller: 'EducationtypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('educationtype', null, { reload: true });
                }, function() {
                    $state.go('educationtype');
                });
            }]
        })
        .state('educationtype.edit', {
            parent: 'educationtype',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educationtype/educationtype-dialog.html',
                    controller: 'EducationtypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Educationtype', function(Educationtype) {
                            return Educationtype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('educationtype', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('educationtype.delete', {
            parent: 'educationtype',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/educationtype/educationtype-delete-dialog.html',
                    controller: 'EducationtypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Educationtype', function(Educationtype) {
                            return Educationtype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('educationtype', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
