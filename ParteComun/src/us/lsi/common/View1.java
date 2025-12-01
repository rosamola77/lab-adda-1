package us.lsi.common;

/**
 * <p>Vista de tipo 1: extrae un elemento y devuelve el resto.</p>
 * 
 * <p>Util para patrones de divide y venceras donde se separa
 * un elemento del resto de la estructura.</p>
 * 
 * @author Miguel Toro
 *
 * @param <D> Tipo de la estructura de datos
 * @param <E> Tipo del elemento extraido
 * @param e Elemento extraido
 * @param r Resto de la estructura
 */
public record View1<D,E>(E e,D r) {
	
	/**
	 * Crea una vista de tipo 1.
	 * 
	 * @param <D> Tipo de la estructura de datos
	 * @param <E> Tipo del elemento
	 * @param e Elemento extraido
	 * @param r Resto de la estructura
	 * @return Una nueva View1
	 */
	public static <D, E> View1<D, E> of(E e, D r) {
		return new View1<D, E>(e, r);
	}
}
