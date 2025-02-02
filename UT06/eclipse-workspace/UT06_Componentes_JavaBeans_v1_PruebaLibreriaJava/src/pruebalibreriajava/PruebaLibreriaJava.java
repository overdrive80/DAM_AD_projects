/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebalibreriajava;

import MisBeans.Pedido;
import MisBeans.Producto;

/**
 *
 * @author Isra
 */
public class PruebaLibreriaJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Producto producto = new Producto(1, "Dabber Sur Femme 2011", 10, 3, 16);
        
        Pedido pedido = new Pedido();
        pedido.setProducto(producto);
        
        producto.addPropertyChangeListener(pedido); //
        //cambiamos el stock actual, le damos el valor 2 --anterior es 10
        producto.setStockactual(2);
    }

}
