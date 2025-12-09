package ejercicio4;

import java.util.Objects;

/**
 * Interseccion
 *
 * <p>Representa una intersección de calles en el contexto del Ejercicio 4.
 * Cada intersección tiene un identificador único, un nombre, un indicador
 * de si contiene un monumento, y un nivel de relevancia.</p>
 *
 * <p>Esta clase se utiliza como vértice en grafos de calles urbanas,
 * donde las intersecciones conectan las diferentes calles y pueden
 * tener atributos especiales (monumentos) que afectan a la optimización
 * de rutas turísticas.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Interseccion inter = Interseccion.ofId(1);
 * Boolean tieneMonumento = inter.hasMonumento();
 * Integer relevancia = inter.getRelevancia();
 * }</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 */
public class Interseccion {

	/**
	 * Crea una intersección con identificador por defecto (-1).
	 *
	 * @return una nueva instancia de {@code Interseccion}
	 */
	public static Interseccion of() {
		return new Interseccion(-1);
	}

	/**
	 * Crea una intersección a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [id, monumento, nombre, relevancia]}
	 * donde el campo de relevancia puede incluir el sufijo "int" que será
	 * eliminado automáticamente, o el valor "-1" para indicar ausencia de relevancia.</p>
	 *
	 * @param formato array de {@code String} con los datos de la intersección
	 * @return una nueva instancia de {@code Interseccion}
	 * @throws NumberFormatException si los elementos numéricos no son válidos
	 * @throws ArrayIndexOutOfBoundsException si el array no tiene al menos 4 elementos
	 */
	public static Interseccion ofFormat(String[] formato) {
		return new Interseccion(formato);
	}

	/**
	 * Crea una intersección con el identificador especificado.
	 *
	 * @param id identificador de la intersección
	 * @return una nueva instancia de {@code Interseccion}
	 */
	public static Interseccion ofId(Integer id) {
		return new Interseccion(id);
	}
	
	/** Identificador único de la intersección. */
	private Integer id = null;
	
	/** Indica si la intersección contiene un monumento. */
	private Boolean monumento = null;
	
	/** Nivel de relevancia de la intersección. */
	private Integer relevancia = null;
	
	/** Nombre de la intersección. */
	private String nombre = null;

	/**
	 * Constructor privado que crea una intersección con el identificador dado.
	 *
	 * @param id identificador de la intersección
	 */
	private Interseccion(Integer id) {
		super();
		this.id = id;
		this.relevancia = null;
		this.nombre = null;
		this.monumento = false;
	}

	/**
	 * Constructor privado que crea una intersección a partir de un array de cadenas.
	 *
	 * @param formato array con formato {@code [id, monumento, nombre, relevancia]}
	 */
	private Interseccion(String[] formato){
		super();
		this.id = Integer.parseInt(formato[0]);
		this.monumento = Boolean.parseBoolean(formato[1]);
		this.nombre = formato[2];				
		if (formato[3].replaceAll(" ","").trim().equals("-1")) {
		   this.relevancia = -1;
		} else {
		   this.relevancia = Integer.parseInt(formato[3].replaceAll("int","").trim());
		}
	}

	/**
	 * Obtiene el identificador de la intersección.
	 *
	 * @return el identificador
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Indica si la intersección contiene un monumento.
	 *
	 * @return {@code true} si hay un monumento; {@code false} en caso contrario
	 */
	public Boolean hasMonumento() {
		return monumento;
	}

	/**
	 * Obtiene el nivel de relevancia de la intersección.
	 *
	 * @return la relevancia, o -1 si no tiene relevancia definida
	 */
	public Integer getRelevancia() {
		return relevancia;
	}

	/**
	 * Obtiene el nombre de la intersección.
	 *
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * Calcula el código hash de la intersección.
	 *
	 * @return el código hash basado en todos los atributos
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, monumento, nombre, relevancia);
	}

	/**
	 * Compara esta intersección con otro objeto para determinar igualdad.
	 *
	 * <p>Dos intersecciones son iguales si tienen el mismo id,
	 * monumento, nombre y relevancia.</p>
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
		Interseccion other = (Interseccion) obj;
		return Objects.equals(id, other.id) && Objects.equals(monumento, other.monumento)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(relevancia, other.relevancia);
	}

	/**
	 * Devuelve una representación en cadena de la intersección.
	 *
	 * @return cadena con formato {@code int-id}
	 */
	@Override
	public String toString() {
		return "int-" + id;
	}
	
	
	
}
