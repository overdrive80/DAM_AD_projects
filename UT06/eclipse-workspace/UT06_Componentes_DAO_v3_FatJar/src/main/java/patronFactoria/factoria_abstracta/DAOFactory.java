/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronFactoria.factoria_abstracta;

import patronFactoria.factorias.NeodatisDAOFactory;
import patronFactoria.factorias.SQLDbDAOFactory;
import patronFactoria.interfazDAO.DepartamentoDAO;

public abstract class DAOFactory {
// Bases de datos soportadas

    public static final int MYSQL = 1;
    public static final int NEODATIS = 2;
    public static final int ORACLE = 3;
    public static final int SQLITE = 4;

    public abstract DepartamentoDAO getDepartamentoDAO();

    public static DAOFactory getDAOFactory(int bd) {
        switch (bd) {
            case MYSQL:
                return new SQLDbDAOFactory();
            case NEODATIS:
                return new NeodatisDAOFactory();
            case ORACLE:
            case SQLITE:
            default:
                return null;
        }
    }
}
