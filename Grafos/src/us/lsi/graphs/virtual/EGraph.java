package us.lsi.graphs.virtual;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.TriFunction;
import us.lsi.path.EGraphPath;
import us.lsi.path.EGraphPath.PathType;

/**
 * EGraph (Extended Graph)
 *
 * <p>Interfaz que extiende Graph de JGraphT con funcionalidades adicionales
 * para algoritmos de busqueda. Proporciona informacion sobre vertices
 * iniciales, objetivos, heuristicas y tipos de caminos.</p>
 *
 * <p>Soporta diferentes tipos de busqueda:
 * <ul>
 *   <li>Min: minimizar peso del camino</li>
 *   <li>Max: maximizar peso del camino</li>
 *   <li>One: encontrar una solucion</li>
 *   <li>All: encontrar todas las soluciones</li>
 * </ul>
 * </p>
 *
 * @param <V> tipo de los vertices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 */
public interface EGraph<V, E> extends Graph<V, E> {
	
	/**
	 * Crea un builder para EGraph a partir de un grafo existente.
	 *
	 * @param <G> tipo del grafo
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph grafo base
	 * @return nuevo builder
	 */
	public static <G extends Graph<V,E>, V,E> EGraphBuilder<V, E> 
		ofGraph(G graph){
		return new EGraphBuilderGraph<G,V,E>(graph);
	}
	
	/**
	 * Crea un builder para EGraph con vertice inicial y objetivo.
	 *
	 * @param <G> tipo del grafo
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph grafo base
	 * @param startVertex vertice inicial
	 * @param goal predicado de objetivo
	 * @return nuevo builder
	 */
	public static <G extends Graph<V, E>, V, E> EGraphBuilder<V, E> 
		ofGraph(G graph, V startVertex, Predicate<V> goal) {
		return new EGraphBuilderGraph<G, V, E>(graph, startVertex, goal);
	}
	
	/**
	 * Crea un builder para EGraph con todos los parametros.
	 *
	 * @param <G> tipo del grafo
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph grafo base
	 * @param startVertex vertice inicial
	 * @param goal predicado de objetivo
	 * @param pathType tipo de camino
	 * @param type tipo de busqueda
	 * @return nuevo builder
	 */
	public static <G extends Graph<V,E>, V,E> EGraphBuilder<V, E>
		ofGraph(G graph,V startVertex,Predicate<V> goal,PathType pathType,Type type){
		return new EGraphBuilderGraph<G,V,E>(graph,startVertex,goal,pathType,type);
	}
	
	/**
	 * Crea un builder para grafo virtual.
	 *
	 * @param <V> tipo de los vertices virtuales
	 * @param <E> tipo de las aristas
	 * @return nuevo builder virtual
	 */
	public static <V extends VirtualVertex<V,E,?>, E extends SimpleEdgeAction<V,?>> EGraphBuilder<V, E> 
		virtual(){
		return new EGraphBuilderVirtual<V,E>();
	}
	
	/**
	 * Crea un builder para grafo virtual con vertice inicial.
	 *
	 * @param <V> tipo de los vertices virtuales
	 * @param <E> tipo de las aristas
	 * @param startVertex vertice inicial
	 * @return nuevo builder virtual
	 */
	public static <V extends VirtualVertex<V, E, ?>, E extends SimpleEdgeAction<V, ?>> EGraphBuilder<V, E>
		virtual(V startVertex) {
		return new EGraphBuilderVirtual<V, E>(startVertex);
	}
	
//	public static <V extends VirtualVertex<V,E,?>, E extends SimpleEdgeAction<V,?>> EGraphBuilder<V, E>
//		virtual(V startVertex,PathType pathType,Type type){
//		return new EGraphBuilderVirtual<V,E>(startVertex,pathType,type);
//	}
	
	/**
	 * Crea un builder para grafo virtual generico.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph grafo base
	 * @param startVertex vertice inicial
	 * @param pathType tipo de camino
	 * @param type tipo de busqueda
	 * @return nuevo builder
	 */
	public static <V, E> EGraphBuilderVirtualG<V, E> 
		virtualG(Graph<V, E> graph, V startVertex,PathType pathType, Type type) {
		return new EGraphBuilderVirtualG<V, E>(graph, startVertex,pathType, type);
	}
	
	/**
	 * Crea un builder para multigrafo virtual.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph multigrafo base
	 * @param startVertex vertice inicial
	 * @param goal predicado de objetivo
	 * @param pathType tipo de camino
	 * @param type tipo de busqueda
	 * @return nuevo builder
	 */
	public static <V, E> EGraphBuilderVirtualMG<V, E> 
			virtualMG(DirectedMultigraph<V, E> graph, V startVertex, Predicate<V> goal,PathType pathType, Type type) {
		return new EGraphBuilderVirtualMG<V, E>(graph, startVertex, goal, pathType, type);
	}

	/**
	 * Obtiene el peso de paso por un vertice.
	 *
	 * @param vertex el vertice
	 * @param edgeIn arista de entrada
	 * @param edgeOut arista de salida
	 * @return peso de paso
	 */
	double getVertexPassWeight(V vertex, E edgeIn, E edgeOut);

	/**
	 * Obtiene el peso de un vertice.
	 *
	 * @param vertex el vertice
	 * @return peso del vertice
	 */
	double getVertexWeight(V vertex);

	/**
	 * Obtiene lista de aristas desde un vertice.
	 *
	 * @param v el vertice
	 * @return lista de aristas salientes
	 */
	List<E> edgesListOf(V v);

