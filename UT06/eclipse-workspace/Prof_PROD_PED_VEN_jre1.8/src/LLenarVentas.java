import MisBeans.BaseDatos;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import MisBeans.Producto;

public class LLenarVentas {
    public static void main(String[] args) {
        // Datos de entrada para la venta
        int idproducto = 2; // idproducto
        int cantidad = 4;   // cantidad

        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        IQuery query = new CriteriaQuery(Producto.class,
                Where.equal("idproducto", idproducto));
        Objects<Producto> objetos = odb.getObjects(query);

        try {
            // Obtiene solo el primer objeto encontrado
            Producto pro = (Producto) objetos.getFirst();
            
            System.out.printf("ID=> %d: %s, STOCK-ACT: %d, STOCK-MIN: %d, PVP:%.2f %n",
                    idproducto, pro.getDescripcion(), pro.getStockactual(),
                    pro.getStockminimo(), pro.getPvp());
             System.out.printf("Cantidad: %d%n",cantidad); 
             
            //se inserta la venta
            bd.insertaVenta(pro, cantidad);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("NO EXISTE EL PRODUCTO");
        } finally {
            bd.closeBD(); // Cerrar BD	 
        }
    }//

}//fin de la clase

