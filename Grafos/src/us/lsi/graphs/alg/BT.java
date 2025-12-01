package us.lsi.graphs.alg;


import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath;

/**
 * BT (Backtracking)
 *
 * <p>Implementación de algoritmo de Backtracking para búsqueda en grafos.
 * Explora el espacio de estados de forma recursiva, podando ramas que
 * no pueden mejorar la mejor solución encontrada.</p>
 *
 * <p>Soporta diferentes modos de búsqueda:
 * <ul>
 *   <li>Min: minimizar el valor objetivo</li>
 *   <li>Max: maximizar el valor objetivo</li>
 *   <li>One: encontrar una solución válida</li>
 *   <li>All: encontrar todas las soluciones hasta un límite</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * EGraph<V,E> graph = ...;
 * BT<V,E,S> bt = BT.ofGreedy(graph);
 * Optional<GraphPath<V,E>> path = bt.search();
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
 * @see State
 */
public class BT<V,E,S> {
	
	/**
	 * Crea un algoritmo BT inicializado con una solución voraz.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @return un nuevo algoritmo BT
	 */
	public static <V, E, S> BT<V, E, S> ofGreedy(EGraph<V, E> graph){
		return BT.ofGreedy(graph, false);
	}

	/**
	 * Crea un algoritmo BT inicializado con una solución voraz.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @param withGraph si true, construye el grafo de exploración
	 * @return un nuevo algoritmo BT
	 */
	public static <V, E, S> BT<V, E, S> ofGreedy(
			EGraph<V, E> graph,Boolean withGraph) {
		GreedyOnGraph<V, E> ga = GreedyOnGraph.of(graph);
		Optional<GraphPath<V, E>> gp = ga.search();
		if(gp.isPresent()) return BT.of(graph,null,gp.get().getWeight(),gp.get(),false);
		else return BT.of(graph, null, null, null, withGraph);
	}
	
	/**
	 * Crea un algoritmo BT con función de solución.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @param fsolution función que transforma un camino en solución
	 * @return un nuevo algoritmo BT
	 */
	public static <V, E, S> BT<V, E, S> of(
			EGraph<V, E> graph,
			Function<GraphPath<V, E>, S> fsolution) {
		return BT.of(graph, fsolution, null, null, false);
	}
	
	/**
	 * Crea un algoritmo BT básico.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @return un nuevo algoritmo BT
	 */
	public static <V, E, S> BT<V, E, S> of(
			EGraph<V, E> graph) {
		return BT.of(graph, null, null, null, false);
	}
	
	/**
	 * Crea un algoritmo BT con valor inicial y camino óptimo.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @param bestValue mejor valor conocido
	 * @param optimalPath mejor camino conocido
	 * @return un nuevo algoritmo BT
	 */
	public static <V, E, S> BT<V, E, S> of(
			EGraph<V, E> graph,
			Double bestValue,
			GraphPath<V,E> optimalPath) {
		return new BT<V, E, S>(graph,null,bestValue,optimalPath,false);
	}
	
	/**
	 * Crea un algoritmo BT con todos los parámetros.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solución
	 * @param graph grafo sobre el que buscar
	 * @param fsolution función que transforma un camino en solución
	 * @param bestValue mejor valor conocido
	 * @param optimalPath mejor camino conocido
	 * @param withGraph si true, construye el grafo de exploración
	 * @return un nuevo algoritmo BT
	 */
	public static <V, E, S> BT<V, E, S> of(
			EGraph<V, E> graph,
			Function<GraphPath<V, E>, S> fsolution,
			Double bestValue,
			GraphPath<V,E> optimalPath,
			Boolean withGraph) {
		return new BT<V, E, S>(graph,fsolution,bestValue,optimalPath,withGraph);
	}
	
	/** Comparador para ordenar valores según el tipo de optimización. */
	private Comparator<Double> comparator = Comparator.naturalOrder();
	
	/** Tipo de búsqueda (Min, Max, One, All). */
	private Type type;
	
	/** Grafo sobre el que se realiza la búsqueda. */
	public EGraph<V,E> graph;
	
	/** Mejor valor encontrado. */
	public Double bestValue;
	
	/** Mejor camino encontrado. */
	public GraphPath<V,E> optimalPath;
	
	/** Conjunto de soluciones encontradas (para tipo All). */
	public Set<S> solutions;
	
	/** Función que transforma un camino en solución. */
	protected Function<GraphPath<V,E>,S> fsolution;
	
	/** Grafo de exploración (opcional). */
	private SimpleDirectedGraph<V,E> outGraph;
	
	/** Indica si se construye el grafo de exploración. */
	private Boolean withGraph = false;
	
