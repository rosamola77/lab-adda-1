package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.ArrowHead;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Preconditions;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath;
import us.lsi.streams.Stream2;

/**
 * AStar
 *
 * <p>Implementación del algoritmo A* para búsqueda de caminos óptimos
 * en grafos. Utiliza una heurística para guiar la búsqueda y un
 * montículo de Fibonacci para seleccionar eficientemente el siguiente
 * vértice a explorar.</p>
 *
 * <p>El algoritmo combina la distancia recorrida con una estimación
 * heurística de la distancia restante para priorizar los vértices
 * más prometedores.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = ...;
 * AStar<V,E,S> astar = AStar.of(graph);
 * Optional<GraphPath<V,E>> path = astar.search();
 * }</p>
 *
 * @param <V> tipo de los vértices
 * @param <E> tipo de las aristas
 * @param <S> tipo de la solución
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see EGraph
 */
public class AStar<V,E,S> implements Iterator<V>, Iterable<V> {
	
	/**
	 * Crea un algoritmo A* inicializado con una solución voraz.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo con heurística definida
	 * @return un nuevo algoritmo A*
	 */
	public static <V, E, S> AStar<V, E, S> ofGreedy(EGraph<V, E> graph) {
		GreedyOnGraph<V, E> ga = GreedyOnGraph.of(graph);
		Optional<GraphPath<V, E>> gp = ga.search();
		if(gp.isPresent()) return AStar.of(graph,null,gp.get().getWeight(),gp.get());
		else return new AStar<V, E, S>(graph,null,null,null);
	}
	
	/**
	 * Crea un algoritmo A* básico.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @return un nuevo algoritmo A*
	 */
	public static <V, E, S> AStar<V, E, S> of(EGraph<V, E> graph) {
		return new AStar<V, E, S>(graph,null,null,null);
	}
	
	/**
	 * Crea un algoritmo A* con valor inicial conocido.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @param bestValue mejor valor conocido
	 * @param optimalPath mejor camino conocido
	 * @return un nuevo algoritmo A*
	 */
	public static <V, E, S> AStar<V, E, S> of(EGraph<V, E> graph,Double bestValue,GraphPath<V, E> optimalPath) {
		return new AStar<V, E, S>(graph,null,bestValue,optimalPath);
	}
	
	/**
	 * Crea un algoritmo A* con función de solución.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @param fsolution función que transforma camino en solución
	 * @param bestValue mejor valor conocido
	 * @param optimalPath mejor camino conocido
	 * @return un nuevo algoritmo A*
	 */
	public static <V, E, S> AStar<V, E, S> of(EGraph<V, E> graph,
			Function<GraphPath<V, E>, S> fsolution,Double bestValue,GraphPath<V, E> optimalPath) {
		return new AStar<V, E, S>(graph,fsolution,bestValue,optimalPath);
	}

	/** Tipo de búsqueda. */
	private Type type;
	
	/** Comparador según el tipo de optimización. */
	public Comparator<Double> comparator;
	
	/** Grafo sobre el que se realiza la búsqueda. */
	public EGraph<V,E> graph; 
	
	/** Mapa de vértices a sus datos en el montículo. */
	public Map<V,Handle<Double,Data<V,E>>> tree;
	
	/** Montículo de Fibonacci para la cola de prioridad. */
	public FibonacciHeap<Double,Data<V,E>> heap; 
	
	/** Mejor valor estimado encontrado. */
	private Double bestValue = null;
	
	/** Mejor camino encontrado. */
	private GraphPath<V, E> optimalPath = null;
	
	/** Conjunto de soluciones (para tipo All). */
	protected Set<S> solutions;
	
	/** Función que transforma camino en solución. */
	protected Function<GraphPath<V,E>,S> fsolution;

	/**
	 * Constructor del algoritmo A*.
	 *
	 * @param graph grafo sobre el que buscar
	 * @param fsolution función de transformación a solución
	 * @param bestValue mejor valor inicial
	 * @param optimalPath mejor camino inicial
	 */
	protected AStar(EGraph<V, E> graph, Function<GraphPath<V, E>, S> fsolution, Double bestValue, GraphPath<V, E> optimalPath) {
		super();
		this.graph = graph;
		this.type = this.graph.type();
		this.comparator = switch(this.type) {
		case All -> {
			Preconditions.checkNotNull(fsolution,"Para el caso All fsolution no puede ser null"); 
			this.solutions = new HashSet<>();
			yield null;}
		case Max -> Comparator.reverseOrder();
		case Min -> Comparator.naturalOrder();
		case One -> null;
		};
		this.comparator = this.graph.type().equals(EGraph.Type.Min)?Comparator.naturalOrder():Comparator.reverseOrder();		
		this.tree = new HashMap<>();
		EGraphPath<V, E> ePath = graph.initialPath();
		this.heap = new FibonacciHeap<>(comparator);
		Data<V,E> data = Data.of(graph.startVertex(),null,ePath.getWeight());	
		Double d = this.graph.estimatedWeightToEnd(graph.startVertex(),data.distanceToOrigin);
		Handle<Double, Data<V, E>> h = this.heap.insert(d,data);
		this.tree.put(graph.startVertex(),h);
		this.bestValue = bestValue;
		this.optimalPath = optimalPath;
		this.fsolution = fsolution;
	}
	
