<%@ page import="java.sql.*" %>
<%@ page import="org.iesvdm.jsp_servlet_jdbc.model.Socio" %>
<%@ page import="java.util.List" %>
<%@ page import="org.iesvdm.jsp_servlet_jdbc.model.Socio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Socios</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="estilos.css" />
</head>
<body class="bg-light">
<div class="container bg-white sticky-top">
    <div class="row mb-2 border-bottom">
        <div class="col-md-12 h1">Listado de Socios</div>
    </div>
    <div class="row">
        <div class="col-md-1 h3">ID</div>
        <div class="col-md-4 h3">Nombre</div>
        <div class="col-md-1 h3">Edad</div>
        <div class="col-md-1 h3">Estatura</div>
        <div class="col-md-3 h3">Localidad</div>
        <div class="col-md-2 h3 text-center">Operación</div>
    </div>
</div>

<div class="container bg-light">
    <%
    //                                                          v----RECOGER listado DE SOCIO DEL request
    List<Socio> listado = (List<Socio>) request.getAttribute("listado");
    // FOR-EACH SOBRE LA COLECCIÓN DE listado DE SOCIO
    for (Socio socio: listado) {
    %>
    <div class="row mt-2 body">
        <div class="col-md-1 align-self-center"><%=socio.getSocioId() %>
        </div>
        <div class="col-md-4 align-self-center"><%=socio.getNombre() %>
        </div>
        <div class="col-md-1 align-self-center"><%=socio.getEdad() %>
        </div>
        <div class="col-md-1 align-self-center"><%=socio.getEstatura() %>
        </div>
        <div class="col-md-3 align-self-center"><%=socio.getLocalidad()%>
        </div>

        <div class="col-md-2 align-self-center text-center">
            <form method="get" action="borraSocio.jsp">
                <input type="hidden" name="codigo" value="<%=socio.getSocioId() %>"/>
                <input class="btn btn-primary"  type="submit" value="Borrar">
            </form>
        </div>
    </div>
        <%
      //v--- FIN DEL BUCLE FOR CON HTML INCRUSTADO
    } // for
    %>
</div>

<script src="js/bootstrap.bundle.js" ></script>
</body>
</html>
