<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
</style>
</head>

<%
//Obtiene los productos del parametro establecido en request
@SuppressWarnings("unchecked")
//Para el combobox
List<Vuelo> vuelos = (List<Vuelo>) request.getAttribute("listavuelos");
//Segun mensaje cambia contenido respuesta
String mensaje = (String) request.getAttribute("mensaje");
if (mensaje == null) {
	mensaje = "";
}

//Vuelo seleccionado
Vuelo vueloEleccion = (Vuelo) request.getAttribute("vuelo");

//Pasajes
@SuppressWarnings("unchecked")
List<Pasaje> pasajes = (List<Pasaje>) request.getAttribute("pasajes");
%>
<body>
	<h1>OPERACIONES CON VUELOS</h1>
	<h3>Práctica realizada por: Israel Lucas Torrijos</h3>
	<hr>

	<h3>Detalle de los vuelos</h3>
	<br />

	<div>
		<!-- Creamos el formulario para pasar datos al Servlet -->
		<form action="Controlador" method="get">
			<label for="eleccionVuelo">Selecciona un vuelo:</label>
			<select id="eleccionVuelo" name="eleccionVuelo">

				<%
				for (Vuelo vuelo : vuelos) {
					String id = vuelo.getIdentificador();
					String selected = (vuelo.getIdentificador().equals(request.getParameter("eleccionVuelo"))) ? "selected" : "";
				%>
				<option value="<%=id%>" <%=selected%>> <%=vuelo.getIdentificador()%> </option>

				<%
				}
				%>
			</select>


			<button type="submit" name="accion" value="btndetalles">Ver
				detalle del vuelo</button>
			<button type="submit" name="accion" value="btnpasajes">Ver
				pasaje del vuelo</button>
		</form>
	</div>
	<br />
	<hr>

	<%
	if (mensaje.equals("mostrardetalles")) {
	%>
	<div class="resultado">
		<h3>
			DETALLE DEL VUELO CON IDENTIFICADOR:
			<%=request.getParameter("eleccionVuelo")%></h3>
		<br /> Aeropuerto de origen:
		<%=vueloEleccion.getAeropuertoOrigen()%><br /> Nombre de aeropuerto
		de origen:
		<%=vueloEleccion.getNombreAeroOrigen()%><br /> País aeropuerto de
		origen:
		<%=vueloEleccion.getPaisOrigen()%><br /> Aeropuerto de destino:
		<%=vueloEleccion.getAeropuertoDestino()%><br /> Nombre de aeropuerto
		de destino:
		<%=vueloEleccion.getNombreAeroDestino()%><br /> País aeropuerto de
		destino:
		<%=vueloEleccion.getPaisDestino()%><br /> Tipo de vuelo:
		<%=vueloEleccion.getTipoVuelo()%><br /> Número de pasajeros:
		<%=vueloEleccion.getNumPasajeros()%><br />
		<hr>
	</div>

	<%
	} else if (mensaje.equals("mostrarpasajes")) {
		
		if (pasajes.size() > 0) {
		
	%>
	<div class="resultado">
		<h3>
			DETALLE DEL VUELO CON IDENTIFICADOR:<%=request.getParameter("eleccionVuelo")%></h3>
		<br />
		<table>
			<tr>
				<th>Id. de pasaje</th>
				<th>Código pasajero</th>
				<th>Nombre pasajero</th>
				<th>País pasajero</th>
				<th>Nombre de asiento</th>
				<th>Clase</th>
				<th>PVP</th>

			</tr>
			<%
			for (Pasaje pasaje : pasajes) {
			%>

			<tr>
				<td><%=pasaje.getIdPasaje()%></td>
				<td><%=pasaje.getPasajeroCod()%></td>
				<td><%=pasaje.getNombrePasajero()%></td>
				<td><%=pasaje.getPaisPasajero()%></td>
				<td><%=pasaje.getNumAsiento()%></td>
				<td><%=pasaje.getClase()%></td>
				<td><%=pasaje.getPvp()%></td>

			</tr>
			<%
			}
			%>
		</table>
		<hr>
	</div>
	<%
		} else {
			
			out.println("<div><p class='negrita'>No hay pasajes relacionados con el vuelo.</p></div><hr>");
		}
		
	}
	%>

	<div class="enlaces">
	<a href="index.html">Volver al inicio</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=listarvuelos">Listar los vuelos</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=modborrarpasaje">Modificar/Borrar Pasaje</a>&nbsp;|&nbsp;
	<a href="Controlador?accion=insertarpasaje">Insertar Pasaje </a>
	</div>
</body>
</html>