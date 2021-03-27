package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria.FactoriaFuenteDatosMemoria;

public enum FactoriaFuenteDatos {

	MEMORIA {

		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosMemoria();
		}

	};

	public abstract IFuenteDatos crear();

}
