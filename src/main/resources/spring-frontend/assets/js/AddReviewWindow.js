$(document).ready(function() {
    $('#reviewWindow').click(function(event) {
        event.preventDefault();
        $('#reviewOverlay').fadeIn(297, function() {
            $('#reviewPopup').css('display', 'block').animate({opacity: 1}, 198);
        });
    });

    $('#reviewPopup__close,#reviewOverlay').click(function() {
        $('#reviewPopup').animate({opacity: 1}, 198, function() {
            $(this).css('display', 'none');
            $('#reviewOverlay').fadeOut(297);
        });
    });

    $('#reviewAuthor,#reviewText').on("change paste keyup", function() {
        var isEmpty = $('#reviewText').val() === "" || $('#reviewAuthor').val() === "";
        if (!isEmpty) {
            $('#reviewSubmit').removeAttr('disabled');
        } else {
            $('#reviewSubmit').attr('disabled', true);
        }
    });
});