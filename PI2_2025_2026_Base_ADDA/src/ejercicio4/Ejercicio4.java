package ejercicio4;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

/**
 * Utilidades para operar sobre un grafo cuyos vértices son {@code Interseccion}
 * y aristas {@code Calle}.
 *
 * <p>Esta clase ofrece una colección de métodos estáticos para realizar tareas
 * de análisis y visualización sobre grafos de intersecciones y calles:
 * <ul>
 *   <li>Búsqueda del camino más corto entre dos monumentos.</li>
 *   <li>Cálculo del recorrido óptimo (TSP) que visita todas las intersecciones minimizando el esfuerzo.</li>
 *   <li>Análisis de componentes conexas con monumentos tras eliminar calles cortadas.</li>
 * </ul>
 *
 * <p>Los métodos generan, además, ficheros DOT en la carpeta {@code ficheros/grafos}
 * para facilitar la visualización mediante herramientas como Graphviz.</p>
 *
 * @author Álvaro Rosa
 * @version 1.0
 * @since 1.0
 * @see org.jgrapht.alg.shortestpath.DijkstraShortestPath
 * @see org.jgrapht.alg.tour.HeldKarpTSP
 * @see org.jgrapht.alg.connectivity.ConnectivityInspector
 */
public class Ejercicio4 {

	/**
	 * Calcula el camino más corto entre dos monumentos en el grafo, utilizando
	 * la duración de las calles como peso.
	 *
	 * <p>El método emplea {@link DijkstraShortestPath} para obtener el camino mínimo
	 * entre las intersecciones que contienen los monumentos especificados.</p>
	 *
	 * <p>Se genera un fichero DOT en {@code ficheros/grafos/} con el nombre indicado,
	 * coloreando en azul los vértices del camino y en verde las aristas.</p>
	 *
	 * @param startMonument nombre del monumento de origen; no puede ser {@code null}
	 * @param endMonument nombre del monumento de destino; no puede ser {@code null}
	 * @param g grafo de {@code Interseccion} y {@code Calle}; no puede ser {@code null}
	 * @param outputFile nombre del fichero DOT de salida (ej: {@code "EJ4A.gv"})
	 * @return un {@code GraphPath} con el camino más corto entre los dos monumentos; {@code null} si no existe camino
	 * @throws NullPointerException si alguno de los parámetros es {@code null}
	 * @see org.jgrapht.alg.shortestpath.DijkstraShortestPath
	 * @see org.jgrapht.GraphPath
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * Graph<Interseccion,Calle> g = ...;
	 * GraphPath<Interseccion,Calle> camino = Ejercicio4.getShortestPathBetweenMonument_EJ4A("m1", "m7", g, "EJ4A.gv");
	 * if (camino != null) {
	 *   System.out.println("Camino: " + camino);
	 * }
	 * }
	 *
	 * Implementación:
	 * <ol>
	 *   <li>Busca las intersecciones correspondientes a los nombres de monumentos dados.</li>
	 *   <li>Establece la duración de cada calle como peso de las aristas.</li>
	 *   <li>Aplica el algoritmo de Dijkstra para encontrar el camino mínimo.</li>
	 *   <li>Genera un fichero DOT con el camino marcado para visualización.</li>
	 * </ol>
	 */
	public static GraphPath<Interseccion,Calle> getShortestPathBetweenMonument_EJ4A(String startMonument, String endMonument, Graph<Interseccion,Calle> g, String outputFile) {
		Interseccion a = null;
		Interseccion b = null;
		for (Interseccion e : g.vertexSet()) {
			if (e.getNombre().equals(startMonument)) {
				a = e;
			} else if (e.getNombre().equals(endMonument)) {
				b = e;
			}
		}
		for (Calle e : g.edgeSet()) {
			g.setEdgeWeight(e, e.getDuracion());
		}
		var alg = new DijkstraShortestPath<>(g);
		var res = alg.getPath(a, b);
		GraphColors.toDot(g, "ficheros/grafos/" + outputFile,
				 v-> v.toString(),
				 e-> String.valueOf(e.getDuracion()),
				 v -> GraphColors.colorIf(Color.blue, res.getVertexList().contains(v)),
				 e -> GraphColors.colorIf(Color.green, res.getEdgeList().contains(e)));
		return res;
	}
	
	/**
	 * Calcula un recorrido que visita todas las intersecciones del grafo minimizando
	 * el esfuerzo total (problema del viajante de comercio - TSP).
	 *
	 * <p>El método emplea el algoritmo {@link HeldKarpTSP} para obtener el tour óptimo
	 * utilizando el esfuerzo de cada calle como peso.</p>
	 *
	 * <p>Se genera el fichero DOT {@code ficheros/grafos/EJ4B.gv} coloreando en azul
	 * los vértices del recorrido y en verde las aristas utilizadas.</p>
	 *
	 * @param g grafo de {@code Interseccion} y {@code Calle}; no puede ser {@code null}
	 * @return un {@code GraphPath} que representa el tour óptimo por todas las intersecciones
	 * @throws NullPointerException si {@code g} es {@code null}
	 * @see org.jgrapht.alg.tour.HeldKarpTSP
	 * @see org.jgrapht.GraphPath
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * Graph<Interseccion,Calle> g = ...;
	 * GraphPath<Interseccion,Calle> tour = Ejercicio4.getRecorrido_E4B(g);
	 * System.out.println("Tour: " + tour);
	 * }
	 *
	 * Implementación:
	 * <ol>
	 *   <li>Establece el esfuerzo de cada calle como peso de las aristas.</li>
	 *   <li>Aplica el algoritmo de Held-Karp para resolver el TSP.</li>
	 *   <li>Genera un fichero DOT con el tour marcado para visualización.</li>
	 * </ol>
	 *
	 * Complejidad:
	 * <ul>
	 *   <li>O(n^2 * 2^n) en tiempo y O(n * 2^n) en espacio, donde {@code n} es el número de vértices.</li>
	 * </ul>
	 */
	public static GraphPath<Interseccion,Calle> getRecorrido_E4B(Graph <Interseccion, Calle> g) {
		for (Calle e : g.edgeSet()) {
			g.setEdgeWeight(e, e.getEsfuerzo());
		}
		var tsp = new HeldKarpTSP<Interseccion, Calle>();
		var res = tsp.getTour(g);
		GraphColors.toDot(g, "ficheros/grafos/EJ4B.gv",
				 v-> v.toString(),
				 e-> String.valueOf(e.getEsfuerzo()),
				 v -> GraphColors.colorIf(Color.blue, res.getVertexList().contains(v)),
				 e -> GraphColors.colorIf(Color.green, res.getEdgeList().contains(e)));
        return res;
	}
	
