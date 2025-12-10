package us.lsi.graphs.alg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import us.lsi.graphs.virtual.EGraph;
import us.lsi.streams.Stream2;

/**
 * DephtPostSearch
 *
 * <p>Implementación de búsqueda en profundidad en postorden (DFS postorder).
 * Este algoritmo recorre el grafo primero completando todos los subárboles
 * antes de visitar un vértice, lo que resulta útil para ordenamiento
 * topológico inverso.</p>
 *
 * <p>El postorden garantiza que un vértice se visita después de todos
 * sus descendientes en el árbol de expansión DFS.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = ...;
 * DephtPostSearch<V,E> dps = DephtPostSearch.of(graph, startVertex);
 * dps.stream().forEach(v -> System.out.println(v));
 * }</p>
 *
 * @param <V> tipo de los vértices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see DephtSearch
 * @see EGraph
 */
public class DephtPostSearch<V, E> implements Iterator<V>, Iterable<V>  {
	
	/**
	 * @param <V> El tipo de los v&eacute;rtices
	 * @param <E> El tipo de las aristas
	 * @param g Un grafo 
	 * @param startVertex El v�rtice inicial
	 * @return Una algoritmo de b&uacute;squeda en profundidad en postorden
	 */
	public static <V, E> DephtPostSearch<V, E> of(EGraph<V, E> g, V startVertex) {
		return new DephtPostSearch<V, E>(g, startVertex);
	}

	protected Map<V,E> edgeToOrigin;
	public EGraph<V,E> graph;
	protected Stack<V> stackPre;
	protected Stack<V> stackPost;
	protected V startVertex; 
	public Graph<V,E> outGraph;
	public Boolean withGraph = false;

	DephtPostSearch(EGraph<V, E> g, V startVertex) {
		this.graph = g;
		this.startVertex = startVertex;
		this.edgeToOrigin = new HashMap<>();
		this.edgeToOrigin.put(startVertex, null);
		this.stackPre = new Stack<>();
		this.stackPre.add(startVertex);
		this.stackPost = new Stack<>();
		this.preorder();
	}
	
	public Stream<V> stream() {
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
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
	
	public boolean hasNextP() {
		return !stackPre.isEmpty();
	}

	public V nextP() {
		V actual = stackPre.pop();
		if(this.withGraph) outGraph.addVertex(actual);
		this.stackPost.add(actual);
		for(V v:Graphs.neighborListOf(graph, actual)) {
			if(!this.edgeToOrigin.containsKey(v)) {
				stackPre.add(v);
				this.edgeToOrigin.put(v,graph.getEdge(actual, v));	
				if(this.withGraph) {
					outGraph.addVertex(v);
					outGraph.addEdge(actual,v,graph.getEdge(actual, v));
				}
			}
		}
		return actual;
	}
	
	private void preorder() {
		while(this.hasNextP()) {
			this.nextP();
		}
	}
	
	@Override
	public boolean hasNext() {
		return !stackPost.isEmpty();
	}

	@Override
	public V next() {
		V v = stackPost.pop();
		return v;
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
