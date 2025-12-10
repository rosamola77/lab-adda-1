package us.lsi.graphs.manual;

/**
 * Cola
 *
 * <p>Implementación de una cola (queue) FIFO (First-In, First-Out).
 * Los elementos se añaden al final y se eliminan del principio,
 * siguiendo el principio de que el primer elemento en entrar es
 * el primero en salir.</p>
 *
 * <p>Extiende AgregadoLineal proporcionando una política de inserción
 * que añade elementos al final de la cola.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Cola<String> cola = Cola.of();
 * cola.add("primero");
 * cola.add("segundo");
 * cola.add("tercero");
 * String elem = cola.remove();  // devuelve "primero"
 * }</p>
 *
 * @param <E> tipo de elementos de la cola
 *
 * @author Miguel Toro
 */
public class Cola<E> extends AgregadoLineal<E>{
	
	/**
	 * Crea una cola vacía.
	 *
	 * @param <E> tipo de elementos
	 * @return una nueva cola vacía
	 */
	public static <E> Cola<E> of() {
		return new Cola<>();
	}

	private Cola() {
		super();
	}

	@Override
	public void add(E e) {
		this.elements.add(e);
	}

}
