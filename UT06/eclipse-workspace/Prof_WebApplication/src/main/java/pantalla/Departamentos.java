/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantalla;

/**
 *
 * @author mjesus
 */
public class Departamentos {
    private int deptno;
	private String dnombre;
	private String loc;
	
	String modificar;
	String eliminar;
        String insertar;
    
	public Departamentos() {}
	public Departamentos(int deptno, final String dnombre, final String loc) {
		this.deptno = deptno;
		this.dnombre = dnombre;
		this.loc = loc;
	}
	public int getDeptno() {return this.deptno;}
	public void setDeptno(int deptno) {this.deptno = deptno;}
	public String getDnombre() {return this.dnombre;}
	public void setDnombre(String dnombre) {this.dnombre = dnombre;}
	public String getLoc() {return this.loc;}
	public void setLoc(String loc) {this.loc = loc;	}
	public String getModificar() {
		return modificar;
	}
	public void setModificar(String modificar) {
		this.modificar = modificar;
	}
	public String getEliminar() {
		return eliminar;
	}
	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
                
	}

    public String getInsertar() {
        return insertar;
    }

    public void setInsertar(String insertar) {
        this.insertar = insertar;
    }
        
	
}
