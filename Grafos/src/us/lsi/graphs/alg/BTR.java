package us.lsi.graphs.alg;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.math.Math2;

/**
 * BTR (Backtracking Randomizado)
 *
 * <p>Extension del algoritmo de Backtracking que introduce aleatoriedad
 * en la seleccion de alternativas cuando el espacio de busqueda supera
 * un umbral. Util para problemas grandes donde el backtracking exhaustivo
 * seria inviable.</p>
 *
 * <p>Repite la busqueda con diferentes elecciones aleatorias hasta
 * encontrar una solucion que cumpla el criterio de parada.</p>
 *
 * @param <V> tipo de los vertices
 * @param <E> tipo de las aristas
 * @param <S> tipo de la solucion (debe ser Comparable)
 *
 * @author Miguel Toro
 */
public class BTR<V,E,S extends Comparable<S>> extends BT<V,E,S> {
	
	/**
	 * Crea un algoritmo BTR con los parametros dados.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solucion
	 * @param graph el grafo
	 * @param solution funcion que transforma camino en solucion
	 * @param size funcion que calcula el tamano del espacio de busqueda
	 * @param threshold umbral para activar seleccion aleatoria
	 * @return nuevo BTR
	 */
	public static <V, E, S extends Comparable<S>> BTR<V, E, S> of(
			EGraph<V, E> graph, 
			Function<GraphPath<V, E>, S> solution, 
			Function<V, Integer> size,
			Integer threshold) {
		return new BTR<V, E, S>(graph,solution, size,threshold);
	}
	

	/**
	 * Constructor de BTR.
	 *
	 * @param graph el grafo
	 * @param solution funcion de transformacion
	 * @param size funcion de tamano
	 * @param threshold umbral de aleatoriedad
	 */
	BTR(EGraph<V, E> graph, 
			Function<GraphPath<V, E>, S> solution,
			Function<V,Integer> size,
			Integer threshold) {
		super(graph, solution, null,null, false);
		this.size = size;
		this.threshold = threshold;
	}
	
	/** Funcion que calcula el tamano del espacio de busqueda. */
	protected Function<V,Integer> size;
	/** Numero de iteraciones ejecutadas. */
	public Integer iterations;
	/** Umbral para activar seleccion aleatoria. */
	public Integer threshold;
	
	
	/**
	 * Ejecuta la busqueda con reintentos aleatorios.
	 *
	 * @return camino optimo encontrado
	 */
	@Override
	public Optional<GraphPath<V, E>> search() {
		State<V,E> initialState = StatePath.of(graph,graph.goal(),graph.endVertex());
		this.iterations = 0;
		Math2.initRandom();
		while (!this.stop) {
			this.iterations++;
			search(initialState);
		}
		return this.optimalPath();
	}
	
	/**
	 * Busqueda recursiva con seleccion aleatoria condicional.
	 *
	 * @param state estado actual
	 */
	@Override
	public void search(State<V, E> state) {
		V actual = state.getActualVertex();
		if (graph.goal().test(actual)) 
			update(state);		
		else {
			List<E> edges = graph.edgesListOf(actual);
			if(size.apply(actual) > this.threshold) edges = List2.randomUnitary(edges);
			for (E edge : edges) {				
				state.forward(edge);
				search(state);
				if(this.stop) return;
				state.back(edge);
			}
		}
	}

	

}
