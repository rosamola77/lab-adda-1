package us.lsi.iterables;

import java.util.Comparator;
import java.util.Iterator;

/**
 * IteratorFusionOrdered
 *
 * <p>Iterador que fusiona dos iterables ordenados en un único iterable
 * ordenado. Asume que ambos iterables de entrada están ordenados según
 * el comparador proporcionado, y produce un iterable ordenado que contiene
 * todos los elementos de ambas secuencias.</p>
 *
 * <p>Es equivalente a la operación merge de algoritmos de ordenamiento,
 * útil para combinar secuencias ya ordenadas manteniendo el orden.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<Integer> lista1 = List.of(1, 5, 9, 12);
 * List<Integer> lista2 = List.of(2, 3, 7, 10, 15);
 * Iterable<Integer> fusionada = IteratorFusionOrdered.of(
 *     lista1, lista2, Integer::compareTo
 * );
 * // Produce: 1, 2, 3, 5, 7, 9, 10, 12, 15
 * }</p>
 *
 * @param <E> tipo de elementos
 *
 * @author Miguel Toro
 */
public class IteratorFusionOrdered<E> implements Iterator<E>,Iterable<E> {
	
	/**
	 * Crea un iterador que fusiona dos iterables ordenados.
	 *
	 * @param <E> tipo de elementos
	 * @param itA primer iterable ordenado
	 * @param itB segundo iterable ordenado
	 * @param cmp comparador para mantener el orden
	 * @return iterador con la fusión ordenada de ambos iterables
	 */
	public static <E> Iterable<E> of(Iterable<E> itA, Iterable<E> itB, Comparator<E> cmp) {
		return new IteratorFusionOrdered<>(itA.iterator(),itB.iterator(),cmp);
	}
	
	private IteratorWithPeek<E> it1;
	private IteratorWithPeek<E> it2;
	private Comparator<E> cmp;
	
	private IteratorFusionOrdered(Iterator<E> itA, Iterator<E> itB, Comparator<E> cmp) {
		super();
		this.it1 = IteratorWithPeek.of(itA);
		this.it2 = IteratorWithPeek.of(itB);
		this.cmp = cmp;
	}
	
	@Override
	public Iterator<E> iterator() {
		return this;
	}
	@Override
	public boolean hasNext() {
		return it1.hasNext() || it2.hasNext();
	}
	
	@Override
	public E next() {
		E e;
		if (it1.hasNext() && it2.hasNext()) {
			e = cmp.compare(it1.peek(), it2.peek()) <= 0 ? it1.next() : it2.next(); 
		} else if (it2.hasNext()) {
			e = it2.next();
		} else {
			e = it1.next();
		}  
		return e;
	}
}
