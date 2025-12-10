package us.lsi.iterables;

import java.util.Iterator;

/**
 * IteratorWithPeek
 *
 * <p>Iterador que proporciona la funcionalidad de peek (espiar).
 * Permite ver el próximo elemento sin consumirlo, útil para tomar
 * decisiones basadas en el siguiente elemento antes de avanzar el
 * iterador.</p>
 *
 * <p>Mantiene internamente un buffer de un elemento para permitir
 * consultar el siguiente valor sin afectar la posición del iterador.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<Integer> numeros = List.of(1, 2, 3, 4, 5);
 * IteratorWithPeek<Integer> it = IteratorWithPeek.of(numeros.iterator());
 * 
 * while(it.hasNext()) {
 *     Integer siguiente = it.peek();  // mira sin consumir
 *     if(siguiente > 3) break;
 *     Integer actual = it.next();     // consume
 * }
 * }</p>
 *
 * @param <E> tipo de elementos
 *
 * @author Miguel Toro
 */
public class IteratorWithPeek<E> implements Iterator<E>, Iterable<E> {
	
	/**
	 * Crea un iterador con funcionalidad de peek.
	 *
	 * @param <E> tipo de elementos
	 * @param iterator el iterador de entrada
	 * @return iterador con capacidad de peek
	 */
	public static <E> IteratorWithPeek<E> of(Iterator<E> iterator) {
		return new IteratorWithPeek<>(iterator);
	}
	
	private Iterator<E> iterator;
	private E next;

	private IteratorWithPeek(Iterator<E> iterator) {
		super();
		this.iterator = iterator;
		this.next = iterator.hasNext() ? iterator.next() : null;
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
		E old = this.next;
		this.next = this.iterator.hasNext() ? this.iterator.next() : null;
		return old;
	}
	
	/**
	 * Devuelve el siguiente elemento sin consumirlo.
	 *
	 * @return el siguiente elemento, o null si no hay más elementos
	 */
	public E peek() {
		return this.next;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