	/**
	 * Verifica si un vértice ya ha sido cerrado (completamente procesado).
	 *
	 * @param v vértice a verificar
	 * @return true si está cerrado
	 */
	public Boolean closed(V v) {
		return this.tree.get(v).getValue().closed();
	}
	
	/**
	 * Obtiene un stream de los vértices explorados.
	 *
	 * @return stream de vértices
	 */
	public Stream<V> stream() {
		return Stream2.of(this);
	}
	
	public Iterator<V> iterator() {
		return this;
	}
	
	/**
	 * Determina si un vértice debe ser olvidado (podado).
	 *
	 * @param actualDistance distancia actual desde el origen
	 * @param v vértice a evaluar
	 * @return true si debe ser podado
	 */
	private Boolean forget(Double actualDistance, V v) {
		Double w = graph.estimatedWeightToEnd(v,actualDistance);
		Boolean r = false;
		r = this.bestValue != null && comparator.compare(w,this.bestValue) >= 0;
		if(r) this.tree.remove(v);
		return r;
	}
	
	public boolean hasNext() {
		return !heap.isEmpty(); 
	}

	@Override
	public V next() {
		Handle<Double, Data<V, E>> hActual = heap.deleteMin();
		Data<V, E> dActual = hActual.getValue();
		V vertexActual = dActual.vertex;
		Double actualDistance = dActual.distanceToOrigin;
		E edgeToOrigen = dActual.edge;
		if(forget(actualDistance,  vertexActual)) return null;
		for (E backEdge : graph.edgesListOf(vertexActual)) {
			V v = Graphs.getOppositeVertex(graph,backEdge,vertexActual);
			Double newDistanceToOrigin = graph.add(v,actualDistance,backEdge,edgeToOrigen);
			Double newDistanceToEnd =  graph.estimatedWeightToEnd(v,newDistanceToOrigin);
			if (!tree.containsKey(v)) {
				Data<V, E> dv = Data.of(v, backEdge, newDistanceToOrigin);
				Handle<Double, Data<V, E>> hv = heap.insert(newDistanceToEnd, dv);
				tree.put(v, hv);
			} else if (comparator.compare(newDistanceToOrigin,tree.get(v).getValue().distanceToOrigin()) < 0) {
				Data<V, E> dv = Data.of(v, backEdge, newDistanceToOrigin);
				Handle<Double, Data<V, E>> hv = tree.get(v);
				hv.setValue(dv);
				hv.decreaseKey(newDistanceToEnd);
			}
		}
		hActual.setValue(Data.toTrue(dActual));
		tree.put(vertexActual, hActual);
		return vertexActual;
	}

	/**
	 * Obtiene la arista que conecta un vértice con su predecesor hacia el origen.
	 *
	 * @param v vértice
	 * @return arista hacia el origen
	 */
	public E getEdgeToOrigin(V v) {
		return tree.get(v).getValue().edge;
	}

	/**
	 * Obtiene el grafo de búsqueda.
	 *
	 * @return el grafo
	 */
	public EGraph<V, E> getGraph() {
		return this.graph;
	}
	
	/**
	 * Reconstruye el camino desde el inicio hasta un vértice dado.
	 *
	 * @param startVertex vértice de inicio
	 * @param last vértice final
	 * @return camino reconstruido como Optional
	 */
	public Optional<GraphPath<V, E>> path(V startVertex, V last) {
		return this.path(startVertex,Optional.of(last));
	}
	
	/**
	 * Reconstruye el camino desde el inicio hasta un vértice dado.
	 *
	 * @param startVertex vértice de inicio
	 * @param last vértice final como Optional
	 * @return camino reconstruido como Optional
	 */
	public Optional<GraphPath<V, E>> path(V startVertex, Optional<V> last) {
		if (!last.isPresent() || !graph.goalHasSolution().test(last.get())) return Optional.empty();
		V endVertex = last.get();
		V v = endVertex;
		if (!tree.containsKey(v)) return Optional.empty();
		Handle<Double, Data<V, E>> hav = this.tree.get(v);
		Data<V, E> dav = hav.getValue();
		Double weight = dav.distanceToOrigin;
		E edge = dav.edge;
		List<E> edges = new ArrayList<>();
		while (edge != null) {
			edges.add(edge);
			v = Graphs.getOppositeVertex(graph, edge, v);
			edge = this.getEdgeToOrigin(v);
		}
		Collections.reverse(edges);
		List<V> vertices = new ArrayList<>();
		v = startVertex;
		vertices.add(v);
		for (E e : edges) {
			v = Graphs.getOppositeVertex(graph, e, v);
			vertices.add(v);
		}
		GraphPath<V, E> gp = new GraphWalk<>(graph, startVertex, endVertex, vertices, edges, weight);
		return Optional.of(gp);
	}

