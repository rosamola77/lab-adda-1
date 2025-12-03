package tests;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.SimpleWeightedGraph;

import ejercicio4.Calle;
import ejercicio4.Ejercicio4;
import ejercicio4.Interseccion;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

/**
 * Clase de pruebas para validar los métodos del {@link Ejercicio4}.
 *
 * <p>Esta clase contiene métodos de prueba para verificar el correcto funcionamiento
 * de las operaciones sobre grafos de intersecciones y calles:
 * <ul>
 *   <li>Prueba del cálculo del camino más corto entre monumentos.</li>
 *   <li>Prueba del cálculo del recorrido óptimo (TSP).</li>
 *   <li>Prueba del análisis de componentes conexas tras eliminar calles.</li>
 * </ul>
 *
 * <p>Las pruebas utilizan el fichero de datos {@code ficheros/PI2E4_DatosEntrada.txt}
 * para construir el grafo de prueba.</p>
 *
 * @author Álvaro Rosa
 * @version 1.0
 * @since 1.0
 * @see ejercicio4.Ejercicio4
 */
public class TestEjercicio4 {

	/**
	 * Método principal que ejecuta todas las pruebas del Ejercicio 4.
	 *
	 * <p>Ejecuta secuencialmente las pruebas de los apartados A, B y C.</p>
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		testsEjercicio4ApA();
		testsEjercicio4ApB();
		testsEjercicio4ApC();
	}
	
	/**
	 * Ruta al fichero de datos de entrada para las pruebas.
	 */
    static String file = "ficheros\\PI2E4_DatosEntrada.txt";
    
    /**
     * Grafo de prueba construido a partir del fichero de datos.
     * <p>Es un grafo simple ponderado con vértices {@code Interseccion} y aristas {@code Calle}.</p>
     */
    static SimpleWeightedGraph<Interseccion, Calle> g =
            GraphsReader.newGraph(file,
                    Interseccion::ofFormat,
                    Calle::ofFormat,
                    Graphs2::simpleWeightedGraph);

	/**
	 * Prueba del apartado A: Camino más corto entre monumentos.
	 *
	 * <p>Calcula y muestra el camino más corto entre los monumentos "m1" y "m7"
	 * utilizando el método {@link Ejercicio4#getShortestPathBetweenMonument_EJ4A}.</p>
	 *
	 * @see Ejercicio4#getShortestPathBetweenMonument_EJ4A(String, String, org.jgrapht.Graph, String)
	 */
	public static void testsEjercicio4ApA() {
	    System.out.println("");
	    System.out.println("PI2 - Ejercicio 4 - Apartado A");
	    System.out.println("");
	    var res = Ejercicio4.getShortestPathBetweenMonument_EJ4A("m1", "m7", g, "EJ4A.gv");
	    System.out.println(res.toString());
	}
	
	/**
	 * Prueba del apartado B: Recorrido óptimo (TSP).
	 *
	 * <p>Calcula y muestra el recorrido óptimo que visita todas las intersecciones
	 * minimizando el esfuerzo total, utilizando el método {@link Ejercicio4#getRecorrido_E4B}.</p>
	 *
	 * @see Ejercicio4#getRecorrido_E4B(org.jgrapht.Graph)
	 */
	public static void testsEjercicio4ApB() {
	    System.out.println("");
	    System.out.println("PI2 - Ejercicio 4 - Apartado B");
	    System.out.println("");
	    var res = Ejercicio4.getRecorrido_E4B(g);
	    System.out.println(res.toString());
	}
	
	/**
	 * Prueba del apartado C: Componente conexa más relevante.
	 *
	 * <p>Elimina un conjunto de calles del grafo y calcula la componente conexa
	 * con mayor relevancia de monumentos, utilizando el método 
	 * {@link Ejercicio4#getRecorridoMaxRelevante_E4C}.</p>
	 *
	 * <p>Las calles cortadas son aquellas con identificadores 0, 3, 5 y 6.</p>
	 *
	 * @see Ejercicio4#getRecorridoMaxRelevante_E4C(Set, org.jgrapht.Graph, String)
	 */
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