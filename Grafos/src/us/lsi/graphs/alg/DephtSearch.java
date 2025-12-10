package us.lsi.graphs.alg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graphs;

import us.lsi.graphs.virtual.EGraph;
import us.lsi.streams.Stream2;

/**
 * DephtSearch
 *
 * <p>Implementación del algoritmo de búsqueda en profundidad (DFS - Depth-First Search)
 * en preorden para recorrer grafos. Explora el grafo siguiendo un camino hasta el
 * final antes de retroceder y explorar otras alternativas.</p>
 *
 * <p>Características:
 * <ul>
 * <li>Utiliza una pila (Stack) para mantener el orden de exploración</li>
 * <li>Realiza un recorrido en preorden (visita el vértice antes que sus descendientes)</li>
 * <li>Implementa Iterator e Iterable para permitir recorridos</li>
 * <li>Mantiene un mapa de aristas al origen para reconstruir caminos</li>
 * <li>Útil para detectar ciclos y ordenación topológica</li>
 * </ul></p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = ...;
 * DephtSearch<V,E> dfs = DephtSearch.of(graph, startVertex);
 * for (V vertex : dfs) {
 *     System.out.println(vertex);
 * }
 * }</p>
 *
 * @param <V> tipo de los vértices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see EGraph
 * @see DephtPostSearch
 */
public class DephtSearch<V, E> implements Iterator<V>, Iterable<V> {
	
	/**
	 * Crea un nuevo algoritmo de búsqueda en profundidad en preorden.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param g grafo sobre el que realizar la búsqueda
	 * @param startVertex vértice inicial desde donde comenzar la búsqueda
	 * @return una nueva instancia de DephtSearch configurada
	 */
	public static <V, E> DephtSearch<V, E> of(EGraph<V, E> g, V startVertex) {
		return new DephtSearch<V, E>(g, startVertex);
	}


	protected Map<V,E> edgeToOrigin;
	public EGraph<V,E> graph;
	protected Stack<V> stack;
	protected V startVertex; 

	DephtSearch(EGraph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.stack = new Stack<>();
		this.stack.add(startVertex);
	}
	
	public Stream<V> stream() {
		return Stream2.of(this);
	}
	
	public DephtSearch<V, E> copy() {
		return DephtSearch.of(this.graph, this.startVertex);
	}
	
	public Iterator<V> iterator() {
		return this;
	}
	
	public boolean isSeenVertex(V v) {
		return this.edgeToOrigin.containsKey(v);
	}
	
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public V next() {
		V actual = stack.pop();
		for(V v:Graphs.neighborListOf(graph, actual)) {
			if(!this.edgeToOrigin.containsKey(v)) {
				stack.add(v);
				this.edgeToOrigin.put(v,graph.getEdge(actual, v));
			}
		}
		return actual;
	}

	public E getEdgeToOrigin(V v) {
		return this.edgeToOrigin.get(v);
	}
	
	public V startVertex() {
		return this.startVertex;
	}
	
	public Set<E> edges() {
		return this.edgeToOrigin.values().stream().collect(Collectors.toSet());
	}

	

}
