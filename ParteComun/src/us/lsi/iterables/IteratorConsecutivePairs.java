package us.lsi.iterables;

import java.util.Iterator;

import us.lsi.common.Pair;

/**
 * IteratorConsecutivePairs
 *
 * <p>Iterador que genera pares de elementos consecutivos de un iterable.
 * Produce pares (e₁, e₂), (e₂, e₃), (e₃, e₄), ... donde cada elemento
 * (excepto el primero y el último) aparece en dos pares consecutivos.</p>
 *
 * <p>Es útil para procesar elementos junto con sus vecinos inmediatos,
 * por ejemplo para calcular diferencias entre elementos consecutivos
 * o detectar patrones locales.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<Integer> numeros = List.of(1, 3, 6, 10, 15);
 * Iterable<Pair<Integer,Integer>> pares = IteratorConsecutivePairs.of(numeros);
 * // Produce: (1,3), (3,6), (6,10), (10,15)
 * }</p>
 *
 * @param <E> tipo de elementos
 *
 * @author Miguel Toro
 */
public class IteratorConsecutivePairs<E> implements Iterator<Pair<E,E>>,Iterable<Pair<E,E>>{

	/**
	 * Crea un iterador de pares consecutivos.
	 *
	 * @param <E> tipo de elementos
	 * @param iterable el iterable de entrada
	 * @return iterador que produce pares de elementos consecutivos
	 */
	public static <E> Iterable<Pair<E,E>> of(Iterable<E> iterable) {
		Iterable<Pair<E,E>> r = IteratorEmpty.of();
		Iterator<E> it = iterable.iterator();
		E last;
		if(it.hasNext()) {
			last = it.next();
			if(it.hasNext()) {
				r = new IteratorConsecutivePairs<E>(it, last);
			}			
		}	
		return r;
	}

	private Iterator<E> iterator;
	private E last;
	
	private IteratorConsecutivePairs(Iterator<E> iterator,E last) {
		super();
		this.iterator = iterator;
		this.last = last;
	}

	@Override
	public Iterator<Pair<E,E>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public Pair<E,E> next() {
		E oldLast = this.last;
		this.last = this.iterator.next();
		return Pair.of(oldLast,this.last);
	}
	
}

