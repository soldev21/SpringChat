var socketHandler = new function(){

    var socket;
    this.currentId;
    this.clients = new Map();

    this.connect = function(){
        //this.clients = this.clients.bind(this);
        socket = new SockJS("http://localhost:8080/chat");
        socket.onmessage = function (e) {
            console.log(e);
            handlerMessage(e);
        }
        socket.onopen = function(){
            console.log("Connected to websockket server");
        }
        

    }

    var handlerMessage = function(e){
        var msg = JSON.parse(e.data);
        switch (msg.header.type){
            case 0:handleNewMessage(msg);  break;
            case 1: handlerNewClient(msg);
                break;
            case 2: handleRemoveClient(msg); break;
            case 3: handleClientList(msg); break;
        }
    }

    var handleNewMessage = function(msg) {
        var map =socketHandler.clients;
        // $("#message").html($("#message").html().concat('<br /> '+msg.body.message));
        var s = map.get(msg.header.senderId);
        var senderId = msg.header.senderId;
        if (s&&s) {
            s.messages = s.messages.concat('<br /> '+msg.body.message);
            map.set(senderId,s);
            if (socketHandler.currentId == senderId) $("#message").html(s.messages);
        }
    }

    var handlerNewClient = function (msg) {
        var map =socketHandler.clients;
        //var map = new Map();
        var id = msg.body.message;
        console.log("ADDED "+id);
        $("#clientList").append('<li class="list-group-item" id="'+id+'">'+id+'</li>');
        var obj = {messages:'',draft:''};
        map.set(id,obj);
        bind();
        //currentId = msg.body.message;
    }

    var handleRemoveClient = function (msg) {
        var map =socketHandler.clients;
        var id =msg.body.message;
        console.log("REMOVED "+id);
        map.delete(id);
        $("#"+msg.body.message).remove();
    }

    var handleClientList = function(msg){
        var map =socketHandler.clients;
        console.log("GOT "+msg.body.message);
        var list  = JSON.parse(msg.body.message);
        list.forEach(function(e){
            $("#clientList").append('<li class="list-group-item" id="'+e+'">'+e+'</li>');
            var obj = {messages:'',draft:''};
            map.set(e,obj);
        });
        bind();
    }



    this.sendFromMessageInput = function () {
        var msg ={};
        var header = {};
        var body = {};
        header.receiverId = this.currentId;
        msg.header= header;
        body.message = $("#minput").val();
        msg.body = body;
        this.sendMessage(JSON.stringify(msg));
    }

    this.sendMessage = function(message){
        socket.send(message);
    }




}