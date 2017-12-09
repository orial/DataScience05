<%-- 
    Document   : Home.jsp
    Created on : 27-nov-2017, 18:55:54
    Author     : Joaquin
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="http://dreyescat.github.io/bootstrap-rating/bootstrap-rating.css" rel="stylesheet">
        <title>Movie Recommender</title>
    </head>
    <body>
        <%@include file="Header.jspf" %>
        <div><p style="color: green"><c:out value="${requestScope.ok}"/></p></div>
        <h1>Las películas que más ven nuestros usuarios</h1>
        <c:forEach var="movie" items="${requestScope.movies}">
            <div style="width:220px; align:center; float:left; padding-left:20px">
                 <div class="panel panel-primary" style="width:200px">
                      <div class="panel-footer" style="height:80px"><h4><c:out value = "${movie.title}"/></h4></div>
                      <div class="panel-body"><a href="MovieServlet?movieid=${movie.id}&rating=${movie.rating}"><img src="${movie.poster}" style="width:170px; height:200px"></a></div>
                      <div class="panel-body">
                          <input type="hidden" class="rating" value="${movie.rating}" data-readonly/>
                          <span class="label label-default"><c:out value = "${movie.rating}"/></span>
                      </div>
                 </div>
            </div>
        </c:forEach>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="http://dreyescat.github.io/bootstrap-rating/bootstrap-rating.js"></script>
    </body>
</html>
