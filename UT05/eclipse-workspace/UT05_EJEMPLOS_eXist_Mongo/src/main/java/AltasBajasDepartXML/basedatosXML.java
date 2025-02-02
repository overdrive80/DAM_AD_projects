package AltasBajasDepartXML;

//REVISAR PUERTO Y URI. COLECCION ColeccionPruebas

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;

import javax.swing.JTextField;
import javax.xml.transform.OutputKeys;
@SuppressWarnings("unused")
public class basedatosXML {

	protected static String driver = "org.exist.xmldb.DatabaseImpl";
	public static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/ColeccionPruebas";
	private Collection col;
	private XPathQueryService servicio;
	private Database database = null;
	private String usu = "admin";
	private String usuPwd = "admin";

	public basedatosXML() {
	};

	public XPathQueryService obtenerservicio() {
		return servicio;
	};

	public void conectar() {
		try {
			Class<?> cl = null;
			cl = Class.forName(driver);
			database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);

			col = DatabaseManager.getCollection(URI, usu, usuPwd);
			servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			servicio.setProperty("pretty", "true");
			servicio.setProperty("encoding", "ISO-8859-1");
			col.setProperty(OutputKeys.INDENT, "no");
			servicio.setProperty("indent", "yes");

		} catch (Exception e) {
			System.out.println("Error en inicializar eXist");
			// e.printStackTrace();
		}
	}

	public boolean insertardep(int dep, String nom, String loc) {
		String nuevodep;
		if (consultar(dep))
			// el departamento existe en el documeto. NO SE INSERTA
			return false;
		try {
			nuevodep = "<DEP_ROW><DEPT_NO>" + dep + "</DEPT_NO><DNOMBRE>" + nom.toString() + "</DNOMBRE><LOC>"
					+ loc.toString() + "</LOC></DEP_ROW>";
			// Nos aseguramos de insertar en el documento departamentos.xml
			ResourceSet result = servicio
					.query("update insert " + nuevodep + " into doc('departamentos.xml')/departamentos");
		} catch (Exception e) {
			System.out.println("Error al insertar.");
			e.printStackTrace();
		}
		return true;
	} // insertardep

	public boolean consultar(int dep) {
		try {
			// El m�todo devuelve true si existe y false si no
			// Nos aseguramos de buscar solo en ese documento departamentos.xml
			ResourceSet result = servicio.query("for $d in doc('departamentos.xml')/departamentos/DEP_ROW[DEPT_NO='"
					+ dep + "']/DEPT_NO return $d");
			ResourceIterator i;
			i = result.getIterator();
			if (!i.hasMoreResources()) {
				return false; // no existe
			}
		} catch (Exception e) {
			System.out.println("ERROOOOORR EN LA CONSULTA");
			e.printStackTrace();
		}
		return true;
	}// consultar

	public boolean bajadep(int dep) {
		if (!consultar(dep))
			// el departamento NO existe en el documeto, no BORRA
			return false;
		try {
			// Nos aseguramos de trabajar en el documento departamentos.xml
			ResourceSet result = servicio
					.query("update delete doc('departamentos.xml')/departamentos/DEP_ROW[DEPT_NO='" + dep + "']");
		} catch (Exception e) {
			System.out.println("Error al borrar.");
			e.printStackTrace();
		}
		return true;

	}// fin baja dep

	public boolean ModificaDep(int dep, String nom, String loc) {

		String nuevodep;
		if (!consultar(dep))
			// el departamento NO existe en el documeto, no MODIFICA
			return false;
		try {
			// Nos aseguramos de trabajar en el documento departamentos.xml
			nuevodep = "<DEP_ROW><DEPT_NO>" + dep + "</DEPT_NO><DNOMBRE>" + nom.toString() + "</DNOMBRE><LOC>"
					+ loc.toString() + "</LOC></DEP_ROW>";
			ResourceSet result = servicio.query("update replace doc('departamentos.xml')/departamentos/DEP_ROW[DEPT_NO="
					+ dep + "] with " + nuevodep);
		} catch (Exception e) {
			System.out.println("Error al modificar.");
			e.printStackTrace();
		}
		return true;
	}

	public void visualizar(int dep, JTextField nombre, JTextField loc) {
		String consulta = " for $de in doc('departamentos.xml')/departamentos/DEP_ROW[DEPT_NO=" + dep + "] "
				+ "return concat($de/DNOMBRE, '**', $de/LOC) ";
		try {
			ResourceSet result = servicio.query(consulta);
			// Recorrer los datos del recurso.
			ResourceIterator i;
			i = result.getIterator();
			if (!i.hasMoreResources())
				System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
			else {
				Resource r = i.nextResource();
				String cadena = r.getContent().toString();
				nombre.setText(cadena.substring(0, cadena.indexOf("**")));
				loc.setText(cadena.substring(cadena.indexOf("**") + 2));
			}

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}

	}

	public void vertodosporconsola() {
		System.out.println("==============================================================");
		String consulta = " for $de in doc('departamentos.xml')/departamentos/DEP_ROW " + " return $de ";
		try {
			ResourceSet result = servicio.query(consulta);
			// Recorrer los datos del recurso.
			ResourceIterator i;
			i = result.getIterator();
			if (!i.hasMoreResources())
				System.out.println(" LA CONSULTA NO DEVUELVE NADA.");
			else {
				while (i.hasMoreResources()) {
					Resource r = i.nextResource();
					String cadena = r.getContent().toString();
					System.out.println(cadena);
				}
			}

		} catch (Exception e) {
			System.out.println("Error al consultar.");
			e.printStackTrace();
		}

	}

} // FIN CLASE basedatosXML
