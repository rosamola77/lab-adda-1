package us.lsi.graphs.manual;

import java.util.Comparator;

/**
 * ListaOrdenadaSinRepeticion
 *
 * <p>Lista ordenada que no permite elementos duplicados. Mantiene los
 * elementos ordenados según un comparador y rechaza la inserción de
 * elementos que ya existen en la lista.</p>
 *
 * <p>Combina las características de una lista ordenada con la unicidad
 * de un conjunto, útil para mantener colecciones ordenadas sin duplicados.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * ListaOrdenadaSinRepeticion<Integer> lista = 
 *     ListaOrdenadaSinRepeticion.of(Integer::compareTo);
 * lista.add(5);
 * lista.add(2);
 * lista.add(5);  // Se ignora, ya existe
 * lista.add(8);
 * // Resultado: [2, 5, 8]
 * }</p>
 *
 * @param <E> tipo de elementos de la lista
 *
 * @author Miguel Toro
 */
public class ListaOrdenadaSinRepeticion<E> extends ListaOrdenada<E> {

	/**
	 * Crea una lista ordenada sin repetición.
	 *
	 * @param <E> tipo de elementos
	 * @param comparator el comparador para ordenar los elementos
	 * @return una nueva lista ordenada sin repetición
	 */
	public static <E> ListaOrdenadaSinRepeticion<E> of(Comparator<E> comparator) {
		return new ListaOrdenadaSinRepeticion<>(comparator);
	}

	protected ListaOrdenadaSinRepeticion(Comparator<E> comparator) {
		super(comparator);
	}

	@Override
	void add(E e) {
		if(this.elements.contains(e))
			return;
		int i = this.indexOrder(e);
		this.elements.add(i, e);
	}
	

}
