package usocomponente;

import clasesbean.BaseDatos;
import clasesbean.Producto;


public class LLenarProductos {

    public static void main(String[] args) {

        
        BaseDatos bd = new BaseDatos();

        Producto p1 = new Producto(1, "Duruss Cobalt", 10, 3, 220);
        Producto p2 = new Producto(2, "Varlion Avant Carbon", 5, 2, 176);
        Producto p3 = new Producto(3, "Star Vie Pyramid R50", 20, 5, 193);
        Producto p4 = new Producto(4, "Dunlop Titan", 8, 3, 85);
        Producto p5 = new Producto(5, "Vision King jm", 7, 1, 159);
        Producto p6 = new Producto(6, "Slazenger Reflex Pro", 5, 2, 80);

        //Almacenamos productos
        bd.insertaProducto(p1);
        bd.insertaProducto(p2);
        bd.insertaProducto(p3);
        bd.insertaProducto(p4);
        bd.insertaProducto(p5);
        bd.insertaProducto(p6);

        System.out.println("Ha finalizado la inserción de productos.");
        
        bd.closeBD(); // Cerrar BD

    }
}
