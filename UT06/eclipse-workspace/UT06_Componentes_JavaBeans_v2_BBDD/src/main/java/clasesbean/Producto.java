/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesbean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author mjesus
 */
public class Producto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String descripcion;
    private int idproducto;
    private int stockactual;
    private int stockminimo;
    private float pvp;

    private final PropertyChangeSupport propertySupport;

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

    public int getStockactual() {
        return stockactual;
    }

    public void setStockactual(int valorNuevo) {
        int valorAnterior = this.stockactual;
        this.stockactual = valorNuevo;

        if (valorAnterior < getStockminimo()) //hay que realizar pedido
        {
            propertySupport.firePropertyChange("stockactual",
                    valorAnterior, valorNuevo);
            //dejamos el stock anterior
            this.stockactual = valorAnterior;
        }

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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
