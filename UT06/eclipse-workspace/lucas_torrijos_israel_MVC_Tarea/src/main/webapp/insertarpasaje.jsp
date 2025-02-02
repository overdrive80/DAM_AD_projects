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
body h1, h3, h4, div {
	display: flex;
	justify-content: center;
}
</style>
</head>
<% 
String idViajero;
String idVuelo;
String numasiento;
String clase;
String pvp;

@SuppressWarnings("unchecked")
List<Pasajero> pasajeros = (List<Pasajero>) request.getAttribute("listapasajeros");
@SuppressWarnings("unchecked")
List<Vuelo> vuelos = (List<Vuelo>) request.getAttribute("listavuelos");

String mensaje = (String) request.getAttribute("mensaje");


if (mensaje != null) {
	idViajero = request.getParameter("eleccionviajero");
	idVuelo = request.getParameter("eleccionVuelo");
	numasiento = request.getParameter("numasiento");
	clase = request.getParameter("clase");
	pvp = request.getParameter("pvp");
	
	//out.println("pasa por mensaje != null del jsp");
	if (clase == null) {clase ="";}
} else {
	mensaje = "";
	idViajero ="";
	idVuelo="";
	numasiento="0";
	clase="";
	pvp="0,0";
}

%>
<body>
	<h1>OPERACIONES CON VUELOS</h1>
	<h3>Práctica realizada por: Israel Lucas Torrijos</h3>
	<hr>
	<h3>INSERTAR PASAJE</h3>
	<%
	if (mensaje.equals("entradaErronea")) {
	%>

	<h3>Entrada no válida, comprueba asiento, clase o pvp</h3>

	<%
	}
	%>
	
	<%
	if (mensaje.equals("sinclase")) {
	%>

	<h3>Debe seleccionar una clase.</h3>

	<%
	}
	%>
	
		<%
	if (mensaje.equals("asientoocupado")) {
	%>

	<h3>EL NÚMERO DE ASIENTO <%=numasiento %> YA ESTÁ OCUPADO EN EL VUELO <%=idVuelo %></h3>

	<%
	}
	%>
	
			<%
	if (mensaje.equals("envuelo")) {
	%>

	<h3>EL PASAJERO YA ESTÁ EN EL VUELO <%=idVuelo %></h3>

	<%
	}
	%>
	<hr>
	<br>
	<div>
		<form action="Controlador" method="get">
			<input name="idpasaje" value="" hidden="true"> 
			
			
			<label for="eleccionviajero">Selecciona viajero:</label> 
			<select id="eleccionviajero" name="eleccionviajero">

				<%
				for (Pasajero pasajero : pasajeros) {
					long idPasajero = pasajero.getPasajeroCod();
					String nombre = pasajero.getNombre();
					String idNombre = idPasajero + " - " + nombre;
					
					String selected = "";
					if (idViajero.equals(String.valueOf(idPasajero))){
						selected = "selected";
					} 
					
				%>
				<option value="<%=idPasajero%>" <%=selected%>> <%=idNombre%> </option>

				<%
				}
				%>
			</select>
			<br><br> 
			
			<label for="eleccionVuelo">Selecciona un vuelo:</label>
			<select id="eleccionVuelo" name="eleccionVuelo">

				<%
				for (Vuelo vuelo : vuelos) {
					String idVueloAux = vuelo.getIdentificador();
					
					String selected = "";
					if (idVueloAux.equals(idVuelo)){
						selected = "selected";
					}
				%>
				<option value="<%=idVueloAux%>" <%=selected%>> <%=idVueloAux%> </option>

				<%
				}
				%>
			</select>
			<br><br> 
			
			<label for="numasiento">Núm. asiento:</label> 
			<input id="numasiento" name="numasiento" type="text" value="<%=numasiento%>"> 
			<br>
			<br> 
			
			<label>Selecciona la clase:</label> 
			<input type="radio"	id="turista" name="clase" value="TURISTA" <%=(clase.equals("TURISTA")) ? "checked" : ""%>> 
			<label for="turista">TURISTA</label> 
			<input type="radio" id="primera" name="clase" value="PRIMERA" <%=(clase.equals("PRIMERA")) ? "checked" : ""%>> 
			<label for="primera">PRIMERA</label> 
			<input type="radio" id="business" name="clase" value="BUSINESS"	<%=(clase.equals("BUSINESS")) ? "checked" : ""%>> 
			<label for="business">BUSINESS</label> 
			<br>
			<br> 
			<label for="pvp">PVP:</label> 
			<input id="pvp" name="pvp" type="text" value="<%=pvp%>"> 
			<br>
			<br>
			<button type="submit" name="accion" value="btninsertar">Insertar Pasaje</button>
		</form>
	</div>
	<br />
	<hr>
	<div class="enlaces">
	<a href="index.html">Volver al inicio</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=listarvuelos">Listar los vuelos</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=modborrarpasaje">Modificar/Borrar Pasaje</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=detallesvuelos">Ir a detalle vuelos</a>
	</div>
</body>
</html>