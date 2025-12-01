package us.lsi.sa;

import us.lsi.ag.agchromosomes.AChromosome;

/**
 * StateSa
 *
 * <p>Interfaz que define el estado para el algoritmo de Simulated Annealing.
 * Cada estado tiene un valor de fitness y operaciones de mutación y
 * generación aleatoria.</p>
 *
 * @param <V> tipo de los valores decodificados
 * @param <G> tipo del genotipo
 * @param <S> tipo de la solución
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see AlgoritmoSA
 */
public interface StateSa<V,G,S> {
	
	/**
	 * Obtiene el valor de fitness del estado.
	 *
	 * @return el fitness (a minimizar)
	 */
	double fitness();
	
	/**
	 * Genera un estado vecino mediante mutación.
	 *
	 * @return un nuevo estado mutado
	 */
	StateSa<V,G,S> mutate();
	
	/**
	 * Genera un estado aleatorio.
	 *
	 * @return un nuevo estado aleatorio
	 */
	StateSa<V,G,S> random();
	
	/**
	 * Crea una copia del estado.
	 *
	 * @return una copia independiente
	 */
	StateSa<V,G,S> copy();
	
	/**
	 * Obtiene el cromosoma subyacente.
	 *
	 * @return el cromosoma asociado
	 */
	AChromosome<V,G,S> achromosome();
}
