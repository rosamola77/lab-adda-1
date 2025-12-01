package us.lsi.graphs;

import java.util.Map;

import org.jgrapht.Graph;


/**
 * <p>Clase que almacena datos compartidos de un grafo.</p>
 * 
 * <p>Proporciona acceso estatico a un grafo con vertices enteros,
 * sus pesos, dimensiones y vertices origen y destino para
 * problemas de caminos.</p>
 * 
 * @author Miguel Toro
 */
public class GraphData {
	
	/** El grafo con vertices enteros y aristas simples */
	public static Graph<Integer,SimpleEdge<Integer>> graph;
	/** Mapa de pesos de vertices */
	public static Map<Integer,Double> vertexWeight;
	/** Numero de filas o dimension N */
	public static Integer n;
	/** Numero de columnas o dimension M */
	public static Integer m;
	/** Vertice origen */
	public static Integer origin;
	/** Vertice destino */
	public static Integer target;
	
	/**
	 * Comprueba si existe una arista entre dos vertices.
	 * 
	 * @param i Vertice origen
	 * @param j Vertice destino
	 * @return true si existe la arista
	 */
	public static Boolean containsEdge(Integer i, Integer j) {
		return graph.containsEdge(i,j);
	}
	
	/**
	 * Obtiene el peso de una arista entre dos vertices.
	 * 
	 * @param i Vertice origen
	 * @param j Vertice destino
	 * @return El peso de la arista
	 */
	public static Double edgeWeight(Integer i, Integer j) {
		return graph.getEdge(i,j).weight();
	}
	
	/**
	 * Comprueba si existe un vertice.
	 * 
	 * @param i Indice del vertice
	 * @return true si el vertice existe
	 */
	public static Boolean containsVertex(Integer i) {
		return graph.containsVertex(i);
	}
	
	/**
	 * Obtiene el peso de un vertice.
	 * 
	 * @param i Indice del vertice
	 * @return El peso del vertice
	 */
	public static Double vertexWeight(Integer i) {
		return vertexWeight.get(i);
	}
	
	/**
	 * Obtiene la dimension N.
	 * 
	 * @return El valor de N
	 */
	public static Integer getN() {
		return n;
	}
	
	/**
	 * Obtiene la dimension M.
	 * 
	 * @return El valor de M
	 */
	public static Integer getM() {
		return m;
	}
	
	/**
	 * Obtiene el vertice origen.
	 * 
	 * @return El vertice origen
	 */
	public static Integer origin() {
		return origin;
	}
	
	/**
	 * Obtiene el vertice destino.
	 * 
	 * @return El vertice destino
	 */
	public static Integer target() {
		return target;
	}



}
