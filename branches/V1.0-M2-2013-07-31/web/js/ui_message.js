var myMessages = ['tinfo','twarning','terror','tsuccess'];
var lastTimeout = 0;

function hideAllMessages(animated){
    var messagesHeights = new Array(); // this array will store height for each
    for (i=0; i<myMessages.length; i++)
    {
        messagesHeights[i] = $('.' + myMessages[i]).outerHeight(); // fill array
        if (animated)
//            $('.' + myMessages[i]).animate({top: -messagesHeights[i]}, 500);
            $('.' + myMessages[i]).animate({top: -messagesHeights[i]}, 500);
        else
            $('.' + myMessages[i]).css('top', -messagesHeights[i]); //move element outside viewport

    }
}



function tAlert(message, title, type, autoClose){
    hideAllMessages(false);
    type = 't' + type;  
    var panel = $('.' + type)[0];
    var titleEl = document.getElementById(type + "Header");
    var messageEl = document.getElementById(type + "Message");
    titleEl.innerHTML = title;
    messageEl.innerHTML = message;
    $(panel).animate({top:"0"}, 500);
    if (autoClose != 0) {
        clearTimeout(lastTimeout);
        lastTimeout = setTimeout("hideAllMessages(true)", autoClose);
    }
}

$(document).ready(function(){

    // Initially, hide them all
    hideAllMessages(false);

    //    // When message is clicked, hide it
    $('.message').click(function(){
        $(this).animate({top: -$(this).outerHeight()}, 500);
    });

});