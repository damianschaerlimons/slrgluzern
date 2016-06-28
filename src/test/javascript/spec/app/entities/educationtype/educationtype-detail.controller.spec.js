'use strict';

describe('Controller Tests', function() {

    describe('Educationtype Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEducationtype;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEducationtype = jasmine.createSpy('MockEducationtype');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Educationtype': MockEducationtype
            };
            createController = function() {
                $injector.get('$controller')("EducationtypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'slrgApp:educationtypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
