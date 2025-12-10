package us.lsi.iterables;

import java.util.Iterator;

import us.lsi.common.Enumerate;

/**
 * IteratorEnumerate
 *
 * <p>Iterador que enumera los elementos de un iterable, asociando a cada
 * elemento su índice de posición (comenzando desde 0).</p>
 *
 * <p>Produce pares (índice, elemento) donde el índice indica la posición
 * del elemento en la secuencia original.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<String> palabras = List.of("hola", "mundo", "java");
 * Iterable<Enumerate<String>> enumerado = IteratorEnumerate.of(palabras);
 * // Produce: (0, "hola"), (1, "mundo"), (2, "java")
 * }</p>
 *
 * @param <E> tipo de elementos a enumerar
 *
 * @author Miguel Toro
 */
public class IteratorEnumerate<E> implements Iterator<Enumerate<E>>,Iterable<Enumerate<E>>{
	
	/**
	 * Crea un iterador enumerado a partir de un iterable.
	 *
	 * @param <E> tipo de elementos
	 * @param iterator el iterable de entrada
	 * @return iterador con elementos enumerados (índice, elemento)
	 */
	public static <E> Iterable<Enumerate<E>> of(Iterable<E> iterator) {
		return new IteratorEnumerate<E>(iterator.iterator());
	}

	private Iterator<E> iterator;
	private Integer index;
	
	private IteratorEnumerate(Iterator<E> iterator) {
		super();
		this.iterator = iterator;
		this.index = 0;
	}
	
	@Override
	public Iterator<Enumerate<E>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@Override
	public Enumerate<E> next() {
		Integer oldIndex = this.index;
		this.index = this.index +1;
		return Enumerate.of(oldIndex,this.iterator.next());
	}
	
}
