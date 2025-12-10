package us.lsi.iterables;

/**
 * IteratorFilter
 *
 * <p>Iterador que filtra elementos según un predicado, similar a la
 * operación filter de Stream.</p>
 *
 * <p>Esta clase implementa el patrón Iterator y permite filtrar elementos
 * manteniendo solo aquellos que cumplen con el predicado especificado.</p>
 *
 * <p>La implementación mantiene un elemento siguiente (ne) que se calcula
 * de forma perezosa, asegurando eficiencia en el filtrado.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Iterable<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
 * IteratorFilter<Integer> even = 
 *     IteratorFilter.of(numbers.iterator(), n -> n % 2 == 0);
 * }</p>
 *
 * @param <E> tipo de elementos del iterador
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */

import java.util.Iterator;
import java.util.function.Predicate;

public class IteratorFilter<E> implements Iterator<E>,Iterable<E> {
	
	private Iterator<E> iterator;
	private Predicate<E> p;
	private E ne;
	
	
	public static <E> IteratorFilter<E> of(Iterator<E> iterator, Predicate<E> p){
		return new IteratorFilter<>(iterator,p);
	}
	
	public IteratorFilter(Iterator<E> iterator, Predicate<E> p) {
		super();
		this.iterator = iterator;
		this.p = p;
		this.ne = first(this.iterator,this.p);
	}

	public static <E> E first(Iterator<E> s, Predicate<E> p) {
		E r = null;
		while(s.hasNext() && r==null){
			E e = s.next();
			if(p.test(e)) r = e;
		}
		return r;
	}

	@Override
	public boolean hasNext() {
		return this.ne != null;
	}


	@Override
	public E next() {
		E e = ne;
		this.ne = first(this.iterator,this.p);
		return e;
	}
	
	@Override
	public Iterator<E> iterator() {
		return this;
	}
	
}

