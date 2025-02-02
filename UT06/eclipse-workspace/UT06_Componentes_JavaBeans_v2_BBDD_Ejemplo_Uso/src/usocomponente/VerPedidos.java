package usocomponente;


import clasesbean.BaseDatos;
import clasesbean.Pedido;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;


/**
 *
 * @author Administrador
 */
public class VerPedidos {

    public static void main(String[] args) {
        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        Objects<Pedido> objects = odb.getObjects(Pedido.class);
        System.out.printf("Numero de Pedidos: %d%n", objects.size());

        int i = 1;
        // visualizar los objetos		
        while (objects.hasNext()) {
            Pedido jug = objects.next();
            System.out.printf("%d => Pedido: %d, Producto: %s, Cantidad: %d, Fecha: %s %n",
                    (i++), jug.getNumeropedido(), jug.getProducto().getDescripcion(),
                    jug.getCantidad(), jug.getFecha());
        }
         bd.closeBD(); // Cerrar BD					
    }
}
