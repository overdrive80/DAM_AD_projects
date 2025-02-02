package apartado3_6_Tema_ejemplos;

//REVISAR URI Y PUERTO Y LA COLECCION CON LA QUE SE HACEN LAS PRUEBAS

import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

public class XPathExample {

    private static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/";

    public static void main(String args[]) throws Exception {
        final String driver = "org.exist.xmldb.DatabaseImpl";
        // initialize database driver

        String coleccion = "ColeccionPruebas";
        String expresionXPath = "for $em in /EMPLEADOS/EMP_ROW return $em";

        Class<?> cl = Class.forName(driver);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        try {
            col = DatabaseManager.getCollection(URI + coleccion);
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");
            ResourceSet result = xpqs.query(expresionXPath);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while (i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                } finally {
                    //dont forget to cleanup resources
                    try {
                        ((EXistResource) res).freeResources();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }
        } finally {
            //dont forget to cleanup
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
}
