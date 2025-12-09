package us.lsi.ag.agstopping;

import org.apache.commons.math3.genetics.FixedElapsedTime;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.StoppingCondition;

/**
 * StoppingConditionFactory
 *
 * <p>Factoría para crear condiciones de parada en algoritmos genéticos.
 * Proporciona diferentes tipos de condiciones de parada configurables
 * para controlar cuándo finaliza la evolución de las poblaciones.</p>
 *
 * <p>Tipos de condiciones de parada disponibles:
 * <ul>
 *   <li><b>ElapsedTime</b>: Finaliza cuando transcurre un tiempo determinado</li>
 *   <li><b>GenerationCount</b>: Finaliza tras un número específico de generaciones</li>
 *   <li><b>SolutionsNumber</b>: Finaliza cuando se encuentran suficientes soluciones
 *       que cumplen un criterio de calidad mínimo (fitness)</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
 * StoppingConditionFactory.NUM_GENERATIONS = 100;
 * StoppingCondition condition = StoppingConditionFactory.getStoppingCondition();
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SolutionsNumber
 */
public class StoppingConditionFactory {

	/**
	 * <p> Distintos tipos de condiciones de parada </p>
	 * 
	 * <ul>
	 * <li> ElapsedTime: Para cuando el tiempo transcurrido se el especificado en <code> elapsedTime </code>.
	 * <li> GenerationCount: Para cuando el número de generaciones sea igual al especificado en <code> NUM_GENERATIONS </code>
	 * <li> SolutionsNumber: Para cuando en una generación encuentra al menos SOLUTIONS_NUMBER de cromososmas 
	 * con <code> fitness</code>  igual o mayor <code> FITNESS </code> o <code> NUM_GENERATIONS </code> ha sido superado.
	 * </ul> 
	 *
	 */
	public enum StoppingConditionType{ElapsedTime,GenerationCount,SolutionsNumber};
	
	/**
	 * Condición de parada
	 */
	public static StoppingConditionType stoppingConditionType = StoppingConditionType.SolutionsNumber;
	/**
	 * Número de soluciones a encontrar si fijamos el criterio de parada en SolutionsNumber
	 */
	public static int SOLUTIONS_NUMBER_MIN = 1;
	/**
	 * Tiempo máximo transcurrido para finalizar el algoritmo si usamos la condición de finalización ElapsedTime.
	 */
	public static long MAX_ELAPSEDTIME = 1000000000;
	
	/**
	 * Valor mínimo de la fitness de los cromosomas en las soluciones que vamos buscando si fijamos el criterio de parada en SolutionsNumber
	 */
	public static double FITNESS_MIN = 0.;	
	/**
	 * Número de generaciones máximo para fijar le criterio de parada
	 */
	public static int NUM_GENERATIONS = Integer.MAX_VALUE;
	
	/**
	 * Obtiene la condición de parada configurada según el tipo seleccionado.
	 *
	 * <p>Crea y devuelve una instancia de la condición de parada apropiada
	 * basándose en el valor de {@link #stoppingConditionType} y los parámetros
	 * configurados correspondientes.</p>
	 *
	 * @return la condición de parada configurada
	 */
	public static StoppingCondition getStoppingCondition(){
		return switch(stoppingConditionType){
		case ElapsedTime -> new FixedElapsedTime(StoppingConditionFactory.MAX_ELAPSEDTIME);
		case GenerationCount -> new FixedGenerationCount(StoppingConditionFactory.NUM_GENERATIONS);
		case SolutionsNumber -> 
		new SolutionsNumber(StoppingConditionFactory.MAX_ELAPSEDTIME,
				StoppingConditionFactory.NUM_GENERATIONS,
				StoppingConditionFactory.SOLUTIONS_NUMBER_MIN,
				StoppingConditionFactory.FITNESS_MIN);
		};
	}	
}
