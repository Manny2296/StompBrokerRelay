var stompClient = null;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/newpoint', function (data) {           
           var cor = JSON.parse(data.body);
           canvas = document.getElementById('myCanvas');
           context = canvas.getContext('2d');   
           context.beginPath();
           context.arc(cor.x,cor.y,1,0,2*Math.PI);
           context.stroke();           
        });
        
        stompClient.subscribe('/topic/newpolygon', function (data) {           
           var coor = JSON.parse(data.body);
           canvas = document.getElementById('myCanvas');
           context = canvas.getContext('2d');
           context.fillStyle = '#f00';
           context.beginPath();
           context.moveTo(coor[0].x, coor[0].y);
           context.lineTo(coor[1].x, coor[1].y);
           context.lineTo(coor[2].x, coor[2].y);
           context.lineTo(coor[3].x, coor[3].y);
           context.closePath();
           context.fill();
           
           
           
        });
    });
}


function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function getMousePos(canvas, evt) {
var rect = canvas.getBoundingClientRect();
        return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
        };
}
var canvas = null;
var context = null;




$(document).ready(
        function () {
            connect();
            console.info('connecting to websockets');
        
            canvas = document.getElementById('myCanvas');
                  
                
            canvas.addEventListener('mousedown', function(evt) {
                var mousePos = getMousePos(canvas, evt);
                stompClient.send("/app/newpoint", {}, JSON.stringify({x:mousePos.x,y:mousePos.y})); 
                
            }, false);
    }
                
                
);
