package tests;

import java.util.List;

import ejercicio1.Ejercicio1;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;


public class TestEjercicio1 {	
	
	private static List<BinaryTree<Integer>> inputs; 
	private static List<Tree<Integer>> inputs2; 

	static int count = 0;
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
