
import MisBeans.BaseDatos;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;

import MisBeans.Venta;

/**
 *
 * @author Administrador
 */
public class VerVentas {

    public static void main(String[] args) {
        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        
        Objects<Venta> objects = odb.getObjects(Venta.class);

        System.out.printf("Número de Ventas: %d%n", objects.size());

        int i = 1;
        // visualizar los objetos		
        while (objects.hasNext()) {
            Venta jug = objects.next();

            System.out.printf("%d => Venta: %d, Producto: %d, Cantidad: %d, Fecha: %s, Observaciones: %s %n",
                    (i++), jug.getNumeroventa(), jug.getIdproducto(),
                    jug.getCantidad(), jug.getFechaventa(), jug.getObservaciones());

        }
         bd.closeBD(); // Cerrar BD					
    }
}
