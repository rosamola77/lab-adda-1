package us.lsi.common;

/**
 * <p>Vista de tipo 2 con elemento: divide en dos partes y extrae un elemento.</p>
 * 
 * <p>Util para patrones de divide y venceras donde se divide
 * la estructura en dos subestructuras y se extrae un elemento central.</p>
 * 
 * @author Miguel Toro
 *
 * @param <D> Tipo de la estructura de datos
 * @param <E> Tipo del elemento central
 * @param e Elemento central extraido
 * @param left Parte izquierda
 * @param right Parte derecha
 */
public record View2E<D,E>(E e,D left,D right) {
	
	/**
	 * Crea una vista de tipo 2E.
	 * 
	 * @param <D> Tipo de la estructura de datos
	 * @param <E> Tipo del elemento
	 * @param e Elemento central
	 * @param left Parte izquierda
	 * @param right Parte derecha
	 * @return Una nueva View2E
	 */
	public static <D, E> View2E<D, E> of(E e, D left, D right) {
		return new View2E<D, E>(e, left, right);
	}

}
