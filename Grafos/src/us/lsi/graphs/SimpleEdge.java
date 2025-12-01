package us.lsi.graphs;

import us.lsi.common.Preconditions;

/**
 * <p>Interfaz que representa una arista simple de un grafo.</p>
 * 
 * <p>Una arista simple conecta dos vertices y puede tener un peso asociado.
 * Proporciona metodos para obtener los vertices origen y destino, el peso
 * de la arista, y el vertice opuesto dado uno de los extremos.</p>
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los vertices
 */
public interface SimpleEdge<V> {
	
	/**
	 * Crea una arista con peso entre dos vertices.
	 * 
	 * @param v1 Un vertice
	 * @param v2 Un segundo vertice
	 * @param weight El peso de la arista
	 * @param <V> El tipo de los vertices
	 * @return Una arista entre ambos vertices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2, Double weight) {
		return new SimpleEdgeR<V>(v1, v2, weight);
	}
	
	/**
	 * Crea una arista con peso 1.0 entre dos vertices.
	 * 
	 * @param v1 Un vertice
	 * @param v2 Un segundo vertice
	 * @param <V> El tipo de los vertices
	 * @return Una arista con peso unitario entre ambos vertices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2) {
		return new SimpleEdgeR<V>(v1, v2,1.);
	}
	
	/**
	 * Obtiene el vertice origen de la arista.
	 * 
	 * @return El vertice origen
	 */
	V source();
	
	/**
	 * Obtiene el vertice destino de la arista.
	 * 
	 * @return El vertice destino
	 */
	V target();
	
	/**
	 * Obtiene el peso de la arista.
	 * 
	 * @return El peso de la arista
	 */
	Double weight();
	
	/**
	 * Obtiene el vertice opuesto dado uno de los extremos de la arista.
	 * 
	 * @param v Un vertice de la arista
	 * @return El otro vertice de la arista
	 */
	public  default V otherVertex(V v){
		Preconditions.checkNotNull(v,"El vertice no puede ser null");
		V r = null;
		if(v.equals(this.source())) r = this.target();
		else if(v.equals(this.target())) r = this.source();
		return r;
	}
	
	/**
	 * <p>Implementacion record de SimpleEdge.</p>
	 * 
	 * @author Miguel Toro
	 *
	 * @param <V> El tipo de los vertices
	 * @param source Vertice origen
	 * @param target Vertice destino
	 * @param weight Peso de la arista
	 */
	public static record SimpleEdgeR<V>(V source,V target,Double weight) implements SimpleEdge<V> {
		/**
		 * Representacion en cadena de la arista.
		 * 
		 * @return Cadena con formato "(source,target,weight)"
		 */
		public String toString() {
			return String.format("(%s,%s,%.2f)",source,target,weight);
		}
	}

}
