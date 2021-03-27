package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;

public class Libros implements ILibros {

	private static final String NOMBRE_FICHERO_LIBROS = "datos/libros.dat";

	private List<Libro> coleccionLibros;

	public Libros() {
		coleccionLibros = new ArrayList<>();
	}

	@Override
	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroLibros = new File(NOMBRE_FICHERO_LIBROS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroLibros))) {
			Libro libro = null;
			do {
				libro = (Libro) entrada.readObject();
				insertar(libro);
			} while (libro != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fichero de libros.");
		} catch (EOFException e) {
			System.out.println("Fichero libros leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void terminar() {
		escribir();
	}

	private void escribir() {
		File ficheroLibros = new File(NOMBRE_FICHERO_LIBROS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroLibros))) {
			for (Libro libro : coleccionLibros)
				salida.writeObject(libro);
			System.out.println("Fichero libros escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de libros.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}

	@Override
	public List<Libro> get() {
		List<Libro> copiaLibros = copiaProfundaLibros();
		copiaLibros.sort(Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor));
		return copiaLibros;
	}

	private List<Libro> copiaProfundaLibros() {
		List<Libro> copiaLibros = new ArrayList<>();
		for (Libro libro : coleccionLibros) {
			if (libro instanceof LibroEscrito) {
				copiaLibros.add(new LibroEscrito((LibroEscrito) libro));
			} else if (libro instanceof AudioLibro) {
				copiaLibros.add(new AudioLibro((AudioLibro) libro));
			}
		}
		return copiaLibros;
	}

	@Override
	public int getTamano() {
		return coleccionLibros.size();
	}

	@Override
	public void insertar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");
		}
		if (!coleccionLibros.contains(libro)) {
			if (libro instanceof LibroEscrito) {
				coleccionLibros.add(new LibroEscrito((LibroEscrito) libro));
			} else if (libro instanceof AudioLibro) {
				coleccionLibros.add(new AudioLibro((AudioLibro) libro));
			}
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese título y autor.");
		}

	}

	@Override
	public Libro buscar(Libro libro) {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		Libro libroDevuelto = null;
		if (indice == -1) {
			libroDevuelto = null;
		} else {
			if (libro instanceof LibroEscrito) {
				libroDevuelto = new LibroEscrito((LibroEscrito) coleccionLibros.get(indice));
			} else if (libro instanceof AudioLibro) {
				libroDevuelto = new AudioLibro((AudioLibro) coleccionLibros.get(indice));
			}
		}
		return libroDevuelto;
	}

	@Override
	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");
		}
		if (!coleccionLibros.contains(libro)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún libro con ese título y autor.");
		} else {
			coleccionLibros.remove(libro);
		}
	}

}
