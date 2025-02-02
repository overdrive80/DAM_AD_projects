package clasesbean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Values;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

/**
 *
 * @author mjesus
 */
public class BaseDatos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static ODB odb;

    public BaseDatos() {
        //Abrir base de datos, la crea si no existe.
        odb = ODBFactory.open("Producto_Ped.BD");
    }

    public static ODB getOdb() {
        //Devuelve el objeto ODB
        return BaseDatos.odb;
    }

    public static void closeBD() {
        //Cerrar base de datos
        odb.close();
    }

    //Obtiene el número de pedido para el próximo pedido
    public static int obtenerNumeroPedido() {
        // Busca el valor máximo de la propiedad 'numeropedido'
        // de la clase Pedido.class y se asigna a ped_max.
        Values val4 = odb.getValues(new ValuesCriteriaQuery(Pedido.class)
                .max("numeropedido", "ped_max"));

        //Se obtienen los valores resultantes de la consulta. 
        ObjectValues ov4 = val4.nextValues();
        BigDecimal maxima = (BigDecimal) ov4.getByAlias("ped_max");

        return maxima.intValue() + 1;
    }//

    //Obtiene el número de venta para la próxima venta
    public int obtenerNumeroVenta() {
        // Busca el valor máximo de la propiedad 'numeroventa'
        // de la clase Venta.class y se asigna a ven_max.
        Values val4
                = odb.getValues(new ValuesCriteriaQuery(Venta.class)
                        .max("numeroventa", "ven_max"));

        //Se obtienen los valores resultantes de la consulta. 
        ObjectValues ov4 = val4.nextValues();
        BigDecimal maxima = (BigDecimal) ov4.getByAlias("ven_max");

        return maxima.intValue() + 1;
    }//

    //Inserta un Producto
    public void insertaProducto(Producto producto) {
        odb.store(producto); //Almacenar producto 
        odb.commit();
    }

    //Inserta un Pedido
    public static void insertaPedido(Producto producto, int cantidad) {
        Pedido pedido = new Pedido(obtenerNumeroPedido(), producto,
                getCurrentDate(), cantidad);

        odb.store(pedido); //Almacenar pedido 
        System.out.printf("PEDIDO GENERADO para el Producto: %s%n",
                producto.getDescripcion());
        odb.commit();
    }

    //Inserta una Venta
    public void insertaVenta(Producto producto, int cantidad) {
        int numeroventa = obtenerNumeroVenta();
        Venta ven = new Venta(numeroventa, producto.getIdproducto(),
                getCurrentDate(), cantidad, "");

        producto.addPropertyChangeListener(ven);//añadir oyente

        actualizarStock(producto, cantidad);
        odb.store(ven); //Almacenar venta             
        System.out.printf("VENTA %d INSERTADA, Observaciones: %s %n",
                numeroventa, ven.getObservaciones());
        odb.commit();
    }

    //Obtener la fecha actual 
    private static java.sql.Date getCurrentDate() {
        java.util.Date hoy = new java.util.Date();
        return new java.sql.Date(hoy.getTime());
    }

    //Actualizar stock del producto
    private void actualizarStock(Producto producto, int cantidad) {
        Pedido pedido = new Pedido();
        pedido.setProducto(producto);
        pedido.setCantidad(cantidad);

        // Añadimos al oyente Pedido. Si se produce el evento (stockactual<stockminimo)
        // realiza el pedido al proveedor
        producto.addPropertyChangeListener(pedido);//añadir oyente

        // Cálculo de stock
        int nuevostock = producto.getStockactual() - cantidad;

        // Intentamos cambiar el stock actual. Si stockactual<stockminimo
        // se lanza el evento dentro de este componente y avisa a los oyentes.
        // No se actualiza el stock
        producto.setStockactual(nuevostock);

        odb.store(producto); //almacenar producto actualizado  
        odb.commit();
    }
}//...
