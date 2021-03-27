package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.util.Objects;

public abstract class Libro {

	private String titulo;
	private String autor;

	protected Libro(String titulo, String autor) {
		setTitulo(titulo);
		setAutor(autor);
	}

	protected Libro(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		titulo = libro.getTitulo();
		autor = libro.getAutor();
	}

	public static Libro getLibroFicticio(String titulo, String autor) {
		int numPaginas = 25;
		return new LibroEscrito(titulo, autor, numPaginas);
	}

	public abstract float getPuntos();

	public String getTitulo() {
		return titulo;
	}

	protected void setTitulo(String titulo) {
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	protected void setAutor(String autor) {
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (autor.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		this.autor = autor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(autor, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Libro)) {
			return false;
		}
		Libro other = (Libro) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return String.format("título=%s, autor=%s", titulo, autor);
	}

}
