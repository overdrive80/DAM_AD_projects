package MisBeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

public class Pedido implements Serializable, PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private int numeropedido;
    private Producto producto;
    private Date fecha;
    private int cantidad;
    private boolean pedir;

    public Pedido() {
        super();
    }

    public Pedido(int numeropedido, Producto producto, Date fecha, int cantidad, boolean pedir) {
        super();
        this.numeropedido = numeropedido;
        this.producto = producto;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.pedir = pedir;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.printf("Stock anterior: %d%n", evt.getOldValue());
        System.out.printf("Stock actual: %d%n", evt.getNewValue());

        System.out.printf("REALIZAR PEDIDO EN PRODUCTO:%s%n",
                producto.getDescripcion());
    }

    public int getNumeropedido() {
        return numeropedido;
    }

    public void setNumeropedido(int numeropedido) {
        this.numeropedido = numeropedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isPedir() {
        return pedir;
    }

    public void setPedir(boolean pedir) {
        this.pedir = pedir;
    }

}
