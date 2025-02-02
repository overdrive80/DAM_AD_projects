package apartado3_6_Tema_ejemplos;

//REVISAR URI Y PUERTO Y LA COLECCION CON LA QUE SE HACEN LAS PRUEBAS

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import javax.xml.transform.OutputKeys;
import org.exist.xmldb.EXistResource;

public class RetrieveExample {
    
    private static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/";
    /**
     * args[0] Should be the name of the collection to access
     * args[1] Should be the name of the resource to read from the collection
     */
    public static void main(String args[]) throws Exception {
        
        String coleccion ="ColeccionPruebas";
        String fichero ="departamentos.xml";
        
        final String driver = "org.exist.xmldb.DatabaseImpl";
        
        // initialize database driver
        Class<?> cl = Class.forName(driver);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        
        Collection col = null;
        XMLResource res = null;
        try {    
            // get the collection
            col = DatabaseManager.getCollection(URI + coleccion);
            col.setProperty(OutputKeys.INDENT, "no");
            res = (XMLResource)col.getResource(fichero);
            
            if(res == null) {
                System.out.println("document not found!");
            } else {
                System.out.println(res.getContent());
            }
        } finally {
            //dont forget to clean up!
            
            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
            
            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) 
                                           {xe.printStackTrace();}
            }
        }
    }
}