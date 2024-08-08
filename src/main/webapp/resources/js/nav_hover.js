$(document).ready(function() {
    $(".snb").hide();

    $(".nav").hover(
        function() {
            let $snb = $(this).closest(".nav-div").find(".snb");
            if (!$snb.is(":visible")) {
                $snb.stop(true, true).slideDown(500);
            }
        },
        function() {
            // Do nothing on mouse leave from .nav
        }
    );

    $(".nav-div").hover(
        function() {
            // Do nothing on mouse enter to .nav-div
        },
        function() {
            let $snb = $(this).find(".snb");
            $snb.stop(true, true).slideUp(500);
        }
    );

    $(".snb").hover(
        function() {
            $(this).stop(true, true);
        },
        function() {
            $(this).stop(true, true).slideUp(500);
        }
    );
});