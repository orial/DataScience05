<%-- 
    Document   : MovieDetails
    Created on : 08-dic-2017, 23:21:43
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
        <title><c:out value="${requestScope.titulo}"/></title>
    </head>
    <body>
        <%@include file="Header.jspf" %>
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
                                        <td><b>Sinopsis: </b><c:out value="${requestScope.sinopsis}"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Año: </b><c:out value="${requestScope.year}"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Duración: </b><c:out value="${requestScope.duracion}"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Director: </b><c:out value="${requestScope.director}"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Reparto: </b><c:out value="${requestScope.reparto}"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Valoración media: </b>
                                            <input type="hidden" class="rating" value="${requestScope.rating}" data-readonly/>
                                            <span class="label label-default"><c:out value = "${requestScope.rating}"/></span>
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
    </body>
</html>
