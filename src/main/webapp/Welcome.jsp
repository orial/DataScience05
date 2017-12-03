<%-- 
    Document   : Welcome
    Created on : 03-dic-2017, 0:52:01
    Author     : Joaquin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Movie Recommender</title>
    </head>
    <body>
        <%@include file="Header2.jspf" %>
        <h1>Vista para mostrar las recomendaciones al usuario ${sessionScope.user}</h1>
    </body>
</html>
