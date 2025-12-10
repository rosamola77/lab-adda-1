package us.lsi.iterables;

import java.util.Iterator;

/**
 * IteratorEmpty
 *
 * <p>Iterador vacío que no contiene ningún elemento.
 * Proporciona una implementación de un iterador que siempre indica
 * que no hay más elementos disponibles.</p>
 *
 * <p>Es útil como caso base o valor por defecto cuando se necesita
 * un iterador pero no hay elementos que iterar.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Iterable<String> vacio = IteratorEmpty.of();
 * for(String s : vacio) {
 *     // Este bucle nunca se ejecuta
 * }
 * }</p>
 *
 * @param <E> tipo de elementos (aunque el iterador está vacío)
 *
 * @author Miguel Toro
 */
public class IteratorEmpty<E> implements Iterator<E>,Iterable<E> {
	
	/**
	 * Crea un iterador vacío.
	 *
	 * @param <E> tipo de elementos
	 * @return un iterador vacío
	 */
	public static <E> IteratorEmpty<E> of() {
		return new IteratorEmpty<E>();
	}

	private IteratorEmpty() {}

	@Override
	public Iterator<E> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public E next() {
		return null;
	}

}
