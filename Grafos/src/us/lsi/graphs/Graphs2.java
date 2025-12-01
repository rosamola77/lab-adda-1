package us.lsi.graphs;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import us.lsi.common.Preconditions;
import us.lsi.graphs.views.CompleteGraphView;
import us.lsi.graphs.views.PathToGraph;
import us.lsi.graphs.views.SubGraphView;

/**
 * <p>Clase de utilidades para operaciones con grafos JGraphT.</p>
 * 
 * <p>Proporciona metodos estaticos para crear grafos, encontrar vertices
 * cercanos y lejanos, convertir entre tipos de grafos, crear subgrafos
 * y vistas de grafos.</p>
 * 
 * @author Miguel Toro
 */
public class Graphs2 {
	
	/**
	 * Obtiene el peso de una arista entre dos vertices.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph El grafo
	 * @param v1 Primer vertice
	 * @param v2 Segundo vertice
	 * @return El peso de la arista entre v1 y v2
	 */
	public static <V, E> Double weightOfEdge(Graph<V,E> graph, V v1, V v2) {
		E e = graph.getEdge(v1,v2); 
		Double w = graph.getEdgeWeight(e); 
		return w;
	}
	
	/**
	 * Obtiene el vertice mas cercano a uno dado.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph El grafo
	 * @param vertex Vertice de referencia
	 * @return El vertice vecino mas cercano
	 */
	public static <V, E> V closestVertex(Graph<V,E> graph, V vertex) {
		return closestVertex(graph,vertex,v->true);
	}
	
	/**
	 * Obtiene el vertice mas cercano a uno dado que cumpla un predicado.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph El grafo
	 * @param vertex Vertice de referencia
	 * @param p Predicado de filtrado
	 * @return El vertice vecino mas cercano que cumple el predicado
	 */
	public static <V, E> V closestVertex(Graph<V,E> graph, V vertex, Predicate<V> p) {
		return (Graphs.neighborSetOf(graph,vertex)).stream()
				.filter(p)
				.min(Comparator.comparingDouble(v->weightOfEdge(graph,vertex,v)))
				.get();
	}
	
	/**
	 * Obtiene el vertice mas lejano a uno dado.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph El grafo
	 * @param vertex Vertice de referencia
	 * @return El vertice vecino mas lejano
	 */
	public static <V, E> V furthestVertex(Graph<V,E> graph, V vertex) {
		return furthestVertex(graph,vertex,v->true);
	}
	
	/**
	 * Obtiene el vertice mas lejano a uno dado que cumpla un predicado.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph El grafo
	 * @param vertex Vertice de referencia
	 * @param p Predicado de filtrado
	 * @return El vertice vecino mas lejano que cumple el predicado
	 */
	public static <V, E> V furthestVertex(Graph<V,E> graph, V vertex, Predicate<V> p) {
		return (Graphs.neighborSetOf(graph,vertex)).stream()
				.filter(p)
				.max(Comparator.comparingDouble(v->weightOfEdge(graph,vertex,v)))
				.get();
	}
	
	/**
	 * Crea un grafo simple no dirigido vacio.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @return Un nuevo grafo simple
	 */
	public static <V,E> SimpleGraph<V, E> simpleGraph() {
		return new SimpleGraph<V,E>(null,null,false);
	}
	
	/**
	 * Crea un grafo simple no dirigido con proveedores.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param vs Proveedor de vertices
	 * @param es Proveedor de aristas
	 * @param weighted Si el grafo es ponderado
	 * @return Un nuevo grafo simple
	 */
    public static <V,E> SimpleGraph<V, E> simpleGraph(Supplier<V> vs, Supplier<E> es, boolean weighted) {
        return new SimpleGraph<V,E>(vs, es, weighted);
    }
    
    /**
     * Crea un grafo simple ponderado no dirigido vacio.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @return Un nuevo grafo ponderado
     */
    public static <V,E> SimpleWeightedGraph<V, E> simpleWeightedGraph() {
		return new SimpleWeightedGraph<>(null,null);
	}
    

    /**
     * Crea un grafo simple ponderado no dirigido con proveedores.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @param vs Proveedor de vertices
     * @param es Proveedor de aristas
     * @return Un nuevo grafo ponderado
     */
    public static <V,E> SimpleWeightedGraph<V, E> simpleWeightedGraph(Supplier<V> vs, Supplier<E> es) {
        return new SimpleWeightedGraph<>(vs, es);
    }
    
