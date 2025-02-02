/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronFactoria.factorias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import patronFactoria.factoria_abstracta.DAOFactory;
import patronFactoria.interfazDAO_Impl.SQLDbDepartamentoImpl;
import patronFactoria.interfazDAO.DepartamentoDAO;

/**
 *
 * @author Isra
 */
public class SQLDbDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "";
    static String URLDB = "";
    static String USUARIO = "unidad6";
    static String CLAVE = "unidad6";

    public SQLDbDAOFactory() {
        DRIVER = "com.mysql.cj.jdbc.Driver";
        URLDB = "jdbc:mysql://localhost:3306/unidad6";
    }
// crear la conexión si no está creada

    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER); // Cargar el driver
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQLDbDAOFactory.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
            try {
                conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
            } catch (SQLException ex) {
                System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
                System.out.printf("Mensaje : %s %n", ex.getMessage());

                System.out.printf("SQL estado: %s %n", ex.getSQLState());
                System.out.printf("Cód error : %s %n", ex.getErrorCode());
            }
        }
        return conexion;
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new SQLDbDepartamentoImpl();
    }
}
