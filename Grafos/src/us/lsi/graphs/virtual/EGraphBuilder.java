package us.lsi.graphs.virtual;

import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

/**
 * EGraphBuilder
 *
 * <p>Interfaz builder para construir grafos extendidos (EGraph) de forma fluida.
 * Permite configurar pesos, vértices de inicio/fin, heurísticas y tipos de camino.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = EGraph.ofGraph(baseGraph)
 *     .startVertex(inicio)
 *     .endVertex(objetivo)
 *     .edgeWeight(e -> e.getWeight())
 *     .heuristic(heuristica)
 *     .build();
 * }</p>
 *
 * @param <V> tipo de los vértices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 */
public interface EGraphBuilder<V, E> {

	/**
	 * Establece la función de peso de aristas.
	 *
	 * @param edgeWeight función que calcula el peso de una arista
	 * @return este builder
	 */
	EGraphBuilder<V, E> edgeWeight(Function<E, Double> edgeWeight);

	/**
	 * Establece la función de peso de vértices.
	 *
	 * @param vertexWeight función que calcula el peso de un vértice
	 * @return este builder
	 */
	EGraphBuilder<V, E> vertexWeight(Function<V, Double> vertexWeight);

	/**
	 * Establece la función de peso de paso por vértice.
	 *
	 * @param vertexPassWeight función que calcula el peso al pasar por un vértice entre dos aristas
	 * @return este builder
	 */
	EGraphBuilder<V, E> vertexPassWeight(TriFunction<V, E, E, Double> vertexPassWeight);

	/**
	 * Establece el vértice inicial.
	 *
	 * @param startVertex vértice de inicio
	 * @return este builder
	 */
	EGraphBuilder<V, E> startVertex(V startVertex);

	/**
	 * Establece el vértice final.
	 *
	 * @param endVertex vértice objetivo
	 * @return este builder
	 */
	EGraphBuilder<V, E> endVertex(V endVertex);

	/**
	 * Establece el tipo de camino a buscar.
	 *
	 * @param pathType tipo de camino (Min, Max, One, All)
	 * @return este builder
	 */
	EGraphBuilder<V, E> pathType(PathType pathType);

	/**
	 * Establece la función heurística para algoritmos informados.
	 *
	 * @param heuristic función que estima el coste desde un vértice al objetivo
	 * @return este builder
	 */
	EGraphBuilder<V, E> heuristic(TriFunction<V, Predicate<V>, V, Double> heuristic);

	/**
	 * Establece el tipo de grafo.
	 *
	 * @param type tipo de grafo (Virtual, Direct)
	 * @return este builder
	 */
	EGraphBuilder<V, E> type(Type type);
	
	/**
	 * Establece el número de soluciones a buscar.
	 *
	 * @param n número de soluciones
	 * @return este builder
	 */
	EGraphBuilder<V, E> solutionNumber(Integer n);

	/**
	 * Construye el grafo extendido con la configuración establecida.
	 *
	 * @return nuevo grafo extendido
	 */
	EGraph<V, E> build();

}