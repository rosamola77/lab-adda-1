package ejercicio3;

import java.util.Objects;

/**
 * Colaboracion
 *
 * <p>Representa una colaboración entre investigadores en el contexto del Ejercicio 3.
 * Cada colaboración tiene un identificador único y un contador del número de
 * colaboraciones realizadas.</p>
 *
 * <p>Esta clase se utiliza como arista en grafos de colaboraciones entre
 * investigadores, donde el número de colaboraciones puede servir como peso
 * o métrica de la relación.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Colaboracion col = Colaboracion.of();
 * Double numColab = col.getNColaboraciones();
 * }</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 */
public class Colaboracion {
	
	/**
	 * Crea una nueva colaboración con número de colaboraciones inicializado a 0.
	 *
	 * @return una nueva instancia de {@code Colaboracion}
	 */
	public static Colaboracion of() {
		return new Colaboracion();
	}

	/**
	 * Crea una colaboración a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [_, _, nColaboraciones]} donde
	 * los dos primeros elementos se ignoran.</p>
	 *
	 * @param formato array de {@code String} con los datos de la colaboración
	 * @return una nueva instancia de {@code Colaboracion}
	 * @throws NumberFormatException si {@code formato[2]} no es un número válido
	 * @throws ArrayIndexOutOfBoundsException si el array no tiene al menos 3 elementos
	 */
	public static  Colaboracion ofFormat(String[] formato) {
		return new Colaboracion(formato);
	}

	/** Contador estático para asignar identificadores únicos. */
	private static int num;
	
	/** Identificador único de la colaboración. */
	private int id;
	
	/** Número de colaboraciones realizadas. */
	private int nColaboraciones;

	/**
	 * Constructor por defecto que crea una colaboración con 0 colaboraciones.
	 */
	public Colaboracion() {
		this.nColaboraciones= 0;
		this.id = num;
		num++;
	} 
	
	/**
	 * Constructor privado que crea una colaboración a partir de un array de cadenas.
	 *
	 * @param nombre array con formato {@code [_, _, nColaboraciones]}
	 */
	private Colaboracion(String[] nombre) {
		this.nColaboraciones =  Integer.parseInt(nombre[2]);
		this.id = num;
		num++;
	}

	/**
	 * Obtiene el número de colaboraciones como valor Double.
	 *
	 * @return el número de colaboraciones
	 */
	public Double getNColaboraciones() {
		return (double)nColaboraciones;
	}
	
	/**
	 * Calcula el código hash de la colaboración.
	 *
	 * @return el código hash basado en id y número de colaboraciones
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, nColaboraciones);
	}

	/**
	 * Compara esta colaboración con otro objeto para determinar igualdad.
	 *
	 * <p>Dos colaboraciones son iguales si tienen el mismo id y
	 * número de colaboraciones.</p>
	 *
	 * @param obj el objeto con el que comparar
	 * @return {@code true} si son iguales; {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaboracion other = (Colaboracion) obj;
		return id == other.id && nColaboraciones == other.nColaboraciones;
	}

	/**
	 * Devuelve una representación en cadena de la colaboración.
	 *
	 * @return cadena con formato {@code [Col-id, nColaboraciones]}
	 */
	@Override
	public String toString() {
		return "[Col-" + id + ", " + nColaboraciones + "]";
	}

}
