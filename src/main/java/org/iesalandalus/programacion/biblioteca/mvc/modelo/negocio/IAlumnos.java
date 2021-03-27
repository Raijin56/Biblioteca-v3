package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;

public interface IAlumnos {

	void comenzar();

	void terminar();

	List<Alumno> get();

	int getTamano();

	void insertar(Alumno alumno) throws OperationNotSupportedException;

	Alumno buscar(Alumno alumno);

	void borrar(Alumno alumno) throws OperationNotSupportedException;

}
