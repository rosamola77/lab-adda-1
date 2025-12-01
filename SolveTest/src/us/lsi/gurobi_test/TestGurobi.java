package us.lsi.gurobi_test;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

/**
 * TestGurobi
 *
 * <p>Clase de pruebas para verificar la integración con el solver Gurobi.
 * Proporciona métodos para resolver modelos LP y mostrar los resultados
 * por consola.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * TestGurobi.test("modelo.lp");
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see GurobiLp
 * @see GurobiSolution
 */
public class TestGurobi {
	
	/**
	 * Resuelve un modelo LP y muestra los resultados por consola.
	 *
	 * <p>Muestra el valor objetivo y los valores de todas las variables,
	 * incluyendo valores desplazados para verificación.</p>
	 *
	 * @param file ruta del fichero con el modelo en formato LP
	 */
	public static void test(String file) {
		Locale.setDefault(Locale.of("en", "US"));
		Optional<GurobiSolution> solution = GurobiLp.gurobi(file);
		if (solution.isPresent()) {
			GurobiSolution sl = solution.get();
			Locale.setDefault(Locale.of("en", "US"));
			System.out.println(sl.toString((s, d) -> d > 0.));

			System.out.println("\n\n\n\n");
			System.out.println(String.format("Objetivo : %.2f", sl.objVal));
			System.out.println("\n\n");
			System.out.println(sl.values.keySet().stream()
					.sorted().map(e -> String.format("%s == %.1f, %.1f, %.1f", 
							e, sl.values.get(e),
							sl.values.get(e) + 1, sl.values.get(e) - 1))
					.collect(Collectors.joining("\n")));
		} else {
			System.out.println("\n\n*****Modelo sin solución****");
		}
	}

	/**
	 * Método principal para ejecutar las pruebas.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		test("ficheros/gurobi.lp");
	}

}
