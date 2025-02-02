/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba;

import patrondao.clasesDAO_POJO.Departamento;
import patrondao.implinterfazDAO.DepartamentoImpl;
import patrondao.interfazOperacionesDAO.DepartamentoDAO;

public class DEP_DAO1_PRUEBA {

    public static void main(String[] args) {
        DepartamentoDAO depDAO = new DepartamentoImpl();
        
        //INSERTAR
        Departamento dep1 = new Departamento(17, "NÓMINAS", "SEVILLA");
        depDAO.InsertarDep(dep1);
        
        //CONSULTAR
        Departamento dep2 = depDAO.ConsultarDep(17);
        System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n",
                dep2.getDeptno(), dep2.getDnombre(), dep2.getLoc());
        
        //MODIFICAR
        dep2.setDnombre("nuevonom");
        dep2.setLoc("nuevaloc");
        depDAO.ModificarDep(17, dep2);
        
        //ELIMINAR
        depDAO.EliminarDep(17);
    }
}
