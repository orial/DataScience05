<%-- 
    Document   : RateMovie
    Created on : 13-dic-2017, 0:45:49
    Author     : Joaquin
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="auxiliar.DBHelper"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="http://dreyescat.github.io/bootstrap-rating/bootstrap-rating.css" rel="stylesheet">
        <title><c:out value="${requestScope.titulo}"/></title>
    </head>
    <body>
        <%
            DBHelper db = new DBHelper();
            HttpSession sesion = request.getSession();
            String userid = (String)sesion.getAttribute("userid");
            int vals = db.getUserValorations(userid);
            if(vals>=20){
        %>
        <%@include file="Header2.jspf" %>
        <%        
            }else{
        %>        
        <%@include file="Header2Pre.jspf" %>
        <%
            }
        %>
        <div class="container theme-showcase" role="main">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title"><c:out value="${requestScope.titulo}"/></h1>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <tr>
                            <td><img src="${requestScope.poster}" style="width:340px; height:400px"></td>
                            <td>
                                <table class="table">
                                    <tr>
                                        <td><b>Sinopsis: </b><c:out value="${requestScope.resumen}"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Año: </b><c:out value="${requestScope.year}"/></td>
                                    </tr>
                                    <tr>
                                        <%  String movieid = (String)request.getAttribute("movieid");
                                            String rating = db.getMovieMeanVal(movieid);
                                            System.out.println(rating);
                                            Float myrating = db.getMovieRatedByUser(userid, movieid);
                                            System.out.println(myrating);
                                        %>
                                        <td><b>Valoración media: </b>
                                            <input type="hidden" class="rating" value="<%=rating%>" data-readonly/>
                                            <span class="label label-default"><c:out value = "<%=rating%>"/></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Tu valoración: </b>
                                            <input type="hidden" class="rating" value="<%=myrating%>" data-fractions="2"/>
                                            <span class="label label-default"><c:out value = "<%=myrating%>"/></span>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="http://dreyescat.github.io/bootstrap-rating/bootstrap-rating.js"></script>
        <script>
            $('.rating').on('change', function (){
                $(this).next('.label').text($(this).val());
                $.ajax({
                    url:'SaveRatingServlet',
                    data:{val:$(this).val(),
                    movieid:<%=movieid%>},
                    type:'get',
                    cache:false
                });
            });
        </script> 
    </body>
</html>
