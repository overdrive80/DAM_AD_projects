/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operaciones;
import java.sql.*;
import basedatos.*;

/* RECORDAR AÑADIR LIBRERIAS AL CLASSPATH */

/**
 *
 * @author Isra
 * 
 * Las operaciones que se realizan en esta clase suelen ser 
 * las que afectan a TODO EL CONTENIDO DE UNA TABLA.
 * 
 * Las que se crean en el componente suelen afectar A UNA FILA/OBJETO
 * de una TABLA
 */
public class Operaciones {
    
    private static Connection conexion;
    
    public static void main(String[] args) {
        /*Desde aqui llamamos a distintos metodos */
    	ejemplo();
    }
    
     /* En cada metodo llamamos a la base de datos del COMPONENTE */
    public static void ejemplo(){
        
        Oracle bbdd = Oracle.crearInstancia("PROYECTOS", "proyectos");
        conexion = bbdd.getConexion();
        
        if (conexion!=null) {System.out.println("Hay conexión");} else {System.out.println("No hay conexión");}
    }
    
    
}
