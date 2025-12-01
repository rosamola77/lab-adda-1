package us.lsi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


/**
 * <p>Implementacion de un multimap basado en listas.</p>
 * 
 * <p>Permite asociar multiples valores a una misma clave,
 * almacenando los valores en listas. Util para relaciones
 * uno a muchos.</p>
 * 
 * @author Miguel Toro
 *
 * @param <K> Tipo de las claves
 * @param <V> Tipo de los valores
 */
public class ListMultimap<K, V>  {
	
	/**
	 * Crea un ListMultimap vacio.
	 * 
	 * @param <K> Tipo de las claves
	 * @param <V> Tipo de los valores
	 * @return Un nuevo ListMultimap vacio
	 */
	public static <K,V> ListMultimap<K,V> empty(){
		return new ListMultimap<>();
	}
	
	/**
	 * Crea un ListMultimap a partir de un mapa de listas.
	 * 
	 * @param <K> Tipo de las claves
	 * @param <V> Tipo de los valores
	 * @param map Mapa de listas
	 * @return Un nuevo ListMultimap
	 */
	public static <K,V> ListMultimap<K,V> of(Map<K,List<V>> map){
		return new ListMultimap<>(map);
	}

	/** Mapa interno que almacena las listas de valores */
	private Map<K,List<V>> map;

	/**
	 * Constructor privado que crea un ListMultimap vacio.
	 */
	private ListMultimap() {
		super();
		this.map = new HashMap<K, List<V>>();
	}
	
	/**
	 * Constructor privado a partir de un mapa.
	 * 
	 * @param map Mapa de listas
	 */
	private ListMultimap(Map<K,List<V>> map) {
		super();
		this.map = map;
	}

	/**
	 * Obtiene el mapa interno como un Map estandar.
	 * 
	 * @return El mapa de listas
	 */
	public Map<K, List<V>> asMap() {
		return map;
	}
	
	/**
	 * Obtiene el mapa reduciendo las listas con un operador.
	 * 
	 * @param op Operador de reduccion
	 * @return Mapa con un unico valor por clave
	 */
	public Map<K, V> asMap(BinaryOperator<V> op) {
		Map<K, V> r = new HashMap<>();
		this.keySet().stream().forEach(k->r.put(k,this.get(k).stream().reduce(op).orElse(null)));
		return r;
	}

	/**
	 * Elimina todas las entradas del multimap.
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Comprueba si existe una clave.
	 * 
	 * @param key Clave a buscar
	 * @return true si la clave existe
	 */
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	/**
	 * Comprueba si existe un par clave-valor.
	 * 
	 * @param key Clave a buscar
	 * @param value Valor a buscar
	 * @return true si existe el par
	 */
	public boolean containsEntry(Object key, Object value) {
		return map.containsKey(key) && map.get(key).contains(value);
	}
	
	/**
	 * Comprueba si existe un valor en alguna de las listas.
	 * 
	 * @param v Valor a buscar
	 * @return true si existe el valor
	 */
	public boolean containsValue(Object v) {
		return this.values().contains(v);
	}

	/**
	 * Comprueba igualdad con otro objeto.
	 * 
	 * @param object Objeto a comparar
	 * @return true si son iguales
	 */
	public boolean equals(Object object) {
		return map.equals(object);
	}

	/**
	 * Obtiene la lista de valores asociada a una clave.
	 * 
	 * @param key Clave a buscar
	 * @return Lista de valores o null si no existe
	 */
	public List<V> get(K key) {
		return map.get(key);
	}

	/**
	 * Calcula el hash code del multimap.
	 * 
	 * @return Hash code
	 */
	public int hashCode() {
		return map.hashCode();
	}

	/**
	 * Comprueba si el multimap esta vacio.
	 * 
	 * @return true si no hay claves
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * Obtiene el conjunto de claves.
	 * 
	 * @return Conjunto de claves
	 */
	public Set<K> keySet() {
		return map.keySet();
	}

	/**
	 * Anade un valor a la lista de una clave.
	 * 
	 * @param key Clave
	 * @param value Valor a anadir
	 * @return true si se anado exitosamente
	 */
	public boolean put(K key, V value) {
		if(!map.containsKey(key)) map.put(key, new ArrayList<>());
		return map.get(key).add(value);
	}

	/**
	 * Obtiene el numero de claves.
	 * 
	 * @return Numero de claves
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Representacion en cadena del multimap.
	 * 
	 * @return Representacion textual
	 */
	public String toString() {
		return map.toString();
	}
	
	/**
	 * Crea una copia del multimap.
	 * 
	 * @return Una nueva copia
	 */
	public ListMultimap<K,V> copy() {
		Map<K,List<V>> r = new HashMap<>();
		this.keySet().forEach(k->r.put(k,List2.copy(this.get(k))));
		return ListMultimap.of(r);
	}
	
	/**
	 * Combina dos multimaps en uno nuevo.
	 * 
	 * @param <K> Tipo de las claves
	 * @param <R> Tipo de los valores
	 * @param m1 Primer multimap
	 * @param m2 Segundo multimap
	 * @return Multimap combinado
	 */
	public static <K,R> ListMultimap<K,R> add(ListMultimap<K,R> m1, ListMultimap<K,R> m2) {
		Map<K,List<R>> r = m1.copy().asMap();
		Map<K,List<R>> r2 = m1.asMap();
		r.keySet().forEach(k->r.put(k,List2.union(r.getOrDefault(k,List2.empty()),r2.getOrDefault(k,List2.empty()))));	
		return ListMultimap.of(r);
	}

	/**
	 * Obtiene el conjunto de todos los valores.
	 * 
	 * @return Conjunto de valores unicos
	 */
	public Set<V> values() {
		return map.keySet()
				.stream()
				.flatMap(x->map.get(x).stream())
				.collect(Collectors.toSet());
	}
	
}
