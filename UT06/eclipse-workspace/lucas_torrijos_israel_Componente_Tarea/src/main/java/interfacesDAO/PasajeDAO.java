package interfacesDAO;

import java.util.List;

import beansaeropuerto.Pasaje;

public interface PasajeDAO {

	//Define las declaraciones de las operaciones CRUD
	public boolean insertar(Pasaje objeto);
	public boolean eliminar(long codigo);
	public boolean modificar(long codigo, Pasaje objeto);
	public Pasaje consultar(long codigo);
	public List<Pasaje> consultarTodos();
	
	//Metodos especificos
	public List<Pasaje> listadoEjercicio2(String id);
	public List<Pasaje> listadoEjercicio3();
	public long nuevoIDPasaje();

}
