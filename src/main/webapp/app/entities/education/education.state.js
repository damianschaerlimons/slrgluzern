(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('education', {
            parent: 'entity',
            url: '/education',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.education.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/education/educations.html',
                    controller: 'EducationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('education');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('education-detail', {
            parent: 'entity',
            url: '/education/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.education.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/education/education-detail.html',
                    controller: 'EducationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('education');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Education', function($stateParams, Education) {
                    return Education.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('education.new', {
            parent: 'education',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education/education-dialog.html',
                    controller: 'EducationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                note: null,
                                valid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('education', null, { reload: true });
                }, function() {
                    $state.go('education');
                });
            }]
        })
        .state('education.edit', {
            parent: 'education',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education/education-dialog.html',
                    controller: 'EducationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Education', function(Education) {
                            return Education.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('education', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('education.delete', {
            parent: 'education',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/education/education-delete-dialog.html',
                    controller: 'EducationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Education', function(Education) {
                            return Education.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('education', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
