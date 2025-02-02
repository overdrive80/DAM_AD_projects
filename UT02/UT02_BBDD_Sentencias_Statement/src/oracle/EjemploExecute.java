package oracle;

import java.sql.*;

public class EjemploExecute {   
	
	public static void main(String[] args) throws 
	       ClassNotFoundException, SQLException {
	  
		// Cargar el driver
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// Establecemos la conexion con la BD
		Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "EJEMPLO", "EJEMPLO");

		//Comprobamos si hay conexion
		if (conexion==null) {
			System.out.println("No hay conexion");
			System.exit(-1);
		}  		   
	   
	   String sql="SELECT * FROM departamentos";
	   Statement sentencia = conexion.createStatement();		   
	   boolean valor = sentencia.execute(sql);  
	   		   
	   if(valor){
	    	ResultSet rs = sentencia.getResultSet();
	   	 while (rs.next())
	   	    System.out.printf("%d, %s, %s %n",
	   	    		rs.getInt(1), rs.getString(2), rs.getString(3));
	    	rs.close();
	   } else {
	    	int f = sentencia.getUpdateCount();
	    	System.out.printf("Filas afectadas:%d %n", f);
	   }
	   
		sentencia.close();
		conexion.close();
	}//main
}//
