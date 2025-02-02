package apartado3_6_Tema_ejemplos;

//REVISAR URI Y PUERTO Y LA COLECCION CON LA QUE SE HACEN LAS PRUEBAS

import java.io.File;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;

@SuppressWarnings("unused")
public class StoreExample {
    
   private static String URI = "xmldb:exist://localhost:8085/exist/xmlrpc/db/";
    /**
     * args[0] Should be the name of the collection to access
     * args[1] Should be the name of the file to read and store in the collection
     */
    public static void main(String args[]) throws Exception {      
       /* if(args.length < 2) {
            System.out.println("usage: StoreExample collection-path document");
            System.exit(1);
        }*/
        final String driver = "org.exist.xmldb.DatabaseImpl";
        
        
        String coleccion ="ColeccionPruebas";
        String fichero ="Catalog.xml";
      
        
        // initialize database driver
        Class<?> cl = Class.forName(driver);
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
                      
        String usu = "admin"; //Usuario
        String usuPwd = "admin"; //Clave
        
        //Collection col = null;
        Collection col = DatabaseManager.getCollection(URI+coleccion, usu, usuPwd);
        //necesito usuario y clave, si no me da error
        //Write permission is not granted on the Collection.
    
        XMLResource res = null;
        
        try { 
            // col = getOrCreateCollection(coleccion);
            //no uso esta llamada a este metodo
            
            File f = new File(fichero);
            if(!f.canRead()) {
                System.out.println("cannot read file " + fichero);
                return;
            }    
           
            // create new XMLResource; an id will be assigned to the new resource
            res = (XMLResource) col.createResource(f.getName(), "XMLResource");
                       
            res.setContent(f);
            
            System.out.print("storing document " + res.getId() + "...");
            
            col.storeResource(res);
          
            System.out.println("ok.");
        } finally {
            //dont forget to cleanup
            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }        
            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
    }//main
    
    private static Collection getOrCreateCollection(String collectionUri) throws 
XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }//
    
    private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException { 
        Collection col = DatabaseManager.getCollection(URI + collectionUri);
        if(col == null) {
            if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }
            String pathSegments[] = collectionUri.split("/");
            if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();
                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }
                Collection start = DatabaseManager.getCollection(URI + path);
                if(start == null) {
                    //collection does not exist, so create
                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parent = DatabaseManager.getCollection(URI + parentPath);
                    CollectionManagementService mgt = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    col.close();
                    parent.close();
                } else {
                    start.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }//
    
    
}//FIN