package us.lsi.iterables;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * IteratorDistinct
 *
 * <p>Iterador que elimina elementos duplicados de un iterable.
 * Produce solo los elementos únicos, manteniendo el orden de primera
 * aparición y descartando las ocurrencias subsiguientes de elementos
 * ya vistos.</p>
 *
 * <p>Utiliza un conjunto interno para realizar un seguimiento de los
 * elementos ya procesados, garantizando que cada elemento aparezca solo
 * una vez en la secuencia resultante.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<Integer> lista = List.of(1, 2, 3, 2, 4, 1, 5);
 * Iterable<Integer> unicos = IteratorDistinct.of(lista);
 * // Produce: 1, 2, 3, 4, 5
 * }</p>
 *
 * @param <E> tipo de elementos
 *
 * @author Miguel Toro
 */
public class IteratorDistinct<E> implements Iterator<E>,Iterable<E> {
	
	private Iterator<E> it;
	private Set<E> set;
	private E next;
	
	/**
	 * Crea un iterador de elementos distintos.
	 *
	 * @param <E> tipo de elementos
	 * @param iterable el iterable de entrada
	 * @return iterador que produce solo elementos únicos
	 */
	public static <E> IteratorDistinct<E> of(Iterable<E> iterable) {
		return new IteratorDistinct<E>(iterable);
	}
	
	private E netValue() {
		E e = it.next();
		while(it.hasNext() && set.contains(e)) e = it.next();
		return e;
	}

	private IteratorDistinct(Iterable<E> iterable) {
		this.it = iterable.iterator();
		this.set = new HashSet<>();
		this.next = it.hasNext()? netValue():null;
	}
	

	@Override
	public Iterator<E> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	@Override
	public E next() {
		E e = next;
		this.next = netValue();
		return e;
	}


}
