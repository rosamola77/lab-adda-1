package ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Pair;
import us.lsi.graphs.views.SubGraphView;

/**
 * Utilidades para operar sobre un grafo cuyos vértices son {@code Investigador}
 * y aristas {@code Colaboracion}.
 *
 * <p>Esta clase ofrece una colección de métodos estáticos para realizar tareas
 * de análisis y visualización sobre grafos de investigadores:
 * <ul>
 *   <li>Filtrado y construcción de subgrafos según predicados.</li>
 *   <li>Selección de los investigadores con mayor número de colaboradores.</li>
 *   <li>Mapeo de colaboradores ordenados por intensidad de colaboración.</li>
 *   <li>Búsqueda del par de vértices más alejados (diámetro aproximado por búsqueda de caminos mínimos).</li>
 *   <li>Construcción de reuniones (particionamiento por coloreado) respetando conflictos.</li>
 * </ul>
 *
 * <p>Los métodos generan, además, ficheros DOT en la carpeta {@code ficheros/grafos}
 * para facilitar la visualización mediante herramientas como Graphviz.</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 * @see us.lsi.graphs.views.SubGraphView
 * @see us.lsi.colors.GraphColors
 */
public class Ejercicio3 {
	
	/**
	 * Construye una vista del grafo que contiene únicamente:
	 * <ul>
	 *   <li>Investigadores nacidos antes de 1982, o</li>
	 *   <li>Investigadores que tengan alguna colaboración con más de 5 trabajos conjuntos.</li>
	 * </ul>
	 * Las aristas con más de 5 trabajos también se incluyen en la vista.
	 *
	 * <p>Se genera el fichero DOT {@code ficheros/grafos/EJ3A.gv} coloreando en azul
	 * los vértices y aristas que cumplen los predicados.</p>
	 *
	 * @param g grafo original cuyos vértices son {@code Investigador} y aristas {@code Colaboracion}; no puede ser {@code null}
	 * @return una vista de {@code g} (posible instancia de {@code SubGraphView}) con los vértices y aristas filtrados
	 * @throws NullPointerException si {@code g} es {@code null}
	 * @see us.lsi.graphs.views.SubGraphView#of(Graph, java.util.function.Predicate, java.util.function.Predicate)
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * Graph<Investigador,Colaboracion> g = ...;
	 * Graph<Investigador,Colaboracion> sub = Ejercicio3.getSubgraph_EJ3A(g);
	 * }
	 *
	 * Implementación:
	 * <ul>
	 *   <li>Define predicados para aristas y vértices.</li>
	 *   <li>Genera un fichero DOT para visualización.</li>
	 *   <li>Devuelve una vista filtrada mediante {@code SubGraphView.of(...)}.</li>
	 * </ul>
	 */
	public static Graph<Investigador,Colaboracion> getSubgraph_EJ3A(Graph <Investigador, Colaboracion> g ) {
		Predicate<Colaboracion> pe = e -> e.getNColaboraciones() > 5;
		Predicate<Investigador> pv = v -> v.getFNacimiento() < 1982 || g.edgesOf(v).stream().anyMatch(pe);
		
		GraphColors.toDot(g,"ficheros/grafos/EJ3A.gv",
				 v-> v.toString() + " " + v.getFNacimiento(),
				 e-> e.getNColaboraciones().intValue()+"",
				 v -> GraphColors.colorIf(Color.blue, pv.test(v)),
				 e -> GraphColors.colorIf(Color.blue, pe.test(e)));
		
		return SubGraphView.of(g, pv, pe);
	}
	
