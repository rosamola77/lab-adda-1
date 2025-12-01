package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath;

import java.util.Optional;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedGraph;

/**
 * PDR (Programacion Dinamica con Reduccion)
 *
 * <p>Implementacion de Programacion Dinamica con memoizacion para
 * grafos extendidos (EGraph). Utiliza recursion con memoria para
 * evitar recalcular subproblemas.</p>
 *
 * <p>Soporta minimizacion, maximizacion y busqueda de soluciones
 * unicas o multiples.</p>
 *
 * @param <V> tipo de los vertices
 * @param <E> tipo de las aristas
 * @param <S> tipo de la solucion
 *
 * @author Miguel Toro
 */
public class PDR<V, E, S> {
	
	/**
	 * Crea un algoritmo PDR basico.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solucion
	 * @param graph el grafo
	 * @return nuevo PDR
	 */
	public static <V, E, S> PDR<V, E, S> of(EGraph<V, E> graph) {
		return new PDR<V, E, S>(graph,null,false);
	}
	
	/**
	 * Crea un algoritmo PDR con funcion de solucion y opcion de grafo.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param <S> tipo de la solucion
	 * @param graph el grafo
	 * @param fsolution funcion que transforma camino en solucion
	 * @param withGraph si construir grafo de exploracion
	 * @return nuevo PDR
	 */
	public static <V, E, S> PDR<V, E, S> of(EGraph<V, E> graph, 
			Function<GraphPath<V, E>, S> fsolution, 
			Boolean withGraph) {
		return new PDR<V, E, S>(graph,fsolution,withGraph);
	}

	/** Grafo sobre el que se realiza la busqueda. */
	private EGraph<V, E> graph;
	/** Comparador de soluciones parciales. */
	private Comparator<Sp<E>> comparatorSp;
	/** Mapa de memoizacion. */
	public Map<V, Sp<E>> solutionsTree;
	/** Camino actual durante la exploracion. */
	private List<V> actualPath;
	/** Conjunto de soluciones encontradas. */
	public Set<S> solutions;
	/** Funcion de transformacion a solucion. */
	protected Function<GraphPath<V,E>,S> fsolution;
	/** Grafo de exploracion. */
	public SimpleDirectedGraph<V, E> outGraph;
	/** Indica si construir grafo de exploracion. */
	private Boolean withGraph = false;
	/** Tipo de optimizacion. */
	private Type type;
	/** Indica si detener la busqueda. */
	public Boolean stop = false;

	/**
	 * Constructor de PDR.
	 *
	 * @param g el grafo
	 * @param fsolution funcion de transformacion
	 * @param withGraph si construir grafo
	 */
	PDR(EGraph<V, E> g, Function<GraphPath<V, E>, S> fsolution, Boolean withGraph) {
		this.graph = g;
		this.comparatorSp = this.graph.type() == EGraph.Type.Min?Comparator.naturalOrder():Comparator.reverseOrder();
		this.type = g.type();
		this.solutionsTree = new HashMap<>();
		this.actualPath = new ArrayList<>();
		this.withGraph = withGraph;
		this.fsolution = fsolution;		
	}
	

	/**
	 * Actualiza las soluciones cuando se alcanza un objetivo valido.
	 *
	 * @param actual vertice actual
	 * @param accumulateValue valor acumulado
	 */
	protected void update(V actual, Double accumulateValue) {
		if (graph.goalHasSolution().test(actual)) {
			switch(this.type) {
			case All:
				S s = fsolution.apply(pathToOrigin(actual,accumulateValue));
				this.solutions.add(s);
				if (this.solutions.size() >= this.graph.solutionNumber()) this.stop = true;
				break;
			case One:
				s = fsolution.apply(pathToOrigin(actual,accumulateValue));
				this.solutions.add(s);
				this.stop = true;
				break;
			case Min:
			case Max:
			}
		}
	}
	
	/**
	 * Obtiene el conjunto de soluciones encontradas.
	 *
	 * @return conjunto de soluciones
	 */
	public Set<S> getSolutions(){
		return this.solutions;
	}
	
	/**
	 * Inicializa el grafo de exploracion si es necesario.
	 */
	public void iniciaGraph() {
		if(this.withGraph) outGraph = Graphs2.simpleDirectedGraph();
	}
	
