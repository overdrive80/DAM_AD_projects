package implDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beansaeropuerto.Aeropuerto;
import factoria.OracleFactoria;
import interfacesDAO.AeropuertoDAO;

public class OracleAeropuertoDAO implements AeropuertoDAO {
	private Connection conexion;

	private void crearConexion() {
		// Si no existe la conexión la creamos
		if (conexion == null) {
			conexion = OracleFactoria.crearConexion();
		}
	}

	@Override
	public boolean insertar(Aeropuerto objeto) {
		boolean fueInsertado = false;

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "INSERT INTO Aeropuerto VALUES (?,?,?,?,?)";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, objeto.getCodAeropuerto());
			ptmt.setString(2, objeto.getNombre());
			ptmt.setString(3, objeto.getCiudad());
			ptmt.setString(4, objeto.getPais());
			ptmt.setLong(5, objeto.getTasa());

			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueInsertado = true;
				System.out.printf("Aeropuerto %s insertado%n", objeto.getNombre());
				
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

		String strSQL = "DELETE FROM Aeropuerto AE WHERE AE.CODAEROPUERTO=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, codigo);


			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueEliminado = true;
				System.out.printf("Aeropuerto %s eliminado%n", codigo);
				
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
	public boolean modificar(String codigo, Aeropuerto objeto) {
		boolean fueModificado = false;
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "UPDATE AEROPUERTO AE " +
                "SET AE.codaeropuerto = ?, " +
                "AE.nombre = ?, " +
                "AE.ciudad = ?, " +
                "AE.pais = ?, " +
                "AE.tasa = ? " +
                "WHERE AE.codaeropuerto = ?";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, objeto.getCodAeropuerto());
			ptmt.setString(2, objeto.getNombre());
			ptmt.setString(3, objeto.getCiudad());
			ptmt.setString(4, objeto.getPais());
			ptmt.setLong(5, objeto.getTasa());
			ptmt.setString(6, objeto.getCodAeropuerto());


			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueModificado = true;
				System.out.printf("Aeropuerto %s modificado%n", codigo);
				
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
	public Aeropuerto consultar(String codigo) {
		Aeropuerto aero = new Aeropuerto();
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT * FROM Aeropuerto AE WHERE AE.CODAEROPUERTO=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setString(1, codigo);

			ResultSet rs = ptmt.executeQuery();
			
			if (rs.next()) {
				aero.setCodAeropuerto(rs.getString(1));
				aero.setNombre(rs.getString(2));
				aero.setCiudad(rs.getString(3));
				aero.setPais(rs.getString(4));
				aero.setTasa(rs.getLong(5));
				
			} else {
				System.out.printf("El aeropuerto %s no existe.%n", codigo);
			}

			rs.close();
			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
	
		return aero;
	}

	private void mensajeError(SQLException ex) {

		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje : %s %n", ex.getMessage().trim());
		System.out.printf("SQL estado: %s %n", ex.getSQLState());
		System.out.printf("Cód error : %s %n%n", ex.getErrorCode());

	}

	@Override
	public List<Aeropuerto> consultarTodos() {
		List<Aeropuerto> listaAeropuertos = new ArrayList<>();
		
		
		// Si no existe la conexión la creamos
		crearConexion();
		
		String strSQL = "SELECT * FROM AEROPUERTO ORDER BY CODAEROPUERTO";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ResultSet rs = ptmt.executeQuery();
			
			while (rs.next()) {
				Aeropuerto aero = new Aeropuerto();
				
				aero.setCodAeropuerto(rs.getString(1));
				aero.setNombre(rs.getString(2));
				aero.setCiudad(rs.getString(3));
				aero.setPais(rs.getString(4));
				aero.setTasa(rs.getLong(5));
				
				listaAeropuertos.add(aero);
				
			}
			
			rs.close();
			ptmt.close();
			
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
		
		return listaAeropuertos;
	}
}
