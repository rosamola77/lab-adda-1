package us.lsi.iterables;

import java.util.Iterator;
import java.util.List;

/**
 * ListIterator
 *
 * <p>Iterador simple sobre una lista que permite recorrer sus elementos
 * de forma secuencial. Proporciona una implementación básica de iterador
 * que mantiene un índice de posición actual.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * List<String> palabras = List.of("hola", "mundo", "java");
 * ListIterator<String> it = new ListIterator<>(palabras);
 * while(it.hasNext()) {
 *     System.out.println(it.next());
 * }
 * }</p>
 *
 * @param <E> tipo de elementos de la lista
 *
 * @author Miguel Toro
 */
public class ListIterator<E> implements Iterator<E>, Iterable<E> {
	private Integer i;
	private List<E> ls;
	/**
	 * Crea un iterador sobre una lista.
	 *
	 * @param ls la lista a iterar
	 */
	public ListIterator(List<E> ls) {
		this.i = 0;
		this.ls = ls;	
	}	
	@Override
	public Iterator<E> iterator() {
		return new ListIterator<>(ls);
	}
	@Override
	public boolean hasNext() { 
		return i < ls.size(); 
	}
	@Override
	public E next() {
		E e = ls.get(i);
		i = i+1;
		return e;
	}
}

