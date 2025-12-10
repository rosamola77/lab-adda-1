package us.lsi.iterables;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * IteratorExplicit
 *
 * <p>Iterador que genera una secuencia basada en una definición explícita.
 * Permite crear iteradores personalizados especificando:</p>
 * <ul>
 * <li>Un estado inicial</li>
 * <li>Una condición de continuación (hasNext)</li>
 * <li>Una función de transición de estado (next state)</li>
 * <li>Una función de extracción del valor (map state to element)</li>
 * </ul>
 *
 * <p>Es útil para generar secuencias complejas que siguen un patrón
 * específico definido por funciones.</p>
 *
 * <p>Ejemplo de uso (secuencia de Fibonacci):
 * {@code
 * IteratorExplicit<Pair<Integer,Integer>, Integer> fib = 
 *     new IteratorExplicit<>(
 *         Pair.of(0, 1),                    // estado inicial
 *         p -> p.first() < 100,             // condición
 *         p -> Pair.of(p.second(), p.first() + p.second()),  // transición
 *         p -> p.first()                    // extracción
 *     );
 * // Produce: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89
 * }</p>
 *
 * @param <T> tipo del estado interno
 * @param <E> tipo de elementos producidos
 *
 * @author Miguel Toro
 */
public class IteratorExplicit<T,E> implements Iterator<E>,Iterable<E> {

	private T t;
	private Predicate<T> hn;
	private UnaryOperator<T> nx;
	private Function<T,E> nx1;
	
	
	
	/**
	 * Crea un iterador explícito con definición personalizada.
	 *
	 * @param t0 estado inicial
	 * @param hn predicado que determina si hay más elementos
	 * @param nx función de transición al siguiente estado
	 * @param nx1 función que extrae el elemento del estado
	 */
	public IteratorExplicit(T t0, Predicate<T> hn, UnaryOperator<T> nx, Function<T, E> nx1) {
		super();
		this.t = t0;
		this.hn = hn;
		this.nx = nx;
		this.nx1 = nx1;
	}


	@Override
	public Iterator<E> iterator() {
		return this;
	}


	@Override
	public boolean hasNext() {
		return hn.test(t);
	}


	@Override
	public E next() {
		E e = nx1.apply(t);
		this.t = nx.apply(t);
		return e;
	}
	
	
}
