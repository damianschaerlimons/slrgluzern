/**
 * Created by damianschaerli on 07/07/16.
 */
(function() {
    'use strict';

    angular
        .module('slrgApp')
        .filter('selection', Selection);

    function Selection() {
        return SelectionFilter;

        function SelectionFilter (input, full, aqua, boat, rescue, skipper) {
            if(full){
                return input;
            }else {

                return input.filter(function(element, index, array) {
                    if((!aqua || element.aquateam === aqua) &&
                        (!boat || element.boatdriver === boat)  &&
                        (!rescue || element.rescue === rescue) &&
                        (!skipper || element.skipper === skipper)){
                        return true;
                    }else return false;

                    // else if(boat && element.boatdriver === boat && !aqua && !rescue && !skipper){
                    //     return true;
                    // } else if(rescue && element.rescue === rescue && !boat && !aqua && !skipper){
                    //     return true;
                    // }  else if(skipper && element.skipper === skipper && !boat && !rescue && !aqua){
                    //     return true;
                    // }

                });

            }

        }
    }
})();
