<%--
  Created by IntelliJ IDEA.
  User: pika_
  Date: 28/11/2023
  Time: 0:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editar Socio</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="estilos.css"/>
</head>
<body class="bg-light">
<div class="container bg-white">
    <div class="row border-bottom">
        <div class="col-12 h2">Introduzca los datos a modificar</div>
    </div>
</div>
<div class="container bg-light">
    <form method="post" action="EditarSociosServlet">
        <div class="row body mt-2">
            <div class="col-md-6 align-self-center">Código Socio</div>
            <div class="col-md-6 align-self-center"><input type="text" name="socioCod"/></div>
        </div>
        <div class="row body mt-2">
            <div class="col-md-6 align-self-center">Nombre</div>
            <div class="col-md-6 align-self-center"><input type="text" name="nombre"/></div>
        </div>
        <div class="row body mt-2">
            <div class="col-md-6 align-self-center">Estatura</div>
            <div class="col-md-6 align-self-center"><input type="text" name="estatura"/></div>
        </div>
        <div class="row body mt-2">
            <div class="col-md-6 align-self-center">Edad</div>
            <div class="col-md-6 align-self-center"><input type="text" name="edad"/></div>
        </div>
        <div class="row body mt-2">
            <div class="col-md-6 align-self-center">Localidad</div>
            <div class="col-md-6 align-self-center"><input type="text" name="localidad"/></div>
        </div>
        <div class="row mt-2">
            <div class="col-md-6">
                &nbsp;
            </div>
            <div class="col-md-6 align-self-center">
                <input class="btn btn-primary" type="submit" value="Aceptar">
            </div>
        </div>
    </form>
    <%
        //                          v---- RECOGER MENSAJE DE ERROR DEL ÁMBITO request
        String error = (String) request.getAttribute("error");
        //     v---- SI ESTÁ PRESENTE INFORMAR DEL ERROR
        if (error != null) {
    %>
    <div class="row mt-2">
        <div class="col-6">
            <div class="alert alert-danger alert-dismissible fade show">
                <strong>Error!</strong> <%=error%>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>
<script src="js/bootstrap.bundle.js"></script>
</body>
</html>