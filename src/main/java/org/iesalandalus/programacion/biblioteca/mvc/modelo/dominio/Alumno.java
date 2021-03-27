package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.util.Objects;

public class Alumno {

	private static final String ER_NOMBRE = "[a-zA-ZÑñÁÉÍÓÚÜáéíóúü]+(\\s+[a-zA-ZÑñÁÉÍÓÚÜáéíóúü]+)+";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";

	private String nombre;
	private String correo;
	private Curso curso;

	public Alumno(String nombre, String correo, Curso curso) {
		setNombre(nombre);
		setCorreo(correo);
		setCurso(curso);
	}

	public Alumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		nombre = alumno.getNombre();
		correo = alumno.getCorreo();
		curso = alumno.getCurso();
	}

	public static Alumno getAlumnoFicticio(String correo) {
		return new Alumno("Calamardo Pizarro", correo, Curso.PRIMERO);
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

	private String formateaNombre(String nombre) {
		nombre = nombre.trim().replaceAll(" +", " ").toLowerCase();
		StringBuilder nombreSB = new StringBuilder();
		char caracter = ' ';
		for (int i = 0; i < nombre.length(); i++) {
			if (caracter == ' ' && nombre.charAt(i) != ' ') {
				nombreSB.append(Character.toUpperCase(nombre.charAt(i)));
			} else {
				nombreSB.append(nombre.charAt(i));
			}
			caracter = nombre.charAt(i);
		}
		return nombreSB.toString();
	}

	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	private String getIniciales() {
		StringBuilder inicialesSB = new StringBuilder();
		inicialesSB.append(Character.toUpperCase(nombre.charAt(0)));
		for (int i = 1; i < nombre.length() - 1; i++) {
			if (nombre.charAt(i) == ' ') {
				inicialesSB.append(Character.toUpperCase(nombre.charAt(i + 1)));
			}
		}
		return inicialesSB.toString();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		if (curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Alumno)) {
			return false;
		}
		Alumno other = (Alumno) obj;
		return Objects.equals(correo, other.correo);
	}

	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, curso=%s", nombre, getIniciales(), correo, curso);
	}

}
