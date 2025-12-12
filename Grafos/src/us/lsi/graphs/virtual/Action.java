package us.lsi.graphs.virtual;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Action
 * 
 * <p>Interfaz que representa una acción que se puede aplicar a un vértice
 * para generar un vértice vecino en un grafo virtual.</p>
 * 
 * <p>Las acciones son la base para la construcción de grafos virtuales,
 * donde los vértices y aristas se generan dinámicamente a medida que
 * se explora el grafo.</p>
 * 
 * @param <V> el tipo de los vértices
 * 
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public interface Action<V> {

	/**
	 * Aplica la acción al vértice dado para generar el vértice vecino.
	 * 
	 * @param v el vértice origen
	 * @return el vértice vecino resultante de aplicar la acción
	 */
	V neighbor(V v);

	/**
	 * Verifica si la acción es aplicable al vértice dado.
	 * 
	 * @param v el vértice a verificar
	 * @return true si la acción es aplicable, false en caso contrario
	 */
	boolean isApplicable(V v);
	
	/**
	 * Obtiene el nombre de la acción.
	 * 
	 * @return el nombre descriptivo de la acción
	 */
	String name();

	/**
	 * Crea una nueva acción con los parámetros especificados.
	 * 
	 * @param <V> el tipo de los vértices
	 * @param id el identificador único de la acción
	 * @param name el nombre de la acción
	 * @param isApplicable predicado que determina si la acción es aplicable
	 * @param neighbor función que genera el vértice vecino
	 * @return una nueva acción configurada
	 */
	public static <V> Action<V> of(Integer id, String name, Predicate<V> isApplicable, Function<V, V> neighbor) {
		return new ActionI<V>(id, name, isApplicable, neighbor);
	}

	/**
	 * Implementación interna de Action usando un record.
	 * 
	 * @param <V> el tipo de los vértices
	 * @param id el identificador de la acción
	 * @param name el nombre de la acción
	 * @param isApplicable predicado de aplicabilidad
	 * @param neighbor función generadora del vecino
	 */
	public record ActionI<V> (Integer id, String name, Predicate<V> isApplicable, Function<V, V> neighbor) implements Action<V> {

		@Override
		public V neighbor(V v) {
			return this.neighbor.apply(v);
		}

		@Override
		public boolean isApplicable(V v) {
			return this.isApplicable.test(v);
		}
		
	}

}