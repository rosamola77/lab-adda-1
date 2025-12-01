package us.lsi.gurobi;

import java.util.Comparator;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * GurobiSolution
 *
 * <p>Representa la solución de un modelo de optimización resuelto
 * con Gurobi. Contiene el valor objetivo óptimo y los valores
 * de todas las variables de decisión.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * GurobiSolution sol = GurobiLp.solveSolution("modelo.lp");
 * Double objetivo = sol.objVal;
 * Double valorX = sol.values.get("x_1");
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see GurobiLp
 */
public class GurobiSolution {

	/**
	 * Crea una nueva solución con el valor objetivo y valores de variables.
	 *
	 * @param objVal valor objetivo óptimo
	 * @param values mapa de nombre de variable a valor
	 * @return una nueva GurobiSolution
	 */
	public static GurobiSolution of(Double objVal, Map<String, Double> values) {
		return new GurobiSolution(objVal, values);
	}

	/** Valor objetivo óptimo de la solución. */
	public Double objVal;
	
	/** Mapa de nombres de variables a sus valores óptimos. */
	public Map<String,Double> values;
	
	/**
	 * Constructor privado de la solución.
	 *
	 * @param objVal valor objetivo
	 * @param values mapa de valores de variables
	 */
	private GurobiSolution(Double objVal, Map<String, Double> values) {
		super();
		this.objVal = objVal;
		this.values = values;
	}
	
	/**
	 * Devuelve una representación filtrada de la solución.
	 *
	 * <p>Solo muestra las variables que cumplen el predicado dado.</p>
	 *
	 * @param pd predicado que recibe el nombre y valor de la variable
	 * @return representación textual filtrada
	 */
	public String toString(BiPredicate<String,Double> pd) {
		return String.format("\n\n\nEl valor objetivo es %.2f\nLos valores de la variables\n%s",this.objVal,
				this.values.entrySet()
				.stream()
				.filter(e->pd.test(e.getKey(),e.getValue()))
				.sorted(Comparator.comparing(e->e.getKey()))
				.map(e->String.format("%s == %d",e.getKey(),e.getValue().intValue()))
				.collect(Collectors.joining("\n")));
	}
	
	/**
	 * Genera una cadena con todos los valores de las variables.
	 *
	 * @return cadena con todos los valores
	 */
	private String allValues() {
		return this.values.entrySet()
				.stream()
				.map(e->String.format("%s == %.1f",e.getKey(),e.getValue()))
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Devuelve una representación completa de la solución.
	 *
	 * @return representación textual con objetivo y todos los valores
	 */
	@Override
	public String toString() {
		return String.format("\n\n\nEl valor objetivo es %.2f\nLos valores de la variables\n%s",
				this.objVal,this.allValues());
	}
	
}
