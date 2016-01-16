<%@page import="java.net.InetAddress"%>
<html>
    <head>
        <link rel="icon" href="favicon.png"/>
        <link rel="stylesheet" href="bootstrap-3.3.6-dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="bootstrap-3.3.6-dist/css/bootstrap.min.css">
        
        <% String direccionIPServer = InetAddress.getLocalHost().getHostAddress(); %>
        
        <script src="jquery-2.2.0.min.js"></script>
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
        <script>
        $(document).ready(function()
        {
            porcentaje = (100 /  parseInt($(".pildora").length)) -1;
            console.log("listo : " + $(".pildora").length + " " +porcentaje);   
            
        });</script>
        <style>
            
            .background
            {
                background-image: url('background.png');

            }
            #imgCentral
            {
                margin-top: 20px;
                box-shadow: 10px 10px 15px black;
                cursor:pointer;
                opacity: 0.8;
            }
            #imgCentral:hover
            {
                transform: scale(1.5, 1.5);
                opacity: 1;
            }
            .barra
            {
                background-color:white;
                padding-top:12px;
                padding-bottom:12px;
                border-radius:5px;
                margin-top:125px;
                text-align:center;
            }
            .pildora
            {
                width: 24%;
                padding: 0px;
                margin: 0px;
            }
            .hx
            {
                color:white;
            }
        </style>
    </head>
    <body class="background" ng-app="app" ng-controller="controller">
        
        
        <h3 class="hx">Uptime : {{uptime}}</h3>
        <h3 class="hx">Memoria Total: {{mem.total}}</h3>
        <h3 class="hx">Memoria Free: {{mem.free}}</h3>
        <h3 class="hx">Memoria Usada: {{mem.used}}</h3>
        <img src="debian-linux.png" class="col-xs-4 col-xs-offset-4 img-thumbnail" id="imgCentral">
        
        
        <div class="">
            <ul class="nav nav-pills col-xs-8 col-xs-offset-2 barra">
              <li class="pildora col-xs-3"><a href="index.html">Home</a></li>
              <li class="pildora col-xs-3"><a href="http://<%=direccionIPServer%>/phpmyadmin/">PHPMyAdmin</a></li>
              <li class="pildora col-xs-3"><a href="http://<%=direccionIPServer%>:8181/">Tomcat</a></li>
              <li class="pildora col-xs-3"><a href="http://<%=direccionIPServer%>:8080/">Glassfish</a></li>
            </ul>
        </div>
        
    </body>
    <script>
    app = angular.module('app', []);
    
    app.controller('controller', function($scope, $http)
{
    $scope.uptime = "Cargando..";
    $scope.mem = JSON.parse('{"free":"Cargando..","used":"Cargando..","total":"Cargando.."}');
    
    console.log($scope.mem);
        $http.get("../WS/uptime.jsp").then(function(response) 
        {
            $scope.uptime = response.data.uptime;
            
            $http.get("../WS/mem.jsp").then(function(response) 
            {
                $scope.mem = response.data;
            });
        });
    });
    </script>
</html>