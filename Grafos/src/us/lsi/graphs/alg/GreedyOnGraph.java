package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath;
import us.lsi.streams.Stream2;
import us.lsi.graphs.alg.GreedyOnGraph.Gog;

/**
 * GreedyOnGraph
 *
 * <p>Implementacion de algoritmo voraz sobre grafos extendidos (EGraph).
 * Genera un camino desde el vertice inicial hasta un objetivo
 * seleccionando en cada paso la arista mas prometedora segun
 * una funcion voraz.</p>
 *
 * <p>Soporta tanto seleccion voraz determinista como aleatoria.</p>
 *
 * @param <V> tipo de los vertices
 * @param <E> tipo de las aristas
 *
 * @author Miguel Toro
 */
public class GreedyOnGraph<V,E> implements  Iterator<Gog<V,E>>, Iterable<Gog<V,E>> {
	
	/**
	 * Obtiene directamente un camino voraz sin crear iterador.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @return camino voraz encontrado
	 */
	public static <V,E> GraphPath<V,E> getPath(EGraph<V,E> graph) {
		V v = graph.startVertex();
		List<V> vertices = new ArrayList<>();
		vertices.add(v);
		Double weight = 0.;
		while(!graph.goal().test(v)) {
			E edge = graph.greedyEdge().apply(v);
			weight += graph.getEdgeWeight(edge);
			v = graph.oppositeVertex(edge, v);
			vertices.add(v);
		}
		return new GraphWalk<V,E>(graph,vertices,weight);
	}
	
	/**
	 * Crea un algoritmo voraz con funcion de seleccion personalizada.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @param greedyEdge funcion que selecciona la arista voraz
	 * @return nuevo GreedyOnGraph
	 */
	public static <V,E> GreedyOnGraph<V,E> of(EGraph<V,E> graph,Function<V,E> greedyEdge) {
		return new GreedyOnGraph<V,E>(graph, greedyEdge);
	}
	
	/**
	 * Crea un algoritmo voraz con la funcion de seleccion del grafo.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @return nuevo GreedyOnGraph
	 */
	public static <V,E> GreedyOnGraph<V,E> of(EGraph<V,E> graph) {
		return new GreedyOnGraph<V,E>(graph,graph.greedyEdge());
	}
	
	/**
	 * Crea un algoritmo voraz con seleccion aleatoria de aristas.
	 *
	 * @param <V> tipo de los vertices
	 * @param <E> tipo de las aristas
	 * @param graph el grafo
	 * @return nuevo GreedyOnGraph aleatorio
	 */
	public static <V,E> GreedyOnGraph<V,E> random(EGraph<V,E> graph) {
		Function<V,E> nextEdge = v -> graph.edgesListOf(v).isEmpty() ? null: 
			List2.randomUnitary(graph.edgesListOf(v)).get(0);
		return new GreedyOnGraph<V,E>(graph,nextEdge);
	}
	
	/**
	 * Gog
	 *
	 * <p>Registro que representa un par vertice-arista durante
	 * el recorrido voraz.</p>
	 *
	 * @param <V> tipo del vertice
	 * @param <E> tipo de la arista
	 * @param vertex el vertice actual
	 * @param edge la arista seleccionada
	 *
	 * @author Miguel Toro
	 */
	public static record Gog<V,E>(V vertex, E edge) {
		/**
		 * Crea un nuevo par vertice-arista.
		 *
		 * @param <V> tipo del vertice
		 * @param <E> tipo de la arista
		 * @param vertex el vertice
		 * @param edge la arista
		 * @return nuevo Gog
		 */
		public static  <V,E> Gog<V,E> of(V vertex, E edge){
			return new Gog<V,E>(vertex,edge);
		}
	}
	
	/** Grafo sobre el que se realiza la busqueda. */
	private EGraph<V,E> graph;
	/** Vertice actual. */
	private V state;
	/** Ultima arista seleccionada. */
	private E edge;
	/** Funcion de seleccion voraz. */
	private Function<V,E> greedyEdge;

	/**
	 * Constructor privado.
	 *
	 * @param graph el grafo
	 * @param greedyEdge funcion de seleccion
	 */
	private GreedyOnGraph(EGraph<V,E> graph,Function<V,E> greedyEdge) {
		super();
		this.graph = graph;
		this.state = graph.startVertex();
		this.greedyEdge = greedyEdge;
	}

	/**
	 * Obtiene stream de vertices visitados.
	 *
	 * @return stream de vertices
	 */
	public Stream<V> stream() {
		return this.streamPair().map(p->p.vertex());
	}
	
	/**
	 * Obtiene stream de aristas seleccionadas.
	 *
	 * @return stream de aristas
	 */
	public Stream<E> streamEdges() {
		return this.streamPair().map(p->p.edge()).filter(e->e!=null);
	}
	
	/**
	 * Obtiene stream de pares vertice-arista.
	 *
	 * @return stream de pares Gog
	 */
	public Stream<Gog<V,E>> streamPair() {
		return Stream2.of(this);
	}
	
	/**
	 * Obtiene el camino completo generado.
	 *
	 * @return el camino
	 */
	public GraphPath<V,E> path(){
		EGraphPath<V,E> path = this.graph.initialPath();
		this.streamEdges().forEach(e->path.add(e));
		return path;
	}
	
	/**
	 * Ejecuta la busqueda voraz.
	 *
	 * @return camino encontrado si existe solucion
	 */
	public Optional<GraphPath<V,E>> search(){	
		GraphPath<V,E> r = path();
		if(!r.getVertexList().isEmpty()) {
			V last = r.getEndVertex();
			if(this.graph.goalHasSolution().test(last)) return Optional.of(r);
			else return Optional.empty();
		} else return Optional.empty();
	}
	
	/**
	 * Ejecuta la busqueda y transforma el resultado.
	 *
	 * @param <S> tipo de la solucion
	 * @param f funcion de transformacion
	 * @return solucion transformada si existe
	 */
	public <S> Optional<S> search(Function<GraphPath<V,E>,S> f) {
		Optional<GraphPath<V, E>> p = search();
		return p.map(f);
	}
	
	/**
	 * Verifica si un camino es solucion.
	 *
	 * @param gp el camino
	 * @return true si termina en objetivo
	 */
	public Boolean isSolution(GraphPath<V,E> gp) {
		V last = gp.getEndVertex();
		return graph.goal().test(last);
	}
	
	/**
	 * Obtiene el ultimo vertice visitado.
	 *
	 * @return ultimo vertice como Optional
	 */
	public Optional<V> last() {
		return Stream2.findLast(this.stream());
	}
	
	/**
	 * Crea una copia del algoritmo.
	 *
	 * @return copia independiente
	 */
	public GreedyOnGraph<V,E> copy() {
		return of(this.graph,this.greedyEdge);
	}
	
	@Override
	public Iterator<Gog<V,E>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return state != null && !this.graph.edgesListOf(state).isEmpty() && !this.graph.goal().test(state) ;
	}

	@Override
	public Gog<V,E> next() {
		V old = state;
		edge= this.greedyEdge.apply(state);
		if(edge !=null) this.state = graph.oppositeVertex(edge, old);
		else this.state = null;
		return Gog.of(old, edge);
	}

}
