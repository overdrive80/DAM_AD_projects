import MisBeans.BaseDatos;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;

import MisBeans.Producto;

public class VerProductos {

    public static void main(String[] args) {
        BaseDatos bd = new BaseDatos();

        ODB odb = bd.getOdb();
        //recuperamos todos los objetos
        Objects<Producto> objects = odb.getObjects(Producto.class);
        System.out.printf("Número de Productos: %d%n", objects.size());

        int i = 1;
        // visualizar los productos		
        while (objects.hasNext()) {
            Producto pro = objects.next();
            System.out.printf("%d: %s, STOCK ACTUAL: %d, MINIMO: %d, Pvp: %.2f%n",
                    i++, pro.getDescripcion(), pro.getStockactual(),
                    pro.getStockminimo(), pro.getPvp());
        }
        bd.closeBD(); // Cerrar BD					
    }
}
