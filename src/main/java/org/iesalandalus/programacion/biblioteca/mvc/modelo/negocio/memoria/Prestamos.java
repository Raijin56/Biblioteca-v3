package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class Prestamos implements IPrestamos {

	private List<Prestamo> coleccionPrestamos;

	public Prestamos() {
		coleccionPrestamos = new ArrayList<>();
	}

	@Override
	public List<Prestamo> get() {
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		List<Prestamo> copiaPrestamos = copiaProfundaPrestamos();
		copiaPrestamos.sort(comparadorPrestamo);
		return copiaPrestamos;
	}

	private List<Prestamo> copiaProfundaPrestamos() {
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			copiaPrestamos.add(new Prestamo(prestamo));
		}
		return copiaPrestamos;
	}

	@Override
	public int getTamano() {
		return coleccionPrestamos.size();
	}

	@Override
	public List<Prestamo> get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		List<Prestamo> prestamosAlumno = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getAlumno().equals(alumno)) {
				prestamosAlumno.add(new Prestamo(prestamo));
			}
		}
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		prestamosAlumno.sort(comparadorPrestamo);
		return prestamosAlumno;
	}

	@Override
	public List<Prestamo> get(Libro libro) {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		List<Prestamo> prestamosLibro = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getLibro().equals(libro)) {
				prestamosLibro.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno);
		prestamosLibro.sort(comparadorPrestamo);
		return prestamosLibro;
	}

	@Override
	public List<Prestamo> get(LocalDate fecha) {
		if (fecha == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		List<Prestamo> prestamosMensuales = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (mismoMes(prestamo.getFechaPrestamo(), fecha)) {
				prestamosMensuales.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		prestamosMensuales.sort(comparadorPrestamo);
		return prestamosMensuales;
	}

	@Override
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		Map<Curso, Integer> estadisticasMensualesPorCurso = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno = prestamo.getAlumno().getCurso();
			estadisticasMensualesPorCurso.put(cursoAlumno,
					estadisticasMensualesPorCurso.get(cursoAlumno) + prestamo.getPuntos());
		}
		return estadisticasMensualesPorCurso;
	}

	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer> estadisticasMensualesPorCurso = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			estadisticasMensualesPorCurso.put(curso, 0);
		}
		return estadisticasMensualesPorCurso;
	}

	private boolean mismoMes(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.getMonthValue() == fecha2.getMonthValue() && fecha1.getYear() == fecha2.getYear();
	}

	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		if (!coleccionPrestamos.contains(prestamo)) {
			coleccionPrestamos.add(new Prestamo(prestamo));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");
		}
	}

	@Override
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.get(indice).devolver(fechaDevolucion);
		}
	}

	@Override
	public Prestamo buscar(Prestamo prestamo) {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			return null;
		} else {
			return new Prestamo(coleccionPrestamos.get(indice));
		}
	}

	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");
		}
		if (!coleccionPrestamos.contains(prestamo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.remove(prestamo);
		}
	}

}
