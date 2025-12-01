package us.lsi.flowgraph;

import org.jgrapht.graph.SimpleDirectedGraph;
import us.lsi.common.Preconditions;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.IntegerVertexGraphView;


/**
 * FlowGraph
 *
 * <p>Grafo simple dirigido y sin peso para modelar redes de flujo.
 * La informacion de la red esta guardada en los vertices y las aristas
 * que son de los tipos FlowVertex y FlowEdge.</p>
 *
 * <p>Soporta vertices fuente, sumidero e intermedios para problemas
 * de flujo maximo y minimo.</p>
 *
 * @author Miguel Toro
 */


public class FlowGraph extends SimpleDirectedGraph<FlowVertex, FlowEdge> {

	
	/**
	 * Version para serializacion.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tipos de optimizacion para problemas de flujo.
	 */
	public enum FGType{
		/** Maximizar flujo. */
		Max,
		/** Minimizar flujo. */
		Min
	}	
	
	/** Indica si todos los valores deben ser enteros. */
	public static Boolean allInteger = false;

	/**
	 * Crea un nuevo grafo de flujo vacio.
	 *
	 * @return nuevo FlowGraph
	 */
	public static FlowGraph create() {
		return new FlowGraph(FlowEdge.class);
	}

	/**
	 * Constructor privado.
	 *
	 * @param arg0 clase de las aristas
	 */
	private FlowGraph(Class<? extends FlowEdge> arg0) {
		super(arg0);
	}
	
	/** Vista con vertices enteros. */
	private IntegerVertexGraphView<FlowVertex, FlowEdge> integerGraph = null;

	/**
	 * Crea un grafo de flujo desde un fichero.
	 *
	 * @param file ruta del fichero
	 * @return nuevo FlowGraph
	 */
	public static FlowGraph newGraph(String file) {
		FlowGraph r = GraphsReader.<FlowVertex,FlowEdge,FlowGraph>
		    newGraph(file, 
				FlowVertex::create, 
				FlowEdge::create, 
				FlowGraph::create);
		Preconditions.checkArgument(check(r),checkMessage(r));
		return r;		
	}
	
	/**
	 * Verifica la validez del grafo de flujo.
	 *
	 * @param fg el grafo a verificar
	 * @return true si es valido
	 */
	private static boolean check(FlowGraph fg){
		boolean r = true;
		for(FlowVertex v: fg.vertexSet()){
			if(v.isSource()){
				r = fg.incomingEdgesOf(v).isEmpty();
			}
			if(!r) break;
			if(v.isSink()){
				r = fg.outgoingEdgesOf(v).isEmpty();
			}
			if(!r) break;
			if(v.isIntermediate()) {
				r = !fg.incomingEdgesOf(v).isEmpty() && !fg.outgoingEdgesOf(v).isEmpty();
			}
		}
		return r;
	}
	
	private static String checkMessage(FlowGraph fg){
		String r = "";
		for(FlowVertex v: fg.vertexSet()){
			if(v.isSource() && !fg.incomingEdgesOf(v).isEmpty()) {
				r = String.format("El v�rtice %s es source pero tiene aristas de entrada", v);
				break;
			}
			if(v.isSink() && !fg.outgoingEdgesOf(v).isEmpty()){
				r = String.format("El v�rtice %s es sumidero pero tiene aristas de salida", v);
				break;
			} 
			if(v.isIntermediate() && (fg.incomingEdgesOf(v).isEmpty() || fg.outgoingEdgesOf(v).isEmpty())){
				r = String.format("El v�rtice %s es intermedio pero o no tiene aristas de entrada o de salida", v);
				break;
			}
		}
		return r;
	}
	
	public IntegerVertexGraphView<FlowVertex, FlowEdge> integerGraph() {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		return this.integerGraph;
	}
	
	public FlowEdge edge(Integer i, Integer j) {
		FlowVertex v1 = this.integerGraph.getVertex(i);
		FlowVertex v2 = this.integerGraph.getVertex(j);
		return this.getEdge(v1,v2);
	}
	
	public FlowVertex vertex(Integer i) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		return this.integerGraph.getVertex(i);
	}
	
	public Integer vertexIndex(FlowVertex v) {
		Preconditions.checkNotNull(v);
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		return this.integerGraph.index(v);
	}
	
	public Double maxEdge(Integer i, Integer j) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Preconditions.checkArgument(this.integerGraph.containsEdge(i, j), String.format("La arista (%d,%d) no existe", i,j));
		FlowVertex v1 = this.integerGraph.getVertex(i);
		FlowVertex v2 = this.integerGraph.getVertex(j);
		return  this.getEdge(v1, v2).max;
	}
	
	public Double minEdge(Integer i, Integer j) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Preconditions.checkArgument(this.integerGraph.containsEdge(i, j), String.format("La arista (%d,%d) no existe", i,j));
		FlowVertex v1 = this.integerGraph.getVertex(i);
		FlowVertex v2 = this.integerGraph.getVertex(j);
		return  this.getEdge(v1, v2).min;
	}
	
	public Double costEdge(Integer i, Integer j) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Preconditions.checkArgument(this.integerGraph.containsEdge(i, j), String.format("La arista (%d,%d) no existe", i,j));
		FlowVertex v1 = this.integerGraph.getVertex(i);
		FlowVertex v2 = this.integerGraph.getVertex(j);
		return  this.getEdge(v1, v2).cost;
	}
	
	public boolean containsEdge(Integer i, Integer j) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		return this.integerGraph.containsEdge(i, j);
	}
	
	public Double maxVertex(Integer i) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Integer n = this.integerGraph.n;
		Preconditions.checkArgument(0<=i && i<n, String.format("El vertice %d no existe", i));
		FlowVertex v1 = this.integerGraph.getVertex(i);
		return  v1.max;
	}
	
	public Double minVertex(Integer i) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Integer n = this.integerGraph.n;
		Preconditions.checkArgument(0<=i && i<n, String.format("El vertice %d no existe", i));
		FlowVertex v1 = this.integerGraph.getVertex(i);
		return  v1.min;
	}
	
	public Double costVertex(Integer i) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Integer n = this.integerGraph.n;
		Preconditions.checkArgument(0<=i && i<n, String.format("El vertice %d no existe", i));
		FlowVertex v1 = this.integerGraph.getVertex(i);
		return  v1.cost;
	}
	
	public boolean containsVertex(Integer i) {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		Integer n = this.integerGraph.n;
		return 0<=i && i<n;
	}
	
	public Integer getN() {
		if(this.integerGraph == null) {
			this.integerGraph = IntegerVertexGraphView.of(this);;
		}
		return this.integerGraph.n;
	}
	
	public FlowEdge edge(String text) {
		String[] partes = text.split("_");
		FlowEdge e = this.edge(Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
		return e;
	}
	
	public FlowVertex vertex(String text) {
		String[] partes = text.split("_");
		FlowVertex v = this.vertex(Integer.parseInt(partes[1]));
		return v;
	}

}
