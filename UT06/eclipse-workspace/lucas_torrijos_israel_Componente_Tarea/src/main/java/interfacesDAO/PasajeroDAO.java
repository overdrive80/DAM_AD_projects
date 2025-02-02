package interfacesDAO;

import java.util.List;

import beansaeropuerto.Pasajero;

public interface PasajeroDAO {
	
	//Define las declaraciones de las operaciones CRUD
	public boolean insertar(Pasajero objeto);
	public boolean eliminar(long codigo);
	public boolean modificar(long codigo, Pasajero objeto);
	public Pasajero consultar(long codigo);
	public List<Pasajero> consultarTodos();
}
