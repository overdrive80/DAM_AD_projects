package implDAO;

import java.util.Date;
import java.util.List;

import beansaeropuerto.*;
import interfacesDAO.*;
@SuppressWarnings("unused")
public class test2 {

	public static void main(String[] args) {
		VueloDAO vueloDao = new OracleVueloDAO();
		List<Vuelo> vuelos = vueloDao.listadoEjercicio1();
		
		Vuelo vuelo = vueloDao.vueloEjercicio2("AVI-ASD");
		
		
		System.out.println(vuelo);
		
		
//		probarVuelo();
//		probarPasajero();
// 		probarPasaje();
//		probarAeropuerto();
		
	}
	
	private static void probarVuelo() {
		Vuelo vuelo = new Vuelo("MAD-99","MAD LEMD", "OSL ENGM", "DIRECTO", new Date(),90);
		VueloDAO vueloDao = new OracleVueloDAO();
		
		vueloDao.insertar(vuelo);
		vuelo.setTipoVuelo("KAMIKAZE");
		vueloDao.modificar("MAD-99", vuelo);
		
		vueloDao.eliminar("MAD-99");
		
		vuelo = vueloDao.consultar("BRU-1234");
		System.out.println(vuelo.toString());
		
		List<Vuelo> vuelos = vueloDao.consultarTodos();
		
		for (Vuelo vuelosAux : vuelos) {
			
			System.out.println(vuelosAux.toString());
		}
	}
	

	private static void probarPasajero() {
		Pasajero pasajero = new Pasajero(99,"Test", "919191910", "Calle prueba", "Bolivia");
		PasajeroDAO pasajeroDao = new OraclePasajeroDAO();
		
		pasajeroDao.insertar(pasajero);
		
		pasajero.setNombre("ISRAEL");
		pasajeroDao.modificar(99, pasajero);
		
		pasajeroDao.eliminar(99);
		
		pasajero = pasajeroDao.consultar(1);
		System.out.println(pasajero.toString());
		
		List<Pasajero> pasajeros = pasajeroDao.consultarTodos();
		
		for (Pasajero pasajerosAux : pasajeros) {
			
			System.out.println(pasajerosAux.toString());
		}
		
	}
	
	private static void probarPasaje() {
		Pasaje pasaje = new Pasaje(200,19, "QAT-900", 9,"PRIMERA", 999.0 );
		
		PasajeDAO pasajeDao= new OraclePasajeDAO();
		
		pasajeDao.insertar(pasaje);
		
		pasaje.setPvp(2000.0);
		pasajeDao.modificar(200, pasaje);
		
		pasajeDao.eliminar(200);
		
		pasaje = pasajeDao.consultar(101);
		System.out.println(pasaje.toString());
		
		List<Pasaje> pasajes = pasajeDao.consultarTodos();
		
		for (Pasaje pasajeAux : pasajes) {
			
			System.out.println(pasajeAux.toString());
		}
	}
	
	private static void probarAeropuerto() {
		
		Aeropuerto aero = new Aeropuerto("MAD SPAN", "Barajas", "Madrid", "España", 50);
		
		AeropuertoDAO areoDAO = new OracleAeropuertoDAO();
		
		areoDAO.insertar(aero);
		
		aero.setTasa(20);
		areoDAO.modificar(aero.getCodAeropuerto(), aero);
				
		areoDAO.eliminar("MAD SPAN");
		
		aero = areoDAO.consultar("OSL ENGM");
		
		System.out.println(aero.toString());
		
		List<Aeropuerto> aeropuertos = areoDAO.consultarTodos();
		
		for (Aeropuerto aeropuertoAux : aeropuertos) {
			
			System.out.println(aeropuertoAux.toString());
		}
	}

}
