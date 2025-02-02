/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package MisBeans;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Isra
 */
public class Producto implements Serializable {

    public static final String PROP_SAMPLE_PROPERTY = "stockactual";

    private static final long serialVersionUID = 1L;
    private String descripcion;
    private int idproducto;
    private int stockactual;
    private int stockminimo;
    private float pvp;

    private PropertyChangeSupport propertySupport;

    public Producto() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public Producto(int idproducto, String descripcion,
            int stockactual, int stockminimo, float pvp) {

        propertySupport = new PropertyChangeSupport(this);

        this.idproducto = idproducto;
        this.descripcion = descripcion;
        this.stockactual = stockactual;
        this.stockminimo = stockminimo;
        this.pvp = pvp;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    public int getStockactual() {
        return stockactual;
    }

    public void setStockactual(int valorNuevo) {
        int valorAnterior = this.stockactual;
        this.stockactual = valorNuevo;

        // Si estamos por debajo de las existencias mínimas 
        // hay que realizar pedido y cancelar la modificacion de stock
        if (this.stockactual < getStockminimo()) {

            propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, valorAnterior, valorNuevo);
            this.stockactual = valorAnterior;
        }
    }

    //métodos get y set
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getStockminimo() {
        return stockminimo;
    }

    public void setStockminimo(int stockminimo) {
        this.stockminimo = stockminimo;
    }

    public float getPvp() {
        return pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }
}
