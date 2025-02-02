package logica;

import java.util.List;
import java.util.ArrayList;

import dto.Entidad;
import dto.Estudiante;
import dto.Proyecto;

public class Logica {

	private static List<Estudiante> estudiantes = new ArrayList<Estudiante>();
	private static List<Proyecto> proyectos = new ArrayList<Proyecto>();
	private static List<Entidad> entidades = new ArrayList<Entidad>();

	public static List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public static void setEstudiantes(List<Estudiante> nuevosEstudiantes) {
		estudiantes = nuevosEstudiantes;
	}

	public static List<Proyecto> getProyectos() {
		return proyectos;
	}

	public static void setProyectos(List<Proyecto> proyectos) {
		Logica.proyectos = proyectos;
	}

	public static List<Entidad> getEntidades() {
		return entidades;
	}

	public static void setEntidades(List<Entidad> entidades) {
		Logica.entidades = entidades;
	}

}
