'use strict';

describe('Controller Tests', function() {

    describe('Member Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMember, MockMembertype;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMember = jasmine.createSpy('MockMember');
            MockMembertype = jasmine.createSpy('MockMembertype');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Member': MockMember,
                'Membertype': MockMembertype
            };
            createController = function() {
                $injector.get('$controller')("MemberDetailController", locals);
            };
        }));


//        describe('Root Scope Listening', function() {
//            it('Unregisters root scope listener upon scope destruction', function() {
//                var eventType = 'slrgApp:memberUpdate';
//
//                createController();
//                expect($rootScope.$$listenerCount[eventType]).toEqual(1);
//
//                $scope.$destroy();
//                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
//            });
//        });
    });

});
