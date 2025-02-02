package interfacesDAO;

import java.util.List;

import beansaeropuerto.Aeropuerto;

public interface AeropuertoDAO {
	
	//Define las declaraciones de las operaciones CRUD
	public boolean insertar(Aeropuerto objeto);
	public boolean eliminar(String codigo);
	public boolean modificar(String codigo, Aeropuerto objeto);
	public Aeropuerto consultar(String codigo);
	public List<Aeropuerto> consultarTodos();
}
