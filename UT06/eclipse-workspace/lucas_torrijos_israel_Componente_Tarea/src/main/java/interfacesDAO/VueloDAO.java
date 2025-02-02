package interfacesDAO;

import java.util.List;

import beansaeropuerto.*;

public interface VueloDAO {
	
	//Define las declaraciones de las operaciones CRUD
	public boolean insertar(Vuelo objeto);
	public boolean eliminar(String codigo);
	public boolean modificar(String codigo, Vuelo objeto);
	public Vuelo consultar(String codigo);
	public List<Vuelo> consultarTodos();
	
	// Operaciones especificas ejercicio
	public List<Vuelo> listadoEjercicio1();
	public Vuelo vueloEjercicio2(String id);

}
