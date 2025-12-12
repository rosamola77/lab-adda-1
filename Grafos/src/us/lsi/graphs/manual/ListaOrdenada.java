package us.lsi.graphs.manual;

import java.util.Comparator;

/**
 * ListaOrdenada
 *
 * <p>Lista que mantiene sus elementos ordenados según un comparador.
 * Los elementos se insertan automáticamente en la posición correcta
 * para mantener el orden establecido.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * ListaOrdenada<Integer> lista = ListaOrdenada.of(Integer::compareTo);
 * lista.add(5);
 * lista.add(2);
 * lista.add(8);
 * // La lista contiene: [2, 5, 8]
 * }</p>
 *
 * @param <E> tipo de los elementos
 *
 * @author Miguel Toro
 */
public class ListaOrdenada<E> extends AgregadoLineal<E> {

	protected Comparator<E> cmp;

	/**
	 * Crea una nueva lista ordenada con el comparador especificado.
	 *
	 * @param <E> tipo de los elementos
	 * @param comparator comparador para ordenar elementos
	 * @return nueva lista ordenada
	 */
	public static <E> ListaOrdenada<E> of(Comparator<E> comparator) {
		return new ListaOrdenada<>(comparator);
	}

	/**
	 * Constructor protegido. Usar el método estático {@code of()}.
	 *
	 * @param comparator comparador para ordenar elementos
	 */
	protected ListaOrdenada(Comparator<E> comparator) {
		super();
		this.cmp = comparator;
	}

	/**
	 * Encuentra el índice donde debe insertarse un elemento para mantener el orden.
	 *
	 * @param e elemento a insertar
	 * @return índice de inserción
	 */
	protected int indexOrder(E e) {
		int ln = this.elements.size();
		if (ln == 0)
			return 0;
		int i = 0;
		if (this.isEmpty() || this.cmp.compare(e, this.elements.get(0)) < 0)
			return 0;
		if (this.cmp.compare(this.elements.get(ln - 1), e) <= 0)
			return ln;
		for (i = 0; i < ln; i++) {
			if (this.cmp.compare(this.elements.get(i), e) <= 0 && this.cmp.compare(this.elements.get(i + 1), e) > 0)
			return i + 1;
		}
		return i;
	}

	/**
	 * Añade un elemento en la posición correcta para mantener el orden.
	 *
	 * @param e elemento a añadir
	 */
	void add(E e) {
		int i = this.indexOrder(e);
		this.elements.add(i, e);
	}
}
