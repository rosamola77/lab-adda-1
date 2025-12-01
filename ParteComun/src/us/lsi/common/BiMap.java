package us.lsi.common;

import java.util.Map;

/**
 * <p>Implementacion de un mapa bidireccional.</p>
 * 
 * <p>Un BiMap permite buscar tanto por clave como por valor,
 * manteniendo una relacion uno a uno entre claves y valores.
 * No se permiten valores duplicados.</p>
 * 
 * @author Miguel Toro
 *
 * @param <K> Tipo de las claves
 * @param <V> Tipo de los valores
 */
public class BiMap<K, V> {
	
	/**
	 * Crea un BiMap vacio.
	 * 
	 * @param <K> Tipo de las claves
	 * @param <V> Tipo de los valores
	 * @return Un nuevo BiMap vacio
	 */
	public static <K, V> BiMap<K, V> empty() {
		return new BiMap<K, V>();
	}


	/**
	 * Crea un BiMap a partir de dos mapas.
	 * 
	 * @param <K> Tipo de las claves
	 * @param <V> Tipo de los valores
	 * @param map Mapa directo
	 * @param inverseMap Mapa inverso
	 * @return Un nuevo BiMap
	 */
	private static <K, V> BiMap<K, V> of(Map<K, V> map, Map<V, K> inverseMap) {
		return new BiMap<K, V>(map, inverseMap);
	}


	/** Mapa directo de claves a valores */
	private Map<K,V> map;
	/** Mapa inverso de valores a claves */
	private Map<V,K> inverseMap;
	
	
	/**
	 * Constructor privado que crea un BiMap vacio.
	 */
	private BiMap() {
		super();
		this.map = Map2.empty();
		this.inverseMap = Map2.empty();
	}


	/**
	 * Constructor privado a partir de mapas existentes.
	 * 
	 * @param map Mapa directo
	 * @param inverseMap Mapa inverso
	 */
	private BiMap(Map<K, V> map, Map<V, K> inverseMap) {
		super();
		this.map = map;
		this.inverseMap = inverseMap;
	}
	
	/**
	 * Inserta un par clave-valor.
	 * 
	 * @param key Clave
	 * @param value Valor
	 * @return El valor insertado
	 * @throws IllegalArgumentException si el valor ya existe en el BiMap
	 */
	public V put(K key, V value) {
		this.map.put(key,value);
		if(this.inverseMap.containsKey(value)) {
			throw new IllegalArgumentException(String.format("El valor %s ya estaba en el Bimap", value.toString()));
		}
		this.inverseMap.put(value, key);
		return value;
	}

	/**
	 * Inserta un par clave-valor forzando la eliminacion de conflictos.
	 * 
	 * @param key Clave
	 * @param value Valor
	 * @return El valor insertado
	 */
	public V forcePut(K key, V value) {
		this.map.put(key,value);
		if(this.inverseMap.containsKey(value)) {
			this.map.remove(key);
			this.inverseMap.remove(value);
		}
		this.inverseMap.put(value, key);
		return value;
	}
	
	/**
	 * Obtiene el BiMap inverso.
	 * 
	 * @return Un BiMap con claves y valores intercambiados
	 */
	public BiMap<V,K> inverse(){
		return of(inverseMap,map);
	}
	
	/**
	 * Obtiene el valor asociado a una clave.
	 * 
	 * @param key Clave a buscar
	 * @return El valor asociado o null si no existe
	 */
	public V get(K key) {
		return map.get(key);
	}
	
	/**
	 * Devuelve una representacion en cadena del BiMap.
	 * 
	 * @return Representacion textual
	 */
	public String toString() {
		return map.toString();
	}
	
	/**
	 * Obtiene el mapa interno como un Map estandar.
	 * 
	 * @return El mapa directo
	 */
	public Map<K,V> asMap(){
		return map;
	}
}
