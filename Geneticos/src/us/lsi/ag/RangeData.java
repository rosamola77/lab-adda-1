package us.lsi.ag;

import java.util.List;

/**
 * RangeData
 *
 * <p>Interfaz que define los datos para cromosomas con valores en rangos
 * en algoritmos genéticos. Cada gen tiene un valor mínimo y máximo.</p>
 *
 * <p>Es útil para problemas donde cada variable de decisión tiene
 * un dominio definido por un rango de valores.</p>
 *
 * @param <E> tipo de los elementos del rango
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 */
public interface RangeData<E,S> extends ChromosomeData<List<E>,S> {

	/**
	 * Obtiene el valor máximo (exclusivo) del rango de valores para el gen i.
	 *
	 * @param i índice del gen (0 &le; i &lt; size())
	 * @return el valor máximo del rango, sin incluir
	 */
	E max(Integer i);
	
	/**
	 * Obtiene el valor mínimo (inclusivo) del rango de valores para el gen i.
	 *
	 * @param i índice del gen (0 &le; i &lt; size())
	 * @return el valor mínimo del rango
	 */
	E min(Integer i);

}
