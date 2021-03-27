package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

public class VistaTexto implements IVista {

	private IControlador controlador;

	public VistaTexto() {
		Opcion.setVista(this);
	}

	@Override
	public void setControlador(IControlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		// Consola.mostrarCabecera("Gestión de los préstamos de la Biblioteca del IES
		// Al-Ándalus");
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOridnal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

	@Override
	public void terminar() {
		controlador.terminar();
	}

	public void insertarAlumno() {
		Consola.mostrarCabecera("Insertar Alumno");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlumno() {
		Consola.mostrarCabecera("Buscar Alumno");
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe dicho alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlumno() {
		Consola.mostrarCabecera("Borrar Alumno");
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlumnos() {
		Consola.mostrarCabecera("Listado de Alumnos");
		List<Alumno> alumnos = controlador.getAlumnos();
		if (!alumnos.isEmpty()) {
			for (Alumno alumno : alumnos) {
				if (alumno != null)
					System.out.println(alumno);
			}
		} else {
			System.out.println("No hay alumnos que mostrar.");
		}
	}

	public void insertarLibro() {
		Consola.mostrarCabecera("Insertar Libro");
		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("Libro insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarLibro() {
		Consola.mostrarCabecera("Buscar Libro");
		Libro libro;
		try {
			libro = controlador.buscar(Consola.leerLibroFicticio());
			String mensaje = (libro != null) ? libro.toString() : "No existe dicho libro.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarLibro() {
		Consola.mostrarCabecera("Borrar Libro");
		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("Libro borrado satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarLibros() {
		Consola.mostrarCabecera("Listado de Libros");
		List<Libro> libros = controlador.getLibros();
		if (!libros.isEmpty()) {
			for (Libro libro : libros) {
				if (libro != null)
					System.out.println(libro);
			}
		} else {
			System.out.println("No hay libros que mostrar.");
		}
	}

	public void prestarLibro() {
		Consola.mostrarCabecera("Prestar Libro");
		try {
			Prestamo prestamo = Consola.leerPrestamo();
			controlador.prestar(prestamo);
			System.out.println("Préstamo de libro hecho correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverLibro() {
		Consola.mostrarCabecera("Devolver Libro");
		try {
			Prestamo prestamo = Consola.leerPrestamoFicticio();
			String mensajeFechaDevolucion = "Introduce la fecha de devolución del préstamo";
			LocalDate fecha = Consola.leerFecha(mensajeFechaDevolucion);
			controlador.devolver(prestamo, fecha);
			System.out.println("Devolución hecha correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarPrestamo() {
		Consola.mostrarCabecera("Buscar Préstamo");
		Prestamo prestamo;
		try {
			prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo != null) ? prestamo.toString() : "No existe dicho préstamo.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarPrestamo() {
		Consola.mostrarCabecera("Borrar Préstamo");
		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Préstamo borrado satisfactoriamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamos() {
		Consola.mostrarCabecera("Listado de Préstamos");
		List<Prestamo> prestamos = controlador.getPrestamos();
		if (!prestamos.isEmpty()) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null)
					System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay préstamos que mostrar.");
		}
	}

	public void listarPrestamosAlumno() {
		Consola.mostrarCabecera("Listado de Préstamos por Alumno");
		try {
			Alumno alumno = Consola.leerAlumnoFicticio();
			List<Prestamo> prestamos = controlador.getPrestamos(alumno);
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null)
						System.out.println(prestamo);
				}
			} else {
				System.out.println("No hay préstamos que mostrar para dicho alumno.");
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamosLibro() {
		Consola.mostrarCabecera("Listado de Préstamos por Libro");
		try {
			Libro libro = Consola.leerLibroFicticio();
			List<Prestamo> prestamos = controlador.getPrestamos(libro);
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null)
						System.out.println(prestamo);
				}
			} else {
				System.out.println("No hay préstamos que mostrar para dicho libro.");
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("Listado de Préstamos por Fecha");
		try {
			LocalDate fechaListarPrestamos;
			String mensajeFechaListarPrestamos = "Introduce la fecha para la que quieres listar los préstamos";
			fechaListarPrestamos = Consola.leerFecha(mensajeFechaListarPrestamos);
			List<Prestamo> prestamos = controlador.getPrestamos(fechaListarPrestamos);
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null)
						System.out.println(prestamo);
				}
			} else {
				System.out.println("No hay préstamos que mostrar para dicha fecha.");
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarEstadisticaMensualPorCurso() {
		Consola.mostrarCabecera("Estadísticas mensuales por curso");
		try {
			LocalDate fechaEstadisticaMensual;
			String mensajeFechaEstadisticaMensual = "Introduce la fecha para la que quieres listar las estadísticas mensuales por curso";
			fechaEstadisticaMensual = Consola.leerFecha(mensajeFechaEstadisticaMensual);
			Map<Curso, Integer> estadisticaMensualPorCurso = controlador
					.getEstadisticaMensualPorCurso(fechaEstadisticaMensual);
			if (!estadisticaMensualPorCurso.isEmpty()) {
				System.out.println(estadisticaMensualPorCurso);
			} else {
				System.out.println("No hay estadísticas a mostrar para dicha fecha.");
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
