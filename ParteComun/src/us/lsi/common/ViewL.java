package us.lsi.common;

import java.util.List;

/**
 * <p>Vista de tipo lista: extrae un elemento y devuelve una lista.</p>
 * 
 * <p>Util para patrones de divide y venceras donde se extrae
 * un elemento y el resto se divide en multiples subestructuras.</p>
 * 
 * @author Miguel Toro
 *
 * @param <D> Tipo de los elementos de la lista
 * @param <E> Tipo del elemento extraido
 * @param e Elemento extraido
 * @param elems Lista de subestructuras
 */
public record ViewL<D,E>(E e, List<D> elems) {
	
	/**
	 * Crea una vista de tipo L.
	 * 
	 * @param <D> Tipo de los elementos de la lista
	 * @param <E> Tipo del elemento
	 * @param e Elemento extraido
	 * @param elems Lista de subestructuras
	 * @return Una nueva ViewL
	 */
	public static <D,E> ViewL<D,E> of(E e, List<D> elems){
		return new ViewL<D,E>(e,elems);
	}
}
