/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronFactoria.interfazDAO;

import patronFactoria.clasesDAO.Departamento;

/**
 * Define operaciones con clases externas e 
 * implica el uso de objetos DAO
 * (objetos Departamento)
 *
 */
//interfaz DAO
public interface DepartamentoDAO {

    public boolean InsertarDep(Departamento dep);

    public boolean EliminarDep(int deptno);

    public boolean ModificarDep(int deptno, Departamento dep);

    public Departamento ConsultarDep(int deptno);
}
