package us.lsi.gurobi.antlr;

import java.io.IOException;

import us.lsi.mochila_test.DataMochila;
import us.lsi.solve_test.AuxGrammar;
;

/**
 * AntLr
 *
 * <p>Clase de prueba para generación de modelos de programación lineal
 * mediante ANTLR. Utiliza AuxGrammar para generar archivos .lp desde
 * archivos .lsi (formato de entrada del modelo).</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * AntLr.test("prueba_0"); // Genera prueba_0.lp desde prueba_0.lsi
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see us.lsi.solve_test.AuxGrammar
 */
public class AntLr {
	
	/**
	 * Ejecuta una prueba de generación de modelo LP.
	 *
	 * @param file nombre base del archivo (sin extensión)
	 * @throws IOException si hay error al leer/escribir archivos
	 */
	public static void test(String file) throws IOException {
		AuxGrammar.generate(DataMochila.class,"ficheros/"+file+".lsi","ficheros/"+file+".lp");	
	}
	
	/**
	 * Método principal de prueba.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 * @throws IOException si hay error en la generación
	 */
	public static void main(String[] args) throws IOException {
			test("prueba_0");
	}

}
