<%-- 
    Document   : ViewGuest.jsp
    Created on : 5-dec-2017
    Author     : Martin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <%@include file="Header.jspf" %>
        <div><p style="color: green"><c:out value="${requestScope.ok}"/></p></div>
        
        
        <h1>AAA ViewGuest - Vista para mostrar las recomendaciones al usuario no registrado</h1>
        <a href="ViewGuestServlet">ViewGuestServlet</a>
    </body>
</html>
