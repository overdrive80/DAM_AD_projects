<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*, beansaeropuerto.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado de vuelos</title>
<style>
body h1, h3, h4, div, table {
	display: flex;
  	justify-content: center;
}

table th, td {
	border: 1px solid grey;
	border-collapse: collapse;
	padding:4px;
}

</style>
</head>

<%
//Obtiene los productos del parametro establecido en request
@SuppressWarnings("unchecked")
List<Vuelo> vuelos = (List<Vuelo>) request.getAttribute("listavuelos");

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
	
			out.println("<h4>VUELO CON ID: " + id + ", BORRADO CORRECTAMENTE.</h4>");
				
		}
	%>
	
	<h3>LISTADO DE LOS VUELOS</h3>
	<table>
		<tr>
			<th>Identificador</th>
			<th>Aeropuerto<br/>de origen</th>
			<th>Nombre<br/>Aeropuerto</th>
			<th>Pais de<br/> origen</th>
			<th>Aeropuerto<br/>de destino</th>
			<th>Nombre<br/>Aeropuerto</th>
			<th>Pais de<br/> destino</th>
			<th>Tipo de Vuelo</th>
			<th>Número<br/>pasajeros</th>
			<th>Borrar<br/>vuelo</th>
		</tr>
		<%
		for (Vuelo vuelo : vuelos) {
			
			String parametros="";
			int numPasajeros = vuelo.getNumPasajeros();
			
			if (numPasajeros == 0) {
				String id = vuelo.getIdentificador();
				
				request.setAttribute("id", id);
				parametros = "Controlador?accion=borrarvuelo&id=" + id;
			}
		%>

		<tr>
			<td><%=vuelo.getIdentificador()%></td>
			<td><%=vuelo.getAeropuertoOrigen()%></td>
			<td><%=vuelo.getNombreAeroOrigen()%></td>
			<td><%=vuelo.getPaisOrigen()%></td>
			<td><%=vuelo.getAeropuertoDestino()%></td>
			<td><%=vuelo.getNombreAeroDestino()%></td>
			<td><%=vuelo.getPaisDestino()%></td>
			<td><%=vuelo.getTipoVuelo()%></td>
			<td><%=vuelo.getNumPasajeros()%></td>
			<td><a href="<%=parametros%>">Borrar</a></td>
		</tr>
		<%
		}
		%>
	</table>

	<br />
	<div class="enlaces">
	<a href="index.html">Volver al inicio</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=detallesvuelos">Ir a detalle vuelos</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=modborrarpasaje">Modificar/Borrar Pasaje</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=insertarpasaje">Insertar Pasaje </a>
	</div>
</body>
</html>