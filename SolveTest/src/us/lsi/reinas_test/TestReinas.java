package us.lsi.reinas_test;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.String2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.mochila_test.DataMochila;
import us.lsi.solve_test.AuxGrammar;

/**
 * TestReinas
 *
 * <p>Clase de prueba para el problema de las N-Reinas usando
 * programación lineal entera con Gurobi. Genera y resuelve modelos
 * PLI para colocar N reinas en un tablero de ajedrez sin que se ataquen.</p>
 *
 * <p>El problema de las N-Reinas consiste en colocar N reinas en un
 * tablero de NxN de forma que ninguna reina ataque a otra. Es un
 * problema clásico de optimización combinatoria.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see us.lsi.gurobi.GurobiLp
 */
public class TestReinas {
	
	/**
	 * Resuelve el problema de las N-Reinas usando Gurobi.
	 *
	 * @param file ruta al archivo .lsi con el modelo
	 * @throws IOException si hay error al leer/escribir archivos
	 */
	public static void reinas(String file) throws IOException {
		AuxGrammar.generate(DataMochila.class,file,"ficheros/reinas.lp");
		GurobiSolution s = GurobiLp.solveSolution("ficheros/reinas.lp");
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",s.objVal));
		System.out.println("\n\n");
		System.out.println(s.values.keySet()
				.stream()
				.filter(e->!e.contains("$"))
//				.filter(e->s.values.get(e)>0)
				.map(e->String.format("%s == %.0f == %.1f == %.0f",e,s.values.get(e),s.values.get(e)+1,s.values.get(e)-1))
				.collect(Collectors.joining("\n")));
	}
	
	/**
	 * Método principal de prueba.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 * @throws IOException si hay error en el procesamiento
	 */
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.of("en", "US"));
		Long a = System.nanoTime();
		reinas("ficheros/reinas_2.lsi");
		Long b = System.nanoTime();
		String2.toConsole("%d",b-a);
	}

}
