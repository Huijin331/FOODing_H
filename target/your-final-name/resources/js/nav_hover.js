$(document).ready(function() {
    $(".nav-div").hover(
        function() {
            $(this).find(".snb").stop(true, true).slideDown(500);
        },
        function() {
            $(this).find(".snb").stop(true, true).slideUp(500);
        }
    );
});