	/**
	 * Obtiene hasta cinco investigadores con mayor número de colaboradores (grado en el grafo).
	 *
	 * <p>Se genera el fichero DOT {@code ficheros/grafos/EJ3B.gv} marcando en azul
	 * los investigadores seleccionados; las aristas se representan en color verde.</p>
	 *
	 * @param g grafo de {@code Investigador} y {@code Colaboracion}; no puede ser {@code null}
	 * @return un {@code Set} con como máximo cinco investigadores ordenados por grado descendente
	 * @throws NullPointerException si {@code g} es {@code null}
	 * @see Graph#degreeOf(Object)
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * Set<Investigador> top5 = Ejercicio3.getMayoresColaboradores_E3B(g);
	 * top5.forEach(System.out::println);
	 * }
	 *
	 * Implementación:
	 * <ol>
	 *   <li>Ordena los vértices por su grado (número de vecinos) de forma descendente.</li>
	 *   <li>Toma los primeros 5 elementos y devuelve un conjunto con ellos.</li>
	 *   <li>Produce el fichero DOT para visualización.</li>
	 * </ol>
	 */
	public static Set<Investigador> getMayoresColaboradores_E3B (Graph<Investigador,Colaboracion> g) {
		Comparator<Investigador> cmp = Comparator.comparing(v -> g.degreeOf(v));
		var ls= g.vertexSet().stream().sorted(cmp.reversed()).limit(5).toList();
		
		GraphColors.toDot(g,"ficheros/grafos/EJ3B.gv",
				 v-> v.toString(),
				 e-> e.getNColaboraciones().intValue()+"",
				 v -> GraphColors.colorIf(Color.blue, ls.contains(v)),
				 e -> GraphColors.color(Color.green));
		return new HashSet<>(ls);
	}

	/**
	 * Para cada investigador construye la lista de sus colaboradores ordenada
	 * por número de colaboraciones conjuntas (descendente).
	 *
	 * <p>El mapa resultante asocia a cada investigador la lista de investigadores
	 * adyacentes ordenada por la intensidad de la colaboración (número de trabajos en común).</p>
	 *
	 * @param g grafo de {@code Investigador} y {@code Colaboracion}; no puede ser {@code null}
	 * @return un {@code Map} que asocia cada investigador a la lista ordenada de sus colaboradores; si un investigador no tiene colaboradores, se asocia a una lista vacía
	 * @throws NullPointerException si {@code g} es {@code null}
	 * @see Graphs#getOppositeVertex(Graph, Object, Object)
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * Map<Investigador,List<Investigador>> mapa = Ejercicio3.getMapListaColabroradores_E3C(g);
	 * List<Investigador> lista = mapa.get(algunInvestigador); // lista ordenada por número de colaboraciones
	 * }
	 *
	 * Implementación:
	 * <ul>
	 *   <li>Para cada vértice obtiene sus aristas, ordena por número de colaboraciones y mapea a los vértices opuestos.</li>
	 *   <li>Genera un fichero DOT donde se destaca en azul la arista principal (la máxima) para cada investigador.</li>
	 * </ul>
	 */
	public static Map<Investigador,List<Investigador>> getMapListaColabroradores_E3C (Graph<Investigador,Colaboracion> g) {
		Map<Investigador,List<Investigador>> res = new HashMap<>();
		Comparator<Colaboracion> cmp = Comparator.comparing(v -> v.getNColaboraciones());
		
		for (Investigador i : g.vertexSet()) {
	        List<Investigador> colaboradoresOrdenados = g.edgesOf(i).stream()
	                .sorted(cmp.reversed())
	                .map(edge -> Graphs.getOppositeVertex(g, edge, i))
	                .collect(Collectors.toList());
	        
	        res.put(i, colaboradoresOrdenados);
	    }
		
		List<Colaboracion> top = new ArrayList<>();
		for (Entry<Investigador, List<Investigador>> l : res.entrySet()) {
			if (!l.getValue().isEmpty()) {
				top.add(g.getEdge(l.getKey(), l.getValue().get(0)));
			}
		}
	    
	    GraphColors.toDot(g,"ficheros/grafos/EJ3C.gv",
				 v-> v.toString(),
				 e-> e.getNColaboraciones().intValue()+"",
				 v -> GraphColors.color(Color.black),
				 e -> GraphColors.colorIf(Color.blue, top.contains(e)));
	    return res;
	}
	
