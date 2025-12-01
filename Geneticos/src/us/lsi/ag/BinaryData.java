package us.lsi.ag;

/**
 * BinaryData
 *
 * <p>Interfaz que define los datos para cromosomas binarios en algoritmos genéticos.
 * Extiende {@link RangeData} con valores fijos 0 y 1 para cada gen.</p>
 *
 * <p>Es útil para problemas donde las decisiones son de tipo sí/no,
 * como incluir o no un elemento en un conjunto.</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RangeData
 * @see ChromosomeData
 */
public interface BinaryData<S> extends RangeData<Integer,S>{
	
	/**
	 * Obtiene el valor máximo para un gen (siempre 2, exclusivo).
	 *
	 * @param i índice del gen (no utilizado)
	 * @return siempre 2, lo que permite valores 0 y 1
	 */
	default Integer max(Integer i) {
		return 2;
	}
	
	/**
	 * Obtiene el valor mínimo para un gen (siempre 0).
	 *
	 * @param i índice del gen (no utilizado)
	 * @return siempre 0
	 */
	default Integer min(Integer i) {
		return 0;
	}
}
