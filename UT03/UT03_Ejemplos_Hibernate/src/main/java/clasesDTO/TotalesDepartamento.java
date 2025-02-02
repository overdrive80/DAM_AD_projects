package clasesDTO;

import java.math.BigInteger;

public class TotalesDepartamento {
	
	private BigInteger numero;
	private Long numEmpleados;
	private Double salarioPromedio;
	private String nombre;
	

	public TotalesDepartamento(BigInteger numero, Long numEmpleados, Double salarioPromedio, String nombre) {
		super();
		this.numero = numero;
		this.numEmpleados = numEmpleados;
		this.salarioPromedio = salarioPromedio;
		this.nombre = nombre;
	}

	public BigInteger getNumero() {
		return numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public Long getNumEmpleados() {
		return numEmpleados;
	}

	public void setNumEmpleados(Long numEmpleados) {
		this.numEmpleados = numEmpleados;
	}

	public Double getSalarioPromedio() {
		return salarioPromedio;
	}

	public void setSalarioPromedio(Double salarioPromedio) {
		this.salarioPromedio = salarioPromedio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
