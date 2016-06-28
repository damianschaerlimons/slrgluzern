'use strict';

describe('Controller Tests', function() {

    describe('FurtherEducation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFurtherEducation, MockMember;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFurtherEducation = jasmine.createSpy('MockFurtherEducation');
            MockMember = jasmine.createSpy('MockMember');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FurtherEducation': MockFurtherEducation,
                'Member': MockMember
            };
            createController = function() {
                $injector.get('$controller')("FurtherEducationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'slrgApp:furtherEducationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
