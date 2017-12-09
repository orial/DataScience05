<%-- 
    Document   : Login
    Created on : 27-nov-2017, 20:00:29
    Author     : Joaquin
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>Iniciar sesión</title>
    </head>
    <body>
        <%@include file="Header.jspf" %>
        <div class="container">
            <div class="formulario">
                <h1>Iniciar Sesión</h1>
                <form action="LoginServlet" method="POST" class="form-signin">
                    <label for="text" class="sr-only">Text</label>
                    <input type="text" class="form-control" placeholder="Introduzca su email o usuario" name="text" required/>
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" class="form-control" placeholder="Contraseña" name="password" required/>
                    <button style="margin-top: 2em;" class="btn btn-lg btn-primary btn-block" type="submit">Iniciar Sesión</button>
                    <div><p style="color: red"><c:out value="${requestScope.error}"/></p></div>
                    <div><p style="color: green"><c:out value="${requestScope.ok}"/></p></div>
                </form>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
