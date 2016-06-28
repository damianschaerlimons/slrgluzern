(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('appearances-type', {
            parent: 'entity',
            url: '/appearances-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.appearancesType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/appearances-type/appearances-types.html',
                    controller: 'AppearancesTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('appearancesType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('appearances-type-detail', {
            parent: 'entity',
            url: '/appearances-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.appearancesType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/appearances-type/appearances-type-detail.html',
                    controller: 'AppearancesTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('appearancesType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AppearancesType', function($stateParams, AppearancesType) {
                    return AppearancesType.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('appearances-type.new', {
            parent: 'appearances-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appearances-type/appearances-type-dialog.html',
                    controller: 'AppearancesTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('appearances-type', null, { reload: true });
                }, function() {
                    $state.go('appearances-type');
                });
            }]
        })
        .state('appearances-type.edit', {
            parent: 'appearances-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appearances-type/appearances-type-dialog.html',
                    controller: 'AppearancesTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AppearancesType', function(AppearancesType) {
                            return AppearancesType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('appearances-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('appearances-type.delete', {
            parent: 'appearances-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/appearances-type/appearances-type-delete-dialog.html',
                    controller: 'AppearancesTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AppearancesType', function(AppearancesType) {
                            return AppearancesType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('appearances-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