	/**
	 * Ejecuta la búsqueda A*.
	 *
	 * @return camino óptimo si existe
	 */
	public Optional<GraphPath<V, E>> search() {
		V startVertex = graph.startVertex();
		EGraphPath<V, E> ePath = graph.initialPath();
		Optional<GraphPath<V, E>> r = Optional.empty();
		if (graph.goal().test(startVertex))
			return Optional.of(ePath);
		switch (this.graph.type()) {
		case All:
			List<V> goals = this.stream()
				.filter(v -> v != null)
				.filter(graph.goal().and(graph.goalHasSolution()))
				.limit(this.graph.solutionNumber())
				.toList();
			for (V v : goals) {
				Optional<GraphPath<V, E>> p = this.path(startVertex, v);
				r = p;
				this.solutions.add(this.fsolution.apply(p.get()));
			}
			break;
		case Max:
		case Min:
		case One:
			Optional<V> last = this.stream().filter(v -> v != null).filter(graph.goal().and(graph.goalHasSolution()))
					.findFirst();
			if (last.isPresent())
				r = path(startVertex, last);
			else
				r = Optional.ofNullable(this.optimalPath);
		}
		return r;
	}
	
	/**
	 * Obtiene el conjunto de soluciones encontradas.
	 *
	 * @return conjunto de soluciones
	 */
	public Set<S> getSolutions() {
		return this.solutions;
	}
	
	/**
	 * Construye un grafo dirigido con los vértices y aristas explorados.
	 *
	 * @return grafo de exploración
	 */
	public SimpleDirectedGraph<V,E> outGraph(){
		SimpleDirectedGraph<V,E> g = Graphs2.simpleDirectedGraph();
		for(V v:tree.keySet()) {
			g.addVertex(v);
		}
		for(V v:tree.keySet()) {
			E e = tree.get(v).getValue().edge();
			if (e != null) {
				V source = graph.getEdgeSource(e);
				V target = graph.getEdgeTarget(e);
				g.addEdge(source, target, e);
			}
		}
		return g;
	}
	
	/**
	 * Exporta un grafo con un camino destacado a formato DOT.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param g grafo a exportar
	 * @param gp camino a destacar
	 * @param file archivo de salida
	 */
	public static <V,E> void toDot(SimpleDirectedGraph<V,E> g, GraphPath<V,E> gp, String file){
		List<V> vertices = gp.getVertexList();
		List<E> edges = gp.getEdgeList();
		GraphColors.toDot(g, file, 
				x -> x.toString(),
				x -> x.toString(),
				x -> GraphColors.colorIf(Color.red, vertices.contains(x)),
				e -> GraphColors.all(GraphColors.arrowHead(ArrowHead.none),
						GraphColors.colorIf(Color.red, edges.contains(e))));
	}
	
	/**
	 * Data
	 *
	 * <p>Registro que almacena información de un vértice durante la búsqueda A*:
	 * el vértice, la arista por la que se llegó, la distancia al origen y
	 * si ya ha sido procesado completamente.</p>
	 *
	 * @param <V> tipo del vértice
	 * @param <E> tipo de la arista
	 * @param vertex el vértice
	 * @param edge arista por la que se llegó
	 * @param distanceToOrigin distancia acumulada desde el origen
	 * @param closed si el vértice ya fue cerrado
	 *
	 * @author Miguel Toro
	 */
	public static record Data<V, E> (V vertex, E edge, Double distanceToOrigin, Boolean closed) {
		
		/**
		 * Crea un nuevo registro Data (no cerrado).
		 *
		 * @param <V> tipo del vértice
		 * @param <E> tipo de la arista
		 * @param vertex el vértice
		 * @param edge arista de llegada
		 * @param distance distancia al origen
		 * @return nuevo Data
		 */
		public static <V, E> Data<V, E> of(V vertex, E edge, Double distance) {
			return new Data<>(vertex, edge, distance,false);
		}

		/**
		 * Crea una copia del Data marcada como cerrada.
		 *
		 * @param <V> tipo del vértice
		 * @param <E> tipo de la arista
		 * @param d Data original
		 * @return Data con closed=true
		 */
		public static <V, E> Data<V, E> toTrue(Data<V, E> d) {
			return new Data<>(d.vertex, d.edge, d.distanceToOrigin,true);
		}

	}
	
}
