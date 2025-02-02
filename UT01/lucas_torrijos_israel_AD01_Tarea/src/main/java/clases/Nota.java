package clases;

public class Nota {
	private int codalumno;
	private int codasig;
	private float nota;
	
	public Nota() {
		super();
	}

	public Nota(int codalumno, int codasig, float nota) {
		super();
		this.codalumno = codalumno;
		this.codasig = codasig;
		this.nota = nota;
	}

	public int getCodalumno() {
		return codalumno;
	}

	public void setCodalumno(int codalumno) {
		this.codalumno = codalumno;
	}

	public int getCodasig() {
		return codasig;
	}

	public void setCodasig(int codasig) {
		this.codasig = codasig;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}
	
	
}
