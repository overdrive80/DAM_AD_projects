/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebalibreriajava;

import MisBeans.*;

/**
 *
 * @author Isra
 */
public class Actividad_61 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Producto p1 = new Producto(1, "Dabber Sur Femme 2011", 10, 3, 16);
        Producto p2 = new Producto(2, "Lapices de colores", 100, 20, 5);
        Producto p3 = new Producto(3, "Bicicleta Monty", 14, 3, 220);
        
        Pedido pedido1 = new Pedido();
        pedido1.setProducto(p1);
        
        Pedido pedido2 = new Pedido();
        pedido2.setProducto(p2);
        
        Pedido pedido3 = new Pedido();
        pedido3.setProducto(p3);
        
        p1.addPropertyChangeListener(pedido1);
        p2.addPropertyChangeListener(pedido2);
        p3.addPropertyChangeListener(pedido3);
        
        System.out.println("----------------------------------------------------------------");
        VerProducto(p1);
        p1.setStockactual(2);// cambiamos el stock
        //ComprobarPedido(pedido1, p1);
        
        System.out.println("----------------------------------------------------------------");
        VerProducto(p2);
        p2.setStockactual(220);// cambiamos el stock
        //ComprobarPedido(pedido2, p2);
        
        System.out.println("----------------------------------------------------------------");
        VerProducto(p3);
        p3.setStockactual(1);// cambiamos el stock
        //ComprobarPedido(pedido3, p3);
    }

    static void VerProducto(Producto p) {
        System.out.printf("Producto : %s, Stock Actual: %d, StockMinimo: %d %n", p.getDescripcion(),
                p.getStockactual(), p.getStockminimo());
    }

}
