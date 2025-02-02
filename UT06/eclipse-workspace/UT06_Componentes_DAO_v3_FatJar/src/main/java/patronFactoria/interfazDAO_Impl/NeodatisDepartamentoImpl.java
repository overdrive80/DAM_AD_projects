/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronFactoria.interfazDAO_Impl;

//implementación de la interfaz
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import patronFactoria.clasesDAO.Departamento;
import patronFactoria.factorias.NeodatisDAOFactory;
import patronFactoria.interfazDAO.DepartamentoDAO;

/**
 * En el patron DAO las clases responsables de la obtención de datos de la BBDD 
 * @author mjesus
 */
public class NeodatisDepartamentoImpl implements DepartamentoDAO {

    static ODB bd;

    public NeodatisDepartamentoImpl() {
        bd = NeodatisDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarDep(Departamento dep) {
        bd.store(dep);
        bd.commit();
        System.out.printf("Departamento: %d Insertado %n", dep.getDeptno());
        return true;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento depart = (Departamento) objetos.getFirst();
            bd.delete(depart);
            bd.commit();
            valor = true;
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Departamento a eliminar: %d No existe%n", deptno);
        }

        return valor;
    }

    @Override
    public boolean ModificarDep(int deptno, Departamento dep) {
        boolean valor = false;
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        try {
            Departamento depart = (Departamento) objetos.getFirst();
            depart.setDnombre(dep.getDnombre());
            depart.setLoc(dep.getLoc());
            bd.store(depart); // actualiza el objeto 
            valor = true;
            bd.commit();
        } catch (IndexOutOfBoundsException i) {
            System.out.printf("Departamento: %d No existe%n", deptno);
        }

        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
        Objects<Departamento> objetos = bd.getObjects(query);
        Departamento dep = new Departamento();
        if (objetos != null) {
            try {
                dep = (Departamento) objetos.getFirst();
            } catch (IndexOutOfBoundsException i) {
                System.out.printf("Departamento: %d No existe%n", deptno);
                dep.setDnombre("no existe");
                dep.setDeptno(deptno);
                dep.setLoc("no existe");
            }
        }
        return dep;
    }

}

