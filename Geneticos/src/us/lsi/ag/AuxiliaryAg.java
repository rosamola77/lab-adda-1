package us.lsi.ag;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;

import us.lsi.common.List2;
import us.lsi.common.Multiset;
import us.lsi.common.Preconditions;
import us.lsi.common.Set2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.streams.Collectors2;

/**
 * AuxiliaryAg
 *
 * <p>Clase de utilidades para algoritmos genéticos. Proporciona métodos
 * de conversión, funciones de distancia para restricciones y utilidades
 * para validación de caminos en grafos.</p>
 *
 * <p>Incluye funciones para:
 * <ul>
 *   <li>Conversión de valores continuos a discretos</li>
 *   <li>Cálculo de distancias a restricciones (para penalización)</li>
 *   <li>Validación de permutaciones y diferencias</li>
 *   <li>Verificación de caminos en grafos</li>
 * </ul>
 * </p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class AuxiliaryAg {
	
	/**
	 * Convierte un valor en [0,1) a un valor Double en el rango [min, max).
	 *
	 * @param d valor en [0,1)
	 * @param min valor mínimo del rango
	 * @param max valor máximo del rango
	 * @return valor convertido
	 * @throws IllegalArgumentException si min &ge; max
	 */
	public static Double convert(Double d, Double min, Double max) {
		Preconditions.checkArgument(min < max, 
				String.format("E valor de min = %.2f debe ser inferior a max = %.2f", min, max));
		return min + (max-min)*d;
	}
	
	/**
	 * Convierte un valor en [0,1) a un valor Integer en el rango [min, max).
	 *
	 * @param d valor en [0,1)
	 * @param min valor mínimo del rango
	 * @param max valor máximo del rango
	 * @return valor convertido
	 * @throws IllegalArgumentException si min &ge; max
	 */
	public static Integer convert(Double d, Integer min, Integer max) {
		Preconditions.checkArgument(min < max, 
				String.format("E valor de min = %d debe ser inferior a max = %d", min, max));
		return (int) (min + (max-min)*d);
	}
	
	/**
	 * Convierte una lista de valores continuos en una permutación de la secuencia normal.
	 *
	 * <p>Ordena los elementos de la secuencia normal según los valores continuos.</p>
	 *
	 * @param <E> tipo de los elementos
	 * @param d lista de valores continuos para ordenar
	 * @param normalSequence secuencia a permutar
	 * @return la secuencia permutada
	 * @throws IllegalArgumentException si los tamaños no coinciden
	 */
	public static <E> List<E> convert(List<Double> d, List<E> normalSequence) {		
		Preconditions.checkArgument(d.size() == normalSequence.size(), 
				String.format("Los tamaños %d, %d debe ser iguales",d.size(), normalSequence.size()));
		Integer n = d.size();
		return IntStream.range(0, n).boxed()
				.map(i->Pair.of(d.get(i),normalSequence.get(i)))
				.sorted(Comparator.comparing(p->p.getFirst()))
				.map(p->p.getSecond())
				.toList();			
	}
	
	/**
	 * Convierte un valor continuo en un elemento de una lista de valores.
	 *
	 * @param d valor en [0,1)
	 * @param values lista de valores posibles
	 * @return el valor seleccionado
	 */
	public static Integer convert(Double d, List<Integer> values) {
		Integer index = (int) (values.size()*d);
		return values.get(index);
	}
	
	/**
	 * Calcula la distancia a un valor booleano verdadero.
	 *
	 * @param in valor booleano
	 * @return 0 si es verdadero, 1 si es falso
	 */
	public static Double distanceToBool(Boolean in) {
		return in?0.:1.;
	}
	
	/**
	 * Calcula la distancia a la restricción in &le; 0.
	 *
	 * @param in valor a verificar
	 * @return in*in si in &gt; 0, cero en caso contrario
	 */
	public static Double distanceToLeZero(Double in) {
		Double r = 0.;		
		if(in > 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * Calcula la distancia a la restricción in &ge; 0.
	 *
	 * @param in valor a verificar
	 * @return in*in si in &lt; 0, cero en caso contrario
	 */
	public static Double distanceToGeZero(Double in) {
		Double r = 0.;		
		if(in < 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * Calcula la distancia a la restricción in = 0.
	 *
	 * @param in valor a verificar
	 * @return in*in
	 */
	public static Double distanceToEqZero(Double in) {
		return in*in;
	}
	
	/**
	 * Verifica si todos los elementos de una lista son diferentes.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls lista a verificar
	 * @return {@code true} si todos son diferentes
	 */
	public static <E> Boolean allDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return n.equals(m);
	}
	
	/**
	 * Calcula el cuadrado de la diferencia entre el tamaño y elementos únicos.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls lista a verificar
	 * @return la distancia a tener todos diferentes
	 */
	public static <E> Double distanceToAllDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return (double)(n-m)*(n-m);
	}
	
	/**
	 * Verifica si una lista es una permutación de otra.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls1 primera lista
	 * @param ls2 segunda lista
	 * @return {@code true} si ls1 es una permutación de ls2
	 */
	public static <E> Boolean isPermutation(List<E> ls1, List<E> ls2) {
		Multiset<E> m1 = ls1.stream().collect(Collectors2.toMultiset());
		Multiset<E> m2 = ls2.stream().collect(Collectors2.toMultiset());
		return m1.equals(m2);
	}
	
	/**
	 * Calcula el cuadrado del cardinal de la diferencia simétrica.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls1 primera lista
	 * @param ls2 segunda lista
	 * @return la distancia a ser permutación
	 */
	public static <E> Double distanceToPermutation(List<E> ls1, List<E> ls2) {
		Multiset<E> m1 = ls1.stream().collect(Collectors2.toMultiset());
		Multiset<E> m2 = ls2.stream().collect(Collectors2.toMultiset());
		Integer n = Multiset.symmetricDifference(m1, m2).size();
		return (double) n*n;
	}
	
	/**
	 * Calcula el cuadrado del número de elementos false.
	 *
	 * @param <E> tipo de los elementos (no usado)
	 * @param ls lista de booleanos
	 * @return la distancia a que todos sean true
	 */
	public static <E> Double distanceToAllMatch(List<Boolean> ls) {
		Integer m = ls.stream().mapToInt(e->e?0:1).sum();
		return (double)m *m;
	}
	
	/**
	 * Compara dos listas para igualdad.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls1 primera lista
	 * @param ls2 segunda lista
	 * @return {@code true} si son iguales
	 */
	public static <E> Boolean equals(List<E> ls1, List<E> ls2) {
		return ls1.equals(ls2);
	}
	
	/**
	 * Compara dos conjuntos para igualdad.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls1 primer conjunto
	 * @param ls2 segundo conjunto
	 * @return {@code true} si son iguales
	 */
	public static <E> Boolean equals(Set<E> ls1, Set<E> ls2) {
		return ls1.equals(ls2);
	}
	
	/**
	 * Calcula la distancia a la igualdad entre dos listas.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls1 primera lista
	 * @param ls2 segunda lista
	 * @return cuadrado del tamaño de la diferencia simétrica
	 */
	public static <E> Double distanceToEquals(List<E> ls1, List<E> ls2) {
		Integer n = List2.symmetricDifference(ls1, ls2).size();
		return (double) n*n;
	}
	
	/**
	 * Calcula la distancia a la igualdad entre dos conjuntos.
	 *
	 * @param <E> tipo de los elementos
	 * @param ls1 primer conjunto
	 * @param ls2 segundo conjunto
	 * @return cuadrado del tamaño de la diferencia simétrica
	 */
	public static <E> Double distanceToEquals(Set<E> ls1, Set<E> ls2) {
		Integer n = Set2.symmetricDifference(ls1, ls2).size();
		return (double) n*n;
	}
	
	/**
	 * Extrae la lista de vértices de una lista de aristas.
	 *
	 * @param graph el grafo
	 * @param edges lista de aristas
	 * @return lista de vértices en orden
	 */
	private static List<Integer> vertices(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){
		Integer n = edges.size();
		List<Integer> vertices = IntStream.range(0,n).boxed()
				.map(i->graph.getEdgeSource(edges.get(i)))
				.collect(Collectors.toList());
		vertices.add(graph.getEdgeTarget(edges.get(n-1)));
		return vertices;
	}
	
	/**
	 * Verifica si una lista de vértices forma un camino simple abierto.
	 *
	 * @param graph el grafo
	 * @param vertices lista de vértices
	 * @return {@code true} si es un camino simple abierto válido
	 */
	public static Boolean isSimpleOpenPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return allDifferents(vertices) &&
				vertices.stream().allMatch(v->graph.containsVertex(v)) &&
				IntStream.range(0,n-1).boxed().allMatch(i->graph.containsEdge(vertices.get(i),vertices.get(i+1)));
	}
	
	/**
	 * Verifica si una lista de aristas forma un camino simple abierto.
	 *
	 * @param graph el grafo
	 * @param edges lista de aristas
	 * @return {@code true} si es un camino simple abierto válido
	 */
	public static Boolean isSimpleOpenPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){		
		return isSimpleOpenPathVertices(graph,vertices(graph,edges));
	}
	
	/**
	 * Verifica si una lista de vértices forma un camino simple cerrado.
	 *
	 * @param graph el grafo
	 * @param vertices lista de vértices
	 * @return {@code true} si es un camino simple cerrado válido
	 */
	public static Boolean isSimpleClosedPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return isSimpleOpenPathVertices(graph,vertices) &&
			   graph.containsEdge(vertices.get(n-1),vertices.get(0));
	}
	
	/**
	 * Verifica si una lista de aristas forma un camino simple cerrado.
	 *
	 * @param graph el grafo
	 * @param edges lista de aristas
	 * @return {@code true} si es un camino simple cerrado válido
	 */
	public static Boolean isSimpleClosedPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){		
		return isSimpleClosedPathVertices(graph,vertices(graph,edges));
	}
	
	/**
	 * Calcula la distancia a un camino simple abierto.
	 *
	 * @param graph el grafo
	 * @param vertices lista de vértices
	 * @return suma de distancias a las restricciones
	 */
	public static Double distanceToSimpleOpenPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		Double d1 = distanceToAllDifferents(vertices);
		Double d2 =	distanceToAllMatch(vertices.stream().map(v->graph.containsVertex(v)).toList());
		Double d3 =	distanceToAllMatch(IntStream.range(0,n-1).boxed()
				.map(i->graph.containsEdge(vertices.get(i),vertices.get((i+1)))).toList());
		return d1+d2+d3;
	}
	
	/**
	 * Calcula la distancia a un camino simple cerrado.
	 *
	 * @param graph el grafo
	 * @param vertices lista de vértices
	 * @return suma de distancias a las restricciones
	 */
	public static Double distanceToSimpleClosedPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return distanceToSimpleOpenPathVertices(graph,vertices)+
				10*distanceToBool(graph.containsEdge(vertices.get(n-1),vertices.get(0)));
	}
	
	/**
	 * Calcula la distancia a un camino simple abierto (versión con aristas).
	 *
	 * @param graph el grafo
	 * @param edges lista de aristas
	 * @return suma de distancias a las restricciones
	 */
	public static Double distanceToSimpleOpenPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges) {
		List<Integer> vertices = vertices(graph,edges);
		Integer n = vertices.size();
		return distanceToSimpleOpenPathVertices(graph,vertices(graph,edges))
				+ 10*distanceToBool(graph.containsEdge(vertices.get(n-1),vertices.get(0)));
	}
	
	/**
	 * Método de prueba para conversión de permutaciones.
	 */
	public static void test1() {
		List<Integer> sn = List.of(0,1,1,1,2,3,4,5,6);
		Integer n = sn.size();
		Random r = new Random();
		List<Double> d = r.doubles().limit(n).boxed().toList();
		System.out.println(convert(d,sn));
	}
	
	/**
	 * Método principal para pruebas.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		
	}

}
