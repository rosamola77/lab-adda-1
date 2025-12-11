package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.streams.Stream2;

/**
 * LocalSearch (Búsqueda Local)
 *
 * <p>Implementación de algoritmo de búsqueda local para optimización en grafos.
 * Explora el espacio de soluciones moviéndose iterativamente a vecinos que
 * mejoren el valor de la función objetivo, hasta alcanzar un óptimo local.</p>
 *
 * <p>La búsqueda continúa mientras encuentre vecinos mejores con una diferencia
 * superior al error especificado. Selecciona aleatoriamente entre los n mejores
 * vecinos para evitar quedar atrapado en óptimos locales subóptimos.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = ...;
 * LocalSearch<V,E> ls = LocalSearch.of(graph, 0.01, 5);
 * Optional<V> optimo = ls.search();
 * }</p>
 *
 * @param <V> tipo de los vértices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see EGraph
 */
public class LocalSearch<V,E> implements  Iterator<V>, Iterable<V>{
	
	/**
	 * Crea un algoritmo de búsqueda local.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @param error error mínimo de mejora para continuar
	 * @param n número de vecinos a considerar aleatoriamente
	 * @return nuevo LocalSearch
	 */
	public static <V, E> LocalSearch<V, E> of(EGraph<V, E> graph, Double error, Integer n) {
		return new LocalSearch<V, E>(graph, error, n);
	}
	
	/**
	 * Repite la búsqueda local desde múltiples puntos de inicio aleatorios
	 * y retorna la mejor solución encontrada.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @param start generador de vértices iniciales aleatorios
	 * @param error error mínimo de mejora
	 * @param n número de vecinos a considerar
	 * @param m número de intentos con diferentes puntos de inicio
	 * @return mejor vértice encontrado entre todos los intentos
	 */
	public static <V extends VirtualVertex<V, E, ?>, E extends SimpleEdgeAction<V, ?>> V repeat(EGraph<V, E> graph,
			Supplier<V> start, Double error, Integer n, Integer m) {
		Double w = null;
		V v = null;
		int i = 0;
		while (i < m) {
			V s = start.get();
			EGraph<V, E> g = EGraph.virtual(s).vertexWeight(x -> graph.getVertexWeight(x)).build();
			LocalSearch<V, E> ls = LocalSearch.of(g, error, n);
			V vr = Stream2.findLast(ls.stream()).get();
			if (v == null || graph.getVertexWeight(vr) < w) {
				w = graph.getVertexWeight(vr);
				v = vr;
			}
			i++;
		}
		return v;
	}

	/** Grafo sobre el que se realiza la búsqueda. */
	private EGraph<V,E> graph;
	/** Vértice actual en la búsqueda. */
	private V actualVertex;
	/** Vértice inicial. */
	private V startVertex;
	/** Camino recorrido (lista de vértices visitados). */
	private List<V> path;
	/** Vértice anterior. */
	private V oldVertex;
	/** Error mínimo de mejora para continuar. */
	private Double error;
	/** Indica si hay siguiente iteración. */
	private Boolean hasNext = true;
	/** Número de vecinos a considerar aleatoriamente. */
	private Integer n;
	
	/**
	 * Constructor privado.
	 *
	 * @param graph el grafo
	 * @param error error mínimo de mejora
	 * @param n número de vecinos a considerar
	 */
	LocalSearch(EGraph<V, E> graph, Double error, Integer n) {
		this.graph = graph;
		this.startVertex = graph.startVertex();
		this.actualVertex = this.startVertex;
		this.path = new ArrayList<>();
		this.oldVertex = null;
		this.error = error;
		this.hasNext = true;
		this.n = n;
	}
	
	/**
	 * Crea una copia independiente del algoritmo.
	 *
	 * @return nueva instancia con los mismos parámetros
	 */
	public LocalSearch<V,E> copy(){
		return LocalSearch.of(this.graph,this.error,this.n);	
	}
	
	/**
	 * Obtiene stream de vértices visitados.
	 *
	 * @return stream de vértices
	 */
	public Stream<V> stream() {
		return Stream2.of(this);
	}
	
	/**
	 * Ejecuta la búsqueda local hasta alcanzar un óptimo local.
	 *
	 * @return el vértice con mejor valor encontrado
	 */
	public Optional<V> search(){
		return Stream2.findLast(this.stream());
	}
	
	/**
	 * Obtiene el iterador de vértices.
	 *
	 * @return este iterador
	 */
	public Iterator<V> iterator() {
		return this;
	}

	/**
	 * Selecciona el siguiente vértice entre los vecinos que mejoren el actual.
	 * Filtra los vecinos con mejor peso, toma una muestra aleatoria de n vecinos
	 * y retorna el mejor de ellos.
	 *
	 * @param v vértice actual
	 * @return siguiente vértice o null si no hay mejora
	 */
	public V nextVertex(V v) {
		List<E> ls1 = graph.edgesListOf(v).stream()
			.filter(e -> this.graph.getVertexWeight(v) > this.graph.getVertexWeight(this.graph.oppositeVertex(e, v)))
			.collect(Collectors.toList());
		List<E> ls2 = List2.random(ls1, n);
		return ls2.stream().map(e -> this.graph.oppositeVertex(e, v))
				.min(Comparator.comparing(x -> this.graph.getVertexWeight(x)))
				.orElse(null);
	}

	/**
	 * Obtiene el grafo.
	 *
	 * @return el grafo
	 */
	public EGraph<V, E> getGraph() {
		return graph;
	}
	
	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public V next() {
		this.oldVertex = this.actualVertex;
		this.path.add(oldVertex);
		this.actualVertex = this.nextVertex(this.oldVertex);
		this.hasNext = this.actualVertex != null && !this.path.contains(this.actualVertex) && 
				Math.abs(graph.getVertexWeight(this.oldVertex) - graph.getVertexWeight(this.actualVertex)) >= this.error;
		return this.oldVertex;
	}
	
	/**
	 * Obtiene el vértice inicial.
	 *
	 * @return el vértice de inicio
	 */
	public V startVertex() {
		return this.startVertex;
	}
	
}
