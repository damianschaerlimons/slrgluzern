'use strict';

describe('Controller Tests', function() {

    describe('Education Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEducation, MockMember, MockEducationtype;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEducation = jasmine.createSpy('MockEducation');
            MockMember = jasmine.createSpy('MockMember');
            MockEducationtype = jasmine.createSpy('MockEducationtype');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Education': MockEducation,
                'Member': MockMember,
                'Educationtype': MockEducationtype
            };
            createController = function() {
                $injector.get('$controller')("EducationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'slrgApp:educationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
