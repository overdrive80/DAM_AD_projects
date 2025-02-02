/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplo;

import java.util.Scanner;
import patronFactoria.clasesDAO.Departamento;
import patronFactoria.factoria_abstracta.DAOFactory;
import patronFactoria.interfazDAO.DepartamentoDAO;

public class PruebaSQLDB {

    public static void main(String[] args) {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();
        
        //crear departamento
        Departamento dep = new Departamento(17, "NÓMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);
        Scanner sc = new Scanner(System.in);
        int entero = 1;
        
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Departamento: ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n",
                    dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }
}
