package factoria;

import interfacesDAO.*;

public abstract class FactoriaDAO {

	public static final int ORACLE = 1;
	
	public abstract AeropuertoDAO getAeropuertoDAO();
	public abstract PasajeDAO getPasajeDAO();
	public abstract PasajeroDAO getPasajeroDAO();
	public abstract VueloDAO getVueloDAO();
	
	public static FactoriaDAO getFactoriaDAO(int tipoBaseDatos) {
		
		switch (tipoBaseDatos){
		case ORACLE:
			return new OracleFactoria();
		default:
			return null;
		}
	}
	
}
