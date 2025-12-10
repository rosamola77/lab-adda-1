package us.lsi.iterables;

/**
 * IteratorZip
 *
 * <p>Iterador que combina dos iteradores en pares, similar a la operación
 * zip de lenguajes funcionales.</p>
 *
 * <p>Esta clase crea un iterador de pares donde cada elemento es la
 * combinación del elemento correspondiente de cada iterador. El iterador
 * resultante termina cuando cualquiera de los dos iteradores base se agota.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Iterable<String> names = Arrays.asList("A", "B", "C");
 * Iterable<Integer> numbers = Arrays.asList(1, 2, 3);
 * Iterable<Pair<String, Integer>> zipped = 
 *     IteratorZip.of(names, numbers);
 * // Resultado: [(A,1), (B,2), (C,3)]
 * }</p>
 *
 * @param <A> tipo de elementos del primer iterador
 * @param <B> tipo de elementos del segundo iterador
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */

import java.util.Iterator;

import us.lsi.common.Pair;


public class IteratorZip<A, B> implements Iterator<Pair<A,B>>,Iterable<Pair<A,B>> {
	
	public static <A, B> Iterable<Pair<A, B>> of(Iterable<A> iteratorA, Iterable<B> iteratorB) {
		return new IteratorZip<A, B>(iteratorA.iterator(), iteratorB.iterator());
	}
	
	private Iterator<A> iteratorA;
	private Iterator<B> iteratorB;
	
	private IteratorZip(Iterator<A> iteratorA, Iterator<B> iteratorB) {
		super();
		this.iteratorA = iteratorA;
		this.iteratorB = iteratorB;
	}
	@Override
	public Iterator<Pair<A, B>> iterator() {
		return this;
	}
	@Override
	public boolean hasNext() {
		return this.iteratorA.hasNext() && this.iteratorB.hasNext();
	}
	@Override
	public Pair<A, B> next() {
		return Pair.of(this.iteratorA.next(), this.iteratorB.next());
	}

}