	/** Indica si se debe detener la búsqueda. */
	protected Boolean stop = false;
	
	/**
	 * Constructor que inicializa el algoritmo BT.
	 *
	 * @param graph grafo sobre el que buscar
	 * @param fsolution función de transformación a solución
	 * @param bestValue mejor valor inicial
	 * @param optimalPath mejor camino inicial
	 * @param withGraph si true, construye grafo de exploración
	 */
	BT(EGraph<V, E> graph,Function<GraphPath<V, E>, S> fsolution, 
			Double bestValue,GraphPath<V,E> optimalPath, Boolean withGraph) {
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
		this.fsolution = fsolution;
		this.bestValue = bestValue;
		this.optimalPath = optimalPath;
		this.withGraph = withGraph;
	}
	
	/**
	 * Determina si una rama debe ser podada.
	 *
	 * @param state estado actual
	 * @param edge arista a explorar
	 * @return true si la rama debe ser descartada
	 */
	protected Boolean forget(State<V,E> state, E edge) {
		Boolean r = false;
		if(graph.type().equals(Type.All) || graph.type().equals(Type.One))  return false;
		Double w = state.getGraph().boundedValue(state.getActualVertex(),state.getAccumulateValue(),
				edge,graph.heuristic());
		if(this.bestValue != null) r = comparator.compare(w,this.bestValue) >= 0;
		return r;
	}
	
	/**
	 * Actualiza la mejor solución si el estado actual es un objetivo válido.
	 *
	 * @param state estado actual
	 */
	protected void update(State<V, E> state) {
		if (graph.goalHasSolution().test(state.getActualVertex())) {
			switch(this.type) {
			case All: 
				this.optimalPath = state.getPath();
				S s = fsolution.apply(state.getPath());
				this.solutions.add(s);
				if (this.solutions.size() >= this.graph.solutionNumber()) this.stop = true;
				break;
			case One:
				this.bestValue = state.getAccumulateValue();
				this.optimalPath = state.getPath();
				this.stop = true;
				break;
			case Min:
			case Max:
				if (this.bestValue == null || this.comparator.compare(state.getAccumulateValue(), this.bestValue) < 0) {
					this.bestValue = state.getAccumulateValue();
					this.optimalPath = state.getPath();
				}
			}
		}
	}
	
	/**
	 * Inicializa el grafo de exploración si es necesario.
	 */
	private void initialGraph() {
		if (this.withGraph) this.outGraph = Graphs2.simpleDirectedGraph();
	}
	
	/**
	 * Añade un vértice y arista al grafo de exploración.
	 *
	 * @param v vértice origen
	 * @param edge arista explorada
	 */
	private void addGraph(V v, E edge) {
		if(withGraph) {
			V v2 = Graphs.getOppositeVertex(graph,edge,v);
			if(!this.outGraph.containsVertex(v)) this.outGraph.addVertex(v);
			if(!this.outGraph.containsVertex(v2)) this.outGraph.addVertex(v2);
			if(!this.outGraph.containsEdge(edge)) this.outGraph.addEdge(v, v2, edge);
		}
	}
	
	/**
	 * Obtiene el grafo de exploración construido.
	 *
	 * @return el grafo de exploración
	 */
	public SimpleDirectedGraph<V,E> outGraph() {
		return this.outGraph;
	}
	
	/**
	 * Ejecuta la búsqueda por backtracking.
	 *
	 * @return camino óptimo si existe
	 */
	public Optional<GraphPath<V, E>> search() {	
		initialGraph();
		State<V,E> initialState = StatePath.of(graph,graph.goal(),graph.endVertex());
		search(initialState);
		return this.optimalPath();
	}
	
	/**
	 * Realiza la búsqueda recursiva desde un estado dado.
	 *
	 * @param state estado actual de la búsqueda
	 */
	public void search(State<V, E> state) {
		V actual = state.getActualVertex();
		if (graph.goal().test(actual)) {
			this.update(state);
		} else {
			for (E edge : graph.edgesListOf(actual)) {
				if (this.forget(state, edge) || this.stop) continue;
				state.forward(edge);
				search(state);
				addGraph(actual, edge);
				state.back(edge);
			}
		}
	}

	/**
	 * Obtiene el conjunto de soluciones encontradas.
	 *
	 * @return conjunto de soluciones
	 */
	public Set<S> getSolutions(){
		if(this.solutions == null) return Set.of(this.fsolution.apply(this.optimalPath));
		return this.solutions;
	}
	
	/**
	 * Obtiene el camino óptimo encontrado.
	 *
	 * @return camino óptimo como Optional
	 */
	public Optional<GraphPath<V, E>> optimalPath(){
		return Optional.ofNullable(this.optimalPath);		
	}
	
