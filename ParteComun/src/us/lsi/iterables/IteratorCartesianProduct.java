package us.lsi.iterables;

import java.util.Iterator;

import us.lsi.common.Pair;

/**
 * IteratorCartesianProduct
 *
 * <p>Iterador que genera el producto cartesiano de dos iterables.
 * Produce pares ordenados (a, b) donde a pertenece al primer iterable
 * y b pertenece al segundo iterable.</p>
 *
 * <p>El producto cartesiano de dos conjuntos A y B es el conjunto de
 * todos los pares ordenados (a, b) donde a ∈ A y b ∈ B.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Iterable<String> letras = List.of("A", "B");
 * Iterable<Integer> numeros = List.of(1, 2, 3);
 * Iterable<Pair<String, Integer>> producto = IteratorCartesianProduct.of(letras, numeros);
 * // Produce: (A,1), (A,2), (A,3), (B,1), (B,2), (B,3)
 * }</p>
 *
 * @param <A> tipo de elementos del primer iterable
 * @param <B> tipo de elementos del segundo iterable
 *
 * @author Miguel Toro
 */
public class IteratorCartesianProduct<A,B> implements Iterator<Pair<A,B>>,Iterable<Pair<A,B>>{
	
	/**
	 * Crea un producto cartesiano de un iterable consigo mismo.
	 *
	 * @param <A> tipo de elementos
	 * @param iterableA el iterable a combinar consigo mismo
	 * @return el producto cartesiano A × A
	 */
	public static <A> Iterable<Pair<A,A>> of(Iterable<A> iterableA) {
		Iterable<Pair<A,A>> r = IteratorEmpty.of();
		if(iterableA.iterator().hasNext()) {
			r = new IteratorCartesianProduct<A,A>(iterableA, iterableA);
		}
		return r;
	}
	
	
	/**
	 * Crea un producto cartesiano de dos iterables.
	 *
	 * @param <A> tipo de elementos del primer iterable
	 * @param <B> tipo de elementos del segundo iterable
	 * @param iterableA el primer iterable
	 * @param iterableB el segundo iterable
	 * @return el producto cartesiano A × B
	 */
	public static <A,B> Iterable<Pair<A,B>> of(Iterable<A> iterableA, Iterable<B> iterableB) {
		Iterable<Pair<A,B>> r = IteratorEmpty.of();
		if(iterableA.iterator().hasNext() && iterableB.iterator().hasNext()) {
			r = new IteratorCartesianProduct<A,B>(iterableA, iterableB);
		}
		return r;
	}
	

	private Iterator<A> iteratorA;
	private A actualA;
	private Iterable<B> iterableB;
	private Iterator<B> iteratorB;
	
	private IteratorCartesianProduct(Iterable<A> iterableA, Iterable<B> iterableB) {
		super();
		this.iteratorA = iterableA.iterator();
		this.actualA = this.iteratorA.next();
		this.iterableB = iterableB;
		this.iteratorB = this.iterableB.iterator();
	}

	@Override
	public Iterator<Pair<A,B>> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		return this.iteratorA.hasNext() || this.iteratorB.hasNext();
	}
	
	@Override
	public Pair<A,B> next() {
		if(!this.iteratorB.hasNext()) {
			this.iteratorB = this.iterableB.iterator();
			this.actualA = this.iteratorA.next();
		}
		return Pair.of(this.actualA,this.iteratorB.next());
	}
}
