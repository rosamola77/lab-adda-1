package us.lsi.ag.agchromosomes;

import us.lsi.ag.ChromosomeData;

/**
 * ChromosomeValues
 *
 * <p>Interfaz que define el contrato para los valores de un cromosoma en
 * algoritmos genéticos. Proporciona métodos para la decodificación de genes
 * en valores concretos del problema.</p>
 *
 * <p>Esta interfaz es implementada por diferentes tipos de representaciones
 * cromosómicas (binarias, permutaciones, rangos, etc.) y permite la conversión
 * entre la representación genética (genes) y los valores del dominio del problema.</p>
 *
 * <p>Los parámetros genéricos representan:
 * <ul>
 *   <li><b>V</b>: Tipo de los valores decodificados (ej: {@code List<Integer>})</li>
 *   <li><b>G</b>: Tipo de los genes del cromosoma (ej: {@code List<Double>})</li>
 *   <li><b>S</b>: Tipo de la solución del problema</li>
 * </ul>
 * </p>
 *
 * @param <V> tipo de los valores decodificados del cromosoma
 * @param <G> tipo de los genes del cromosoma
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 */
public interface ChromosomeValues<V,G,S> {
	
	/**
	 * Decodifica los genes del cromosoma en valores del dominio del problema.
	 *
	 * @param g genes del cromosoma a decodificar
	 * @return valores decodificados
	 */
	V decodeValues(G g);
	
	/**
	 * Obtiene la dimensión del cromosoma.
	 *
	 * @return el número de genes en el cromosoma
	 */
	Integer dimension();
	
	/**
	 * Obtiene los datos del problema asociados al cromosoma.
	 *
	 * @return los datos del problema
	 */
	ChromosomeData<V,S> data();

}
