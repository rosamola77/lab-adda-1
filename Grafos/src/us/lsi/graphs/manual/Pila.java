package us.lsi.graphs.manual;

/**
 * Pila
 *
 * <p>Implementación de una pila (stack) LIFO (Last In First Out).
 * Los elementos se añaden y eliminan por el mismo extremo (tope).</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Pila<Integer> pila = Pila.of();
 * pila.add(1);
 * pila.add(2);
 * Integer elemento = pila.remove(); // devuelve 2
 * }</p>
 *
 * @param <E> tipo de los elementos
 *
 * @author Miguel Toro
 */
public class Pila<E> extends AgregadoLineal<E>{
	
	/**
	 * Crea una nueva pila vacía.
	 *
	 * @param <E> tipo de los elementos
	 * @return nueva pila vacía
	 */
	public static <E> Pila<E> of() {
		return new Pila<>();
	}

	/**
	 * Constructor privado. Usar el método estático {@code of()}.
	 */
	private Pila() {
		super();
	}

	/**
	 * Añade un elemento en el tope de la pila.
	 *
	 * @param e elemento a añadir
	 */
	@Override
	public void add(E e) {
		this.elements.add(0, e);
	}

}
