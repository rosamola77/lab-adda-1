package ejercicio3;

import java.util.Objects;

/**
 * Investigador
 *
 * <p>Representa un investigador en el contexto del Ejercicio 3.
 * Cada investigador tiene un identificador único, año de nacimiento
 * y universidad de afiliación.</p>
 *
 * <p>Esta clase se utiliza como vértice en grafos de colaboraciones
 * entre investigadores.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Investigador inv = Investigador.ofId(1);
 * String uni = inv.getUniversidad();
 * }</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 */
public class Investigador {

	/**
	 * Crea un investigador con identificador por defecto (-1).
	 *
	 * @return una nueva instancia de {@code Investigador}
	 */
	public static Investigador of() {
		return new Investigador(-1);
	}

	/**
	 * Crea un investigador a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [id, anyoNacimiento, universidad]}</p>
	 *
	 * @param formato array de {@code String} con los datos del investigador
	 * @return una nueva instancia de {@code Investigador}
	 * @throws NumberFormatException si los elementos numéricos no son válidos
	 * @throws ArrayIndexOutOfBoundsException si el array no tiene al menos 3 elementos
	 */
	public static Investigador ofFormat(String[] formato) {
		return new Investigador(formato);
	}

	/**
	 * Crea un investigador con el identificador especificado.
	 *
	 * @param id identificador del investigador
	 * @return una nueva instancia de {@code Investigador}
	 */
	public static Investigador ofId(Integer id) {
		return new Investigador(id);
	}
	
	/** Identificador único del investigador. */
	private Integer id = null;
	
	/** Año de nacimiento del investigador. */
	private Integer anyoNacimiento = null;
	
	/** Universidad de afiliación del investigador. */
	private String universidad = null;

	/**
	 * Constructor privado que crea un investigador con el identificador dado.
	 *
	 * @param id identificador del investigador
	 */
	private Investigador(Integer id) {
		super();
		this.id = id;
		this.anyoNacimiento = null;
		this.universidad = null;
	}

	/**
	 * Constructor privado que crea un investigador a partir de un array de cadenas.
	 *
	 * @param formato array con formato {@code [id, anyoNacimiento, universidad]}
	 */
	private Investigador(String[] formato){
		super();
		this.id = Integer.parseInt(formato[0]);
		this.anyoNacimiento = Integer.parseInt(formato[1]);
		this.universidad = formato[2];
	}

	/**
	 * Obtiene el identificador del investigador.
	 *
	 * @return el identificador
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Obtiene el año de nacimiento del investigador.
	 *
	 * @return el año de nacimiento
	 */
	public Integer getFNacimiento() {
		return anyoNacimiento;
	}

	/**
	 * Obtiene la universidad del investigador.
	 *
	 * @return el nombre de la universidad
	 */
	public String getUniversidad() {
		return universidad;
	}

	/**
	 * Calcula el código hash del investigador.
	 *
	 * @return el código hash basado en todos los atributos
	 */
	@Override
	public int hashCode() {
		return Objects.hash(anyoNacimiento, id, universidad);
	}

	/**
	 * Compara este investigador con otro objeto para determinar igualdad.
	 *
	 * <p>Dos investigadores son iguales si tienen el mismo id,
	 * año de nacimiento y universidad.</p>
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
		Investigador other = (Investigador) obj;
		return Objects.equals(anyoNacimiento, other.anyoNacimiento) && Objects.equals(id, other.id)
				&& Objects.equals(universidad, other.universidad);
	}

	/**
	 * Devuelve una representación en cadena del investigador.
	 *
	 * @return cadena con formato {@code inv-id}
	 */
	@Override
	public String toString() {
		return "inv-" + id;
	}
	
	
	
}
