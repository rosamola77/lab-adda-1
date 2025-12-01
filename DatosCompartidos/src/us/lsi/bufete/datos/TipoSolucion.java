package us.lsi.bufete.datos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TipoSolucion
 *
 * <p>Clase base abstracta para representar soluciones a problemas de optimización.
 * Proporciona funcionalidad común para mostrar soluciones por consola.</p>
 *
 * <p>Las subclases deben implementar {@link #toString()} para proporcionar
 * una representación específica de la solución.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SolucionBufete
 */
public class TipoSolucion {
	
	/**
	 * Constructor que crea una solución a partir de una lista de valores.
	 *
	 * @param ls lista de valores de la solución
	 */
	protected TipoSolucion(List<Integer> ls) {
		super();
	}

	/**
	 * Constructor que crea una solución a partir de variables de optimización.
	 *
	 * @param vo valor objetivo de la solución
	 * @param vbles mapa de variables y sus valores
	 */
	protected TipoSolucion(Double vo, Map<String, Double> vbles) {
		super();
	}

	/**
	 * Muestra la solución por consola con decoración.
	 */
	public void toConsole() {
		pintarLinea();
		System.out.println(toString());
		pintarLinea();
	}
	
	/**
	 * Imprime una línea decorativa en la consola.
	 */
	private static void pintarLinea() {
		System.out.println(IntStream.range(0, 10).mapToObj(i->"o").collect(Collectors.joining("~~~~�~~~~", "\n", "\n")));		
	}	
}

