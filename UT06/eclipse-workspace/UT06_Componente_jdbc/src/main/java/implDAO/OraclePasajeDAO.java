package implDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beansaeropuerto.*;
import factoria.OracleFactoria;
import interfacesDAO.PasajeDAO;

public class OraclePasajeDAO implements PasajeDAO {
	private Connection conexion;

	private void crearConexion() {
		// Si no existe la conexión la creamos
		if (conexion == null) {
			conexion = OracleFactoria.crearConexion();
		}
	}

	@Override
	public boolean insertar(Pasaje objeto) {
		boolean fueInsertado = false;

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "INSERT INTO Pasaje VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, objeto.getIdPasaje());
			ptmt.setLong(2, objeto.getPasajeroCod());
			ptmt.setString(3, objeto.getIdentificador());
			ptmt.setLong(4, objeto.getNumAsiento());
			ptmt.setString(5, objeto.getClase());
			ptmt.setDouble(6, objeto.getPvp());

			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueInsertado = true;
				System.out.printf("Pasaje %s insertado%n", objeto.getIdPasaje());

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

		String strSQL = "DELETE FROM Pasaje WHERE IDPASAJE=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, codigo);

			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueEliminado = true;
				System.out.printf("Pasaje %s eliminado%n", codigo);

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
	public boolean modificar(long codigo, Pasaje objeto) {
		boolean fueModificado = false;

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "UPDATE PASAJE " + "SET IDPASAJE = ?, " + "PASAJEROCOD = ?, " + "IDENTIFICADOR = ?, "
				+ "NUMASIENTO = ?, " + "CLASE = ?, " + "PVP = ? " + "WHERE IDPASAJE = ?";

		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, objeto.getIdPasaje());
			ptmt.setLong(2, objeto.getPasajeroCod());
			ptmt.setString(3, objeto.getIdentificador());
			ptmt.setLong(4, objeto.getNumAsiento());
			ptmt.setString(5, objeto.getClase());
			ptmt.setDouble(6, objeto.getPvp());
			ptmt.setLong(7, objeto.getIdPasaje());

			int filas = ptmt.executeUpdate();

			if (filas > 0) {
				fueModificado = true;
				System.out.printf("Pasaje %s modificado%n", codigo);

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
	public Pasaje consultar(long codigo) {
		Pasaje pasaje = new Pasaje();

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT * FROM PASAJE WHERE IDPASAJE=?";
		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ptmt.setLong(1, codigo);

			ResultSet rs = ptmt.executeQuery();

			if (rs.next()) {
				pasaje.setIdPasaje(rs.getLong(1));
				pasaje.setPasajeroCod(rs.getLong(2));
				pasaje.setIdentificador(rs.getString(3));
				pasaje.setNumAsiento(rs.getLong(4));
				pasaje.setClase(rs.getString(5));
				pasaje.setPvp(rs.getDouble(6));

			} else {
				System.out.printf("El pasaje %s no existe.%n", codigo);
			}

			rs.close();
			ptmt.close();
		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}

		return pasaje;
	}

	private void mensajeError(SQLException ex) {

		System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
		System.out.printf("Mensaje : %s %n", ex.getMessage().trim());
		System.out.printf("SQL estado: %s %n", ex.getSQLState());
		System.out.printf("Cód error : %s %n%n", ex.getErrorCode());

	}

	@Override
	public List<Pasaje> consultarTodos() {
		List<Pasaje> listaPasajes = new ArrayList<>();

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT * FROM PASAJE ORDER BY IDPASAJE";

		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);
			ResultSet rs = ptmt.executeQuery();

			while (rs.next()) {
				Pasaje pasaje = new Pasaje();

				pasaje.setIdPasaje(rs.getLong(1));
				pasaje.setPasajeroCod(rs.getLong(2));
				pasaje.setIdentificador(rs.getString(3));
				pasaje.setNumAsiento(rs.getLong(4));
				pasaje.setClase(rs.getString(5));
				pasaje.setPvp(rs.getDouble(6));

				listaPasajes.add(pasaje);

			}

			rs.close();
			ptmt.close();

		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}

		return listaPasajes;
	}

	public List<Pasaje> listadoEjercicio2(String id) {
		List<Pasaje> listaPasajes = new ArrayList<>();

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT pa.idpasaje, pa.pasajerocod, p.nombre, p.pais,"
				+ "pa.numasiento, pa.clase, pa.pvp, pa.identificador " + "FROM pasaje pa INNER JOIN pasajero p on "
				+ "(pa.pasajerocod = p.pasajerocod) " + "WHERE pa.identificador= '" + id + "' "
				+ "ORDER BY pa.idpasaje";

		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);

			ResultSet rs = ptmt.executeQuery();

			while (rs.next()) {
				Pasaje pasaje = new Pasaje();

				pasaje.setIdPasaje(rs.getLong(1));
				pasaje.setPasajeroCod(rs.getLong(2));
				pasaje.setNombrePasajero(rs.getString(3));
				pasaje.setPaisPasajero(rs.getString(4));

				pasaje.setNumAsiento(rs.getLong(5));
				pasaje.setClase(rs.getString(6));
				pasaje.setPvp(rs.getDouble(7));

				pasaje.setIdentificador(rs.getString(8));

				listaPasajes.add(pasaje);

			}

			rs.close();
			ptmt.close();

		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}

		return listaPasajes;
	}

	@Override
	public List<Pasaje> listadoEjercicio3() {
		List<Pasaje> listaPasajes = new ArrayList<>();

		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "SELECT pa.idpasaje, pa.pasajerocod, p.nombre, p.pais,"
				+ "pa.numasiento, pa.clase, pa.pvp, pa.identificador " + "FROM pasaje pa INNER JOIN pasajero p on "
				+ "(pa.pasajerocod = p.pasajerocod) " + "ORDER BY pa.idpasaje";

		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);

			ResultSet rs = ptmt.executeQuery();

			while (rs.next()) {
				Pasaje pasaje = new Pasaje();

				pasaje.setIdPasaje(rs.getLong(1));
				pasaje.setPasajeroCod(rs.getLong(2));
				pasaje.setNombrePasajero(rs.getString(3));
				pasaje.setPaisPasajero(rs.getString(4));

				pasaje.setNumAsiento(rs.getLong(5));
				pasaje.setClase(rs.getString(6));
				pasaje.setPvp(rs.getDouble(7));

				pasaje.setIdentificador(rs.getString(8));

				listaPasajes.add(pasaje);

			}

			rs.close();
			ptmt.close();

		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}

		return listaPasajes;
	}

	@Override
	public long nuevoIDPasaje() {
		long nuevo = -1;
		// Si no existe la conexión la creamos
		crearConexion();

		String strSQL = "select max(idpasaje) as maxID from pasaje";

		try {
			PreparedStatement ptmt = conexion.prepareStatement(strSQL);

			ResultSet rs = ptmt.executeQuery();

			if (rs.next()) {
				long maxID = rs.getLong("maxID");
				nuevo = maxID + 1; // Incrementa el máximo ID para obtener el nuevo
			} else {
				// Si no hay registros, el primer ID será 1
				nuevo = 1;
			}

			rs.close();
			ptmt.close();

		} catch (SQLException e) {
			System.out.println("La consulta no se ejecutó correctamente.\n");
			mensajeError(e);
		}
		
		return nuevo;
	}

}
