package tests;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.SimpleWeightedGraph;

import ejercicio4.Calle;
import ejercicio4.Ejercicio4;
import ejercicio4.Interseccion;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio4 {

	public static void main(String[] args) {
		testsEjercicio4ApA();
		testsEjercicio4ApB();
		testsEjercicio4ApC();
	}
	
    static String file = "ficheros\\PI2E4_DatosEntrada.txt";
    static SimpleWeightedGraph<Interseccion, Calle> g =
            GraphsReader.newGraph(file,
                    Interseccion::ofFormat,
                    Calle::ofFormat,
                    Graphs2::simpleWeightedGraph);

	
	public static void testsEjercicio4ApA() {
	    System.out.println("");
	    System.out.println("PI2 - Ejercicio 4 - Apartado A");
	    System.out.println("");
	    var res = Ejercicio4.getShortestPathBetweenMonument_EJ4A("m1", "m7", g, "EJ4A.gv");
	    System.out.println(res.toString());
	}
	
	public static void testsEjercicio4ApB() {
	    System.out.println("");
	    System.out.println("PI2 - Ejercicio 4 - Apartado B");
	    System.out.println("");
	    var res = Ejercicio4.getRecorrido_E4B(g);
	    System.out.println(res.toString());
	}
	
	public static void testsEjercicio4ApC() {
	    System.out.println("");
	    System.out.println("PI2 - Ejercicio 4 - Apartado C");
	    System.out.println("");

	    Set<Calle> cortadas = new HashSet<>();
	    System.out.println(g.edgeSet());
	    for (Calle c : g.edgeSet()) {
	    	if (c.getId() == 0 || c.getId() == 3 || c.getId() == 6 || c.getId() == 5 || c.getId() == 0) {
	    		cortadas.add(c);
	    	}
	    }
	    var res = Ejercicio4.getRecorridoMaxRelevante_E4C(cortadas, g, "EJ4C.gv");
	    System.out.println(res.toString());
	}
}