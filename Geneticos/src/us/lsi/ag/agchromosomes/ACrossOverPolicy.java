package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.commons.math3.genetics.NPointCrossover;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.UniformCrossover;

/**
 * ACrossOverPolicy
 *
 * <p>Factoría para crear políticas de cruce (crossover) en algoritmos genéticos.
 * Proporciona diferentes estrategias de cruce para combinar cromosomas durante
 * la reproducción en el proceso evolutivo.</p>
 *
 * <p>Tipos de operadores de cruce disponibles:
 * <ul>
 *   <li><b>Cycle</b>: Cruce cíclico que preserva la posición relativa de los genes</li>
 *   <li><b>NPoint</b>: Cruce en N puntos configurables</li>
 *   <li><b>OnePoint</b>: Cruce en un único punto</li>
 *   <li><b>Ordered</b>: Cruce ordenado que preserva el orden de los elementos</li>
 *   <li><b>Uniform</b>: Cruce uniforme con probabilidad configurable</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * ACrossOverPolicy.crossoverType = CrossoverType.OnePoint;
 * CrossoverPolicy policy = ACrossOverPolicy.getCrossoverPolicyBin(CrossoverType.OnePoint);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class ACrossOverPolicy {
	
	/**
	 * Enumeración de los tipos de operadores de cruce disponibles.
	 */
	public enum CrossoverType{Cycle,NPoint,OnePoint,Ordered,Uniform};
	
	/**
	 * Tipo del operador de cruce
	 */
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	/**
	 * Número de puntos usados en la partición si se usa un operador de cruce de tipo NPointCrossover
	 */
	public static int NPOINTCROSSOVER = 3;
	/**
	 * La ratio si se usa el operador de cruce de tipo UniformCrossover
	 */
	public static double RATIO_UNIFORMCROSSOVER = 0.7;
	
	/**
	 * Obtiene una política de cruce para cromosomas binarios.
	 *
	 * @param tipo el tipo de operador de cruce deseado
	 * @return un operador de cruce adecuado para cromosomas de tipo {@code Integer}
	 */
	public static CrossoverPolicy getCrossoverPolicyBin(CrossoverType tipo){
		CrossoverPolicy crossOverPolicy = null;	
		switch(tipo){
		case Cycle: crossOverPolicy = new CycleCrossover<Integer>();break;
		case NPoint: crossOverPolicy = new NPointCrossover<Integer>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicy = new OnePointCrossover<Integer>();break;
		case Ordered: crossOverPolicy = new OrderedCrossover<Integer>(); break;
		case Uniform: crossOverPolicy = new UniformCrossover<Integer>(RATIO_UNIFORMCROSSOVER); break;
		}
		return crossOverPolicy;
	}
	
	/**
	 * Obtiene una política de cruce para cromosomas de claves aleatorias (RandomKey).
	 *
	 * @param tipo el tipo de operador de cruce deseado
	 * @return un operador de cruce adecuado para cromosomas de tipo {@code Double}
	 */
	public static CrossoverPolicy getCrossoverPolicyKey(CrossoverType tipo) {
		CrossoverPolicy crossOverPolicyKey = null;
		switch (crossoverType) {
		case Cycle: crossOverPolicyKey = new CycleCrossover<Double>(); break;
		case NPoint: crossOverPolicyKey = new NPointCrossover<Double>(NPOINTCROSSOVER); break;
		case OnePoint: crossOverPolicyKey = new OnePointCrossover<Double>(); break;
		case Ordered: crossOverPolicyKey = new OrderedCrossover<Double>(); break;
		case Uniform: crossOverPolicyKey = new UniformCrossover<Double>(RATIO_UNIFORMCROSSOVER); break;
		}
		return crossOverPolicyKey;
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
