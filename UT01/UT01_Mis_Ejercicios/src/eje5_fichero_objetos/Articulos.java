package eje5_fichero_objetos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Articulos implements Serializable{
	public int codigo;
	public String deno;
	public float pvp;
	public int uni;
	public String zon;
	
	

	public Articulos(){}
	public Articulos(int codigo, String deno, float pvp, int uni, String zon) {
		super();
		this.codigo = codigo;
		this.deno = deno;
		this.pvp = pvp;
		this.uni = uni;
		this.zon = zon;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDeno() {
		return deno;
	}
	public void setDeno(String deno) {
		this.deno = deno;
	}
	public float getPvp() {
		return pvp;
	}
	public void setPvp(float pvp) {
		this.pvp = pvp;
	}
	public int getUni() {
		return uni;
	}
	public void setUni(int uni) {
		this.uni = uni;
	}
	public String getZon() {
		return zon;
	}
	public void setZon(String zon) {
		this.zon = zon;
	}
}//fin Articulos