	/**
	 * Obtiene el camino inicial (vacio).
	 *
	 * @return camino inicial
	 */
	EGraphPath<V, E> initialPath();

	/**
	 * Obtiene el vertice opuesto de una arista.
	 *
	 * @param edge la arista
	 * @param v vertice conocido
	 * @return vertice opuesto
	 */
	V oppositeVertex(E edge, V v);

	/**
	 * Obtiene el vertice inicial de busqueda.
	 *
	 * @return vertice inicial
	 */
	V startVertex();

	/**
	 * Obtiene el predicado de objetivo.
	 *
	 * @return predicado que indica si un vertice es objetivo
	 */
	Predicate<V> goal();

	/**
	 * Obtiene el vertice final (si existe).
	 *
	 * @return vertice final
	 */
	V endVertex();
	
	/**
	 * Obtiene el predicado que indica si un objetivo tiene solucion.
	 *
	 * @return predicado de solucion valida
	 */
	Predicate<V> goalHasSolution();
	
	/**
	 * Obtiene la funcion de seleccion voraz.
	 *
	 * @return funcion que dado un vertice devuelve la arista voraz
	 */
	Function<V, E> greedyEdge();

	/**
	 * Obtiene el tipo de camino.
	 *
	 * @return tipo de camino (Sum, Last, etc.)
	 */
	PathType pathType();
	
	/**
	 * Obtiene la funcion heuristica.
	 *
	 * @return funcion heuristica
	 */
	TriFunction<V, Predicate<V>, V, Double> heuristic(); 
	
	/**
	 * Tipos de busqueda soportados.
	 */
	public static enum Type{
		/** Minimizar valor objetivo. */
		Min,
		/** Maximizar valor objetivo. */
		Max,
		/** Encontrar todas las soluciones. */
		All,
		/** Encontrar una solucion. */
		One
	}
	
	/**
	 * Obtiene el tipo de busqueda.
	 *
	 * @return tipo de busqueda
	 */
	Type type();
	
	/**
	 * Obtiene el numero maximo de soluciones a buscar.
	 *
	 * @return numero de soluciones
	 */
	Integer solutionNumber();
		
	/**
	 * Calcula el valor acumulado al anadir una arista.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @param edgeOut arista de salida
	 * @param edgeIn arista de entrada
	 * @return nuevo valor acumulado
	 */
	public default Double add(V vertexActual, Double acumulateValue, E edgeOut, E edgeIn) {
		return this.initialPath().add(vertexActual, acumulateValue, edgeOut, edgeIn);
	}

	/**
	 * Calcula una cota del valor de la solucion.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @param edgeOut arista de salida
	 * @param heuristic funcion heuristica
	 * @return valor acotado
	 */
	public default Double boundedValue(V vertexActual, Double acumulateValue, E edgeOut,
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		return this.initialPath().boundedValue(vertexActual, acumulateValue, edgeOut, this.goal(), this.endVertex(),
				heuristic);
	}

	/**
	 * Estima el peso hasta el objetivo.
	 *
	 * @param vertexActual vertice actual
	 * @param acumulateValue valor acumulado
	 * @return peso estimado total
	 */
	public default Double estimatedWeightToEnd(V vertexActual, Double acumulateValue) {
		return this.initialPath().estimatedWeightToEnd(vertexActual, acumulateValue, this.goal(), this.endVertex(),
				this.heuristic());
	}

	/**
	 * Obtiene el valor de solucion en un objetivo.
	 *
	 * @param vertexActual vertice objetivo
	 * @return valor de la solucion
	 */
	public default Double goalSolutionValue(V vertexActual) {
		return pathType().equals(PathType.Sum) ? 0. : getVertexWeight(vertexActual);
	}

	/**
	 * Calcula el valor de solucion desde un vecino.
	 *
	 * @param vertexActual vertice actual
	 * @param weight peso del vecino
	 * @param edgeOut arista de salida
	 * @param edgeIn arista de entrada
	 * @return valor de solucion
	 */
	public default Double fromNeighbordSolutionValue(V vertexActual, Double weight, E edgeOut, E edgeIn) {
		 Double r = pathType().equals(PathType.Sum) ? initialPath().add(vertexActual, weight, edgeOut, edgeIn) : weight;
		 return r;
	}
	
	/**
	 * Convierte un camino de vertices virtuales G en un camino normal.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param p camino de vertices virtuales
	 * @param graph grafo original
	 * @return camino convertido
	 */
	public static <V,E> GraphPath<V,E> pathG(GraphPath<VirtualVertexG<V,E>,VirtualEdgeG<V,E>> p, Graph<V,E> graph) {
		return new GraphWalk<V, E>(graph, 
				p.getStartVertex().vertex(), 
				p.getEndVertex().vertex(), 
				p.getVertexList().stream().map(v->v.vertex()).toList(), 
				p.getEdgeList().stream().map(e->e.action()).toList(),
				p.getWeight());
	}
	
	/**
	 * Convierte un camino de vertices virtuales MG en un camino normal.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param p camino de vertices virtuales
	 * @param graph grafo original
	 * @return camino convertido
	 */
	public static <V,E> GraphPath<V,E> pathMG(GraphPath<VirtualVertexMG<V,E>,VirtualEdgeMG<V,E>> p, Graph<V,E> graph) {
		return new GraphWalk<V, E>(graph, 
				p.getStartVertex().vertex(), 
				p.getEndVertex().vertex(), 
				p.getVertexList().stream().map(v->v.vertex()).toList(), 
				p.getEdgeList().stream().map(e->e.action()).toList(),
				p.getWeight());
	}

}
