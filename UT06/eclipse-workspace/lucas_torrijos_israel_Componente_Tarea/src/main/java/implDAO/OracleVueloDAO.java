package implDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beansaeropuerto.Vuelo;
import clasesfuncionales.GestorFechas;
import factoria.OracleFactoria;
import interfacesDAO.VueloDAO;

public class OracleVueloDAO implements VueloDAO{
	private Connection conexion = null;
	
	public OracleVueloDAO() {
		conexion = OracleFactoria.crearConexion();
	}

	private void crearConexion() {
		// Si no existe la conexión la creamos
		if (conexion == null) {
			conexion = OracleFactoria.crearConexion();
		}
	}
	
	private void mensajeError(SQLException ex) {

		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje : %s %n", ex.getMessage().trim());
		System.out.printf("SQL estado: %s %n", ex.getSQLState());
		System.out.printf("Cód error : %s %n%n", ex.getErrorCode());

	}	
	
	@Override
	public boolean insertar(Vuelo objeto) {
		boolean fueInsertado = false;

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "INSERT INTO Vuelo VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, objeto.getIdentificador());
			ptmt.setString(2, objeto.getAeropuertoOrigen());
			ptmt.setString(3, objeto.getAeropuertoDestino());
			ptmt.setString(4, objeto.getTipoVuelo());
			ptmt.setDate(5, GestorFechas.toSQLDate(objeto.getFechaVuelo()));
			ptmt.setInt(6, objeto.getDescuento());

			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueInsertado = true;
				System.out.printf("Vuelo %s insertado%n", objeto.getIdentificador());
				
				if (conexion.getAutoCommit() == false) {
					conexion.commit();
				}
			}

			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}

		return fueInsertado;
	}

	@Override
	public boolean eliminar(String codigo) {
		boolean fueEliminado = false;
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "DELETE FROM Vuelo WHERE IDENTIFICADOR=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, codigo);


			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueEliminado = true;
				System.out.printf("Vuelo %s eliminado%n", codigo);
				
				if (conexion.getAutoCommit() == false) {
					conexion.commit();
				}
			}

			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}

		return fueEliminado;
	}

	@Override
	public boolean modificar(String codigo, Vuelo objeto) {
		boolean fueModificado = false;
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "UPDATE VUELO " +
                "SET IDENTIFICADOR = ?, " +
                "AEROPUERTOORIGEN = ?, " +
                "AEROPUERTODESTINO = ?, " +
                "TIPOVUELO = ?, " +
                "FECHAVUELO = ?, " + 
                "DESCUENTO = ? " +
                "WHERE IDENTIFICADOR = ?";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, objeto.getIdentificador());
			ptmt.setString(2, objeto.getAeropuertoOrigen());
			ptmt.setString(3, objeto.getAeropuertoDestino());
			ptmt.setString(4, objeto.getTipoVuelo());
			ptmt.setDate(5, GestorFechas.toSQLDate(objeto.getFechaVuelo()));
			ptmt.setLong(6, objeto.getDescuento());
			ptmt.setString(7, objeto.getIdentificador());


			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueModificado = true;
				System.out.printf("Vuelo %s modificado%n", codigo);
				
				if (conexion.getAutoCommit() == false) {
					conexion.commit();
				}
			}

			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
		
		return fueModificado;
	}

	@Override
	public Vuelo consultar(String codigo) {
		Vuelo vuelo = new Vuelo();
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT * FROM VUELO WHERE IDENTIFICADOR=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, codigo);

			ResultSet rs = ptmt.executeQuery();
			
			if (rs.next()) {
				vuelo.setIdentificador(rs.getString(1));
				vuelo.setAeropuertoOrigen(rs.getString(2));
				vuelo.setAeropuertoDestino(rs.getString(3));
				vuelo.setTipoVuelo(rs.getString(4));
				vuelo.setFechaVuelo(rs.getDate(5));
				vuelo.setDescuento(rs.getInt(6));
				
			} else {
				System.out.printf("El vuelo %s no existe.%n", codigo);
			}

			rs.close();
			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
	
		return vuelo;
	}

	@Override
	public List<Vuelo> consultarTodos() {
		List<Vuelo> listaVuelos = new ArrayList<>();
		
		
		// Si no existe la conexión la creamos
		crearConexion();
		
		String strSQL = "SELECT * FROM VUELO ORDER BY IDENTIFICADOR";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ResultSet rs = ptmt.executeQuery();
			
			while (rs.next()) {
				Vuelo vuelo = new Vuelo();
				
				vuelo.setIdentificador(rs.getString(1));
				vuelo.setAeropuertoOrigen(rs.getString(2));
				vuelo.setAeropuertoDestino(rs.getString(3));
				vuelo.setTipoVuelo(rs.getString(4));
				vuelo.setFechaVuelo(rs.getDate(5));
				vuelo.setDescuento(rs.getInt(6));
				
				listaVuelos.add(vuelo);
				
			}
			
			rs.close();
			ptmt.close();
			
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
		
		return listaVuelos;
	}
	
	public List<Vuelo> listadoEjercicio1(){
		
		// Si no existe la conexión la creamos
		crearConexion();
		
		List<Vuelo> listado = new ArrayList<>();
		
		String strSQL = "SELECT v.identificador, v.aeropuertoorigen, ao.nombre, ao.pais,  v.aeropuertodestino, ad.nombre, ad.pais, v.tipovuelo, count(p.identificador) "+
					    "FROM VUELO v left JOIN PASAJE p on (p.identificador = v.identificador) " +
					    "LEFT JOIN AEROPUERTO ao on (v.aeropuertoorigen = ao.codaeropuerto) " +
					    "LEFT JOIN AEROPUERTO ad on (v.aeropuertodestino = ad.codaeropuerto) " + 
					    "GROUP BY v.identificador, v.aeropuertoorigen, ao.nombre, ao.pais,  v.aeropuertodestino, ad.nombre, ad.pais, v.tipovuelo " + 
					    "ORDER BY v.identificador";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			
			ResultSet rs = ptmt.executeQuery();
			
			while (rs.next()) {
				Vuelo vuelo = new Vuelo();
				
				vuelo.setIdentificador(rs.getString(1));
				
				vuelo.setAeropuertoOrigen(rs.getString(2));
				vuelo.setNombreAeroOrigen(rs.getString(3));
				vuelo.setPaisOrigen(rs.getString(4));
				
				vuelo.setAeropuertoDestino(rs.getString(5));
				vuelo.setNombreAeroDestino(rs.getString(6));
				vuelo.setPaisDestino(rs.getString(7));
				
				vuelo.setTipoVuelo(rs.getString(8));
				vuelo.setNumPasajeros(rs.getInt(9));
				
				listado.add(vuelo);
				
			}
			
			rs.close();
			ptmt.close();
			
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
		
		
		return listado;
		
		
	}
	
	public Vuelo vueloEjercicio2(String id){
		Vuelo vuelo = new Vuelo();
		
		List<Vuelo> vuelos = listadoEjercicio1();
		
		for (Vuelo vueloBuscado : vuelos) {
			
			if (vueloBuscado.getIdentificador().equals(id)) {
				return vueloBuscado;
			}
			
		}
		
		
		return vuelo;
	}

}