	/**
	 * Devuelve las soluciones como cadena.
	 *
	 * @return representación textual de las soluciones
	 */
	public String toStringSolutions() {
		return this.solutions.stream().sorted().map(e->e.toString()).collect(Collectors.joining("\n"));
	}

	/**
	 * State
	 *
	 * <p>Interfaz que define el estado durante la búsqueda por backtracking.
	 * Permite avanzar y retroceder en el espacio de estados.</p>
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 *
	 * @author Miguel Toro
	 */
	public interface State<V, E> {
		/**
		 * Avanza al siguiente estado mediante una arista.
		 *
		 * @param edge arista por la que avanzar
		 */
		void forward(E edge);
		
		/**
		 * Retrocede al estado anterior deshaciendo el avance por una arista.
		 *
		 * @param edge arista por la que se había avanzado
		 */
		void back(E edge);
		
		/**
		 * Obtiene el valor acumulado hasta el estado actual.
		 *
		 * @return valor acumulado
		 */
		Double getAccumulateValue();
		
		/**
		 * Obtiene el camino desde el inicio hasta el estado actual.
		 *
		 * @return el camino actual
		 */
		EGraphPath<V, E> getPath();
		
		/**
		 * Obtiene el grafo sobre el que se realiza la búsqueda.
		 *
		 * @return el grafo
		 */
		EGraph<V, E> getGraph();
		
		/**
		 * Obtiene el vértice actual.
		 *
		 * @return vértice actual
		 */
		V getActualVertex();
	}
	
	/**
	 * StatePath
	 *
	 * <p>Implementación de State que mantiene el camino recorrido
	 * mediante una lista de aristas.</p>
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las aristas
	 *
	 * @author Miguel Toro
	 */
	public static class StatePath<V,E> implements State<V, E> {
		/** Vértice actual. */
		private V actualVertex;
		
		/** Camino actual. */
		private EGraphPath<V, E> path;
		
		/** Grafo de búsqueda. */
		private EGraph<V,E> graph;
		
		/** Lista de aristas recorridas. */
		private List<E> edges;
		
		/** Lista de pesos acumulados. */
		private List<Double> weights;
		
		/** Valor acumulado actual. */
		private Double accumulateValue;
		
		/**
		 * Crea un nuevo StatePath.
		 *
		 * @param <V> tipo de los vértices
		 * @param <E> tipo de las aristas
		 * @param graph grafo de búsqueda
		 * @param goal predicado de objetivo
		 * @param end vértice final
		 * @return un nuevo StatePath
		 */
		public static <V,E> State<V, E> of(EGraph<V,E> graph, Predicate<V> goal, V end){
			return new StatePath<>(graph,goal,end);
		}		
		
		/**
		 * Constructor de StatePath.
		 *
		 * @param graph grafo de búsqueda
		 * @param goal predicado de objetivo
		 * @param end vértice final
		 */
		public StatePath(EGraph<V,E> graph, Predicate<V> goal, V end) {
			super();
			this.actualVertex = graph.startVertex();
			this.graph = graph;
			this.path = graph.initialPath();
			this.edges = new ArrayList<>();
			this.weights = new ArrayList<>();
			this.accumulateValue = this.path.getWeight();
		}		
	
		@Override
		public void forward(E edge) {
			E lastEdge = edges.isEmpty()?null:List2.last(edges);
			this.accumulateValue = this.graph.add(this.actualVertex,this.accumulateValue,edge,lastEdge);
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);
			this.edges.add(edge);
			this.weights.add(this.accumulateValue);
		}
		
		@Override
		public void back(E edge) {
			this.actualVertex = Graphs.getOppositeVertex(graph,edge,this.actualVertex);	
			this.edges.remove(this.edges.size()-1);
			this.weights.remove(this.weights.size()-1);
			this.accumulateValue = !this.weights.isEmpty()? List2.last(this.weights): graph.initialPath().getWeight();
		}
		
		@Override
		public Double getAccumulateValue() {
			return this.accumulateValue;
		}	
	
		@Override
		public EGraph<V, E> getGraph() {
			return graph;
		}

		@Override
		public EGraphPath<V, E> getPath() {				
			EGraphPath<V,E> ePath = graph.initialPath();
			for(E e:this.edges) {
				ePath.add(e);
			}
			return ePath;
		}
					
		@Override
		public V getActualVertex() {
			return actualVertex;
		}
		
		@Override
		public String toString() {
			return String.format("%s,\n%.2f,\n%s",
					this.actualVertex,this.getAccumulateValue(),
					this.getPath().getEdgeList().stream().map(e->e.toString()).collect(Collectors.joining(",","{","}")));
		}	
	}	
}
