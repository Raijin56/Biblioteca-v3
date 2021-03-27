package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;

public interface ILibros {

	List<Libro> get();

	int getTamano();

	void insertar(Libro libro) throws OperationNotSupportedException;

	Libro buscar(Libro libro);

	void borrar(Libro libro) throws OperationNotSupportedException;

}
