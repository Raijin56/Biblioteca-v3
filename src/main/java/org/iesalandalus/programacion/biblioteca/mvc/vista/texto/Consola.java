package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {
	}

	public static void mostrarMenu() {
		mostrarCabecera("Gestión de los préstamos de la Biblioteca del IES Al-Ándalus");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String formatoStr = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(formatoStr, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}

	public static Alumno leerAlumno() {
		System.out.print("Introduce el nombre del alumno: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo del alumno: ");
		String correo = Entrada.cadena();
		int cursoLeido = 0;
		while (cursoLeido < 1 || cursoLeido > 4) {
			System.out.print("Introduce el curso del alumno (1 - PRIMERO, 2 - SEGUNDO, 3 - TERCERO, 4 - CUARTO): ");
			cursoLeido = Entrada.entero();
			if (cursoLeido < 1 || cursoLeido > 4) {
				System.out.println("ERROR: El curso introducido no es correcto.");
			}
		}
		Curso curso = Curso.values()[cursoLeido - 1];
		return new Alumno(nombre, correo, curso);
	}

	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduce el correo del alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}

	public static Libro leerLibro() {
		System.out.print("Introduce el título del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		int tipoLibroLeido = 0;
		while (tipoLibroLeido < 1 || tipoLibroLeido > 2) {
			System.out.print("Introduce el tipo de libro (1 - Libro escrito, 2 - Audiolibro): ");
			tipoLibroLeido = Entrada.entero();
			if (tipoLibroLeido < 1 || tipoLibroLeido > 2) {
				System.out.println("ERROR: El tipo de libro introducido no es correcto.");
			}
		}
		Libro libro = null;
		if (tipoLibroLeido == 1) {
			System.out.print("Introduce el número de páginas del libro: ");
			int numPaginas = Entrada.entero();
			libro = new LibroEscrito(titulo, autor, numPaginas);
		} else if (tipoLibroLeido == 2) {
			System.out.print("Introduce la duración del audiolibro en minutos: ");
			int duracion = Entrada.entero();
			libro = new AudioLibro(titulo, autor, duracion);
		}
		return libro;

	}

	public static Libro leerLibroFicticio() {
		System.out.print("Introduce el título del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		return Libro.getLibroFicticio(titulo, autor);
	}

	public static Prestamo leerPrestamo() {
		Alumno alumno = leerAlumnoFicticio();
		Libro libro = leerLibroFicticio();
		String mensajeFechaPrestamo = "Introduce la fecha del préstamo";
		LocalDate fechaPrestamo = leerFecha(mensajeFechaPrestamo);
		return new Prestamo(alumno, libro, fechaPrestamo);
	}

	public static Prestamo leerPrestamoFicticio() {
		Alumno alumno = leerAlumnoFicticio();
		Libro libro = leerLibroFicticio();
		return Prestamo.getPrestamoFicticio(alumno, libro);
	}

	public static LocalDate leerFecha(String mensaje) {
		System.out.printf("%s (En formato dd/MM/yyyy): ", mensaje);
		String fechaStr = Entrada.cadena();
		LocalDate fecha = null;
		try {
			fecha = LocalDate.parse(fechaStr, Prestamo.FORMATO_FECHA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("ERROR: La fecha introducida no tiene el formato adecuado.");
		}
		return fecha;
	}

}
