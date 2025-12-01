package us.lsi.graphs.alg;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import us.lsi.streams.Stream2;

/**
 * Greedy
 *
 * <p>Implementación genérica de un algoritmo voraz (Greedy).
 * Genera una secuencia de estados aplicando una función de transición
 * hasta alcanzar un estado objetivo.</p>
 *
 * <p>Es iterable y proporciona acceso mediante streams para facilitar
 * su uso con programación funcional.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Greedy<Estado> greedy = Greedy.of(
 *     estadoInicial,
 *     e -> e.siguiente(),
 *     e -> e.esFinal()
 * );
 * Optional<Estado> ultimo = greedy.last();
 * }</p>
 *
 * @param <E> tipo de los estados
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class Greedy<E> implements  Iterator<E>, Iterable<E> {
	
	/**
	 * Crea un algoritmo voraz con los parámetros dados.
	 *
	 * @param <E> tipo de los estados
	 * @param start estado inicial
	 * @param next función que calcula el siguiente estado
	 * @param goal predicado que indica si un estado es objetivo
	 * @return un nuevo algoritmo Greedy
	 */
	public static <E> Greedy<E> of(E start, UnaryOperator<E> next, Predicate<E> goal) {
		return new Greedy<E>(start, next, goal);
	}

	/** Estado actual. */
	private E state;
	
	/** Función de transición al siguiente estado. */
	private UnaryOperator<E> next;
	
	/** Predicado que indica si un estado es objetivo. */
	private Predicate<E> goal;

	/**
	 * Constructor privado del algoritmo Greedy.
	 *
	 * @param start estado inicial
	 * @param next función de transición
	 * @param goal predicado de objetivo
	 */
	private Greedy(E start, UnaryOperator<E> next, Predicate<E> goal) {
		super();
		this.state = start;
		this.next = next;
		this.goal = goal;
	}

	/**
	 * Obtiene un stream de los estados generados.
	 *
	 * @return stream de estados
	 */
	public Stream<E> stream() {
		return Stream2.of(this);
	}
	
	/**
	 * Crea una copia del algoritmo.
	 *
	 * @return una copia independiente
	 */
	public Greedy<E> copy() {
		return of(state, next, goal);
	}
	
	@Override
	public Iterator<E> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return state != null && !this.goal.test(state);
	}

	@Override
	public E next() {
		E old = state;
		state = this.next.apply(state);
		return old;
	}
	
	/**
	 * Obtiene el último estado generado (el estado objetivo).
	 *
	 * @return el último estado como Optional
	 */
	public Optional<E> last(){
		return Stream2.findLast(this.stream());
	}
	
}
