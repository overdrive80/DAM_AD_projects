/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Isra
 */
public class Pojo implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3865501279643231601L;
	private String campo;

    public Pojo(String campo) {
        this.campo = campo;
    }

    public Pojo() {
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
    
    
    
}
