package us.lsi.ag;


import java.util.List;

import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Operator;
import us.lsi.tiposrecursivos.ast.Type;

/**
 * ExpressionData
 *
 * <p>Interfaz que define los datos para cromosomas que representan
 * expresiones matemáticas en algoritmos genéticos basados en
 * programación de expresiones de genes (GEP).</p>
 *
 * <p>Permite evolucionar expresiones matemáticas combinando operadores,
 * variables y constantes.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 */
public interface ExpressionData extends ChromosomeData<Exp,Exp>{
	
	/**
	 * Obtiene la longitud de la cabeza de un gen.
	 *
	 * @return la longitud de la cabeza
	 */
	Integer headLength();
	
	/**
	 * Obtiene el número de genes en el cromosoma.
	 *
	 * @return número de genes
	 */
	Integer numGenes();
	
	/**
	 * Obtiene el número de variables disponibles.
	 *
	 * @return número de variables
	 */
	Integer numVariables();
	
	/**
	 * Obtiene el número de constantes disponibles.
	 *
	 * @return número de constantes
	 */
	Integer numConstants();
	
	/**
	 * Obtiene el rango máximo del valor de cada constante.
	 *
	 * <p>Cada constante tendrá un valor en el rango {@code 0..maxValueConstant()-1}.</p>
	 *
	 * @return el valor máximo de las constantes
	 */
	Integer maxValueConstant();
	
	/**
	 * Obtiene el tipo de las constantes.
	 *
	 * @return el tipo de las constantes
	 */
	Type constType();
	
	/**
	 * Obtiene la lista de operadores disponibles para construir expresiones.
	 *
	 * @return lista de operadores
	 */
	List<Operator> operators();	
	
	/**
	 * Obtiene el operador n-ario para combinar los resultados de los genes.
	 *
	 * @return el operador n-ario de combinación
	 */
	Operator.Nary nAryOperator();
	
	/**
	 * Calcula la aridad máxima de los operadores disponibles.
	 *
	 * @return la aridad máxima
	 */
	default Integer maxArity() {
		return operators().stream().mapToInt(x ->x.operatorId().arity()).max().getAsInt();
	}
	
	/**
	 * Calcula la longitud de la cola de un gen.
	 *
	 * <p>Se calcula como: {@code headLength() * (maxArity() - 1) + 1}</p>
	 *
	 * @return la longitud de la cola
	 */
	default Integer tailLength() {
		return headLength() * (maxArity() - 1) + 1;
	}
	
	/**
	 * Calcula el número de items por gen.
	 *
	 * @return la suma de cabeza y cola
	 */
	default Integer numItemsPorGen() {
		return headLength() + tailLength();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>El tamaño total es: {@code numItemsPorGen() * numGenes() + numConstants()}</p>
	 */
	default Integer size() {
		return this.numItemsPorGen()*this.numGenes() + this.numConstants();
	}
	
}