	/**
	 * Determina la componente conexa con mayor relevancia de monumentos tras eliminar
	 * un conjunto de calles cortadas del grafo.
	 *
	 * <p>El método elimina las calles especificadas y analiza las componentes conexas
	 * resultantes. Si hay varias componentes con monumentos, selecciona la de mayor
	 * relevancia acumulada (suma de la relevancia de todos los monumentos en la componente).</p>
	 *
	 * <p>Se genera un fichero DOT en {@code ficheros/grafos/} con el nombre indicado,
	 * coloreando en azul los vértices de la componente más relevante.</p>
	 *
	 * @param cs conjunto de calles a eliminar (cortadas); no puede ser {@code null}
	 * @param g grafo de {@code Interseccion} y {@code Calle}; no puede ser {@code null}
	 * @param ftest nombre del fichero DOT de salida (ej: {@code "EJ4C.gv"})
	 * @return el grafo modificado tras eliminar las calles cortadas
	 * @throws NullPointerException si alguno de los parámetros es {@code null}
	 * @see org.jgrapht.alg.connectivity.ConnectivityInspector
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * Graph<Interseccion,Calle> g = ...;
	 * Set<Calle> cortadas = new HashSet<>();
	 * // añadir calles cortadas al conjunto
	 * Graph<Interseccion,Calle> resultado = Ejercicio4.getRecorridoMaxRelevante_E4C(cortadas, g, "EJ4C.gv");
	 * }
	 *
	 * Implementación:
	 * <ol>
	 *   <li>Elimina todas las aristas del conjunto {@code cs} del grafo.</li>
	 *   <li>Obtiene las componentes conexas mediante {@link ConnectivityInspector}.</li>
	 *   <li>Si hay una sola componente, la devuelve directamente.</li>
	 *   <li>Si hay varias componentes con monumentos, calcula la relevancia total de cada una.</li>
	 *   <li>Ordena las componentes por relevancia descendente y selecciona la primera.</li>
	 *   <li>Genera un fichero DOT marcando la componente más relevante.</li>
	 * </ol>
	 *
	 * Observación:
	 * <p>El grafo original es modificado (se eliminan las aristas del conjunto {@code cs}).</p>
	 */
	public static Graph<Interseccion,Calle> getRecorridoMaxRelevante_E4C(Set<Calle> cs,Graph <Interseccion, Calle> g, String ftest) {
		g.removeAllEdges(cs);
		var conInspector = new ConnectivityInspector<Interseccion, Calle>(g);
		var conComp = conInspector.connectedSets();
		int count = 0;
		if (conComp.size() == 1) {
			GraphColors.toDot(g, "ficheros/grafos/" + ftest,
					 v-> v.toString(),
					 e-> String.valueOf(e.toString()),
					 v -> GraphColors.colorIf(Color.blue, true),
					 e -> GraphColors.colorIf(Color.green, false));
			return g;
		} else {
			for (Set<Interseccion> l : conComp) {
				boolean hasMonumento = false;
				for (Interseccion i : l) {
					if (i.hasMonumento()) {
						hasMonumento = true;
					}
				}
				if (hasMonumento) {
					count += 1;
				}
			}
			if (count > 1) {
				record Tupla(Set<Interseccion> s, Integer relevancia) {
				}
				List<Tupla> lista = new ArrayList<>();
				for (Set<Interseccion> l : conComp) {
					Integer rel = 0;
					for (Interseccion i : l) {
						if (i.hasMonumento()) {
							rel += i.getRelevancia();
						}
					}
					lista.add(new Tupla(l, rel));
				}
				Comparator<Tupla> cmp = Comparator.comparing(e -> e.relevancia);
				lista.sort(cmp.reversed());
				GraphColors.toDot(g, "ficheros/grafos/" + ftest,
						 v-> v.toString(),
						 e-> String.valueOf(e.toString()),
						 v -> GraphColors.colorIf(Color.blue, lista.get(0).s.contains(v)),
						 e -> GraphColors.colorIf(Color.green, false));
				return g;
			} else {
				GraphColors.toDot(g, "ficheros/grafos/" + ftest,
						 v-> v.toString(),
						 e-> String.valueOf(e.toString()),
						 v -> GraphColors.colorIf(Color.blue, v.hasMonumento()),
						 e -> GraphColors.colorIf(Color.green, false));
				return g;
			}
			
		}
	}
		
}