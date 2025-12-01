package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;

import us.lsi.colors.GraphColors;
import us.lsi.graphs.SimpleEdge;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleHyperEdge;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.hypergraphs.VirtualHyperVertex;

/**
 * PD (Programación Dinámica)
 *
 * <p>Implementación de Programación Dinámica para resolver problemas
 * modelados como hipergrafos. Utiliza memoización para evitar recalcular
 * subproblemas ya resueltos.</p>
 *
 * <p>Soporta tanto minimización como maximización según el tipo especificado.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * SimpleVirtualHyperGraph<V,E,A> graph = ...;
 * PD<V,E,A,S> pd = PD.dynamicProgrammingSearch(graph, PDType.Min);
 * Sp<E> resultado = pd.search();
 * GraphTree<V,E,A,S> arbol = pd.searchTree(verticeInicial);
 * }</p>
 *
 * @param <V> tipo de los vértices (deben implementar VirtualHyperVertex)
 * @param <E> tipo de las hiperaristas
 * @param <A> tipo de las acciones/alternativas
 * @param <S> tipo de la solución
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SimpleVirtualHyperGraph
 * @see VirtualHyperVertex
 */
public class PD<V extends VirtualHyperVertex<V,E,A,S>,
			E extends SimpleHyperEdge<V,E,A>,A,S> {
	
	/**
	 * Tipos de optimización para Programación Dinámica.
	 */
	public enum PDType{
		/** Minimizar el valor objetivo. */
		Min,
		/** Maximizar el valor objetivo. */
		Max
	}

	/** Hipergrafo sobre el que se realiza la búsqueda. */
	public SimpleVirtualHyperGraph<V,E, A> graph;
	
	/** Comparador para ordenar soluciones parciales. */
	private Comparator<Sp<E>> comparatorSp;
	
	/** Mapa de memoización: vértice -> mejor solución parcial. */
	public Map<V,Sp<E>> solutionsTree;
	
	/** Tipo de optimización (Min o Max). */
	private PDType type;
	
	/** Vértice de inicio. */
	private V startVertex;
	
	/** Grafo de exploración para visualización. */
	public Graph<VertexGraph<V,E>,SimpleEdge<VertexGraph<V,E>>> outGraph;
	
	/** Indica si se construye el grafo de exploración. */
	public Boolean withGraph = false;
	
	/**
	 * Constructor de la Programación Dinámica.
	 *
	 * @param graph hipergrafo del problema
	 * @param type tipo de optimización
	 */
	PD(SimpleVirtualHyperGraph<V,E, A> graph, PDType type) {
		this.graph = graph;
		this.startVertex = graph.getStartVertex();
		this.type = type;
		if(this.type == PDType.Min) this.comparatorSp = Comparator.naturalOrder();
		if(this.type == PDType.Max) this.comparatorSp = Comparator.<Sp<E>>naturalOrder().reversed();
		this.solutionsTree = new HashMap<>();
	}
	
	/**
	 * Ejecuta la búsqueda por Programación Dinámica desde el vértice inicial.
	 *
	 * @return solución parcial óptima
	 */
	public Sp<E> search(){
		if(this.withGraph) outGraph = new SimpleDirectedWeightedGraph<>(null,null);
		return search(this.startVertex);
	}

	/**
	 * Ejecuta la búsqueda recursiva con memoización.
	 *
	 * @param actual vértice actual
	 * @return solución parcial óptima desde este vértice
	 */
	public Sp<E> search(V actual) {
		Sp<E> r = null;
		if (this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (graph.isBaseCase(actual)) {
			Double w = graph.baseCaseWeight(actual);
			if(w!=null) r = Sp.of(w,null);
			else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Sp<E>> sps = new ArrayList<>();
			for (E edge : graph.edgesOf(actual)) {
				List<Sp<E>> spNeighbors = new ArrayList<>();
				List<V> neighbords = graph.getEdgeTargets(edge);
				for (V neighbor : neighbords) {
					Sp<E> nb = search(neighbor);
					if (nb == null) {
						spNeighbors = null;
						break;
					}
					spNeighbors.add(nb);
				}
				Sp<E> spa = null;
				if(spNeighbors != null) {
					List<Double> solutions = spNeighbors.stream().map(sp->sp.weight()).toList();
					spa = Sp.of(graph.getEdgeWeight(edge,solutions), edge);
				}
				sps.add(spa);
				if(this.withGraph) this.completeGraph(actual,edge,neighbords);
			}
			r = sps.stream().filter(s -> s != null).min(this.comparatorSp).orElse(null);
			this.solutionsTree.put(actual, r);
			
		}
		return r;
	}
	
	/**
	 * Añade vértices y aristas al grafo de exploración.
	 *
	 * @param actual vértice actual
	 * @param edge arista explorada
	 * @param neighbords vértices vecinos
	 */
	private void completeGraph(V actual,E edge,List<V> neighbords) {
		VertexGraph<V,E> vg = VertexGraph.ofVertex(actual);
		outGraph.addVertex(vg);
		VertexGraph<V,E> ve = VertexGraph.ofEdge(edge);
		outGraph.addVertex(ve);
	    outGraph.addEdge(vg,ve,SimpleEdge.of(vg,ve,1.));
	    for (V neighbord:neighbords) {
			VertexGraph<V, E> vn = VertexGraph.ofVertex(neighbord);
			outGraph.addVertex(vn);
			outGraph.addEdge(ve, vn, SimpleEdge.of(ve, vn, 1.));
		}
	}

	/**
	 * Exporta el grafo de exploración a formato DOT.
	 *
	 * @param file archivo de salida
	 * @param stringVertex función para representar vértices
	 * @param stringEdge función para representar aristas
	 * @param s conjunto de vértices óptimos a destacar
	 */
	public void toDot(String file,Function<V,String> stringVertex, Function<E,String> stringEdge, Set<V> s) {
		GraphColors.toDot(this.outGraph,
				file,
				v->v.toStringVertex(stringVertex),
				e->VertexGraph.toStringEdge(this.outGraph,e,stringEdge),
				v->v.shapeAndColorVertex(s),
				e->VertexGraph.colorEdge(this.outGraph,e,s)
				);
	}
	
	/**
	 * Obtiene el hipergrafo.
	 *
	 * @return el hipergrafo
	 */
	public SimpleVirtualHyperGraph<V, E, A> getGraph() {
		return graph;
	}
	
	/**
	 * Obtiene el mapa de soluciones memoizadas.
	 *
	 * @return mapa vértice -> solución parcial
	 */
	public Map<V, Sp<E>> getSolutionsTree() {
		return solutionsTree;
	}	
	
	/**
	 * Obtiene el tipo de optimización.
	 *
	 * @return el tipo
	 */
	public PDType getType() {
		return type;
	}
	
	/**
	 * Construye el árbol de solución óptima desde un vértice.
	 *
	 * @param vertex vértice raíz
	 * @return árbol de solución óptima
	 */
	public GraphTree<V,E,A,S> searchTree(V vertex){
		return GraphTree.optimalTree(vertex, solutionsTree);
	}
	
	/**
	 * Crea un algoritmo de Programación Dinámica.
	 *
	 * @param <V> tipo de los vértices
	 * @param <E> tipo de las hiperaristas
	 * @param <A> tipo de las acciones
	 * @param <S> tipo de la solución
	 * @param graph hipergrafo del problema
	 * @param type tipo de optimización
	 * @return un nuevo algoritmo PD
	 */
	public static <V extends VirtualHyperVertex<V, E, A,S>, E extends SimpleHyperEdge<V, E, A>, A, S> 
		PD<V, E, A, S> dynamicProgrammingSearch(
			SimpleVirtualHyperGraph<V, E, A> graph, 
			PDType type) {
		return new PD<V, E, A, S>(graph, type);
	}

	/**
	 * Sp (Solución Parcial)
	 *
	 * <p>Registro que representa una solución parcial: el peso acumulado
	 * y la arista que conduce a ella.</p>
	 *
	 * @param <E> tipo de la arista
	 * @param weight peso acumulado
	 * @param edge arista que condujo a esta solución
	 *
	 * @author Miguel Toro
	 */
	public record Sp<E>(Double weight, E edge) implements Comparable<Sp<E>> {
		
		/**
		 * Crea una solución parcial con peso y arista.
		 *
		 * @param <E> tipo de la arista
		 * @param weight peso
		 * @param edge arista
		 * @return nueva solución parcial
		 */
		public static <E> Sp<E> of(Double weight,E edge) {
			return new Sp<>(weight,edge);
		}
		
		/**
		 * Crea una solución parcial solo con peso.
		 *
		 * @param <E> tipo de la arista
		 * @param weight peso
		 * @return nueva solución parcial sin arista
		 */
		public static <E> Sp<E> of(Double weight) {
			return new Sp<>(weight,null);
		}
		
		/**
		 * Obtiene un comparador para soluciones parciales.
		 *
		 * @param <E> tipo de la arista
		 * @return comparador por peso
		 */
		public static <E> Comparator<Sp<E>> comparator() {
			return Comparator.naturalOrder();
		}

		@Override
		public int compareTo(Sp<E> sp) {
			return this.weight.compareTo(sp.weight);
		}

	}

	/**
	 * VertexGraph
	 *
	 * <p>Registro que representa un vértice en el grafo de exploración,
	 * que puede ser un vértice real o una arista del hipergrafo.</p>
	 *
	 * @param <V> tipo del vértice
	 * @param <E> tipo de la arista
	 * @param vertex vértice (si tipo es Vertex)
	 * @param edge arista (si tipo es Edge)
	 * @param tipo tipo de este nodo
	 *
	 * @author Miguel Toro
	 */
	public static record VertexGraph<V,E>(V vertex, E edge, DPTipoVertex tipo) {
		
		/** Tipos de nodo en el grafo de exploración. */
		public static enum DPTipoVertex{
			/** Representa un vértice del hipergrafo. */
			Vertex,
			/** Representa una arista del hipergrafo. */
			Edge
		};
		
		/**
		 * Crea un nodo que representa un vértice.
		 *
		 * @param <V> tipo del vértice
		 * @param <E> tipo de la arista
		 * @param v el vértice
		 * @return nuevo VertexGraph
		 */
		public static <V,E> VertexGraph<V,E> ofVertex(V v){
			return new VertexGraph<>(v, null,DPTipoVertex.Vertex);
		}
		
		/**
		 * Crea un nodo que representa una arista.
		 *
		 * @param <V> tipo del vértice
		 * @param <E> tipo de la arista
		 * @param e la arista
		 * @return nuevo VertexGraph
		 */
		public static <V,E> VertexGraph<V,E> ofEdge(E e){
			return new VertexGraph<>(null, e,DPTipoVertex.Edge);
		}
		
		/**
		 * Obtiene representación textual del vértice.
		 *
		 * @param stringVertex función de conversión
		 * @return representación textual
		 */
		public String toStringVertex(Function<V,String> stringVertex) {
			return switch(tipo()) {
			case Vertex -> stringVertex.apply(vertex);
			case Edge -> "";
			};
		}
		
		/**
		 * Obtiene representación textual de una arista del grafo de exploración.
		 *
		 * @param <V> tipo del vértice
		 * @param <E> tipo de la arista
		 * @param graph el grafo
		 * @param edge la arista
		 * @param stringEdge función de conversión
		 * @return representación textual
		 */
		public static <V,E> String toStringEdge(Graph<VertexGraph<V,E>,SimpleEdge<VertexGraph<V,E>>> graph,
				SimpleEdge<VertexGraph<V,E>> edge, Function<E,String> stringEdge) {
			VertexGraph<V,E> source = graph.getEdgeSource(edge);
			VertexGraph<V,E> target = graph.getEdgeTarget(edge);
			String r = "";
			if(source.tipo().equals(DPTipoVertex.Vertex)) {
				r = stringEdge.apply(target.edge());
			}
			return r;
		}
		
		/**
		 * Determina si una arista pertenece al camino óptimo.
		 */
		private static <V,E> Boolean optimalEdge(Graph<VertexGraph<V,E>,SimpleEdge<VertexGraph<V,E>>> graph,
				SimpleEdge<VertexGraph<V,E>> edge, Set<V> optimalVertex) {
			VertexGraph<V, E> source = graph.getEdgeSource(edge);
			VertexGraph<V, E> target = graph.getEdgeTarget(edge);
			VertexGraph<V, E> vv = null;;
			if(source.tipo().equals(DPTipoVertex.Edge)) vv = source;				
			if(source.tipo().equals(DPTipoVertex.Vertex)) vv = target;
			final VertexGraph<V, E> vf = vv;
			return graph.edgesOf(vf).stream()
					.map(v->v.otherVertex(vf))
					.map(v->v.vertex())
					.allMatch(v->optimalVertex.contains(v));
		}
		
		/**
		 * Obtiene los atributos de color para una arista.
		 *
		 * @param <V> tipo del vértice
		 * @param <E> tipo de la arista
		 * @param graph el grafo
		 * @param edge la arista
		 * @param optimalVertex conjunto de vértices óptimos
		 * @return mapa de atributos
		 */
		public static <V,E> Map<String, Attribute> colorEdge(Graph<VertexGraph<V,E>,SimpleEdge<VertexGraph<V,E>>> graph,
				SimpleEdge<VertexGraph<V,E>> edge, Set<V> optimalVertex) {
			Map<String, Attribute> r = Map.of();	
			if(optimalEdge(graph,edge,optimalVertex)) {
				r = Map.of("color", DefaultAttribute.createAttribute(GraphColors.Color.red.name()),
						   "style", DefaultAttribute.createAttribute(GraphColors.Style.bold.name()));				
			}
			return r;
		}
		
		/**
		 * Obtiene los atributos de forma para este nodo.
		 *
		 * @return mapa de atributos
		 */
		public Map<String, Attribute> shapeVertex(){
			return switch(tipo()) {
			case Vertex -> Map.of();
			case Edge -> Map.of("shape", DefaultAttribute.createAttribute(GraphColors.Shape.point.name()));
			};
		}

		/**
		 * Obtiene los atributos de color para este nodo.
		 *
		 * @param optimalVertex conjunto de vértices óptimos
		 * @return mapa de atributos
		 */
		public Map<String, Attribute> colorVertex(Set<V> optimalVertex) {
			return switch(tipo()) {
			case Vertex -> optimalVertex.contains(this.vertex)? 
					Map.of("color", DefaultAttribute.createAttribute(GraphColors.Color.red.name()),
						   "style", DefaultAttribute.createAttribute(GraphColors.Style.bold.name())):
					Map.of();
			case Edge -> Map.of();
			};
		}
	
		/**
		 * Obtiene los atributos combinados de forma y color.
		 *
		 * @param optimalVertex conjunto de vértices óptimos
		 * @return mapa de atributos combinados
		 */
		public Map<String, Attribute> shapeAndColorVertex(Set<V> optimalVertex) {
			Map<String, Attribute> shape = shapeVertex();
			Map<String, Attribute> color = colorVertex(optimalVertex);
			shape = new HashMap<>(shape);
			shape.putAll(color);
			return shape;
		}
			
	}
	
}