package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public class AudioLibro extends Libro {

	private static final long serialVersionUID = 1L;

	private static final int MINUTOS_PARA_RECOMPENSA = 15;
	private static final float PUNTOS_PREMIO = 0.25f;

	private int duracion;

	public AudioLibro(String titulo, String autor, int duracion) {
		super(titulo, autor);
		setDuracion(duracion);
	}

	public AudioLibro(AudioLibro audioLibro) {
		super(audioLibro);
		duracion = audioLibro.getDuracion();
	}

	public int getDuracion() {
		return duracion;
	}

	private void setDuracion(int duracion) {
		if (duracion <= 0) {
			throw new IllegalArgumentException("ERROR: La duración debe ser mayor que cero.");
		}
		this.duracion = duracion;
	}

	@Override
	public float getPuntos() {
		return ((duracion / MINUTOS_PARA_RECOMPENSA) + 1) * PUNTOS_PREMIO;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format("%s, duración=%s", super.toString(), duracion);
	}

}
