package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.RandomKey;
import org.apache.commons.math3.genetics.RandomKeyMutation;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agchromosomes.ACrossOverPolicy.CrossoverType;

/**
 * ARandomKey
 *
 * <p>Implementación de cromosoma basada en claves aleatorias (Random Key).
 * Esta técnica utiliza una lista de valores reales en [0,1) que se
 * decodifican según el tipo de problema.</p>
 *
 * <p>Las claves aleatorias son muy versátiles y se utilizan para representar:
 * <ul>
 *   <li>Permutaciones (ordenando por los valores de las claves)</li>
 *   <li>Valores en rangos (escalando las claves a los rangos deseados)</li>
 *   <li>Selecciones de conjuntos (mapeando claves a valores discretos)</li>
 *   <li>Estructuras por bloques</li>
 * </ul>
 * </p>
 *
 * <p>Esta implementación extiende {@link RandomKey} de Apache Commons Math
 * y añade funcionalidad específica para problemas de optimización.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * RangeIntegerData<Solucion> data = RangeIntegerData.of(...);
 * RangeIntegerValues<Solucion> values = RangeIntegerValues.of(data);
 * ARandomKey.iniValues(values);
 * ARandomKey<List<Integer>, Solucion> cromosoma = ARandomKey.getInitialChromosome();
 * }</p>
 *
 * @param <V> tipo de los valores decodificados del cromosoma
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RandomKey
 * @see ChromosomeValues
 * @see AChromosome
 */
public class ARandomKey<V,S> extends RandomKey<Object> implements AChromosome<V,List<Double>,S> {

	/**
	 * Valores del cromosoma que definen la decodificación.
	 */
	private static ChromosomeValues<Object,List<Double>,Object> values = null;
	
	/**
	 * Dimensión del cromosoma (número de genes).
	 */
	private static Integer DIMENSION = null;
	
	/**
	 * Datos del problema asociados al cromosoma.
	 */
	private static ChromosomeData<Object,Object> data = null;
	
	/**
	 * Inicializa los valores estáticos del cromosoma.
	 *
	 * <p>Este método debe llamarse antes de crear cualquier instancia
	 * de {@code ARandomKey} para configurar la decodificación y los
	 * datos del problema.</p>
	 *
	 * @param <V> tipo de valores decodificados
	 * @param <G> tipo de genes
	 * @param <S> tipo de solución
	 * @param values valores de cromosoma que definen la decodificación
	 */
	@SuppressWarnings("unchecked")
	public static <V,G,S> void iniValues(ChromosomeValues<V,List<Double>,S> values){
		ARandomKey.values = (ChromosomeValues<Object,List<Double>,Object>) values; 
		ARandomKey.DIMENSION = values.dimension();
		ARandomKey.data = (ChromosomeData<Object, Object>) values.data();
	}
	
	/**
	 * Genera un cromosoma inicial aleatorio.
	 *
	 * @param <E> tipo de valores decodificados
	 * @param <S> tipo de solución
	 * @return un nuevo cromosoma con claves aleatorias
	 */
	public static <E,S> ARandomKey<E,S> getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(ARandomKey.DIMENSION);
		return new ARandomKey<>(ls);
	}
	
	/**
	 * Constructor que crea un cromosoma a partir de un array de valores.
	 *
	 * @param representation array de valores reales
	 * @throws InvalidRepresentationException si la representación no es válida
	 */
	public ARandomKey(Double[] representation) throws InvalidRepresentationException {
		super(representation);
	}
	
	/**
	 * Constructor que crea un cromosoma a partir de una lista de valores.
	 *
	 * @param representation lista de valores reales
	 * @throws InvalidRepresentationException si la representación no es válida
	 */
	public ARandomKey(List<Double> representation) throws InvalidRepresentationException {
		super(representation);
	}
	
	/**
	 * Crea un nuevo cromosoma con la longitud fija especificada.
	 *
	 * @param ls lista de valores reales
	 * @return un nuevo cromosoma de claves aleatorias
	 */
	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new ARandomKey<>(ls);
	}
	
	/**
	 * Calcula el fitness del cromosoma.
	 *
	 * <p>Decodifica el cromosoma y evalúa su calidad usando la
	 * función de fitness del problema.</p>
	 *
	 * @return el valor de fitness (mayor es mejor)
	 */
	@Override
	public double fitness() {
		V d = this.decode();
		return ARandomKey.data.fitnessFunction(d);
	}
	
	/**
	 * Decodifica el cromosoma en valores del dominio del problema.
	 *
	 * @return los valores decodificados
	 */
	@SuppressWarnings("unchecked")
	public V decode() {
		List<Double> ls = super.getRepresentation();
		return (V) values.decodeValues(ls);
	}
	
	/**
	 * Convierte el cromosoma en una solución del problema.
	 *
	 * @return la solución representada por este cromosoma
	 */
	@SuppressWarnings("unchecked")
	@Override
	public S solution() {
		return (S) data.solution(this.decode());
	}
	
	/**
	 * Tipo de operador de cruce a utilizar.
	 */
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	/**
	 * Obtiene la política de cruce para claves aleatorias.
	 *
	 * @return la política de cruce configurada
	 */
	public CrossoverPolicy crossOverPolicy() {
		return ACrossOverPolicy.getCrossoverPolicyKey(crossoverType);
	}
	
	/**
	 * Obtiene la política de mutación para claves aleatorias.
	 *
	 * @return una política de mutación de claves aleatorias
	 */
	public MutationPolicy mutationPolicy() {
		return new RandomKeyMutation();
	}
	
	/**
	 * Aridad del torneo para la selección (número de competidores).
	 */
	public static int TOURNAMENT_ARITY = 2;
	
	/**
	 * Obtiene la política de selección por torneo.
	 *
	 * @return una política de selección por torneo
	 */
	public SelectionPolicy selectionPolicy() {
		return new TournamentSelection(TOURNAMENT_ARITY);
	}
	
	/**
	 * Genera un cromosoma inicial.
	 *
	 * @return un cromosoma inicial aleatorio
	 */
	@Override
	public Chromosome initialChromosome() {
		return ARandomKey.getInitialChromosome();
	}
	
	/**
	 * Decodifica una lista de genes en valores del dominio.
	 *
	 * @param g lista de genes (claves aleatorias)
	 * @return los valores decodificados
	 */
	@SuppressWarnings("unchecked")
	public V decodeValues(List<Double> g) {
		return (V) values.decodeValues(g);
	}
	
	/**
	 * Decodifica un cromosoma genérico.
	 *
	 * @param cr el cromosoma a decodificar
	 * @return los valores decodificados
	 */
	public V decode(Chromosome cr) {
		return this.decode();
	}

	/**
	 * Obtiene la dimensión del cromosoma.
	 *
	 * @return el número de genes
	 */
	@Override
	public Integer dimension() {
		return DIMENSION;
	}
	
	/**
	 * Obtiene los datos del problema.
	 *
	 * @return los datos del problema
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ChromosomeData<V, S> data() {
		return (ChromosomeData<V, S>) data;
	}

	/**
	 * Método principal para pruebas.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		// Método para pruebas
	}

	
}
