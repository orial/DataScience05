<%-- 
    Document   : Welcome
    Created on : 03-dic-2017, 0:52:01
    Author     : Joaquin
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="auxiliar.Movie"%>
<%@page import="java.util.List"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="http://dreyescat.github.io/bootstrap-rating/bootstrap-rating.css" rel="stylesheet">
        <title>Movie Recommender</title>
    </head>
    <body>
        <%@include file="Header2.jspf" %>
        <div><p style="color: green"><c:out value="${requestScope.ok}"/></p></div>
        <h1>Te recomendamos estas películas</h1>
        <%  HttpSession sesion = request.getSession();
            List<Movie> movies = (List<Movie>)sesion.getAttribute("movies");
            for(Movie m : movies){
        %>
            <div style="width:220px; align:center; float:left; padding-left:20px">
                 <div class="panel panel-primary" style="width:200px">
                      <div class="panel-footer" style="height:80px"><h4><c:out value = "<%=m.getTitle() %>"/></h4></div>
                      <div class="panel-body"><a href="RateMovieServlet?movieid=<%=m.getId() %>"><img src="<%=m.getPoster() %>" style="width:170px; height:200px"></a></div>
                      <div class="panel-body">
                          <input type="hidden" class="rating" value="<%=m.getRating() %>" data-readonly/>
                          <span class="label label-default"><c:out value = "<%=m.getRating() %>"/></span>
                      </div>
                 </div>
            </div>
        <%}%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="http://dreyescat.github.io/bootstrap-rating/bootstrap-rating.js"></script>
    </body>
</html>
