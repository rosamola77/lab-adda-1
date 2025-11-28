package tests;

import ejercicio3.Colaboracion;
import ejercicio3.Ejercicio3;
import ejercicio3.Investigador;
import org.jgrapht.graph.SimpleGraph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio3 {

	public static void main(String[] args) {
		testsEjercicio3Ap4();
	}

	public static void testsEjercicio3Ap3() {
		
		String file = "ficheros\\PI2E3_DatosEntrada.txt";
		SimpleGraph<Investigador, Colaboracion> g = 
				GraphsReader.newGraph(file,
						Investigador::ofFormat,
						Colaboracion::ofFormat,
						Graphs2::simpleGraph);
		
		System.out.println("************************************************************");
		System.out.println("PI2 - Ejercicio 3 - Apartado C");
		System.out.println("************************************************************");
		
		System.out.println(Ejercicio3.getMapListaColabroradores_E3C(g));

	}
	
	public static void testsEjercicio3Ap4() {
		
		String file = "ficheros\\PI2E3_DatosEntrada.txt";
		SimpleGraph<Investigador, Colaboracion> g = 
				GraphsReader.newGraph(file,
						Investigador::ofFormat,
						Colaboracion::ofFormat,
						Graphs2::simpleGraph);
		
		System.out.println("************************************************************");
		System.out.println("PI2 - Ejercicio 3 - Apartado D");
		System.out.println("************************************************************");
		
		System.out.println(Ejercicio3.getParMasLejano_E3D(g));

	}

}
