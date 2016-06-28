(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('appearances', {
            parent: 'entity',
            url: '/appearances',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.appearances.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/appearances/appearances.html',
                    controller: 'AppearancesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('appearances');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('appearances-detail', {
            parent: 'entity',
            url: '/appearances/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.appearances.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/appearances/appearances-detail.html',
                    controller: 'AppearancesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('appearances');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Appearances', function($stateParams, Appearances) {
                    return Appearances.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('appearances.new', {
            parent: 'appearances',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appearances/appearances-dialog.html',
                    controller: 'AppearancesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                valid: null,
                                hours: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('appearances', null, { reload: true });
                }, function() {
                    $state.go('appearances');
                });
            }]
        })
        .state('appearances.edit', {
            parent: 'appearances',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appearances/appearances-dialog.html',
                    controller: 'AppearancesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Appearances', function(Appearances) {
                            return Appearances.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('appearances', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('appearances.delete', {
            parent: 'appearances',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appearances/appearances-delete-dialog.html',
                    controller: 'AppearancesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Appearances', function(Appearances) {
                            return Appearances.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('appearances', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
