package ConsultaDepartamentoXML;

//REVISAR PUERTO Y URI. COLECCION ColeccionPruebas

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;

import org.xmldb.api.modules.XPathQueryService;
import javax.xml.transform.OutputKeys;

public class basedatosXML {

	protected static String driver = "org.exist.xmldb.DatabaseImpl";
	public static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/ColeccionPruebas";
	private Collection col;
	private XPathQueryService service;
	private Database database=null;
	private String usu="admin";
	private String usuPwd="admin";
		
	public basedatosXML() {};
	public XPathQueryService obtenerservicio(){
		return service;
	};
	
	public void conectar()  {
		try {
			Class<?> cl = null;
			cl = Class.forName(driver);
		     database = (Database) cl.getDeclaredConstructor().newInstance();
		     DatabaseManager.registerDatabase(database);
		}catch (Exception e) {
			 System.out.println("Error inicializar driver");
			 e.printStackTrace();
		}
		try{
	     	col = DatabaseManager.getCollection(URI, usu, usuPwd);
		}catch (Exception e) {
			 System.out.println("Error  inicializar colecc");
			 e.printStackTrace();
		}
		try
		{service =(XPathQueryService) col.getService("XPathQueryService", "1.0");
			service.setProperty("pretty", "true");
			service.setProperty("encoding", "ISO-8859-1");
			col.setProperty(OutputKeys.INDENT, "no");
			service.setProperty("indent", "yes");
		}catch (Exception e) {
			 System.out.println("Error  inicializar servicio");
			 e.printStackTrace();
		}
}
	
	
	}
// FIN CLASE basedatosXML

