package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;

import us.lsi.ag.ChromosomeData;

/**
 * AChromosome
 *
 * <p>Interfaz principal que define el contrato para cromosomas en algoritmos
 * genéticos. Extiende la funcionalidad básica de {@link ChromosomeValues}
 * añadiendo operadores genéticos y métodos de evaluación.</p>
 *
 * <p>Un cromosoma representa una solución candidata al problema y debe
 * proporcionar:
 * <ul>
 *   <li>Políticas de operadores genéticos (cruce, mutación, selección)</li>
 *   <li>Generación de cromosoma inicial</li>
 *   <li>Evaluación de fitness</li>
 *   <li>Decodificación a solución del problema</li>
 * </ul>
 * </p>
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
 * @see ChromosomeValues
 * @see ChromosomeData
 */
public interface AChromosome<V,G,S> {
	
	/**
	 * Obtiene la política de cruce (crossover) para este tipo de cromosoma.
	 *
	 * @return la política de cruce configurada
	 */
	CrossoverPolicy crossOverPolicy();
	
	/**
	 * Obtiene la política de mutación para este tipo de cromosoma.
	 *
	 * @return la política de mutación configurada
	 */
	MutationPolicy mutationPolicy();
	
	/**
	 * Obtiene la política de selección para este tipo de cromosoma.
	 *
	 * @return la política de selección configurada
	 */
	SelectionPolicy selectionPolicy();
	
	/**
	 * Genera un cromosoma inicial aleatorio.
	 *
	 * @return un cromosoma inicial para la población
	 */
	Chromosome initialChromosome();
	
	/**
	 * Calcula el fitness (aptitud) del cromosoma.
	 *
	 * @return el valor de fitness (valores mayores indican mejores soluciones)
	 */
	double fitness();
	
	/**
	 * Convierte el cromosoma en una solución del problema.
	 *
	 * @return la solución representada por este cromosoma
	 */
	S solution();
	
	/**
	 * Decodifica el cromosoma actual en valores del dominio.
	 *
	 * @return los valores decodificados del cromosoma
	 */
	V decode();
	
	/**
	 * Decodifica un cromosoma dado en valores del dominio.
	 *
	 * @param chromosome el cromosoma a decodificar
	 * @return los valores decodificados
	 */
	V decode(Chromosome chromosome);
	
	/**
	 * Decodifica genes específicos en valores del dominio.
	 *
	 * @param g los genes a decodificar
	 * @return los valores decodificados
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
