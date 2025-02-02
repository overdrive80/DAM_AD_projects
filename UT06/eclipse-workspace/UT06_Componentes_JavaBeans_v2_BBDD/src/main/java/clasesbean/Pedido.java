/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesbean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author mjesus
 */
public class Pedido implements Serializable, PropertyChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numeropedido;
    private Producto producto;
    private Date fecha;
    private int cantidad;    

    public Pedido() {

    }

    public Pedido(int numeropedido, Producto producto,
            Date fecha, int cantidad) {
        this.numeropedido = numeropedido;
        this.producto = producto;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public int getNumeropedido() {
        return numeropedido;
    }

    public void setNumeropedido(int numeropedido) {
        this.numeropedido = numeropedido;
    }

    public Producto getProducto() {
        return this.producto;
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.printf("Stock anterior: %d%n", evt.getOldValue());
        System.out.printf("Nuevo Stock: %d (menor que el minimo) %n", evt.getNewValue());
       
        //insertar pedido       
        BaseDatos.insertaPedido(producto, cantidad);        
    }

}