    /**
     * Crea un grafo simple dirigido vacio.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @return Un nuevo grafo dirigido
     */
    public static <V,E> SimpleDirectedGraph<V, E> simpleDirectedGraph() {
		return new SimpleDirectedGraph<>(null,null,false);
	}
    
    /**
     * Anade un camino a un nuevo grafo dirigido.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @param gp Camino a anadir
     * @return Grafo dirigido con el camino
     */
    public static <V,E> SimpleDirectedGraph<V, E> addPathToGraph(GraphPath<V,E> gp) {
    	SimpleDirectedGraph<V, E> g = Graphs2.simpleDirectedGraph();
    	return PathToGraph.addPathToGraph(g, gp);
    }
    
    /**
     * Anade un camino a un grafo dirigido existente.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @param g Grafo destino
     * @param gp Camino a anadir
     * @return El grafo con el camino anadido
     */
    public static <V,E> SimpleDirectedGraph<V, E> addPathToGraph(SimpleDirectedGraph<V,E> g, GraphPath<V,E> gp) {
    	return PathToGraph.addPathToGraph(g, gp);
    }
    
    /**
     * Crea un grafo simple dirigido ponderado vacio.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @return Un nuevo grafo dirigido ponderado
     */
    public static <V,E> SimpleDirectedWeightedGraph<V, E> simpleDirectedWeightedGraph() {
		return new SimpleDirectedWeightedGraph<>(null,null);
	}
    
    /**
     * Crea un multigrafo dirigido ponderado vacio.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @return Un nuevo multigrafo dirigido ponderado
     */
    public static <V,E> DirectedWeightedMultigraph<V,E> directedWeightedMultigraph() {
    	return new DirectedWeightedMultigraph<>(null,null);
    }

    /**
     * Obtiene los vertices de una arista.
     * 
     * @param <V> Tipo de los vertices
     * @param <E> Tipo de las aristas
     * @param graph El grafo
     * @param edge La arista
     * @return Conjunto con los dos vertices de la arista
     */
	public static <V,E> Set<V> getVertices(Graph<V,E> graph, E edge){
		return Set.of(graph.getEdgeSource(edge),graph.getEdgeTarget(edge));
	}
	
	/**
	 * Crea un grafo dirigido con las aristas invertidas.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Grafo original
	 * @return Grafo con todas las aristas invertidas
	 */
	public static <V, E> SimpleDirectedGraph<V, E> inversedDirectedGraph(SimpleDirectedGraph<V, E> graph){
		SimpleDirectedGraph<V, E> gs = Graphs2.simpleDirectedGraph();
		for (V v : graph.vertexSet()) {
			gs.addVertex(v);
		}
		for (E e : graph.edgeSet()) {
			V s = graph.getEdgeSource(e);
			V t = graph.getEdgeTarget(e);
			gs.addEdge(t, s, e);
		}
		return gs;
	}
	
	
	/**
	 * Convierte un grafo no dirigido ponderado en uno dirigido.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Grafo no dirigido
	 * @param edgeReverse Funcion para crear aristas inversas
	 * @return Grafo dirigido ponderado equivalente
	 */
	public static <V, E> SimpleDirectedWeightedGraph<V, E> toDirectedWeightedGraph(SimpleWeightedGraph<V, E> graph,
			Function<E, E> edgeReverse) {
		SimpleDirectedWeightedGraph<V, E> gs = new SimpleDirectedWeightedGraph<V, E>(graph.getVertexSupplier(),
				graph.getEdgeSupplier());
		for (V v : graph.vertexSet()) {
			gs.addVertex(v);
		}
		for (E e : graph.edgeSet()) {
			V s = graph.getEdgeSource(e);
			V t = graph.getEdgeTarget(e);
			Double w = graph.getEdgeWeight(e);
			gs.addEdge(s, t, e);
			gs.setEdgeWeight(e, w);
			E e1 = edgeReverse.apply(e);
			gs.addEdge(t, s, e1);
			gs.setEdgeWeight(e1, w);
		}
		return gs;
	}

