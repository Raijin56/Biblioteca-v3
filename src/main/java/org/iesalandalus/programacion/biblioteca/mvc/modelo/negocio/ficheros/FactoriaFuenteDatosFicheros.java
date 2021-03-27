package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class FactoriaFuenteDatosFicheros implements IFuenteDatos {

	@Override
	public IAlumnos crearAlumnos() {
		return new Alumnos();
	}

	@Override
	public ILibros crearLibros() {
		return new Libros();
	}

	@Override
	public IPrestamos crearPrestamos() {
		return new Prestamos();
	}

}
