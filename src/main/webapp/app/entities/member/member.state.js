(function() {
    'use strict';

    angular
        .module('slrgApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('member', {
            parent: 'entity',
            url: '/member',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.member.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/member/members.html',
                    controller: 'MemberController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('member');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('member-detail', {
            parent: 'entity',
            url: '/member/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'slrgApp.member.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/member/member-detail.html',
                    controller: 'MemberDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('member');
                    $translatePartialLoader.addPart('education');
                    $translatePartialLoader.addPart('appearances');
                    $translatePartialLoader.addPart('furtherEducation');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('assessment');

                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Member', function($stateParams, Member) {
                    return Member.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('member.new', {
            parent: 'member',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/member/member-dialog.html',
                    controller: 'MemberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                lastname: null,
                                birthday: null,
                                brevetnr: null,
                                adress: null,
                                plz: null,
                                place: null,
                                aquateam: false,
                                skipper: false,
                                boatdriver: false,
                                rescue: false,
                                phone: null,
                                email: null,
                                ownboat: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('member', null, { reload: true });
                }, function() {
                    $state.go('member');
                });
            }]
        })
        .state('member.edit', {
            parent: 'member',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/member/member-dialog.html',
                    controller: 'MemberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Member', function(Member) {
                            return Member.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('member', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
           .state('member.export', {
                    parent: 'member',
                    url: '/export',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/member/member-export.html',
                            controller: 'MemberExportController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                               entity: function () {
                                   return {
                                       aquateam: false,
                                       skipper: false,
                                       boatdriver: false,
                                       rescue: false,
                                       ownboat: false,
                                   };
                               }
                            }
                        }).result.then(function() {
                            $state.go('member', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        });
                    }]
                })
        .state('member.delete', {
            parent: 'member',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/member/member-delete-dialog.html',
                    controller: 'MemberDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Member', function(Member) {
                            return Member.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('member', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        }).state('member.editAssessment', {
                      parent: 'member',
                      url: '/{id}/assessment/{assId}',
                      data: {
                          authorities: ['ROLE_USER']
                      },
                      onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                          $uibModal.open({
                              templateUrl: 'app/entities/assessment/assessment-dialog.html',
                              controller: 'AssessmentDialogController',
                              controllerAs: 'vm',
                              backdrop: 'static',
                              size: 'lg',
                              resolve: {
                                  entity: ['Assessment', function(Assessment) {
                                      return Assessment.get({id : $stateParams.assId}).$promise;
                                  }]

                              }
                          }).result.then(function() {
                              $state.go('member-detail', {id: $stateParams.id}, { reload: true, location: true });
                          }, function() {
                              $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                          });
                      }]
          }).state('member.deleteAssessment', {
                        parent: 'member',
                        url: '/{id}/assessment/{assId}/delete',
                        data: {
                            authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'app/entities/assessment/assessment-delete-dialog.html',
                                controller: 'AssessmentDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Assessment', function(Assessment) {
                                        return Assessment.get({id : $stateParams.assId}).$promise;
                                    }]
                                }
                            }).result.then(function() {
                                $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                            }, function() {
                                $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                            });
                        }]
        }).state('member.newAssessment', {
              parent: 'member',
              url: '/{id}/assessment',
              data: {
                  authorities: ['ROLE_USER']
              },
              onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                  $uibModal.open({
                      templateUrl: 'app/entities/assessment/assessment-dialog.html',
                      controller: 'AssessmentDialogController',
                      controllerAs: 'vm',
                      backdrop: 'static',
                      size: 'lg',
                      resolve: {
                          entity: function () {
                              return {
                                  description: null,
                                  date: null,
                                  furthereducation: null,
                                  member: {id: $stateParams.id},
                                  id: null
                              };
                          }
                      }
                  }).result.then(function() {
                      $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                  }, function() {
                      $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                  });
              }]
          }).state('member.further-education-new', {
            parent: 'member',
            url: '/{id}/further-education/new',
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
                                member: {id: $stateParams.id},
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                }, function() {
                    $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                });
            }]
        })
            .state('member.further-education-edit', {
                parent: 'member',
                url: '/{id}/further-education/{eduId}/edit',
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
                                return FurtherEducation.get({id : $stateParams.eduId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    });
                }]
            })
            .state('member.further-education-delete', {
                parent: 'member',
                url: '/{id}/further-education/{eduId}/delete',
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
                                return FurtherEducation.get({id : $stateParams.eduId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    });
                }]
            }).state('member.appearances-new', {
            parent: 'member',
            url: '/{id}/appearances/new',
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
                                member: {id: $stateParams.id},
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                }, function() {
                    $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                });
            }]
        })
            .state('member.appearances-edit', {
                parent: 'member',
                url: '/{id}/appearances/{appId}/edit',
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
                                return Appearances.get({id : $stateParams.appId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    });
                }]
            })
            .state('member.appearances-delete', {
                parent: 'member',
                url: '/{id}/appearances/{appId}/delete',
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
                                return Appearances.get({id : $stateParams.appId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    });
                }]
            }).state('member.education-new', {
            parent: 'member',
            url: '/{id}/education/new',
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
                                member: {id: $stateParams.id},
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                }, function() {
                    $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                });
            }]
        })
            .state('member.education-edit', {
                parent: 'member',
                url: '/{id}/education/{eduId}/edit',
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
                                return Education.get({id : $stateParams.eduId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    });
                }]
            })
            .state('member.education-delete', {
                parent: 'member',
                url: '/{id}/education/{eduId}/delete',
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
                                return Education.get({id : $stateParams.eduId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    }, function() {
                        $state.go('member-detail', {id: $stateParams.id}, { reload: true });
                    });
                }]
            });

    }

})();
