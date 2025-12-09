package tests;

import java.util.List;

import ejercicio2.Ejercicio2;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

/**
 * TestEjercicio2
 *
 * <p>Clase de prueba para el Ejercicio 2 del PI2. Ejecuta casos de test
 * sobre árboles binarios y n-arios para verificar la propiedad de
 * igualdad de vocales en subárboles.</p>
 *
 * <p>Lee los datos de entrada desde archivos y ejecuta las soluciones
 * recursivas sobre cada caso de prueba, mostrando si cada árbol
 * cumple o no la propiedad.</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 * @see ejercicio2.Ejercicio2
 */
public class TestEjercicio2 {

	/**
	 * Método principal que ejecuta todas las pruebas del Ejercicio 2.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		testsEjercicio2Binario();
		testsEjercicio2Nario();

	}


	/**
	 * Contador para numerar los tests.
	 */
	public static int count = 0;
	
	/**
	 * Ejecuta pruebas sobre árboles binarios.
	 *
	 * <p>Lee árboles binarios etiquetados con Strings desde fichero
	 * y verifica si cumplen la propiedad de igualdad de vocales en
	 * subárboles izquierdo y derecho.</p>
	 */
	public static void testsEjercicio2Binario() {
		
		String file = "ficheros/PI2E2_DatosEntradaBinario.txt";
		List<BinaryTree<String>> inputsBinary = 
				Files2.streamFromFile(file)
				.map(linea -> BinaryTree.parse(linea))
				.toList();
		
		System.out.println("************************************************************");
		System.out.println("PI2 - Ejercicio 2");
		System.out.println("************************************************************");
		
		System.out.println("\nSOLUCIÓN RECURSIVA BINARIA:\n");	

		count = 0;
		inputsBinary.stream()
			.forEach(x->System.out.println("Test" +(++count) + ": "+x+": "+Ejercicio2.solucion_recursiva(x)));			

	}
	
	/**
	 * Ejecuta pruebas sobre árboles n-arios.
	 *
	 * <p>Lee árboles n-arios etiquetados con Strings desde fichero
	 * y verifica si cumplen la propiedad de igualdad de vocales
	 * entre todos los hijos de cada nodo.</p>
	 */
	public static void testsEjercicio2Nario() {
		
		String file2 = "ficheros/PI2E2_DatosEntradaNario.txt";
		List<Tree<String>> inputsNary = 
				Files2.streamFromFile(file2)
				.map(linea -> Tree.parse(linea))
				.toList();
		
		
		System.out.println("\nSOLUCIÓN RECURSIVA NARIA:\n");	
		
		count = 0;
		inputsNary.stream()
		.forEach(x->System.out.println("Test" +(++count) + ": "+x+": "+Ejercicio2.solucion_recursiva(x)));	

	}
}
