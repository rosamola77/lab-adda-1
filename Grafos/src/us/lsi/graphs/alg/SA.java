package us.lsi.graphs.alg;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;
import us.lsi.streams.Stream2;

/**
 * SA (Simulated Annealing - Recocido Simulado)
 *
 * <p>Implementación del algoritmo de recocido simulado para optimización en grafos.
 * Es una metaheurística inspirada en el proceso de recocido en metalurgia que permite
 * escapar de óptimos locales mediante la aceptación probabilística de soluciones peores.</p>
 *
 * <p>La probabilidad de aceptar una solución peor decrece con la temperatura según
 * la distribución de Boltzmann. La temperatura disminuye gradualmente según un
 * esquema de enfriamiento controlado por el parámetro alfa.</p>
 *
 * <p>Parámetros configurables:
 * <ul>
 * <li>{@code numPorIntento} - número máximo de iteraciones (default: 200)</li>
 * <li>{@code numMismaTemperatura} - iteraciones por temperatura (default: 10)</li>
 * <li>{@code temperaturaInicial} - temperatura inicial (default: 1000)</li>
 * <li>{@code alfa} - factor de enfriamiento (default: 0.97)</li>
 * <li>{@code stop} - predicado de parada temprana</li>
 * </ul></p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = ...;
 * Function<V,Double> fitness = v -> ...; // menor es mejor
 * SA<V,E> sa = SA.simulatedAnnealing(graph, verticeInicial, fitness);
 * Optional<V> mejorSolucion = sa.search();
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
public class SA<V,E> implements Iterator<V>, Iterable<V> {
	
	/**
	 * Crea un algoritmo de recocido simulado.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @param startVertex vértice inicial
	 * @param fitness función de aptitud (menor valor es mejor)
	 * @return nuevo SA
	 */
	public static <V, E> SA<V, E> simulatedAnnealing(EGraph<V, E> graph, V startVertex,
			Function<V, Double> fitness) {
		return new SA<V, E>(graph, startVertex, fitness);
	}

	/** Grafo sobre el que se realiza la búsqueda. */
	private EGraph<V,E> graph;
	/** Vértice actual. */
	private V actualVertex;
	/** Vértice inicial. */
	private V startVertex;
	/** Función de aptitud (menor es mejor). */
	private Function<V,Double> fitness;
	/** Temperatura actual del sistema. */
	private double temperatura;
	/** Mejor vértice encontrado hasta el momento. */
	public V bestVertex;
	/** Mejor valor de aptitud encontrado. */
	public Double bestWeight;
	
		
	/**
	 * Constructor privado.
	 *
	 * @param graph el grafo
	 * @param startVertex vértice inicial
	 * @param fitness función de aptitud
	 */
	SA(EGraph<V, E> graph, V startVertex,Function<V, Double> fitness) {
		super();
		this.graph = graph;
		this.actualVertex = null;
		this.startVertex = startVertex;
		this.fitness = fitness;
		this.i = 0;
		this.s = 0;
		this.temperatura = temperaturaInicial;
		this.actualVertex = this.startVertex;
	}

	/**
	 * Obtiene el grafo.
	 *
	 * @return el grafo
	 */
	public EGraph<V, E> getGraph() {
		return graph;
	}
	
	/**
	 * Selecciona aleatoriamente un vecino del vértice actual.
	 *
	 * @param vertex vértice actual (no utilizado, se usa this.actualVertex)
	 * @return vértice vecino aleatorio
	 */
	private V nextVertex(V vertex) {
		 List<E> edges = this.graph.edgesListOf(this.actualVertex);
		 List<E> edge = List2.randomUnitary(edges);
		 return this.graph.getEdgeTarget(edge.get(0));
	}
	
	/** Número máximo de iteraciones. */
	public static Integer numPorIntento = 200;
	/** Número de iteraciones con la misma temperatura. */
	public static Integer numMismaTemperatura = 10;
	/** Temperatura inicial del sistema. */
	public static double temperaturaInicial = 1000;
	/** Factor de enfriamiento (0 < alfa < 1). */
	public static double alfa = 0.97;
	/** Predicado de parada temprana basado en el mejor valor. */
	public static Predicate<Double> stop = e->false;
	/** Contador de iteraciones totales. */
	public Integer i;
	/** Contador de iteraciones a la misma temperatura. */
	public Integer s;
	
	

	/**
	 * Actualiza el mejor valor encontrado si el vértice actual es mejor.
	 */
	private void actualizaMejorValor() {
		Double w = this.fitness.apply(this.actualVertex);
		if (this.bestWeight == null ||  w < this.bestWeight) {
			this.bestVertex= this.actualVertex;	
			this.bestWeight = w;
		}
	}

	/**
	 * Calcula la siguiente temperatura según el esquema de enfriamiento.
	 *
	 * @param i número de iteración (no utilizado en esquema actual)
	 * @return nueva temperatura
	 */
	private double nexTemperatura(Integer i) {
		return alfa * temperatura;
		// return temperaturaInicial/Math.log(2+3*i);
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
	 * Ejecuta el algoritmo de recocido simulado.
	 *
	 * @return el último vértice visitado
	 */
	public Optional<V> search(){
		return Stream2.findLast(this.stream());
	}
	
	@Override
	public Iterator<V> iterator() {
		return this;
	}


	@Override
	public boolean hasNext() {
		return this.i < numPorIntento && !SA.stop.test(this.bestWeight);
	}

	@Override
	public V next() {
		this.temperatura = nexTemperatura(i);
		V nv = nextVertex(this.actualVertex);	
		Double incr = fitness.apply(nv) - fitness.apply(this.actualVertex);
		if (Math2.aceptaBoltzmann(incr,temperatura)) {
			this.actualVertex = nv;
			actualizaMejorValor();
		}
		this.s++;
		this.i++;
		if(this.s >= numMismaTemperatura) this.s = 0;
		return this.actualVertex;
	}

	/**
	 * Obtiene la arista al vértice origen (no implementado).
	 *
	 * @param v el vértice
	 * @return la arista
	 * @throws UnsupportedOperationException siempre
	 */
	public E getEdgeToOrigin(V v) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtiene el vértice inicial.
	 *
	 * @return el vértice de inicio
	 */
	public V startVertex() {
		return this.startVertex;
	}

	/**
	 * Crea una copia independiente del algoritmo.
	 *
	 * @return nueva instancia con los mismos parámetros
	 */
	public SA<V, E> copy() {
		return new SA<>(graph,startVertex,fitness);
	}

	
	
	
}
