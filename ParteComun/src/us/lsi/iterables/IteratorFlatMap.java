package us.lsi.iterables;

import java.util.Iterator;
import java.util.function.Function;

/**
 * IteratorFlatMap
 *
 * <p>Iterador que aplica una transformación flatMap sobre un iterador.
 * Para cada elemento del iterador original, aplica una función que genera
 * un iterable, y luego aplana todos los iterables resultantes en una
 * secuencia única.</p>
 *
 * <p>Es equivalente a la operación flatMap de streams: primero mapea cada
 * elemento a un iterable, y luego concatena todos los iterables resultantes.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<String> palabras = List.of("hola", "mundo");
 * Function<String, Iterable<Character>> aCaracteres = s -> s.chars()
 *     .mapToObj(c -> (char)c).toList();
 * Iterable<Character> caracteres = IteratorFlatMap.of(palabras.iterator(), aCaracteres);
 * // Produce: h, o, l, a, m, u, n, d, o
 * }</p>
 *
 * @param <E> tipo de elementos del iterador original
 * @param <R> tipo de elementos del iterador resultante
 *
 * @author Miguel Toro
 */
public class IteratorFlatMap<E,R> implements Iterator<R>, Iterable<R> {
	
	private Iterator<E> iterator;
	private Function<E,Iterable<R>> fmap;
	private Iterator<R> actual;
	
	/**
	 * Crea un iterador flatMap.
	 *
	 * @param <E> tipo de elementos del iterador original
	 * @param <R> tipo de elementos del resultado
	 * @param iterator el iterador de entrada
	 * @param fmap función que mapea cada elemento a un iterable
	 * @return el iterador flatMap resultante
	 */
	public static <E,R> IteratorFlatMap<E,R> of(Iterator<E> iterator, Function<E,Iterable<R>> fmap){
		return new IteratorFlatMap<>(iterator,fmap);
	}
	
	IteratorFlatMap(Iterator<E> iterator, Function<E,Iterable<R>> fmap) {
		this.iterator = IteratorFilter.of(iterator,e->fmap.apply(e).iterator().hasNext());
		this.fmap = fmap;
		this.actual = this.iterator.hasNext()? fmap.apply(this.iterator.next()).iterator(): null;
	}

	@Override
	public boolean hasNext() {
		return this.actual.hasNext() || this.iterator.hasNext();
	}
	
	@Override
	public R next() {
		if(!this.actual.hasNext()) {
			do 
				this.actual = fmap.apply(this.iterator.next()).iterator();
			while(!this.actual.hasNext());
		}
		return this.actual.next();
	}

	@Override
	public Iterator<R> iterator() {
		return this;
	}
	
}

