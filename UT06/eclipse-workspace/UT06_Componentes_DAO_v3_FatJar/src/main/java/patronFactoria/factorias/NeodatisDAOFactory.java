/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronFactoria.factorias;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import patronFactoria.factoria_abstracta.DAOFactory;
import patronFactoria.interfazDAO_Impl.NeodatisDepartamentoImpl;
import patronFactoria.interfazDAO.DepartamentoDAO;

/**
 * Patron Singleton
 * @author Isra
 */
public class NeodatisDAOFactory extends DAOFactory {

    static ODB odb = null;

    public NeodatisDAOFactory() {
    }

    public static ODB crearConexion() {
        if (odb == null) {
            odb = ODBFactory.open("Departamento.BD");
        }
        return odb;
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new NeodatisDepartamentoImpl();
    }
}
