package us.lsi.ag;
import java.util.List;

/**
 * BlocksData
 *
 * <p>Interfaz que define los datos para cromosomas organizados en bloques
 * en algoritmos genéticos. Los valores del cromosoma son permutaciones
 * de los elementos dentro de cada bloque definido.</p>
 *
 * <p>Es útil para problemas donde hay grupos de elementos que deben
 * ordenarse internamente pero mantener su pertenencia al grupo.</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 */
public interface BlocksData<S> extends ChromosomeData<List<Integer>,S> {
	
	/**
	 * Obtiene los límites de los bloques en los que se descompone el cromosoma.
	 *
	 * <p>Un bloque está definido por dos valores consecutivos en esta lista.
	 * Por ejemplo, [0, 3, 7] define dos bloques: [0,3) y [3,7).</p>
	 *
	 * @return lista de índices que delimitan los bloques
	 */
	List<Integer> blocksLimits();
	
	/**
	 * Obtiene los valores iniciales del cromosoma.
	 *
	 * <p>Los valores del cromosoma serán permutaciones de los valores
	 * dentro de cada bloque definido por {@link #blocksLimits()}.</p>
	 *
	 * @return lista de valores iniciales
	 */
	List<Integer> initialValues();

}
