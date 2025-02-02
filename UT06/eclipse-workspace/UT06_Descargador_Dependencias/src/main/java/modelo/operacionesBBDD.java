package modelo;

public class operacionesBBDD {

	private BaseDatos bbdd = null;
	private String url="jdbc:oracle:thin:@localhost:1521:xe";
	private String user="EJEMPLO";
	private String pass="ejemplo";
	
	public static void main(String[] args) {
		new operacionesBBDD();
	}
	
	public operacionesBBDD() {
		this.bbdd = BaseDatos.crearInstancia(url, user, pass);
		bbdd.setConexion();
		
		if (bbdd.hasConexion()) {
			System.out.println("Tiene conexion");
		}
	}
}
