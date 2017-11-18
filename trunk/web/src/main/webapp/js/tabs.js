/*global jQuery */

(function ($) {

    "use strict";

    $(document).ready(function () {

        // #category uses the default options.
        $("#category").tabs();
        
        $("#animated-category").tabs({
            
            // Start with the second items displayed.
            // (0-based index)
            selected: 1,
            
            // Animate the transitions.
            showPage: function (elem) {
                elem.css({
                    "z-index": 1
                });
                elem.fadeIn(500);
            },
            hidePage: function (elem) {
                elem.css({
                    "z-index": 0
                });
                elem.fadeOut(500);
            }
            
        });

    });

}(jQuery));
