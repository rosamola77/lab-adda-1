package us.lsi.anuncios.datos;

import us.lsi.common.Preconditions;

/**
 * Anuncio
 *
 * <p>Representa un anuncio publicitario con información sobre su código
 * identificador, duración en segundos y precio base. Esta clase se utiliza
 * en problemas de optimización para la selección de anuncios a emitir.</p>
 *
 * <p>Los anuncios son comparables por su precio unitario (precio base / duración),
 * lo que permite ordenarlos por rentabilidad.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Anuncio a = Anuncio.create(1, 30, 5000);
 * Double precioUnitario = a.getPrecioUnitario();
 * Double precioEnPosicion = a.getPrecio(1);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ListaDeAnunciosAEmitir
 * @see DatosAnuncios
 */
public class Anuncio implements Comparable<Anuncio> {

	/**
	 * Crea un anuncio con los parámetros especificados.
	 *
	 * @param codigo código identificador del anuncio
	 * @param duracion duración del anuncio en segundos
	 * @param precioBase precio base del anuncio
	 * @return una nueva instancia de {@code Anuncio}
	 */
	public static Anuncio create(Integer codigo, Integer duracion,Integer precioBase) {
		return new Anuncio(codigo, duracion, precioBase);
	}
	
	/**
	 * Crea un anuncio a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [codigo, duracion, precioBase]}</p>
	 *
	 * @param fm array de {@code String} con los datos del anuncio
	 * @return una nueva instancia de {@code Anuncio}
	 * @throws IllegalArgumentException si el array no tiene exactamente 3 elementos
	 * @throws NumberFormatException si algún elemento no es un número válido
	 */
	public static Anuncio create(String[] fm) {
		return new Anuncio(fm);
	}
	
	/** Código identificador del anuncio. */
	private Integer codigo;
	
	/** Duración del anuncio en segundos. */
	private Integer duracion;
	
	/** Precio base del anuncio. */
	private Integer precioBase;
	
	/**
	 * Constructor privado que crea un anuncio a partir de un array de cadenas.
	 *
	 * @param fm array con formato {@code [codigo, duracion, precioBase]}
	 * @throws IllegalArgumentException si el array no tiene exactamente 3 elementos
	 */
	private Anuncio(String[] fm) {
		super();
		Preconditions.checkArgument(fm.length==3);
		this.codigo = Integer.parseInt(fm[0]);
		this.duracion = Integer.parseInt(fm[1]);
		this.precioBase = Integer.parseInt(fm[2]);
	}
	
	/**
	 * Constructor privado que crea un anuncio con los parámetros especificados.
	 *
	 * @param codigo código identificador del anuncio
	 * @param duracion duración del anuncio en segundos
	 * @param precioBase precio base del anuncio
	 */
	private Anuncio(Integer codigo, Integer duracion, Integer precioBase) {
		super();
		this.codigo = codigo;
		this.duracion = duracion;
		this.precioBase = precioBase;           
	}
	
	/**
	 * Obtiene la duración del anuncio.
	 *
	 * @return la duración en segundos
	 */
	public Integer getDuracion(){
		return duracion;
	}
	
	/**
	 * Obtiene el precio base del anuncio.
	 *
	 * @return el precio base
	 */
	public Integer getPrecioBase() {
		return precioBase;
	}
	
	/**
	 * Obtiene el código identificador del anuncio.
	 *
	 * @return el código del anuncio
	 */
	public Integer getCodigo (){
		return codigo;
	}
	
	/**
	 * Calcula el precio unitario del anuncio.
	 *
	 * <p>El precio unitario se define como el precio base dividido
	 * por la duración del anuncio.</p>
	 *
	 * @return el precio unitario como {@code Double}
	 */
	public Double getPrecioUnitario(){
		return (precioBase*1.)/duracion;
	}
	
	/**
	 * Calcula el precio del anuncio según su posición en la emisión.
	 *
	 * <p>El precio se calcula como: {@code precioBase * 1000 / pos + 50000}</p>
	 *
	 * @param pos posición del anuncio en la secuencia de emisión (comenzando en 1)
	 * @return el precio calculado para la posición dada
	 */
	public Double getPrecio(Integer pos){
		return precioBase*1000./pos + 50000;
	}
	
	/**
	 * Compara este anuncio con otro por precio unitario.
	 *
	 * <p>Si los precios unitarios son iguales, se compara por código.</p>
	 *
	 * @param a el anuncio con el que comparar
	 * @return un valor negativo, cero o positivo según este anuncio sea menor,
	 *         igual o mayor que el especificado
	 */
	@Override
	public int compareTo(Anuncio a) {
		int r = getPrecioUnitario().compareTo(a.getPrecioUnitario());
		if(r==0){
			r = codigo.compareTo(codigo);
		}
		return r;
	}
	
	

	/**
	 * Devuelve una representación en cadena del anuncio.
	 *
	 * @return una cadena con formato {@code (codigo,duracion,precioBase)}
	 */
	@Override
	public String toString() {
		return String.format("(%s,%d,%d)",codigo,duracion,precioBase);
	}
	
	/**
	 * Calcula el código hash del anuncio basado en su código.
	 *
	 * @return el código hash
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	/**
	 * Compara este anuncio con otro objeto para determinar igualdad.
	 *
	 * <p>Dos anuncios son iguales si tienen el mismo código.</p>
	 *
	 * @param obj el objeto con el que comparar
	 * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Anuncio))
			return false;
		Anuncio other = (Anuncio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	
}

