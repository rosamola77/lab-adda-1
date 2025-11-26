package tests;

import java.util.List;

import ejercicio2.Ejercicio2;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio2 {

	public static void main(String[] args) {
		testsEjercicio2Binario();
		testsEjercicio2Nario();

	}


	public static int count = 0;
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
