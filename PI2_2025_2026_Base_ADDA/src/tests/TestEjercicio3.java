package tests;

import ejercicio3.Colaboracion; import ejercicio3.Ejercicio3; import ejercicio3.Investigador; import org.jgrapht.graph.SimpleGraph;

import us.lsi.graphs.Graphs2; import us.lsi.graphs.GraphsReader;

import java.util.List; import java.util.Set;

/**

	•  TestEjercicio3 *
	•  Conjunto de pruebas sencillas para los métodos de la clase {@code Ejercicio3}.
	•  Cada método de prueba:
	•  - Construye el grafo de entrada a partir de ficheros\PI2E3_DatosEntrada.txt.
	•  - Invoca el método correspondiente de {@code Ejercicio3}.
	•  - Imprime en consola un encabezado y el resultado para facilitar la inspección manual. *
	•  Nota:
	•  - Estos tests son de integración ligera; no usan un framework de testing como JUnit
	•  para mantener el estilo de los tests ya existentes en el proyecto. */ public class TestEjercicio3 {

public static void main(String[] args) {
    testsEjercicio3Ap1();
    testsEjercicio3Ap2();
    testsEjercicio3Ap3();
    testsEjercicio3Ap4();
    testsEjercicio3Ap5();
}

/**
 * Test para el Apartado A: {@link Ejercicio3#getSubgraph_EJ3A(org.jgrapht.Graph)}.
 *
 * Construye el grafo de entrada, ejecuta el método y muestra la vista resultante.
 * Este test permite verificar que el filtrado por año de nacimiento y por número
 * de colaboraciones funciona y que se genera el fichero DOT correspondiente.
 *
 * Ejemplo de uso:
 * {@code
 * testsEjercicio3Ap1();
 * }
 */
public static void testsEjercicio3Ap1() {

    String file = "ficheros\\PI2E3_DatosEntrada.txt";
    SimpleGraph<Investigador, Colaboracion> g =
            GraphsReader.newGraph(file,
                    Investigador::ofFormat,
                    Colaboracion::ofFormat,
                    Graphs2::simpleGraph);

    System.out.println("************************************************************");
    System.out.println("PI2 - Ejercicio 3 - Apartado A");
    System.out.println("************************************************************");

    System.out.println(Ejercicio3.getSubgraph_EJ3A(g));
}

/**
 * Test para el Apartado B: {@link Ejercicio3#getMayoresColaboradores_E3B(org.jgrapht.Graph)}.
 *
 * Ejecuta la selección de los cinco investigadores con mayor número de colaboradores
 * e imprime el conjunto resultante.
 *
 * Ejemplo de uso:
 * {@code
 * testsEjercicio3Ap2();
 * }
 */
public static void testsEjercicio3Ap2() {

    String file = "ficheros\\PI2E3_DatosEntrada.txt";
    SimpleGraph<Investigador, Colaboracion> g =
            GraphsReader.newGraph(file,
                    Investigador::ofFormat,
                    Colaboracion::ofFormat,
                    Graphs2::simpleGraph);

    System.out.println("************************************************************");
    System.out.println("PI2 - Ejercicio 3 - Apartado B");
    System.out.println("************************************************************");

    Set<Investigador> top5 = Ejercicio3.getMayoresColaboradores_E3B(g);
    System.out.println(top5);
}

/**
 * Test para el Apartado C: {@link Ejercicio3#getMapListaColabroradores_E3C(org.jgrapht.Graph)}.
 *
 * (Ya existente) Construye el grafo, invoca el método y muestra el mapa resultado.
 *
 * Ejemplo de uso:
 * {@code
 * testsEjercicio3Ap3();
 * }
 */
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

/**
 * Test para el Apartado D: {@link Ejercicio3#getParMasLejano_E3D(org.jgrapht.Graph)}.
 *
 * (Ya existente) Ejecuta la búsqueda del par más lejano y muestra el par obtenido.
 *
 * Ejemplo de uso:
 * {@code
 * testsEjercicio3Ap4();
 * }
 */
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

/**
 * Test para el Apartado E: {@link Ejercicio3#getReuniones_E3E(org.jgrapht.Graph)}.
 *
 * Construye el grafo, ejecuta el algoritmo de coloreado sobre el grafo de conflictos
 * y muestra la lista de reuniones (clases) resultante.
 *
 * Ejemplo de uso:
 * {@code
 * testsEjercicio3Ap5();
 * }
 */
public static void testsEjercicio3Ap5() {

    String file = "ficheros\\PI2E3_DatosEntrada.txt";
    SimpleGraph<Investigador, Colaboracion> g =
            GraphsReader.newGraph(file,
                    Investigador::ofFormat,
                    Colaboracion::ofFormat,
                    Graphs2::simpleGraph);

    System.out.println("************************************************************");
    System.out.println("PI2 - Ejercicio 3 - Apartado E");
    System.out.println("************************************************************");

    List<Set<Investigador>> reuniones = Ejercicio3.getReuniones_E3E(g);
    System.out.println(reuniones);
}

}