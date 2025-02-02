package implDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beansaeropuerto.Pasajero;
import factoria.OracleFactoria;
import interfacesDAO.PasajeroDAO;

public class OraclePasajeroDAO implements PasajeroDAO  {
	private Connection conexion;

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
	public boolean insertar(Pasajero objeto) {
		boolean fueInsertado = false;

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "INSERT INTO Pasajero VALUES (?,?,?,?,?)";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, objeto.getPasajeroCod());
			ptmt.setString(2, objeto.getNombre());
			ptmt.setString(3, objeto.getTelefono());
			ptmt.setString(4, objeto.getDireccion());
			ptmt.setString(5, objeto.getPais());


			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueInsertado = true;
				System.out.printf("Pasajero %s insertado%n", objeto.getPasajeroCod());
				
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
	public boolean eliminar(long codigo) {
		boolean fueEliminado = false;
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "DELETE FROM Pasajero WHERE PASAJEROCOD=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, codigo);


			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueEliminado = true;
				System.out.printf("Pasajero %s eliminado%n", codigo);
				
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
	public boolean modificar(long codigo, Pasajero objeto) {
		boolean fueModificado = false;
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "UPDATE PASAJERO " +
                "SET PASAJEROCOD = ?, " +
                "NOMBRE = ?, " +
                "TLF = ?, " +
                "DIRECCION = ?, " +
                "PAIS = ? " +
                "WHERE PASAJEROCOD = ?";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, objeto.getPasajeroCod());
			ptmt.setString(2, objeto.getNombre());
			ptmt.setString(3, objeto.getTelefono());
			ptmt.setString(4, objeto.getDireccion());
			ptmt.setString(5, objeto.getPais());
			ptmt.setLong(6, objeto.getPasajeroCod());

			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueModificado = true;
				System.out.printf("Pasajero %s modificado%n", codigo);
				
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
	public Pasajero consultar(long codigo) {
		Pasajero pasajero = new Pasajero();
		
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT * FROM PASAJERO WHERE PASAJEROCOD=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, codigo);

			ResultSet rs = ptmt.executeQuery();
			
			if (rs.next()) {
				pasajero.setPasajeroCod(rs.getLong(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setTelefono(rs.getString(3));
				pasajero.setDireccion(rs.getString(4));
				pasajero.setPais(rs.getString(5));

				
			} else {
				System.out.printf("El vuelo %s no existe.%n", codigo);
			}

			rs.close();
			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
	
		return pasajero;
	}

	@Override
	public List<Pasajero> consultarTodos() {
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		
		// Si no existe la conexión la creamos
		crearConexion();
		
		String strSQL = "SELECT * FROM PASAJERO ORDER BY PASAJEROCOD";
		
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ResultSet rs = ptmt.executeQuery();
			
			while (rs.next()) {
				Pasajero pasajero = new Pasajero();
				
				pasajero.setPasajeroCod(rs.getLong(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setTelefono(rs.getString(3));
				pasajero.setDireccion(rs.getString(4));
				pasajero.setPais(rs.getString(5));
				
				listaPasajeros.add(pasajero);
				
			}
			
			rs.close();
			ptmt.close();
			
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
		
		return listaPasajeros;
	}

}
