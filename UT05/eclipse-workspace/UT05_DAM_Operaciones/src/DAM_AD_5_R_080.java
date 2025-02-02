import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;















import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
@SuppressWarnings("unused")
public class DAM_AD_5_R_080 {

 static String driver = "org.exist.xmldb.DatabaseImpl"; //Driver para eXist
 static String URI="xmldb:exist://localhost:8082/exist/xmlrpc/db"; //URI base de datos   
 static String usu="admin"; //Usuario
 static String usuPwd="admin"; //Clave
 static String nombrecoleccion = "ColeccionEjercicio";
 
public static void main(String[] args)  {
	
	crearcoleccion(nombrecoleccion);	
	Crearclientes("Clientes.xml");
	Crearproveedores("Proveedores.xml");
	Crearcompras("Compras.xml");
	Creararticulos("Articulos.xml");
	Creardetallecompras("Detallecompras.xml");
}
////////////////////////////////////////////////////////////////////////
private static void Creardetallecompras(String nombrefic)  {
int errorfi=0;
File archivo= new File(nombrefic);
try
{	
	errorfi = crearfichero(nombrefic, archivo); 
	if(errorfi==1) System.out.println("ERROR. No se crea el documento por error al crear el fichero "+ nombrefic);
	else 
	{
		Collection col = creardocumento(archivo); 
		Connection conexion = conectarmysql();
		Statement sentencia;
		String raiz=nombrefic.substring(0,nombrefic.indexOf('.'));
		
		try {
			sentencia = (Statement) conexion.createStatement();
			ResultSet resul = sentencia.executeQuery ("SELECT * from detallecompras");  
			while (resul.next())
			{
				String nodo = "<detallecompra>"
						+ "<numcompra>"+resul.getInt(1)+"</numcompra>"
				+ "<codarticulo>"+resul.getInt(2)+"</codarticulo>"
				+ "<unidades>"+resul.getInt(3)+"</unidades>"
				+ "</detallecompra>";
				insertarnodo( col,  nodo,  raiz) ;
			}
			System.out.println("-----------------------------------------------------------");
			resul.close();
			sentencia.close();
		}	catch (SQLException e) 
		{  	visualizarerrorSQL(e);
		} 
	}
} catch (XMLDBException e) {
	System.out.println("Error al crear el documento en la colección.");
	e.printStackTrace();   
} 	
}//Creardetallecompras
////////////////////////////////////////////////////////////////////////
private static void Creararticulos(String nombrefic)  {
int errorfi=0;
File archivo= new File(nombrefic);
try{	
	errorfi = crearfichero(nombrefic, archivo); 
	if(errorfi==1) System.out.println("ERROR. No se crea el documento por error al crear el fichero "+ nombrefic);
	else 
		{
		Collection col = creardocumento(archivo); 
		Connection conexion = conectarmysql();
		Statement sentencia;
		String raiz=nombrefic.substring(0,nombrefic.indexOf('.'));
		try {
			sentencia = (Statement) conexion.createStatement();
			ResultSet resul = sentencia.executeQuery ("SELECT * from articulos");  
			while (resul.next())
			{
				String nodo = "<articulo>"
				+ "<codarticulo>"+resul.getInt(1)+"</codarticulo>"
				+ "<denominacion>"+resul.getString(2)+"</denominacion>"
				+ "<precio>"+resul.getFloat(3)+"</precio>"
				+ "<stock>"+resul.getInt(4)+"</stock>"
				+ "<zona>"+resul.getString(5)+"</zona>"
				+ "<codigoprov>"+resul.getInt(6)+"</codigoprov>"
				+ "</articulo>";
				insertarnodo( col,  nodo,  raiz) ;
			}
			System.out.println("-----------------------------------------------------------");
			resul.close();
			sentencia.close();
		}	catch (SQLException e) 
		{  	visualizarerrorSQL(e);
		} 
	}
} catch (XMLDBException e) {
	System.out.println("Error al crear el documento en la colección.");
	e.printStackTrace();   
	} 	
}//Creararticulos
////////////////////////////////////////////////////////////////////////
private static void Crearcompras(String nombrefic)  {
int errorfi=0;
File archivo= new File(nombrefic);
try{	
	errorfi = crearfichero(nombrefic, archivo); 
	if(errorfi==1) System.out.println("ERROR. No se crea el documento por error al crear el fichero "+ nombrefic);
	else 
	{
		Collection col = creardocumento(archivo); 
		Connection conexion = conectarmysql();
		Statement sentencia;
		String raiz=nombrefic.substring(0,nombrefic.indexOf('.'));
		try {
			sentencia = (Statement) conexion.createStatement();
			ResultSet resul = sentencia.executeQuery ("SELECT * from compras");  
			while (resul.next())
			{
				String nodo = "<compra>"
				+ "<numcompra>"+resul.getInt(1)+"</numcompra>"
				+ "<codigocli>"+resul.getInt(2)+"</codigocli>"
				+ "<fechacompra>"+resul.getDate(3)+"</fechacompra>"
				+ "</compra>";
			    insertarnodo( col,  nodo,  raiz) ;
			}
			System.out.println("-----------------------------------------------------------");
			resul.close();
			sentencia.close();
			}	catch (SQLException e) 
			{  	visualizarerrorSQL(e);
			} 
		}
	} catch (XMLDBException e) {
		System.out.println("Error al crear el documento en la colección.");
		e.printStackTrace();   
	} 	
}//Crearcompras
////////////////////////////////////////////////////////////////////////
private static void Crearproveedores(String nombrefic)  {
int errorfi=0;
File archivo= new File(nombrefic);
try{	
    errorfi = crearfichero(nombrefic, archivo); 
	if(errorfi==1) System.out.println("ERROR. No se crea el documento por error al crear el fichero "+ nombrefic);
	else 
	{
		Collection col = creardocumento(archivo); 
		Connection conexion = conectarmysql();
		Statement sentencia;
		String raiz=nombrefic.substring(0,nombrefic.indexOf('.'));
		try {
			sentencia = (Statement) conexion.createStatement();
		    ResultSet resul = sentencia.executeQuery ("SELECT * from proveedores");  
			while (resul.next())
			  {
				String nodo = "<proveedor>"
						+ "<codigoprov>"+resul.getInt(1)+"</codigoprov>"
						+ "<nombre>"+resul.getString(2)+"</nombre>"
						+ "<localidad>"+resul.getString(3)+"</localidad>"
						+ "<fechaalta>"+resul.getString(4)+"</fechaalta>"
						+ "<comision>"+resul.getString(5)+"</comision>"
						+ "</proveedor>";
				insertarnodo( col,  nodo,  raiz) ;	
			  }
			System.out.println("-----------------------------------------------------------");
			resul.close();
			sentencia.close();
		  }	catch (SQLException e) 
			{  visualizarerrorSQL(e);
			} 
	}
 } catch (XMLDBException e) {
	  System.out.println("Error al crear el documento en la colección.");
      e.printStackTrace();   
    } 	
}//Crearproveedores
////////////////////////////////////////////////////////////////////////
private static void visualizarerrorSQL(SQLException e) {
	System.out.println ("-------------------------------------");
	   System.out.println ("Código de error: " + e.getErrorCode());
	   System.out.println ("Mensaje de error: " + e.getMessage());
	   System.out.println ("-------------------------------------");
}//visualizarerror
////////////////////////////////////////////////////////////////////////
private static int crearfichero(String nombrefic, File archivo) {
	FileWriter fic;
	int errorfichero=0;
	try {
		fic = new FileWriter(archivo);    
	    BufferedWriter fichero = new BufferedWriter(fic);
	    //grabamos las primeras líneas   	      
	   	fichero.write("<?xml version='1.0' encoding='ISO-8859-1'?>");  
	   	fichero.newLine();
	   	String etiqraiz=nombrefic.substring(0,nombrefic.indexOf('.'));
	   	fichero.write("<"+etiqraiz+"></"+etiqraiz+">");  
	    System.out.println("FICHERO CREADO."); 
	    fichero.close(); 
	} catch (IOException e1) {
		System.out.println("ERROR AL CREAR EL FICHERO.");
		errorfichero=1;
		//e1.printStackTrace();
	}
	return errorfichero;
}//crearfichero
////////////////////////////////////////////////////////////////////////
private static void Crearclientes(String nombrefic)  {
int errorfi=0;
File archivo= new File(nombrefic);
try{		
    errorfi = crearfichero(nombrefic, archivo); 
	if(errorfi==1) System.out.println("ERROR. No se crea el documento por error al crear el fichero "+ nombrefic);
	else 
	{
			Collection col = creardocumento(archivo); 
			Connection conexion = conectarmysql();
			Statement sentencia;
			String raiz=nombrefic.substring(0,nombrefic.indexOf('.'));
			try {
					sentencia = (Statement) conexion.createStatement();
				    ResultSet resul = sentencia.executeQuery ("SELECT * from clientes");  
					while (resul.next())
					  {
						String nodo = "<cliente>"
								+ "<codigocli>"+resul.getInt(1)+"</codigocli>"
								+ "<nombre>"+resul.getString(2)+"</nombre>"
								+ "<localidad>"+resul.getString(3)+"</localidad>"
								+ "<telefono>"+resul.getString(4)+"</telefono>"
								+ "</cliente>";
						insertarnodo( col,  nodo,  raiz) ;
					  }
					System.out.println("-----------------------------------------------------------");
					resul.close();
					sentencia.close();
			  }	catch (SQLException e) 
				{  visualizarerrorSQL(e);
				} 
	}
 } catch (XMLDBException e) {
	  System.out.println("Error al crear el documento en la colección.");
      e.printStackTrace();   
    } 
}//Crearclientes
////////////////////////////////////////////////////////////////////////
private static Collection creardocumento(File archivo) throws XMLDBException {
	Collection col = conectaracoleccion(driver, nombrecoleccion, usu, usuPwd );
	//Borro el documento por si ya existe, 
	// y lo creo de nuevo, para que no se dupliquen las etiquetas
	try
	 {  Resource recursoParaBorrar = col.getResource(archivo.getName());
	    col.removeResource(recursoParaBorrar);	
	 }catch(NullPointerException e)
	   { 	//	No se puede borrar. No se encuentra
	   }
	Resource nuevoRecurso = col.createResource(archivo.getName(), "XMLResource");
	nuevoRecurso.setContent(archivo); 
	col.storeResource(nuevoRecurso);
	return col;
}//creardocumento
////////////////////////////////////////////////////////////////////////
public static void insertarnodo(Collection col, String nodo, String raiz) 
{
  try{
			XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			System.out.printf("Inserto nodo: %s\n" , nodo);
			ResourceSet result = servicio.query ("update insert " + nodo + " into /"+raiz);
			col.close(); 
			System.out.println("Nodo insertado.");
		} catch (Exception e) {
             System.out.println("Error al insertar el nodo.");
             e.printStackTrace();}

}// insertarnodo
////////////////////////////////////////////////////////////////////////
private static boolean crearcoleccion(String nombrecoleccion) {

	 try {
		  Class<?> cl = Class.forName(driver); //Cargar del driver 
		  Database database = (Database) cl.getDeclaredConstructor().newInstance(); //Instancia de la BD
		  DatabaseManager.registerDatabase(database); //Registro del driver
		  Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
		  CollectionManagementService mgtService;
		  mgtService = (CollectionManagementService)col.getService("CollectionManagementService","1.0");
		  mgtService.createCollection(nombrecoleccion); 
		  col.close();
	  	  return true;
		} catch (XMLDBException e) {
		  System.out.println("Error al inicializar la BD eXist. NO se ha podido crear la colección.");
	      e.printStackTrace();   
		} catch (ClassNotFoundException e) {
	     	System.out.println("Error en el driver.");
			e.printStackTrace();
		} catch (InstantiationException e) {
		    System.out.println("Error al instanciar la BD.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Error al instanciar la BD.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	return false;
}//crearcoleccion
	
////////////////////////////////////////////////////////////////////////
private static Connection conectarmysql() {
	try
	 {   Class.forName("com.mysql.jdbc.Driver");
		 Connection conexion = (Connection) DriverManager.getConnection    
	          ("jdbc:mysql://localhost/prueba","root", "");    
		return conexion;
	  } 
	   catch (ClassNotFoundException cn) {
		   System.out.println ("-------------------------------------");
		   System.out.println ("ERRORRRR EN EL DRIVER MYSQL");
		   System.out.println (cn);
		   System.out.println ("-------------------------------------");} 
	   catch (SQLException e) 
		   {  visualizarerrorSQL(e);
		   } 	
	return null;

}//conectarmysql
/////////////////////////////////////////////////////////////////////////
public static Collection conectaracoleccion(String driverc, String nomcolecc , String usuc, String usucPwd ) {
	
	try {
		  Class<?> cl = Class.forName(driverc); //Cargar del driver 
	      Database database = (Database) cl.getDeclaredConstructor().newInstance(); //Instancia de la BD
	      DatabaseManager.registerDatabase(database); //Registro del driver
	      String URIColec = URI + "/" + nomcolecc;
	      Collection col = DatabaseManager.getCollection(URIColec, usuc, usucPwd);
	      return col;
	    } catch (XMLDBException e) {
		  System.out.println("Error al inicializar la BD eXist.");
	      e.printStackTrace();   
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Error en el driver.");
			e.printStackTrace();
		} catch (InstantiationException e) {
		    System.out.println("Error al instanciar la BD.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Error al instanciar la BD.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	return null;
}//conectaracoleccion

}// FIN clase
