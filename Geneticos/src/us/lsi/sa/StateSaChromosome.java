package us.lsi.sa;

import org.apache.commons.math3.genetics.Chromosome;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agchromosomes.AChromosome;

/**
 * StateSaChromosome
 *
 * <p>Adaptador que permite utilizar cromosomas de algoritmos genéticos
 * como estados en algoritmos de recocido simulado (Simulated Annealing).
 * Envuelve un cromosoma genético y proporciona la interfaz necesaria
 * para SA.</p>
 *
 * <p>Esta clase permite reutilizar las implementaciones de cromosomas
 * genéticos en algoritmos de recocido simulado, aprovechando las
 * mismas representaciones y operadores de mutación.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * AChromosome<List<Integer>, List<Double>, Solucion> cromosoma = ...;
 * StateSaChromosome<List<Integer>, List<Double>, Solucion> estado = 
 *     StateSaChromosome.of(cromosoma);
 * // Usar en algoritmo de recocido simulado
 * }</p>
 *
 * @param <V> tipo de los valores decodificados
 * @param <G> tipo de los genes del cromosoma
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see StateSa
 * @see AChromosome
 */
public class StateSaChromosome<V,G,S> implements StateSa<V,G,S>  {

	/**
	 * Crea un estado de SA a partir de un cromosoma genético.
	 *
	 * @param <V> tipo de valores decodificados
	 * @param <G> tipo de genes
	 * @param <S> tipo de solución
	 * @param achromosome el cromosoma a adaptar
	 * @return un nuevo estado de SA que envuelve el cromosoma
	 */
	public static <V,G,S> StateSaChromosome<V,G,S> of(AChromosome<V,G,S> achromosome){
		return new StateSaChromosome<>(achromosome);
	}
	
	/**
	 * Crea un estado de SA a partir de un cromosoma genérico.
	 *
	 * @param <V> tipo de valores decodificados
	 * @param <G> tipo de genes
	 * @param <S> tipo de solución
	 * @param chromosome el cromosoma genérico a adaptar
	 * @return un nuevo estado de SA que envuelve el cromosoma
	 */
	@SuppressWarnings("unchecked")
	public static <V,G,S> StateSaChromosome<V,G,S> of(Chromosome chromosome){
		AChromosome<V,G,S> achromosome = (AChromosome<V,G,S>) chromosome;
		return new StateSaChromosome<>(achromosome);
	}
	
	/**
	 * Genera un cromosoma aleatorio a partir de un cromosoma modelo.
	 *
	 * @param <V> tipo de valores decodificados
	 * @param <G> tipo de genes
	 * @param <S> tipo de solución
	 * @param achromosome el cromosoma modelo
	 * @return un nuevo cromosoma aleatorio
	 */
	public static <V,G,S> Chromosome random(AChromosome<V,G,S> achromosome) {	
		Chromosome chr = achromosome.initialChromosome();
		return chr;
	}
	
	/**
	 * Datos del problema.
	 */
	private ChromosomeData<V,S> data;
	
	/**
	 * Cromosoma genético envuelto.
	 */
	private AChromosome<V,G,S> achromosome;
	
	/**
	 * Constructor privado que crea un estado de SA.
	 *
	 * @param achromosome el cromosoma a envolver
	 */
	private StateSaChromosome(AChromosome<V,G,S> achromosome) {
		super();
		this.data = achromosome.data();	
		this.achromosome = achromosome;	
	}
	
	/**
	 * Obtiene los datos del problema.
	 *
	 * @return los datos del problema
	 */
	public ChromosomeData<V, S> data() {
		return data;
	}

	/**
	 * Obtiene el cromosoma genético envuelto.
	 *
	 * @return el cromosoma
	 */
	public AChromosome<V,G,S> achromosome() {
		return achromosome;
	}
	
	/**
	 * Calcula el fitness del estado.
	 *
	 * <p>Nota: El fitness se invierte (negativo) para SA, ya que SA
	 * minimiza por defecto.</p>
	 *
	 * @return el fitness negativo del cromosoma
	 */
	@Override
	public double fitness() {
		return -this.achromosome.fitness();
	}
	
	/**
	 * Genera un estado vecino mediante mutación.
	 *
	 * @return un nuevo estado resultado de mutar el cromosoma actual
	 */
	@Override
	public StateSa<V,G,S> mutate() {
		Chromosome c = this.achromosome.mutationPolicy().mutate(this.chromosome());
		return StateSaChromosome.of(c);
	}

	/**
	 * Genera un estado aleatorio.
	 *
	 * @return un nuevo estado con un cromosoma aleatorio
	 */
	@Override
	public StateSa<V,G,S> random() {
		Chromosome c = StateSaChromosome.random(this.achromosome);
		return StateSaChromosome.of(c);
	}

	/**
	 * Crea una copia del estado actual.
	 *
	 * @return una copia del estado
	 */
	@Override
	public StateSa<V,G,S> copy() {
		return StateSaChromosome.of(this.achromosome);
	}
	
	/**
	 * Decodifica el cromosoma en valores del dominio.
	 *
	 * @return los valores decodificados
	 */
	public V decode() {
		return this.achromosome.decode();
	}
	
	/**
	 * Obtiene el cromosoma subyacente.
	 *
	 * @return el cromosoma
	 */
	public Chromosome chromosome() {
		return (Chromosome) this.achromosome;
	}
}