	/**
	 * Convierte un grafo simple en uno dirigido.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Grafo no dirigido
	 * @return Grafo dirigido equivalente
	 */
	public static <V,E> SimpleDirectedGraph<V,E> toDirectedGraph(SimpleGraph<V,E> graph){
		SimpleDirectedGraph<V,E> gs = 
				new SimpleDirectedGraph<V,E>(
						graph.getVertexSupplier(), 
						graph.getEdgeSupplier(),
						true);
		for(V v:graph.vertexSet()){
			gs.addVertex(v);
		}
		for(E e:graph.edgeSet()){			
			gs.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e));
			gs.addEdge(graph.getEdgeTarget(e), graph.getEdgeSource(e));
		}
		return gs;
	}
	
	/**
	 * Crea un subgrafo con vertices filtrados.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo original
	 * @param pv Predicado para filtrar vertices
	 * @param creator Proveedor del nuevo grafo
	 * @return Subgrafo con los vertices que cumplen el predicado
	 */
	public static <V,E,G extends Graph<V,E>> G subGraphOfVertices(G graph, 
			Predicate<V> pv,
			Supplier<G> creator) {
		return subGraph(graph,pv,null,creator);
	}
	
	/**
	 * Crea un subgrafo con aristas filtradas.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo original
	 * @param pe Predicado para filtrar aristas
	 * @param creator Proveedor del nuevo grafo
	 * @return Subgrafo con las aristas que cumplen el predicado
	 */
	public static <V, E, G extends Graph<V, E>> G subGraphOfEdges(G graph, Predicate<E> pe, Supplier<G> creator) {
		return subGraph(graph, null, pe, creator);
	}
	
	/**
	 * Crea un subgrafo con vertices y aristas filtrados.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo original
	 * @param pv Predicado para filtrar vertices (null para todos)
	 * @param pe Predicado para filtrar aristas (null para todas)
	 * @param creator Proveedor del nuevo grafo
	 * @return Subgrafo filtrado
	 */
	public static <V, E, G extends Graph<V, E>> G subGraph(G graph, Predicate<V> pv, Predicate<E> pe,
			Supplier<G> creator) {

		Predicate<V> npv = pv == null ? v -> true : pv;

		Set<V> vertices = graph.vertexSet().stream().filter(npv).collect(Collectors.toSet());

		Predicate<E> npe = e -> vertices.contains(graph.getEdgeSource(e)) && vertices.contains(graph.getEdgeTarget(e));

		Predicate<E> npe2 = pe == null ? npe : e -> npe.test(e) && pe.test(e);

		Set<E> edges = graph.edgeSet().stream().filter(npe2).collect(Collectors.toSet());

		G r = creator.get();

		vertices.stream().forEach(x -> r.addVertex(x));
		edges.stream().forEach(x -> r.addEdge(graph.getEdgeSource(x), graph.getEdgeTarget(x), x));

		return r;
	}

	/**
	 * Crea un grafo completo explicito a partir de uno existente.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo original
	 * @param weight Peso por defecto para aristas nuevas
	 * @param creator Proveedor del nuevo grafo
	 * @param edgeCreator Proveedor de nuevas aristas
	 * @param edgeWeight Funcion de peso para aristas
	 * @return Grafo completo
	 */
	public static <V, E, G extends Graph<V, E>> G explicitCompleteGraph(
			G graph, 
			Double weight,
			Supplier<G> creator, 
			Supplier<E> edgeCreator,
			Function<E,Double> edgeWeight) {

		G r = creator.get();

		graph.vertexSet().stream().forEach(x -> r.addVertex(x));
		graph.edgeSet().stream().forEach(x -> r.addEdge(graph.getEdgeSource(x), graph.getEdgeTarget(x), x));

		for (V v1 : graph.vertexSet()) {
			for (V v2 : graph.vertexSet()) {
				if (!v1.equals(v2)) {
					if (!graph.containsEdge(v1, v2)) {
						E e = edgeCreator.get();
						r.addEdge(v1, v2, e);
					}
				}
			}
		}
		r.edgeSet().forEach(e->r.setEdgeWeight(e, edgeWeight.apply(e)));
		return r;
	}
	
	/**
	 * Crea una vista de grafo completo.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param g Grafo base
	 * @param edgeWeightFactory Fabrica de aristas con peso
	 * @return Vista de grafo completo
	 */
	public static <V, E> Graph<V, E> completeGraphView(Graph<V, E> g, Supplier<E> edgeWeightFactory) {
			return CompleteGraphView.of(g, edgeWeightFactory);
	}
	
	/**
	 * Crea una vista de subgrafo.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo base
	 * @param vertices Predicado para filtrar vertices
	 * @param edges Predicado para filtrar aristas
	 * @return Vista de subgrafo
	 */
	public static <V, E, G extends Graph<V,E>> SubGraphView<V, E, G> subGraphView(G graph, Predicate<V> vertices, Predicate<E> edges) {
		return  SubGraphView.of(graph, vertices, edges);
	}
	
	/**
	 * Crea una vista de subgrafo filtrando solo vertices.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo base
	 * @param vertices Predicado para filtrar vertices
	 * @return Vista de subgrafo
	 */
	public static <V, E, G extends Graph<V,E>> SubGraphView<V, E, G> subGraphView(G graph, Predicate<V> vertices) {
		return  SubGraphView.of(graph, vertices,e->true);
	}
	
	/**
	 * Crea una vista de subgrafo filtrando solo aristas.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo base
	 * @param edges Predicado para filtrar aristas
	 * @return Vista de subgrafo
	 */
	public static <V, E, G extends Graph<V,E>> SubGraphView<V, E, G> subGraphViewOfEdges(G graph, Predicate<E> edges) {
		return  SubGraphView.of(graph, v->true, edges);
	}

	/**
	 * Obtiene el vertice opuesto de una arista dado un vertice.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas (debe extender SimpleEdge)
	 * @param graph El grafo
	 * @param edge La arista
	 * @param vertex El vertice conocido
	 * @return El vertice opuesto
	 */
	public static <V, E extends SimpleEdge<V>> V getOppositeVertex(Graph<V, E> graph, E edge, V vertex) {
		V r = null;
		if (edge.source().equals(vertex)) r = edge.target();
		if (edge.target().equals(vertex)) r = edge.source();
		Preconditions.checkNotNull(r);
		return r;
	}
	
	/**
	 * Sustituye una arista por un camino en un grafo.
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param <G> Tipo del grafo
	 * @param graph Grafo a modificar
	 * @param edge Arista a sustituir
	 * @param graphPath Camino de sustitucion
	 * @return El grafo modificado
	 */
	public static <V, E, G extends Graph<V, E>> G sustituteEdge(G graph, E edge, GraphPath<V,E> graphPath) {
		Graph<V,E> origin = graphPath.getGraph();
		graph.removeEdge(edge);
		graphPath.getVertexList().stream().forEach(v->{if(!graph.containsVertex(v))graph.addVertex(v);});
		graphPath.getEdgeList().stream()
			.forEach(e->graph.addEdge(origin.getEdgeSource(e), origin.getEdgeTarget(e), e));
		return graph;
	}
	
	/**
	 * Convierte un grafo no dirigido en uno dirigido para flujo.
	 * 
	 * <p>Los vertices fuente no tienen aristas de entrada y
	 * los sumideros no tienen aristas de salida.</p>
	 * 
	 * @param <V> Tipo de los vertices
	 * @param <E> Tipo de las aristas
	 * @param graph Un grafo no dirigido
	 * @param edgeReverse Una funcion que produce una arista inversa con el mismo peso
	 * @param sources Los vertices que seran fuentes
	 * @param targets Los vertices que seran sumideros
	 * @return Un grafo dirigido para problemas de flujo
	 */
	public static <V,E> SimpleDirectedWeightedGraph<V,E> toDirectedWeightedGraphFlow(
			SimpleWeightedGraph<V,E> graph, 
			Function<E,E> edgeReverse, 
			Set<V> sources, 
			Set<V> targets){
		SimpleDirectedWeightedGraph<V,E> gs = Graphs2.toDirectedWeightedGraph(graph, edgeReverse);
		Set<E> remove = new HashSet<>();
		for(E e:gs.edgeSet()) {
			V s = gs.getEdgeSource(e);
			V t = gs.getEdgeTarget(e);
			if(sources.contains(t) || targets.contains(s)) remove.add(e);
		}
		gs.removeAllEdges(remove);
		return gs;
	}

	
	
}