	/**
	 * Anade vertices y aristas al grafo de exploracion.
	 *
	 * @param v vertice origen
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
	 * Obtiene el grafo de exploracion.
	 *
	 * @return el grafo de exploracion
	 */
	public SimpleDirectedGraph<V,E> outGraph() {
		return this.outGraph;
	}

	/**
	 * Ejecuta la busqueda por programacion dinamica.
	 *
	 * @return camino optimo si existe
	 */
	public Optional<GraphPath<V, E>> search() {
		iniciaGraph();
		this.solutionsTree = new HashMap<>();
		Sp<E> r = search(graph.startVertex(),0., null);	
		if(r == null) return Optional.empty();
		return pathFrom(graph.startVertex());
	}
	
	/**
	 * Busqueda recursiva con memoizacion.
	 *
	 * @param actual vertice actual
	 * @param accumulateValue valor acumulado
	 * @param edgeToOrigin arista hacia el origen
	 * @return solucion parcial optima
	 */
	private Sp<E> search(V actual, Double accumulateValue, E edgeToOrigin) {
		this.actualPath.add(actual);
		Sp<E> r = null;
		if(this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (graph.goal().test(actual)) {
			if (graph.goalHasSolution().test(actual)) {
				r = Sp.of(graph.goalSolutionValue(actual), null);
				this.solutionsTree.put(actual, r);
				update(actual,accumulateValue);			
			} else {
				r = null;
				this.solutionsTree.put(actual, r);
			}		
		} else {
			List<Sp<E>> rs = new ArrayList<>();	
			for (E edge : graph.edgesListOf(actual)) {					
				V v = Graphs.getOppositeVertex(graph,edge,actual);
				Double ac = this.graph.add(actual,accumulateValue,edge,edgeToOrigin); 
				Sp<E> s = search(v,ac,edge);
				if (s!=null) {
					Double spv = this.graph.fromNeighbordSolutionValue(actual,s.weight,edge,edgeToOrigin);	
					Sp<E> sp = Sp.of(spv,edge);
					rs.add(sp);
				}
				addGraph(actual, edge);
			}
			if (!rs.isEmpty()) {
				r = rs.stream().filter(s -> s != null).min(this.comparatorSp).orElse(null);
				if(r != null)
					this.solutionsTree.put(actual, r);
			}
		}
		this.actualPath.remove(actual);
		return r;
	}
	
	/**
	 * Construye el camino hacia el origen desde un vertice.
	 *
	 * @param vertex vertice final
	 * @param accumulateValue valor acumulado
	 * @return el camino
	 */
	private GraphPath<V, E> pathToOrigin(V vertex,Double accumulateValue) {
		return new GraphWalk<>(this.graph,this.actualPath,accumulateValue);
	}

	/**
	 * Construye el camino desde un vertice hacia adelante.
	 *
	 * @param vertex vertice inicial
	 * @return camino como Optional
	 */
	private Optional<GraphPath<V, E>> pathFrom(V vertex) {	
		if(this.solutionsTree.get(vertex) == null) return Optional.empty();
		E edge = this.solutionsTree.get(vertex).edge;	
		EGraphPath<V,E> ePath = EGraphPath.ofVertex(this.graph,vertex,this.graph.pathType());
		while(edge != null) {
			ePath.add(edge);
			vertex = Graphs.getOppositeVertex(graph,edge,vertex);
			edge = this.solutionsTree.get(vertex).edge;	
		}
		return Optional.of(ePath);
	}
	
	/**
	 * Sp (Solucion Parcial)
	 *
	 * <p>Registro que almacena una solucion parcial con su peso
	 * y la arista que conduce a ella.</p>
	 *
	 * @param <E> tipo de la arista
	 * @param weight peso acumulado
	 * @param edge arista de llegada
	 *
	 * @author Miguel Toro
	 */
	public record Sp<E>(Double weight, E edge) implements Comparable<Sp<E>> {
		
		/**
		 * Crea una solucion parcial.
		 *
		 * @param <E> tipo de la arista
		 * @param weight peso
		 * @param edge arista
		 * @return nueva solucion parcial
		 */
		public static <E> Sp<E> of(Double weight,E edge) {
			return new Sp<>(weight,edge);
		}

		@Override
		public int compareTo(Sp<E> sp) {
			return this.weight.compareTo(sp.weight);
		}

	}
}
