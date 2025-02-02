package controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import implDAO.*;
import interfacesDAO.*;
import beansaeropuerto.Pasaje;
import beansaeropuerto.Pasajero;
import beansaeropuerto.Vuelo;

//@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// En este momento debe crearse la conexión o dará error.
	private VueloDAO vuelosDAO = new OracleVueloDAO();
	private PasajeDAO pasajesDAO = new OraclePasajeDAO();
	private PasajeroDAO pasajerosDAO = new OraclePasajeroDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String accion = request.getParameter("accion");

		if (accion == null) {
			return;
		}

		switch (accion) {
		/*** EJERCICIO 1 ***/
		case "listarvuelos":
			listarvuelos(request, response);
			break;
		case "borrarvuelo":
			borrarvuelo(request, response);
			break;
		/*** EJERCICIO 2 ***/
		case "detallesvuelos":
			detallesvuelos(request, response);
			break;
		case "btndetalles":
			mostrardetalles(request, response);
			break;
		case "btnpasajes":
			mostrarpasajes(request, response);
			break;
		/*** EJERCICIO 3 ***/
		case "modborrarpasaje":
			modifInserPasaje(request, response);
			break;
		case "borrarpasaje":
			borrarPasaje(request, response);
			break;
		case "modifpasaje":
			formModifcarPasaje(request, response);
			break;
		case "btnmodificar":
			modifPasaje(request, response);
			break;
		/*** EJERCICIO 4 ***/
		case "insertarpasaje":
			formInsertarPasaje(request, response);
			break;
		case "btninsertar":
			insertarPasaje(request, response);
			break;
		default:
			response.sendRedirect("index.html");
		}

	}

	private void formInsertarPasaje(HttpServletRequest request, HttpServletResponse response) {
		try {

			// Lista de pasajeros para el combobox
			List<Pasajero> listaPasajeros = pasajerosDAO.consultarTodos();
			request.setAttribute("listapasajeros", listaPasajeros);
			
			// Lista de Vuelos para el combobox
			List<Vuelo> vuelos = vuelosDAO.listadoEjercicio1();
			request.setAttribute("listavuelos", vuelos);
			
			// Enviamos el request a la pagina JSP, debe importar el paquete de las clases
			RequestDispatcher rd = (RequestDispatcher) request.getRequestDispatcher("/insertarpasaje.jsp");
			rd.forward(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
	}

	private void insertarPasaje(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			long idPasajeNuevo = pasajesDAO.nuevoIDPasaje();
			
			/*** RECUPERACION DE LOS DATOS DEL FORMULARIO CON CONTROL BASICO **/
			String strIDviajero= request.getParameter("eleccionviajero");
			String strIDVuelo = request.getParameter("eleccionVuelo");
			
			String strNumAsiento = request.getParameter("numasiento");
			String strClase = request.getParameter("clase");
			String strPVP = request.getParameter("pvp");
			
			//System.out.printf("%s, %s, %s, %s, %s", strIDviajero, strIDVuelo, strNumAsiento, strClase, strPVP);
			
			//**** ERRORES CLASE *****//
			if (strClase == null) {
				recargarConMensajeInsertar(request, response, "sinclase");
				return;
			}
			
			// **** ERRORES ASIENTO ****//
			boolean valorAsientoValido = esAsientoValido(strNumAsiento);

			if (valorAsientoValido == false) {
				recargarConMensajeInsertar(request, response, "entradaErronea");
				return;
			}
			
			long numAsiento = Long.parseLong(strNumAsiento);

			// ***** ERRORES PRECIO ****//
			boolean valorPVPValido = esPVPValido(strPVP);

			if (valorPVPValido == false) {
				recargarConMensajeInsertar(request, response, "entradaErronea");
				return;
			}
			
			Double pvp = Double.parseDouble(strPVP);
			
			/*** VERIFICAR ASIENTO OCUPADO **/		
			boolean ocupado = false;

			List<Pasaje> pasajesVuelo = pasajesDAO.listadoEjercicio2(strIDVuelo);

			for (Pasaje pasajeVuelo : pasajesVuelo) {

				if (pasajeVuelo.getNumAsiento() == numAsiento) {
					ocupado = true;
				}
			}

			if (ocupado) {

				recargarConMensajeInsertar(request, response, "asientoocupado");
				return;
			}
			
			/*** VERIFICAR PASAJERO ESTÁ EN ESE VUELO **/
			long pasajeroCod = Long.parseLong(strIDviajero);
			boolean enVuelo = false;
			
			for (Pasaje pasajeVuelo : pasajesVuelo) {
				
				if (pasajeVuelo.getPasajeroCod() == pasajeroCod) {
					enVuelo = true;
				}
			}
			
			if (enVuelo) {

				recargarConMensajeInsertar(request, response, "envuelo");
				return;
			}
			
			/*************** SI TODAS LAS ENTRADAS SON CORRECTAS ******************///
			// Insertamos el pasaje
			Pasaje nuevoPasaje = new Pasaje(idPasajeNuevo, pasajeroCod, strIDVuelo, numAsiento, strClase, pvp);

			boolean insertado = pasajesDAO.insertar(nuevoPasaje);
			
			if (insertado) {
				// Lo asignamos como atributo. Con RequestDispatcher su tiempo de vida no se
				// limita a la llamada html
				request.setAttribute("pasaje", nuevoPasaje);
				request.setAttribute("mensaje", "insertado");
				

				// Enviamos el request a la pagina JSP, debe importar el paquete de las clases
				RequestDispatcher rd = (RequestDispatcher) request.getRequestDispatcher("/modifpasaje.jsp");
				rd.forward(request, response);
				return;
			}
			
			recargarConMensajeInsertar(request, response, "");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
	}
	
	private void recargarConMensajeInsertar(HttpServletRequest request, HttpServletResponse response, String mensaje) {
		try {

			// Lista de pasajeros para el combobox
			List<Pasajero> listaPasajeros = pasajerosDAO.consultarTodos();
			request.setAttribute("listapasajeros", listaPasajeros);
			
			// Lista de Vuelos para el combobox
			List<Vuelo> vuelos = vuelosDAO.listadoEjercicio1();
			request.setAttribute("listavuelos", vuelos);
			
			request.setAttribute("eleccionviajero", request.getParameter("eleccionviajero"));
			request.setAttribute("eleccionVuelo", request.getParameter("eleccionVuelo"));
			request.setAttribute("numasiento", request.getParameter("numasiento"));
			request.setAttribute("idvuelo", request.getParameter("idvuelo"));

			request.setAttribute("clase", request.getParameter("clase"));
			request.setAttribute("pvp", request.getParameter("pvp"));

			// Establece el mensaje de error, si es necesario
			request.setAttribute("mensaje", mensaje);

			// Reenviar de vuelta a modifpasaje.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/insertarpasaje.jsp");
			rd.forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void formModifcarPasaje(HttpServletRequest request, HttpServletResponse response) {
		try {

			// Recuperamos el identificador del parametro y obtenemos el pasaje
			String id = (String) request.getParameter("id");
			Long idPasaje = Long.valueOf(id);

			Pasaje pasaje = pasajesDAO.consultar(idPasaje);

			// Lo asignamos como atributo. Con RequestDispatcher su tiempo de vida no se
			// limita a la llamada html
			request.setAttribute("pasaje", pasaje);

			// Enviamos el request a la pagina JSP, debe importar el paquete de las clases
			RequestDispatcher rd = (RequestDispatcher) request.getRequestDispatcher("/modifpasaje.jsp");
			rd.forward(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Al apretar el boton de modificar
	 * 
	 * @param request
	 * @param response
	 */
	private void modifPasaje(HttpServletRequest request, HttpServletResponse response) {
		try {

			/*** RECUPERACION DE LOS DATOS DEL FORMULARIO CON CONTROL BASICO **/
			// Recuperamos los datos modificados del formulario
			// Los atributos NAME de cada elemento del formulario
			String strIdPasaje = request.getParameter("idpasaje");
			long idPasaje = Long.parseLong(strIdPasaje);

			String strPasCod = request.getParameter("codpasajero");
			long pasajeroCod = Long.parseLong(strPasCod);

			String identificador = request.getParameter("idvuelo");
			String clase = request.getParameter("clase");
			String sPVP = request.getParameter("pvp");

			// **** ERRORES ASIENTO ****//
			String strAsiento = request.getParameter("numasiento");
			boolean valorAsientoValido = esAsientoValido(strAsiento);

			if (valorAsientoValido == false) {
				recargarConMensaje(request, response, "entradaErronea");
				return;
			}
			
			long numAsiento = Long.parseLong(strAsiento);

			// ***** ERRORES PRECIO ****//
			boolean valorPVPValido = esPVPValido(sPVP);

			if (valorPVPValido == false) {
				recargarConMensaje(request, response, "entradaErronea");
				return;
			}
			
			Double pvp = Double.parseDouble(sPVP);
			
			/*** VERIFICAR ASIENTO OCUPADO **/
			Pasaje pasajeOriginal = pasajesDAO.consultar(idPasaje);
			long numAsientoOriginal = pasajeOriginal.getNumAsiento();
			
			boolean ocupado = false;

			List<Pasaje> pasajesVuelo = pasajesDAO.listadoEjercicio2(identificador);

			for (Pasaje pasajeVuelo : pasajesVuelo) {

				if (pasajeVuelo.getNumAsiento() == numAsiento && pasajeVuelo.getNumAsiento() != numAsientoOriginal) {
					ocupado = true;
				}
			}

			if (ocupado) {

				recargarConMensaje(request, response, "asientoocupado");
				return;
			}

			/*** SI TODO ESTA BIEN ACTUALIZAMOS **/
			// Creamos un objeto con los valores modificados
			Pasaje pasajeActualizado = new Pasaje(idPasaje, pasajeroCod, identificador, numAsiento, clase, pvp);

			// Lo actualizamos
			boolean modificado = pasajesDAO.modificar(idPasaje, pasajeActualizado);

			if (modificado) {
				recargarConMensaje(request, response, "actualizado");
				return;
			}
			
			recargarConMensaje(request, response, "");

		} catch (

		NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void recargarConMensaje(HttpServletRequest request, HttpServletResponse response, String mensaje) {
		try {

			request.setAttribute("idpasaje", request.getParameter("idpasaje"));
			request.setAttribute("codpasajero", request.getParameter("codpasajero"));
			request.setAttribute("numasiento", request.getParameter("numasiento"));
			request.setAttribute("idvuelo", request.getParameter("idvuelo"));
			request.setAttribute("clase", request.getParameter("clase"));
			request.setAttribute("pvp", request.getParameter("pvp"));

			// Establece el mensaje de error, si es necesario
			request.setAttribute("mensaje", mensaje);

			// Reenviar de vuelta a modifpasaje.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/modifpasaje.jsp");
			rd.forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private boolean esPVPValido(String sPVP) {

		try {
			if (sPVP == null || sPVP.trim().isEmpty()) {
				return false;
			}

			String normalizedPVP = sPVP.replace(",", ".");
	        double pvp = Double.parseDouble(normalizedPVP);

			if (pvp <= 0) {
				return false;
			}

		} catch (NumberFormatException e) {

			e.printStackTrace();
		}

		return true;
	}

	private boolean esAsientoValido(String strAsiento) {

		try {
			if (strAsiento == null || strAsiento.equals("")) {
				return false;
			}

			long numAsiento = Long.parseLong(strAsiento);

			if (numAsiento <= 0) {
				return false;
			}

		} catch (NumberFormatException e) {

			e.printStackTrace();
		}

		return true;
	}

	private void borrarPasaje(HttpServletRequest request, HttpServletResponse response) {

		// Recuperemos el id de pasaje a borrar
		String id = (String) request.getParameter("id");
		Long idPasaje = Long.valueOf(id);

		// Eliminamos de la BBDD
		boolean borrado = pasajesDAO.eliminar(idPasaje);

		// Volvemos a la pagina
		String param = "false";

		if (borrado) {
			param = "true";
		}

		request.setAttribute("borrado", param);

		modifInserPasaje(request, response);

	}

	private void modifInserPasaje(HttpServletRequest request, HttpServletResponse response) {
		try {

			// Listar los pasajes
			List<Pasaje> pasajes = pasajesDAO.listadoEjercicio3();
			request.setAttribute("pasajes", pasajes);

			// Enviamos el request a la pagina JSP, debe importar la clase Vuelo
			RequestDispatcher rd = (RequestDispatcher) request.getRequestDispatcher("/modborrarpasaje.jsp");
			rd.forward(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	private void mostrarpasajes(HttpServletRequest request, HttpServletResponse response) {
		// Segun el boton apretado
		String idVuelo = request.getParameter("eleccionVuelo");

		List<Pasaje> pasajes = pasajesDAO.listadoEjercicio2(idVuelo);

		request.setAttribute("mensaje", "mostrarpasajes");
		request.setAttribute("pasajes", pasajes);

		detallesvuelos(request, response);

	}

	private void mostrardetalles(HttpServletRequest request, HttpServletResponse response) {
		// Segun el boton apretado
		String idVuelo = request.getParameter("eleccionVuelo");

		Vuelo vuelo = vuelosDAO.vueloEjercicio2(idVuelo);

		request.setAttribute("mensaje", "mostrardetalles");
		request.setAttribute("vuelo", vuelo);

		detallesvuelos(request, response);

	}

	private void detallesvuelos(HttpServletRequest request, HttpServletResponse response) {
		try {

			// Lista de Vuelos para el combobox
			List<Vuelo> vuelos = vuelosDAO.listadoEjercicio1();
			request.setAttribute("listavuelos", vuelos);

			// Enviamos el request a la pagina JSP, debe importar la clase Vuelo
			RequestDispatcher rd = (RequestDispatcher) request.getRequestDispatcher("/detallesvuelos.jsp");
			rd.forward(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	private void borrarvuelo(HttpServletRequest request, HttpServletResponse response) {
		// Recuperamos el parametro con el ID
		String identificador = request.getParameter("id");

		// Eliminamos de la BBDD
		boolean borrado = vuelosDAO.eliminar(identificador);

		// Volvemos a la pagina
		String param = "false";

		if (borrado) {
			param = "true";
		}

		request.setAttribute("borrado", param);

		// Se llama a listar vuelos para que cargue de nuevo la lista y muestre la
		// pagina
		listarvuelos(request, response);

	}

	private void listarvuelos(HttpServletRequest request, HttpServletResponse response) {

		try {
			// Obtenemos la lista de Vuelos
			List<Vuelo> vuelos = vuelosDAO.listadoEjercicio1();

			// Agregamos la lista al Request
			request.setAttribute("listavuelos", vuelos);

			// Enviamos el request a la pagina JSP que debe importar el paquete donde está
			// la clase VUELO
			RequestDispatcher rd = (RequestDispatcher) request.getRequestDispatcher("/listarvuelos.jsp");
			rd.forward(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