	/**
	 * Calcula un par de investigadores que estén a la máxima distancia geodésica
	 * (longitud del camino mínimo en número de aristas) en el grafo.
	 *
	 * <p>El método emplea {@link DijkstraShortestPath} para obtener caminos mínimos
	 * entre pares de vértices (en grafos no ponderados esto equivale a minimizar
	 * el número de aristas). Devuelve un par correspondiente a la máxima
	 * distancia encontrada entre vértices alcanzables.</p>
	 *
	 * @param g grafo de {@code Investigador} y {@code Colaboracion}; no puede ser {@code null}
	 * @return un {@code Pair} con los dos investigadores más lejanos (por número de aristas); {@code null} si no existe pareja conectada
	 * @throws NullPointerException si {@code g} es {@code null}
	 * @see org.jgrapht.alg.shortestpath.DijkstraShortestPath
	 * @see org.jgrapht.GraphPath
	 * @ApiNote
	 * Ejemplo de uso:
	 * {@code
	 * Pair<Investigador,Investigador> par = Ejercicio3.getParMasLejano_E3D(g);
	 * if (par != null) {
	 *   System.out.println("Mas lejanos: " + par.first() + " - " + par.second());
	 * }
	 * }
	 *
	 * Implementación:
	 * <ol>
	 *   <li>Itera sobre todos los pares de vértices (i,j) con i < j.</li>
	 *   <li>Calcula el camino mínimo entre ellos y mide su longitud en número de aristas.</li>
	 *   <li>Registra el par con mayor longitud; genera un fichero DOT con el camino marcado si existe.</li>
	 * </ol>
	 *
	 * Complejidad:
	 * <ul>
	 *   <li>O(n^2 * T_path) en el peor caso, donde {@code n} es el número de vértices y {@code T_path}
	 *   es el coste de obtener un camino mínimo (depende de la implementación de Dijkstra).</li>
	 * </ul>
	 */
	public static Pair<Investigador,Investigador> getParMasLejano_E3D (Graph<Investigador,Colaboracion> g) {
		var alg = new DijkstraShortestPath<>(g);
		Pair<Investigador, Investigador> res = null;
		
		List<Investigador> investigadores = new ArrayList<>(g.vertexSet());
	    
		Double maxDistancia = 0.0;
	    for (int i = 0; i < investigadores.size(); i++) {
	        for (int j = i + 1; j < investigadores.size(); j++) {
	            
	            Investigador inv1 = investigadores.get(i);
	            Investigador inv2 = investigadores.get(j);
	            
	            GraphPath<Investigador, Colaboracion> path = alg.getPath(inv1, inv2);
	            
	            if (path == null) continue;
	            
	            Double distanciaActual = (double) path.getEdgeList().size();
	                
	            if (distanciaActual > maxDistancia) {
	                maxDistancia = distanciaActual;
	                res = new Pair<>(inv1, inv2);
	            }
	        }
	    }
	    
	    if (res != null) {
		    Investigador origen = res.first();
		    Investigador destino = res.second();
		    GraphPath<Investigador, Colaboracion> camino = alg.getPath(origen, destino);
		    
		    Set<Investigador> verticesEnCamino = new HashSet<>(camino.getVertexList());
		    Set<Colaboracion> aristasEnCamino = new HashSet<>(camino.getEdgeList());
		    
		    GraphColors.toDot(g,"ficheros/grafos/EJ3D.gv",
					 v-> v.toString(),
					 e-> e.getNColaboraciones().intValue()+"",
					 v -> GraphColors.colorIf(Color.blue, verticesEnCamino.contains(v)),
					 e -> GraphColors.colorIf(Color.blue, aristasEnCamino.contains(e)));
	    }
	    return res;
	}
	
