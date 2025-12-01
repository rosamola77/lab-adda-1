package us.lsi.common;

/**
 * <p>Representa un elemento enumerado con un contador.</p>
 * 
 * <p>Util para asociar un indice numerico a cada elemento
 * de una secuencia o coleccion.</p>
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo del valor contenido
 * @param counter Indice o contador asociado al elemento
 * @param value Valor del elemento
 */
public record Enumerate<E>(Integer counter, E value) {

	/**
	 * Crea un Enumerate con el contador y valor especificados.
	 * 
	 * @param <E> Tipo del valor
	 * @param num Numero de contador
	 * @param value Valor del elemento
	 * @return Un nuevo Enumerate
	 */
	public static <E> Enumerate<E> of(Integer num, E value) {
		return new Enumerate<E>(num, value);
	}

	/**
	 * Representacion en cadena del elemento enumerado.
	 * 
	 * @return Cadena con formato "(counter,value)"
	 */
	@Override
	public String toString() {
		return String.format("(%d,%s)", counter(), value().toString());
	}

}