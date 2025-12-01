package us.lsi.ag;

import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

/**
 * ChromosomeData
 *
 * <p>Interfaz que define los datos necesarios para configurar un cromosoma
 * en un algoritmo genético. Esta interfaz debe ser implementada por
 * cada problema específico que se desee resolver.</p>
 *
 * <p>Define la estructura del cromosoma (tamaño), la función de fitness
 * para evaluar soluciones, y la transformación de valores del cromosoma
 * a una solución del problema.</p>
 *
 * @param <V> tipo de los valores decodificados del cromosoma
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see us.lsi.ag.agchromosomes.AlgoritmoAG
 */
public interface ChromosomeData<V,S> {
	
	/**
	 * Obtiene el número de valores (genes) del cromosoma.
	 *
	 * @return el tamaño del cromosoma
	 */
	Integer size();
	
	/**
	 * Calcula la función de fitness para un valor decodificado del cromosoma.
	 *
	 * <p>El fitness mide la calidad de una solución. Valores más altos
	 * indican mejores soluciones en problemas de maximización.</p>
	 *
	 * @param value valores decodificados del cromosoma
	 * @return el valor de fitness de la solución
	 */
	Double fitnessFunction(V value);
	
	/**
	 * Transforma los valores decodificados del cromosoma en una solución del problema.
	 *
	 * @param value valores decodificados del cromosoma
	 * @return la solución correspondiente
	 */
	S solution(V value);
	
	/**
	 * Obtiene el tipo de cromosoma a utilizar.
	 *
	 * @return el tipo de cromosoma
	 * @see ChromosomeType
	 */
	ChromosomeType type();
}
