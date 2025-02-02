package apartado3_6_Tema_ejemplos;

//REVISAR URI Y PUERTO Y LA COLECCION CON LA QUE SE HACEN LAS PRUEBAS

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import org.exist.xmldb.EXistResource;

public class XQueryExample {
  private static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/";
     
    public static void main(String args[]) throws Exception {
        final String driver = "org.exist.xmldb.DatabaseImpl"; 
        // initialize database driver
        
        String coleccion ="ColeccionPruebas";
        String expresionXQuery ="/EMPLEADOS/EMP_ROW[DEPT_NO=10]";
      
        
        Class<?> cl = Class.forName(driver);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);  
        Collection col = null;
        try { 
            col = DatabaseManager.getCollection(URI + coleccion);
            XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
            xqs.setProperty("indent", "yes");
        
            CompiledExpression compiled = xqs.compile(expresionXQuery);
            ResourceSet result = xqs.execute(compiled);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                } finally {
                    //dont forget to cleanup resources
                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }
            }
        } finally {
            //dont forget to cleanup
            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
    }
}
