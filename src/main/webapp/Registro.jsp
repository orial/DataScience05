<%-- 
    Document   : Registro
    Created on : 27-nov-2017, 19:50:31
    Author     : Joaquin
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>Registro</title>
    </head>
    <body>
        <%@include file="Header.jspf" %>
        <div class="container theme-showcase" role="main">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h1 class="panel-title">Registro - Introduzca sus datos</h1>
                </div>
                <div class="panel-body">
                    <form class="form-signin" action = "RegistroServlet" method="POST">
                        <div class="table-responsive">
                            <table>
                                <tr>
                                    <td><p style="color: red; display: inline">*</p>Nick:</td>
                                    <td> 
                                        <label for="nickNameInput" class="sr-only">Nick</label>
                                        <input type="text" class="form-control" placeholder="Nickname" name="nickNameInput" required>
                                    </td>
                                </tr>
                                <tr>
                                    <td><p style="color: red; display: inline">*</p>Correo electrónico:</td>
                                    <td>
                                        <label for="emailInput" class="sr-only">Correo electrónico</label>
                                        <input type="email" class="form-control" placeholder="Email" name="emailInput" required >
                                    </td>
                                </tr>
                                <tr>
                                    <td><p style="color: red; display: inline">*</p>Contraseña:</td>
                                    <td>
                                        <label for="passwordInput" class="sr-only">Contraseña</label>
                                        <input type="password" class="form-control" placeholder="Contraseña" name="passwordInput" required>
                                    </td>
                                </tr>
                                <tr>
                                    <td><p style="color: red; display: inline">*</p>Repetir contraseña:</td>
                                    <td>
                                        <label for="rePasswordInput" class="sr-only">Repetir contraseña</label>
                                        <input type="password" class="form-control" placeholder="Repite contraseña" name="rePasswordInput" required>
                                    </td>
                                </tr>
                                <tr>
                            </table>
                            <div><p style="color: red"><c:out value="${requestScope.error}"/></p></div>
                            <div class="botones">
                                <button type="submit" class="btn btn-primary">Continuar</button>
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
