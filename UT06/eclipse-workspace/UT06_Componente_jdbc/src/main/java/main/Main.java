/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import basedatos.Oracle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Isra
 */
public class Main {
    public static void main(String[] args) {
        Oracle bbdd = Oracle.crearInstancia("PROYECTOS", "proyectos");
        Connection conexion = bbdd.getConexion();
        
        if (conexion!=null) {System.out.println("Hay conexión");} else {System.out.println("No hay conexión");}
        
        String sSQL = "SELECT * FROM PROYECTOS";
        
        try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sSQL);
			
			while (rs.next()) {
				System.out.println(rs.getString(1));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
        
    }
}