	/**
	 * Agrupa a los investigadores en reuniones (clases) cumpliendo restricciones
	 * de conflicto. Se consideran dos tipos de conflicto:
	 * <ol>
	 *   <li>Si dos investigadores ya colaboran (existe arista en el grafo original).</li>
	 *   <li>Si dos investigadores pertenecen a la misma universidad.</li>
	 * </ol>
	 *
	 * <p>Construye un grafo de conflictos donde cada vértice es un investigador
	 * y cada arista representa un conflicto. Aplica un coloreado goloso
	 * ({@link GreedyColoring}) para obtener clases/reuniones (cada color es una reunión).</p>
	 *
	 * @param g grafo de {@code Investigador} y {@code Colaboracion}; no puede ser {@code null}
	 * @return una {@code List} de {@code Set} donde cada conjunto contiene investigadores que pueden reunirse (mismo color)
	 * @throws NullPointerException si {@code g} es {@code null}
	 * @see org.jgrapht.alg.color.GreedyColoring
	 * @apiNote
	 * Ejemplo de uso:
	 * {@code
	 * List<Set<Investigador>> reuniones = Ejercicio3.getReuniones_E3E(g);
	 * for (int i = 0; i < reuniones.size(); i++) {
	 *   System.out.println("Reunion " + i + ": " + reuniones.get(i));
	 * }
	 * }
	 *
	 * Implementación:
	 * <ol>
	 *   <li>Crea un grafo simple sin ponderar {@code gConflictos} con los investigadores como vértices.</li>
	 *   <li>Añade aristas por colaboración y por pertenencia a la misma universidad.</li>
	 *   <li>Aplica coloreado goloso y transforma las clases de color en la lista de reuniones.</li>
	 *   <li>Genera un fichero DOT {@code ficheros/grafos/EJ3E.gv} coloreado según la asignación.</li>
	 * </ol>
	 *
	 * Observación:
	 * <p>El coloreado goloso no garantiza un número mínimo de reuniones pero es eficiente y produce una partición válida.</p>
	 */
	public static List<Set<Investigador>> getReuniones_E3E (Graph<Investigador,Colaboracion> g) {
		Graph<Investigador, DefaultEdge> gConflictos = new SimpleGraph<>(DefaultEdge.class);
		
	    for (Investigador inv : g.vertexSet()) {
	        gConflictos.addVertex(inv);
	    }
	    
	    for (Colaboracion edge : g.edgeSet()) {
	        Investigador v1 = g.getEdgeSource(edge);
	        Investigador v2 = g.getEdgeTarget(edge);
	        if (!gConflictos.containsEdge(v1, v2)) {
	            gConflictos.addEdge(v1, v2);
	        }
	    }
	    
	    List<Investigador> listaInv = new ArrayList<>(g.vertexSet());
	    for (int i = 0; i < listaInv.size(); i++) {
	        for (int j = i + 1; j < listaInv.size(); j++) {
	            Investigador inv1 = listaInv.get(i);
	            Investigador inv2 = listaInv.get(j);
	            if (inv1.getUniversidad().equals(inv2.getUniversidad())) {
	                 if (!gConflictos.containsEdge(inv1, inv2)) {
	                    gConflictos.addEdge(inv1, inv2);
	                }
	            }
	        }
	    }
	    
	    var alg = new GreedyColoring<>(gConflictos);
	    Coloring<Investigador> coloreado = alg.getColoring();
	    
	    Map<Investigador, Integer> asignacion = coloreado.getColors();
	    var res = coloreado.getColorClasses();
	    
	    GraphColors.toDot(gConflictos, "ficheros/grafos/EJ3E.gv", 
				v->v.toString(),
				e->"",
				v -> GraphColors.color(asignacion.get(v)),
				e -> GraphColors.style(Style.solid));
	    return res;
	}

}