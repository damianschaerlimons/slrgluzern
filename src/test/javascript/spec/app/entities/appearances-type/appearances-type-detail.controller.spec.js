'use strict';

describe('Controller Tests', function() {

    describe('AppearancesType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAppearancesType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAppearancesType = jasmine.createSpy('MockAppearancesType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AppearancesType': MockAppearancesType
            };
            createController = function() {
                $injector.get('$controller')("AppearancesTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'slrgApp:appearancesTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
