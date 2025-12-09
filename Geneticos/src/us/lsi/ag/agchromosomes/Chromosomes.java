package us.lsi.ag.agchromosomes;


import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.BlocksData;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.ExpressionData;
import us.lsi.ag.InSetData;
import us.lsi.ag.PermutationData;
import us.lsi.ag.RangeDoubleData;
import us.lsi.ag.RangeIntegerData;
import us.lsi.tiposrecursivos.ast.Exp;

/**
 * Chromosomes
 *
 * <p>Clase factoría para crear cromosomas de diferentes tipos en algoritmos
 * genéticos. Proporciona métodos estáticos para instanciar cromosomas según
 * el tipo de datos del problema.</p>
 *
 * <p>Soporta múltiples tipos de cromosomas:
 * <ul>
 *   <li><b>Binary</b>: Cromosomas binarios (0s y 1s)</li>
 *   <li><b>RangeInteger</b>: Valores enteros en rangos específicos</li>
 *   <li><b>RangeDouble</b>: Valores reales en rangos específicos</li>
 *   <li><b>InSet</b>: Valores de conjuntos específicos por posición</li>
 *   <li><b>Permutation</b>: Permutaciones completas</li>
 *   <li><b>PermutationSubList</b>: Sublistas de permutaciones</li>
 *   <li><b>PermutationPrefix</b>: Prefijos de permutaciones</li>
 *   <li><b>Blocks</b>: Cromosomas por bloques</li>
 *   <li><b>Expression</b>: Expresiones matemáticas</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * RangeIntegerData<Solucion> data = RangeIntegerData.of(...);
 * AChromosome<List<Integer>, List<Double>, Solucion> cromosoma = 
 *     Chromosomes.of(data);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 * @see AChromosome
 */
public class Chromosomes {
	
	/**
	 * Enumeración de los tipos de cromosomas disponibles.
	 */
	public enum ChromosomeType {Binary,RangeInteger,RangeDouble,InSet,Permutation,PermutationSubList,PermutationPrefix,Blocks,Expression}
	
	/**
	 * Crea un cromosoma del tipo apropiado según los datos del problema.
	 *
	 * <p>Método factoría principal que determina el tipo de cromosoma
	 * basándose en el tipo declarado en los datos del problema y delega
	 * en el método específico correspondiente.</p>
	 *
	 * @param <V> tipo de los valores decodificados
	 * @param <G> tipo de los genes del cromosoma
	 * @param <S> tipo de la solución
	 * @param cd datos del problema con el tipo de cromosoma especificado
	 * @return un cromosoma inicializado del tipo apropiado
	 */
	@SuppressWarnings("unchecked")
	public static <V,G,S> AChromosome<V, G, S> of(ChromosomeData<V,S> cd) {
		ChromosomeType type = cd.type();
		return switch(type) {
		case Binary -> ((AChromosome<V, G, S>) ofBinary((BinaryData<S>)cd));
		case RangeInteger ->(AChromosome<V, G, S>) ofRangeInteger((RangeIntegerData<S>)cd);
		case RangeDouble -> (AChromosome<V, G, S>) ofRangeDouble((RangeDoubleData<S>)cd);
		case InSet -> (AChromosome<V, G, S>) ofInset((InSetData<S>)cd);
		case Permutation ->  (AChromosome<V, G, S>) ofPermutation((PermutationData<S>)cd);
		case PermutationSubList ->  (AChromosome<V, G, S>) ofPermutationSubList((PermutationData<S>)cd);
		case PermutationPrefix -> (AChromosome<V, G, S>) ofPermutationPrefix((PermutationData<S>)cd);
		case Blocks ->  (AChromosome<V, G, S>) ofBlocks((BlocksData<S>)cd);
		case Expression -> (AChromosome<V, G, S>) ofExp((ExpressionData)cd);
		};
	}
	
	/**
	 * Crea un cromosoma binario.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema binario
	 * @return un cromosoma binario inicializado
	 */
	public static <S> AChromosome<List<Integer>,List<Integer>,S> ofBinary(BinaryData<S> cd) {
		BinaryValues<S> rv = BinaryValues.of(cd);
		ABinaryChromosome.iniValues(rv);
		return ABinaryChromosome.getInitialChromosome();	
	}
	
	/**
	 * Crea un cromosoma de rangos enteros.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema de rangos enteros
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Integer>,List<Double>,S> ofRangeInteger(RangeIntegerData<S> cd) {
		RangeIntegerValues<S> rv = RangeIntegerValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();	
	}
	
	/**
	 * Crea un cromosoma de conjuntos de valores.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema de conjuntos
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Integer>,List<Double>,S> ofInset(InSetData<S> cd) {
		InSetValues<S> rv = InSetValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();	
	}
	
	/**
	 * Crea un cromosoma de rangos reales.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema de rangos reales
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Double>,List<Double>,S> ofRangeDouble(RangeDoubleData<S> cd) {
		RangeDoubleValues<S> rv = RangeDoubleValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();	
	}
	
	/**
	 * Crea un cromosoma de permutación completa.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema de permutación
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofPermutation(PermutationData<S> cd) {
		PermutationValues<S> rv = PermutationValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	/**
	 * Crea un cromosoma de sublista de permutación.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema de permutación
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofPermutationSubList(PermutationData<S> cd) {
		PermutationSubListValues<S> rv = PermutationSubListValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	/**
	 * Crea un cromosoma de prefijo de permutación.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema de permutación
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofPermutationPrefix(PermutationData<S> cd) {
		PermutationPrefixValues<S> rv = PermutationPrefixValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	/**
	 * Crea un cromosoma por bloques.
	 *
	 * @param <S> tipo de la solución
	 * @param cd datos del problema por bloques
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofBlocks(BlocksData<S> cd) {
		BlocksValues<S> rv = BlocksValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	/**
	 * Crea un cromosoma de expresión matemática.
	 *
	 * @param cd datos del problema de expresiones
	 * @return un cromosoma de claves aleatorias inicializado
	 */
	public static AChromosome<Exp, List<Double>, Exp> ofExp(ExpressionData cd) {
//		AuxExpression.iniValues(cd);
		ExpressionValues rv = ExpressionValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	

}
