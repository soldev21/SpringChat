window.onload = function(){
    socketHandler.connect();
    //$("#clientList").hide();

}

var bind = function () {
    $("#clientList li").hover(function (e) {
            e.preventDefault();
            $(this).css('background-color', 'cyan');
        },
        function () {
            $(this).css('background-color', '');
        });

    $("#clientList li").click(function (e) {
        e.preventDefault();
        var id = $(this).attr('id');
        console.log("Current ID "+id);
        socketHandler.currentId = id;
        $("#message").html(socketHandler.clients.get(id).messages);
    });
}