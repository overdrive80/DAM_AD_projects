/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesbean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable, PropertyChangeListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numeroventa;
    private int idproducto;
    private Date fechaventa;
    private int cantidad;
    private String observaciones;

    public Venta() {
    }

    public Venta(int numeroventa, int idproducto,
            Date fechaventa, int cantidad, String observaciones) {
        this.numeroventa = numeroventa;
        this.idproducto = idproducto;
        this.fechaventa = fechaventa;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
    }

    public int getNumeroventa() {
        return this.numeroventa;
    }

    public void setNumeroventa(int numeroventa) {
        this.numeroventa = numeroventa;
    }

    public int getIdproducto() {
        return this.idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public Date getFechaventa() {
        return this.fechaventa;
    }

    public void setFechaventa(Date fechaventa) {
        this.fechaventa = fechaventa;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //el stock actual del producto es < que el stock minimo
        //la venta queda pendiente hasta que haya stock
        this.observaciones = "Pendiente de pedido por falta de stock";
    }

}//Venta
