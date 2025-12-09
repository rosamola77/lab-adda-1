package tests;

import java.util.List;

import ejercicio1.Ejercicio1;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

/**
 * TestEjercicio1
 *
 * <p>Clase de prueba para el Ejercicio 1 del PI2. Ejecuta casos de test
 * sobre árboles binarios y n-arios para verificar el cálculo del camino
 * que maximiza el producto de valores.</p>
 *
 * <p>Lee los datos de entrada desde archivos y ejecuta las soluciones
 * recursivas sobre cada caso de prueba.</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 * @see ejercicio1.Ejercicio1
 */
public class TestEjercicio1 {	
	
	/**
	 * Lista de árboles binarios para las pruebas.
	 */
	private static List<BinaryTree<Integer>> inputs; 
	
	/**
	 * Lista de árboles n-arios para las pruebas.
	 */
	private static List<Tree<Integer>> inputs2; 

	/**
	 * Contador para numerar los tests.
	 */
	static int count = 0;
	
	/**
	 * Método principal que ejecuta todas las pruebas del Ejercicio 1.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {

		cargaDatos ();

		System.out.println("************************************************************");
		System.out.println("PI2 - Ejercicio 1");
		System.out.println("************************************************************");

		count = 0;
		System.out.println("\nSOLUCIÓN RECURSIVA BINARIA:\n");	
		inputs
		.stream()
		.forEach(x->System.out.println("Test" +(++count) + ": "+ x+": "+Ejercicio1.caminoMaximo(x)));
		
		count=0;
		System.out.println("\nSOLUCIÓN RECURSIVA NARIA:\n");	
		inputs2
		.stream()
		.forEach(x->System.out.println("Test" +(++count) + ": "+x+": "+Ejercicio1.caminoMaximo(x)));


	}

	/**
	 * Carga los datos de entrada desde archivos de texto.
	 *
	 * <p>Lee los árboles binarios y n-arios desde ficheros y los parsea
	 * para crear las estructuras de datos correspondientes.</p>
	 */
	private static void cargaDatos () {
		inputs = Files2
				.streamFromFile("ficheros/PI2E1_DatosEntradaBinario.txt")
				.map(linea -> BinaryTree.parse(linea,s->Integer.parseInt(s)))
				.toList();
		
		inputs2 = Files2
				.streamFromFile("ficheros/PI2E1_DatosEntradaNario.txt")
				.map(linea -> Tree.parse(linea,s->Integer.parseInt(s)))
				.toList();
	}
	

}
