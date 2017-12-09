<%-- 
    Document   : Registro2
    Created on : 04-dic-2017, 19:10:39
    Author     : Joaquin
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>Registro</title>
    </head>
    <body>
        <%@include file="Header.jspf" %>
        <c:set var="points" value="0,1,2,3" scope="application"/>
        <c:set var="groups" value="1,2,3,4,5,6" scope="application"/>
        <div class="container theme-showcase" role="main">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Registro - <b>Divida 3 puntos</b> entre sus grupos de películas favoritos</h1>
                </div>
                <div class="panel-body">
                    <form class="form-signin" action = "Registro2Servlet" method="POST">
                        <div class="table-responsive">
                            <table>
                                    <c:forEach items="${fn:split(groups, ',')}" var="group">
                                        <tr>
                                            <td>
                                            <label>Grupo ${group}
                                                <select id="Grupo${group}" name="Grupo${group}">
                                                    <c:forEach items="${fn:split(points, ',')}" var="point">
                                                        <option value="${point}">${point}</option>
                                                    </c:forEach>
                                                </select>
                                            </label>
                                            </td>
                                        </tr>
                                    </c:forEach>
                            </table>
                            <div><p style="color: red"><c:out value="${requestScope.error}"/></p></div>
                            <div class="botones">
                                <button type="submit" class="btn btn-primary">Finalizar registro</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
