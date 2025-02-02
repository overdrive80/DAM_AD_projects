<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!--  IMPORTACION DE LIBRERIAS -->
<%@ page import="java.util.*, beansaeropuerto.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle de vuelos</title>
<style>
body h1, h3, h4 {
	display: flex;
	justify-content: center;
}

div {
	text-align: center;
	margin: 0 auto;
}

p {
	display: inline-block;
	margin: 0 auto;
	text-align: center;
}

.negrita {
	font-weight: bold;
}

table {
	margin: 0 auto;
}

table th, td {
	padding:4px;
}

</style>
</head>
<% 
@SuppressWarnings("unchecked")
//Listado de pasajes
List<Pasaje> pasajes = (List<Pasaje>) request.getAttribute("pasajes");

String borrado = (String) request.getAttribute("borrado");
if (borrado == null) {borrado="";}
%>
<body>
	<h1>OPERACIONES CON VUELOS</h1>
	<h3>Práctica realizada por: Israel Lucas Torrijos</h3>
	<hr>
	<%
				
		if (borrado.equals("true")){
			
			String id = (String) request.getParameter("id");
	
			out.println("<h4>PASAJE CON ID: " + id + ", BORRADO CORRECTAMENTE.</h4>");
				
		}
	%>
	<h3>LISTADO DE PASAJE</h3>
		<div class="resultado">
		<table>
			<tr>
				<th>Id. de pasaje</th>
				<th>Código<br/>pasajero</th>
				<th>Nombre<br/>pasajero</th>
				<th>País<br/>pasajero</th>
				<th>Nombre de<br/>asiento</th>
				<th>Clase</th>
				<th>PVP</th>
				<th>Borrar<br/>Modificar</th>

			</tr>
			<%
			for (Pasaje pasaje : pasajes) {
				String paramBorrar="";
				String paramModif="";
				
				long idPasaje = pasaje.getIdPasaje();

				request.setAttribute("idpasaje", idPasaje);
				
				paramBorrar = "Controlador?accion=borrarpasaje&id=" + idPasaje;
				paramModif = "Controlador?accion=modifpasaje&id=" + idPasaje;
				
			%>

			<tr>
				<td><%=pasaje.getIdPasaje()%></td>
				<td><%=pasaje.getPasajeroCod()%></td>
				<td><%=pasaje.getNombrePasajero()%></td>
				<td><%=pasaje.getPaisPasajero()%></td>
				<td><%=pasaje.getNumAsiento()%></td>
				<td><%=pasaje.getClase()%></td>
				<td><%=pasaje.getPvp()%></td>
				<td>
					<a href="<%=paramBorrar%>">Borrar</a>&nbsp;|
					<a href="<%=paramModif%>">Modificar</a>
				</td>

			</tr>
			<%
			}
			%>
		</table>
		<hr>
	</div>

	<div class="enlaces">
	<a href="index.html">Volver al inicio</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=listarvuelos">Listar los vuelos</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=detallesvuelos">Ir a detalle vuelos</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=insertarpasaje">Insertar Pasaje </a>
	</div>
</body>
</html>