var socketHandler = new function(){

    var socket;

    this.connect = function(){
        socket = new SockJS("http://localhost:8080/chat");
        socket.onopen = function(){
            alert("Connected to websockket server");
        }
    }
    
    this.sendFromMessageInput = function () {
        this.sendMessage(document.getElementById("minput").value);
    }

    this.sendMessage = function(message){
        socket.send(message);
    }

    this.close = function(){
        socket.disconnect();
    }
}