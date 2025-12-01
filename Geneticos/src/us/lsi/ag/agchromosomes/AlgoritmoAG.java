package us.lsi.ag.agchromosomes;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.random.JDKRandomGenerator;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.Preconditions;

/**
 * AlgoritmoAG
 *
 * <p>Implementación de un Algoritmo Genético (AG) para resolver problemas
 * de optimización. Utiliza la librería Apache Commons Math para la
 * mecánica del algoritmo genético.</p>
 *
 * <p>El algoritmo evoluciona una población de cromosomas mediante
 * operadores de selección, cruce y mutación hasta cumplir una
 * condición de parada.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * AlgoritmoAG<List<Integer>, Solucion> ag = AlgoritmoAG.of(problema);
 * ag.ejecuta();
 * Solucion mejor = ag.bestSolution();
 * }</p>
 *
 * @param <V> tipo de los valores decodificados del cromosoma
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 * @see AChromosome
 */
public class AlgoritmoAG<V,S> {
	
	/**
	 * Crea un algoritmo genético para el problema dado.
	 *
	 * @param <V> tipo de los valores decodificados
	 * @param <S> tipo de la solución
	 * @param chromosomeData datos del problema
	 * @return un nuevo algoritmo genético configurado
	 */
	public static <V,S> AlgoritmoAG<V,S> of(ChromosomeData<V,S> chromosomeData) {
		return new AlgoritmoAG<V,S>(chromosomeData);
	}
	
	/**
	 * Tamaño de la población.
	 * 
	 * <p>Usualmente un valor cercano a la dimensión de los cromosomas o mayor.</p>
	 */
	public static int POPULATION_SIZE = 30;
	
	/**
	 * Tasa de elitismo.
	 * 
	 * <p>El porcentaje especificado de los mejores cromosomas pasa a la
	 * siguiente generación sin cambio.</p>
	 */
	public static double ELITISM_RATE = 0.2;
	
	/**
	 * Tasa de cruce.
	 * 
	 * <p>Indica con qué frecuencia se realiza el cruce. Si no hay cruce,
	 * la descendencia es copia exacta de los padres. Si hay cruce, la
	 * descendencia está hecha de partes del cromosoma de los padres.</p>
	 * 
	 * <p>El cruce se hace con la esperanza de que los nuevos cromosomas
	 * tendrán las partes buenas de los padres y tal vez serán mejores.</p>
	 *
	 * <p>Valores usuales entre 0.8 y 0.95.</p>
	 */
	public static double CROSSOVER_RATE = 0.8;
	
	/**
	 * Tasa de mutación.
	 * 
	 * <p>Indica con qué frecuencia serán mutados los cromosomas. Si no hay
	 * mutación, la descendencia se toma después del cruce sin cambio.
	 * La mutación se hace para evitar caer en máximos locales.</p>
	 *
	 * <p>Valores usuales entre 0.5 y 1.</p>
	 */
	public static double MUTATION_RATE = 0.6;
	
	/** Tiempo de inicio de la ejecución. */
	public static long INITIAL_TIME;
	
	/** Tiempo de finalización de la ejecución. */
	public static long FINAL_TIME;
	
	/** Cromosoma base para crear la población. */
	public AChromosome<V,?,S>  aChromosome;
	
	/** Datos del problema. */
	public ChromosomeData<V,S> data;
	
	/** Política de cruce. */
	public CrossoverPolicy crossOverPolicy;
	
	/** Política de mutación. */
	public MutationPolicy mutationPolicy;
	
	/** Política de selección. */
	public SelectionPolicy selectionPolicy;
	
	/** Condición de parada. */
	private StoppingCondition stopCond;

	/**
	 * Lista con los mejores cromosomas de cada generación.
	 * 
	 * <p>Disponible si se usa la condición de parada SolutionsNumbers.
	 * En otro caso null.</p>
 	 */
	public static List<Chromosome> bestChromosomes;
	
	/** Población inicial. */
	protected static Population initialPopulation;
	
	/** Mejor cromosoma de la población final. */
	protected static Chromosome bestFinal;
	
	/** Población final tras la evolución. */
	protected static Population finalPopulation;
	
	/** Mejor fitness encontrado. */
	public static Double bestFitNess;
	
	/** Generador de números aleatorios. */
	public static JDKRandomGenerator random;
	
	
	/**
	 * Constructor que crea un algoritmo genético para el problema dado.
	 *
	 * @param chromosomeData datos del problema a resolver
	 */
	public AlgoritmoAG(ChromosomeData<V,S> chromosomeData) {
		super();
		AlgoritmoAG.random = new JDKRandomGenerator();		
		AlgoritmoAG.random.setSeed((int)System.currentTimeMillis());
		GeneticAlgorithm.setRandomGenerator(random);
		this.aChromosome = Chromosomes.of(chromosomeData);
		this.data = chromosomeData;
		this.selectionPolicy =  this.aChromosome.selectionPolicy();
		this.mutationPolicy = this.aChromosome.mutationPolicy();
		this.crossOverPolicy = this.aChromosome.crossOverPolicy();
		this.stopCond = StoppingConditionFactory.getStoppingCondition();
//		this.data.iniValues(this.data);
	}

