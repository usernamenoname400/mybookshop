$(document).ready(function() {
    $('a.btn').click(function(event) {
        event.preventDefault();
        $('#downloadOverlay').fadeIn(297, function() {
            $('#downloadPopup').css('display', 'block').animate({opacity: 1}, 198);
        });
    });

    $('#downloadPopup__close,#downloadOverlay').click(function() {
       $('#downloadPopup').animate({opacity: 1}, 198, function() {
           $(this).css('display', 'none');
           $('#downloadOverlay').fadeOut(297);
       });
    });
});