package us.lsi.iterables;

/**
 * IteratorMap
 *
 * <p>Iterador que aplica una función de transformación a cada elemento
 * del iterador subyacente, similar a la operación map de Stream.</p>
 *
 * <p>Esta clase implementa el patrón Iterator y permite transformar
 * elementos de tipo E a tipo R mediante una función.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Iterable<Integer> numbers = Arrays.asList(1, 2, 3);
 * IteratorMap<Integer, String> stringNumbers = 
 *     IteratorMap.of(numbers, n -> "Num: " + n);
 * }</p>
 *
 * @param <E> tipo de elementos del iterador original
 * @param <R> tipo de elementos del iterador resultante
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */

import java.util.Iterator;
import java.util.function.Function;

public class IteratorMap<E,R> implements Iterator<R>, Iterable<R> {
	
	private Iterator<E> iterator;
	private Function<E,R> fmap;
	
	/**
	 * Crea un IteratorMap a partir de un iterable y una función de mapeo.
	 *
	 * @param <E> tipo de elementos del iterable original
	 * @param <R> tipo de elementos resultantes
	 * @param iterator iterable con los elementos originales
	 * @param fmap función de transformación
	 * @return nuevo IteratorMap que aplica la función
	 */
	public static <E,R> IteratorMap<E,R> of(Iterable<E> iterator, Function<E, R> fmap) {
		return new IteratorMap<>(iterator.iterator(), fmap);
	}
	
	public IteratorMap(Iterator<E> iterator, Function<E, R> fmap) {
		super();
		this.iterator = iterator;
		this.fmap = fmap;
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public R next() {
		return this.fmap.apply(this.iterator.next());
	}
	
	@Override
	public Iterator<R> iterator() {
		return this;
	}
	
}