package us.lsi.path;

import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.PDR.Sp;
import us.lsi.graphs.virtual.EGraph;

/**
 * EGraphPath
 *
 * <p>Interfaz que extiende GraphPath de JGraphT con funcionalidades
 * adicionales para algoritmos de busqueda. Soporta diferentes tipos
 * de calculo de peso (suma o ultimo valor).</p>
 *
 * @param <V> tipo de los vertices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 */
public interface EGraphPath<V, E> extends GraphPath<V, E> {	
	/**
	 * Obtiene el grafo asociado.
	 *
	 * @return el grafo
	 */
	EGraph<V,E> graph();
	
	/**
	 * Obtiene la ultima arista del camino.
	 *
	 * @return ultima arista
	 */
	E lastEdge();
	
	/**
	 * Anade una arista al camino.
	 *
	 * @param edge arista a anadir
	 * @return el camino modificado
	 */
	EGraphPath<V, E> add(E edge);
	
	/**
	 * Elimina la ultima arista del camino.
	 *
	 * @return el camino modificado
	 */
	EGraphPath<V, E> remove();
	
	/**
	 * Calcula el valor acumulado al anadir una arista.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @param edgeOut arista de salida
	 * @param edgeIn arista de entrada
	 * @return nuevo valor acumulado
	 */
	Double add(V vertexActual, Double acumulateValue, E edgeOut, E edgeIn);
	
	/**
	 * Calcula el valor acumulado con valor final conocido.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @param toEnd valor hasta el final
	 * @param edgeOut arista de salida
	 * @param edgeIn arista de entrada
	 * @return nuevo valor acumulado
	 */
	Double add(V vertexActual, Double acumulateValue, Double toEnd,E edgeOut, E edgeIn);
	
	/**
	 * Crea una copia del camino.
	 *
	 * @return copia del camino
	 */
	EGraphPath<V, E> copy();
	
	/**
	 * Calcula una cota del valor de la solucion.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @param edge arista
	 * @param goal predicado de objetivo
	 * @param end vertice final
	 * @param heuristic funcion heuristica
	 * @return valor acotado
	 */
	Double boundedValue(V vertexActual,Double acumulateValue,E edge,Predicate<V> goal,V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic);
	
	/**
	 * Estima el peso hasta el objetivo.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @param goal predicado de objetivo
	 * @param end vertice final
	 * @param heuristic funcion heuristica
	 * @return peso estimado
	 */
	Double estimatedWeightToEnd(V vertexActual,Double acumulateValue,Predicate<V> goal,V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic);
	
	/**
	 * Concatena otro camino al final.
	 *
	 * @param path camino a concatenar
	 * @return camino concatenado
	 */
	EGraphPath<V, E> concat(GraphPath<V,E> path);
	
	/**
	 * Invierte el camino.
	 *
	 * @return camino invertido
	 */
	GraphPath<V, E> reverse();
	
	/**
	 * Obtiene el tipo de camino.
	 *
	 * @return tipo de camino
	 */
	PathType type();
	
	/**
	 * Tipos de calculo de peso para caminos.
	 */
	public static enum PathType{
		/** Peso es suma de aristas. */
		Sum,
		/** Peso es el ultimo valor. */
		Last
	}	
	
	/**
	 * Calcula el peso de un camino sumando pesos de aristas.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param path el camino
	 * @return peso total
	 */
	public static <V,E> Double weight(GraphPath<V,E> path) {
		return path.getEdgeList().stream().mapToDouble(e->path.getGraph().getEdgeWeight(e)).sum();
	}
	
	/**
	 * Crea un camino a partir de un mapa de soluciones.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @param vertex vertice inicial
	 * @param solutions mapa de soluciones
	 * @return nuevo camino
	 */
	public static <V, E> GraphPathSum<V, E> ofMap(EGraph<V,E> graph, V vertex, Map<V,Sp<E>> solutions) {
		return GraphPathSum.ofMap(graph, vertex, solutions);
	}
	
	/**
	 * Crea un camino desde un vertice.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @param vertex vertice inicial
	 * @param type tipo de camino
	 * @return nuevo camino
	 */
	public static <V, E> EGraphPath<V, E> ofVertex(EGraph<V, E> graph, V vertex, PathType type){
		EGraphPath<V, E> r = null;
		switch(type) {
		case Sum: r =  GraphPathSum.ofVertex(graph, vertex); break;
		case Last: r = GraphPathLast.ofVertex(graph, vertex); break;
		}
		return r;
	}

}
