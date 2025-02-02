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
body h1, h3, h4, div {
	display: flex;
	justify-content: center;
}
</style>
</head>

<%
//Obtiene los productos del parametro establecido en request
Pasaje pasaje = (Pasaje) request.getAttribute("pasaje");

String idpasaje;
String codpasajero;
String numasiento;
String idvuelo;
String clase;
String pvp;

if (pasaje != null) {

	idpasaje = String.valueOf(pasaje.getIdPasaje());
	codpasajero = String.valueOf(pasaje.getPasajeroCod());
	numasiento = String.valueOf(pasaje.getNumAsiento());
	idvuelo = pasaje.getIdentificador();
	clase = pasaje.getClase();
	pvp = String.valueOf(pasaje.getPvp());

} else {

	idpasaje = request.getParameter("idpasaje");
	codpasajero = request.getParameter("codpasajero");
	numasiento = request.getParameter("numasiento");
	idvuelo = request.getParameter("idvuelo");
	clase = request.getParameter("clase");
	pvp = request.getParameter("pvp");

}

String mensaje = (String) request.getAttribute("mensaje");

//Control de nulos
if (mensaje == null) {
	mensaje = "";
}
if (clase == null)
	clase = "";
%>

<body>
	<h1>OPERACIONES CON VUELOS</h1>
	<h3>Práctica realizada por: Israel Lucas Torrijos</h3>
	<hr>


	<%	 
	if (mensaje.equals("insertado")){
	%>
	<h3>PASAJE INSERTADO CON ID: <%=idpasaje%></h3>
	<%
	} else {
	%>
	<h3>MODIFICAR PASAJE: <%=idpasaje%></h3>
	<%
	}
	%>
	<%
	if (mensaje.equals("entradaErronea")) {
	%>

	<h3>Entrada no válida, comprueba asiento, clase o pvp</h3>

	<%
	}

	if (mensaje.equals("asientoocupado")) {
	%>
	<h3>ERROR AL ACTUALIZAR. EL NÚMERO DE ASIENTO <%=numasiento %> YA ESTÁ OCUPADO EN EL VUELO <%=idvuelo %>. Id de pasaje: <%=idpasaje%></h3>
	<%
	}
	 
	if (mensaje.equals("actualizado")){
	%>
	<h3>PASAJE <%=idpasaje%> ACTUALIZADO.</h3>
	<%
	}
	%>
	

	<hr>
	<br />
	<div>
		<form action="Controlador" method="get">
			<input name="idpasaje" value=<%=idpasaje%> hidden="true"> 
			<label for="codpasajero">Código de pasajero:</label> 
			<input id="codpasajero" name="codpasajero" type="text" value="<%=codpasajero%>" readonly> 
			<br><br> 
			
			<label for="idvuelo">Id de Vuelo:</label> 
			<input id="idvuelo"	name="idvuelo" type="text" value="<%=idvuelo%>" readonly> 
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
			<button type="submit" name="accion" value="btnmodificar">Modificar Pasaje</button>
		</form>
	</div>
	<br />
	<hr>
	<div class="enlaces">
	<a href="index.html">Volver al inicio</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=listarvuelos">Listar los vuelos</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=modborrarpasaje">Modificar/Borrar Pasaje</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=detallesvuelos">Ir a detalle vuelos</a>
	<% 
	 if (mensaje.equals("insertado")){
	%>
		 &nbsp;|&nbsp;<a href="Controlador?accion=insertarpasaje">Insertar Pasaje </a>
	<%
	 }
	%>
	</div>
</body>
</html>