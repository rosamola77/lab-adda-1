package ejercicio4;

import java.util.Objects;

/**
 * Calle
 *
 * <p>Representa una calle en el contexto del Ejercicio 4.
 * Cada calle conecta dos intersecciones y tiene asociada una duración
 * de recorrido y un nivel de esfuerzo requerido.</p>
 *
 * <p>Esta clase se utiliza como arista en grafos de calles urbanas,
 * donde la duración y el esfuerzo sirven como métricas para
 * optimización de rutas.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Calle calle = Calle.of();
 * double duracion = calle.getDuracion();
 * double esfuerzo = calle.getEsfuerzo();
 * }</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 */
public class Calle {
	
	/**
	 * Crea una nueva calle con valores por defecto (duración y esfuerzo = 0).
	 *
	 * @return una nueva instancia de {@code Calle}
	 */
	public static Calle of() {
		return new Calle();
	}

	/**
	 * Crea una calle a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [_, _, duracion, esfuerzo]} donde
	 * los dos primeros elementos se ignoran (típicamente representan
	 * las intersecciones origen y destino). Las cadenas de duración y
	 * esfuerzo pueden contener sufijos como "min" y "esf" que serán
	 * eliminados automáticamente.</p>
	 *
	 * @param formato array de {@code String} con los datos de la calle
	 * @return una nueva instancia de {@code Calle}
	 * @throws NumberFormatException si los elementos numéricos no son válidos
	 * @throws ArrayIndexOutOfBoundsException si el array no tiene al menos 4 elementos
	 */
	public static  Calle ofFormat(String[] formato) {
		return new Calle(formato);
	}

	/**
	 * Contador estático para asignar identificadores únicos.
	 */
	private static int num;
	
	/**
	 * Identificador único de la calle.
	 */
	private int id;
	
	/**
	 * Duración del recorrido de la calle en minutos.
	 */
	private int duracion;
	
	/**
	 * Nivel de esfuerzo requerido para recorrer la calle.
	 */
	private int esfuerzo;

	/**
	 * Constructor por defecto que crea una calle con duración y esfuerzo 0.
	 */
	public Calle() {
		this.id = num;
		num++;
		this.duracion = 0;
		this.esfuerzo = 0;
	} 
	
	/**
	 * Constructor privado que crea una calle a partir de un array de cadenas.
	 *
	 * @param nombre array con formato {@code [_, _, duracion, esfuerzo]}
	 */
	private Calle(String[] nombre) {
		
		this.id = num;
		num++;
		this.duracion =  Integer.parseInt(nombre[2].replaceAll("min","").trim());
		this.esfuerzo =  Integer.parseInt(nombre[3].replaceAll("esf","").trim());
	}

	/**
	 * Obtiene la duración del recorrido de la calle.
	 *
	 * @return la duración en minutos
	 */
	public double getDuracion() {
		return duracion;
	}

	/**
	 * Obtiene el nivel de esfuerzo requerido para recorrer la calle.
	 *
	 * @return el nivel de esfuerzo
	 */
	public double getEsfuerzo() {
		return esfuerzo;
	}

	/**
	 * Obtiene el identificador de la calle.
	 *
	 * @return el identificador
	 */
	public int getId() {
		return id;
	}

	/**
	 * Calcula el código hash de la calle.
	 *
	 * @return el código hash basado en todos los atributos
	 */
	@Override
	public int hashCode() {
		return Objects.hash(duracion, esfuerzo, id);
	}

	/**
	 * Compara esta calle con otro objeto para determinar igualdad.
	 *
	 * <p>Dos calles son iguales si tienen el mismo id, duración y esfuerzo.</p>
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
		Calle other = (Calle) obj;
		return duracion == other.duracion && esfuerzo == other.esfuerzo && id == other.id;
	}

	/**
	 * Devuelve una representación en cadena de la calle.
	 *
	 * @return cadena con formato {@code Calle-id}
	 */
	@Override
	public String toString() {
		return "Calle-" + id + "";
	}



	
	
}
