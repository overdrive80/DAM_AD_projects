/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dep.DAOFactory;
import Dep.Departamento;
import Dep.DepartamentoDAO;
import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mjesus
 */
public class Controlador extends HttpServlet {    

	private static final long serialVersionUID = -2908378233602428340L;
	DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();
     
        // se obtiene la acción a realizar
        String op = request.getParameter("accion");

        // pantalla de alta de departamento
        if (op.equals("alta")) {
            response.sendRedirect("alta.jsp");
        }

        // se inserta departamento en la tabla
        if (op.equals("insertar")) {
            pantalla.Departamentos dep = (pantalla.Departamentos) request.getAttribute("depart");// obtenerlos

            Departamento departamento = new Departamento(dep.getDeptno(), dep.getDnombre(), dep.getLoc());
            boolean insertar = depDAO.InsertarDep(departamento);
            String mensaje = "";
            if (insertar) {
                mensaje = "Departamento " + dep.getDeptno() + " insertado";
            } else {
                mensaje = "Error al insertar Departamento " + dep.getDeptno();
            }

            request.setAttribute("mensaje", mensaje); //se envía mensaje al jsp
            RequestDispatcher rd
                    = request.getRequestDispatcher("/DepartamentoInsertado.jsp");
            rd.forward(request, response);

        }
        // se obtienen los datos de los departamentos para visualizarlos
        if (op.equals("listado")) {
            @SuppressWarnings("rawtypes")
			ArrayList lista = depDAO.ObtenerDepartamentos();
            request.setAttribute("departamentos", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/listado.jsp");
            rd.forward(request, response);
        }
       

    }
   
     public void destroy() {            
            bd = null;
        }
}