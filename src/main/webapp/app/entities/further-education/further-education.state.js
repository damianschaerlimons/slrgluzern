(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('further-education', {
            parent: 'entity',
            url: '/further-education',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.furtherEducation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/further-education/further-educations.html',
                    controller: 'FurtherEducationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('furtherEducation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('further-education-detail', {
            parent: 'entity',
            url: '/further-education/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.furtherEducation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/further-education/further-education-detail.html',
                    controller: 'FurtherEducationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('furtherEducation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FurtherEducation', function($stateParams, FurtherEducation) {
                    return FurtherEducation.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('further-education.new', {
            parent: 'further-education',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/further-education/further-education-dialog.html',
                    controller: 'FurtherEducationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                niveau: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('further-education', null, { reload: true });
                }, function() {
                    $state.go('further-education');
                });
            }]
        })
        .state('further-education.edit', {
            parent: 'further-education',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/further-education/further-education-dialog.html',
                    controller: 'FurtherEducationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FurtherEducation', function(FurtherEducation) {
                            return FurtherEducation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('further-education', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('further-education.delete', {
            parent: 'further-education',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/further-education/further-education-delete-dialog.html',
                    controller: 'FurtherEducationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FurtherEducation', function(FurtherEducation) {
                            return FurtherEducation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('further-education', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