	/**
	 * Genera una población inicial aleatoria.
	 *
	 * @return población inicial con cromosomas aleatorios
	 */
	public ElitisticListPopulation randomPopulation() {
		List<Chromosome> popList = new LinkedList<>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Chromosome randChrom = this.aChromosome.initialChromosome();
			popList.add(randChrom);
		}
		return new ElitisticListPopulation(popList, popList.size(), ELITISM_RATE);
	}	

	/**
	 * Ejecuta el algoritmo genético.
	 *
	 * <p>Inicializa la población, evoluciona hasta cumplir la condición
	 * de parada y almacena la mejor solución encontrada.</p>
	 */
	public void ejecuta() {
		AlgoritmoAG.INITIAL_TIME = System.currentTimeMillis();
		AlgoritmoAG.initialPopulation = randomPopulation();
		Preconditions.checkNotNull(AlgoritmoAG.initialPopulation);		
		
		GeneticAlgorithm ga = new GeneticAlgorithm(
				crossOverPolicy, 
				CROSSOVER_RATE,
				mutationPolicy, 
				MUTATION_RATE, 
				selectionPolicy);	
		
		AlgoritmoAG.finalPopulation = ga.evolve(AlgoritmoAG.initialPopulation, this.stopCond);		
		Preconditions.checkNotNull(AlgoritmoAG.finalPopulation);
		AlgoritmoAG.bestFinal = AlgoritmoAG.finalPopulation.getFittestChromosome();
		AlgoritmoAG.bestFitNess = this.getBestFitness();
		AlgoritmoAG.FINAL_TIME = System.currentTimeMillis();
	}

	/**
	 * Obtiene la población inicial.
	 *
	 * @return la población inicial
	 */
	public Population getInitialPopulation() {
		return initialPopulation;
	}

	/**
	 * Obtiene el mejor cromosoma de la población final.
	 *
	 * @return el mejor cromosoma
	 */
	protected Chromosome getBestChromosome() {
		return bestFinal;
	}
	
	/**
	 * Obtiene el mejor cromosoma como AChromosome.
	 *
	 * @return el mejor cromosoma tipado
	 */
	@SuppressWarnings("unchecked")
	public AChromosome<V,?,S> getBestAChromosome() {
		return (AChromosome<V,?,S>)bestFinal;
	}
	
	/**
	 * Obtiene el mejor valor de fitness.
	 *
	 * @return el mejor fitness
	 */
	public Double getBestFitness() {
		return bestFinal.fitness();
	}

	/**
	 * Obtiene la lista de mejores cromosomas de cada generación.
	 *
	 * @return lista de mejores cromosomas
	 */
	protected List<Chromosome> getBestChromosomes(){
		return bestChromosomes.stream()
				.collect(Collectors.toList());
	}
	
	/**
	 * Obtiene la lista de mejores cromosomas como AChromosome.
	 *
	 * @return lista de mejores cromosomas tipados
	 */
	@SuppressWarnings("unchecked")
	public List<AChromosome<V, ?, S>> getBestAChromosomes(){
		return bestChromosomes.stream()
				.map(c->(AChromosome<V,?,S>)c)
				.collect(Collectors.toList());
	}

	/**
	 * Obtiene la población final.
	 *
	 * @return la población final tras la evolución
	 */
	public Population getFinalPopulation() {
		return finalPopulation;
	}	
	
	/**
	 * Obtiene la mejor solución encontrada.
	 *
	 * @return la solución correspondiente al mejor cromosoma
	 */
	public S bestSolution() {
		V d = this.getBestAChromosome().decode();
		return this.data.solution(d);
	}
	
	/**
	 * Obtiene el conjunto de mejores soluciones encontradas.
	 *
	 * @return conjunto de soluciones de los mejores cromosomas
	 */
	@SuppressWarnings("unchecked")
	public Set<S> bestSolutions() {
		return this.getBestChromosomes().stream()
				.<S>map(c->this.data.solution(((AChromosome<V,?,S>)c).decode(c)))
				.collect(Collectors.toSet());
	} 
	
	/**
	 * Obtiene la condición de parada utilizada.
	 *
	 * @return la condición de parada
	 */
	public StoppingCondition stoppingCondition() {
		return this.stopCond;
	}
	
	/**
	 * Obtiene el tiempo de ejecución del algoritmo.
	 *
	 * @return tiempo en milisegundos
	 */
	public static Long time() {
		return (AlgoritmoAG.FINAL_TIME - AlgoritmoAG.INITIAL_TIME);
	}

}
