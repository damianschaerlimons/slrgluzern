(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('membertype', {
            parent: 'entity',
            url: '/membertype',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.membertype.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/membertype/membertypes.html',
                    controller: 'MembertypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('membertype');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('membertype-detail', {
            parent: 'entity',
            url: '/membertype/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.membertype.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/membertype/membertype-detail.html',
                    controller: 'MembertypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('membertype');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Membertype', function($stateParams, Membertype) {
                    return Membertype.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('membertype.new', {
            parent: 'membertype',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/membertype/membertype-dialog.html',
                    controller: 'MembertypeDialogController',
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
                    $state.go('membertype', null, { reload: true });
                }, function() {
                    $state.go('membertype');
                });
            }]
        })
        .state('membertype.edit', {
            parent: 'membertype',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/membertype/membertype-dialog.html',
                    controller: 'MembertypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Membertype', function(Membertype) {
                            return Membertype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('membertype', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('membertype.delete', {
            parent: 'membertype',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/membertype/membertype-delete-dialog.html',
                    controller: 'MembertypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Membertype', function(Membertype) {
                            return Membertype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('membertype', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
