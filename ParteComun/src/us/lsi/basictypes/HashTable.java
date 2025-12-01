package us.lsi.basictypes;

import java.util.List;

import us.lsi.common.List2;
import us.lsi.math.Math2;

/**
 * <p>Implementacion de una tabla hash con encadenamiento.</p>
 * 
 * <p>Esta clase implementa una tabla hash que almacena pares clave-valor.
 * Utiliza encadenamiento para resolver colisiones y rehashing automatico
 * cuando el factor de carga supera el umbral establecido.</p>
 * 
 * @author Miguel Toro
 *
 * @param <K> Tipo de las claves
 * @param <V> Tipo de los valores
 */
public class HashTable<K, V> {
	
	

	/**
	 * Crea una tabla hash vacia.
	 * 
	 * @param <K> Tipo de las claves
	 * @param <V> Tipo de los valores
	 * @return Una nueva tabla hash vacia
	 */
	public static <K, V> HashTable<K, V> empty() {
		return new HashTable<K, V>();
	}

	/** Numero de grupos (buckets) en la tabla */
	private int groupsNumber;
	/** Numero de elementos almacenados */
	private int size;
	/** Capacidad del array de datos */
	private int capacityData;
	/** Array de indices de grupos */
	private AList<Integer> groups;
	/** Array de entradas de datos */
	private AList<EntryData<K,V>> data;
	/** Indice de la primera entrada libre */
	private Integer firstFreeData = 0;
	
	/** Factor de carga de referencia para rehashing */
	private static double loadFactorReference = 0.75;
	
	/**
	 * Constructor privado que crea una tabla hash vacia.
	 */
	private HashTable() {
		super();
		initialParameters(13);
	}
	
	/**
	 * Calcula el siguiente numero primo mayor que el dado.
	 * 
	 * @param prime Numero de referencia
	 * @return El siguiente numero primo
	 */
	private static Integer nextPrime(int prime) {
		return Math2.siguientePrimo(prime);
	}
	
	/**
	 * Inicializa los parametros de la tabla hash.
	 * 
	 * @param groupsNumber Numero inicial de grupos
	 */
	private void initialParameters(int groupsNumber){
		this.firstFreeData = 0;
		this.size = 0;
		this.groupsNumber = groupsNumber;
		this.capacityData = (int)(this.groupsNumber*loadFactorReference+1);
		this.groups = AList.of(this.groupsNumber);
		this.data = AList.of(this.capacityData);						
		for(int i = 0; i < groupsNumber; i++){
			groups.add(-1);
		}
		for(int i = 0; i < capacityData; i++){
			data.add(EntryData.create(i+1));
		}
		data.get(this.capacityData-1).next = -1;
	}
	
	/**
	 * Calcula el grupo (bucket) correspondiente a una clave.
	 * 
	 * @param key Clave a procesar
	 * @return Indice del grupo
	 */
	private int group(K key){
		return key.hashCode()%this.groupsNumber;
	}
	
	/**
	 * Realiza rehashing si el factor de carga supera el umbral.
	 */
	private void rehash(){
		if((((double)this.size)/this.groupsNumber) >= 0.75) {
			AList<EntryData<K,V>> oldData = data;
			initialParameters(nextPrime(2*this.groupsNumber));
			for(int i=0;i<oldData.size();i++) {
				EntryData<K,V> e = oldData.get(i);
				if(e.key == null) continue;
				this.put(e.key, e.value);
			}
		}
	}
	
	
	/**
	 * Devuelve el numero de elementos en la tabla.
	 * 
	 * @return Numero de pares clave-valor almacenados
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Comprueba si la tabla esta vacia.
	 * 
	 * @return true si no hay elementos, false en caso contrario
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/**
	 * Busca la entrada correspondiente a una clave.
	 * 
	 * @param key Clave a buscar
	 * @return La entrada encontrada o null si no existe
	 */
	private EntryData<K,V> findEntry(K key){
		EntryData<K,V> r = null;
		int g = group(key);
		int next = groups.get(g);
		while(next >= 0) {
			r = data.get(next);
			if(r.key.equals(key)) break;
			next = r.next;
		}
		return r;
	}
	
	/**
	 * Obtiene el valor asociado a una clave.
	 * 
	 * @param key Clave a buscar
	 * @return El valor asociado o null si la clave no existe
	 */
	public V get(K key){
		V r = null;
		EntryData<K,V> e = findEntry(key);
		if(e!=null) r = e.value;
		return r;
	}
	
	
	/**
	 * Inserta o actualiza un par clave-valor en la tabla.
	 * 
	 * @param key Clave del elemento
	 * @param value Valor a asociar
	 * @return El valor insertado
	 */
	public V put(K key, V value){
		rehash();
		EntryData<K,V> r = findEntry(key);
		if(r==null) {
			r = data.get(this.firstFreeData);
			int g = group(key);
			int oldfirstInGrup= groups.get(g);
			groups.set(g, this.firstFreeData);
			this.firstFreeData = r.next;
			r.next=oldfirstInGrup;
			r.key = key;
			this.size = this.size +1;
		}
		r.value = value;		
		return value;
	}
	
	
	/**
	 * Libera una entrada de datos.
	 * 
	 * @param group Indice del grupo
	 * @param beforeIndex Indice de la entrada anterior
	 * @param indexData Indice de la entrada a liberar
	 */
	private void freeEntryData(int group, int beforeIndex, int indexData) {
		EntryData<K,V> e = data.get(indexData);
		if(beforeIndex < 0) {			
			groups.set(group, e.next);			
		} else {
			data.get(beforeIndex).next = e.next;
		}
		e.next = this.firstFreeData;
		this.firstFreeData = indexData;
	}
	
	
	/**
	 * Elimina el par clave-valor asociado a una clave.
	 * 
	 * @param key Clave del elemento a eliminar
	 * @return El valor eliminado o null si no existia
	 */
	public V remove(K key){
		V r = null;
		EntryData<K,V> e = null;
		int g = group(key);
		int before = -1;
		int next = groups.get(g);
		while(next >= 0) {
			e = data.get(next);
			if(e.key.equals(key)) {
				r = e.value;
				e.key = null;
				e.value = null;
				this.size = this.size -1;
				freeEntryData(g,before,next);
				break;
			}
			before = next;
			next = e.next;
		}
		return r;
	}
	
	/**
	 * Obtiene una lista con todas las entradas de la tabla.
	 * 
	 * @return Lista de entradas clave-valor
	 */
	public List<EntryTable<K,V>> entryList(){
		List<EntryTable<K,V>> r = List2.empty();
		for(int i =0;i<this.capacityData;i++) {
			EntryData<K,V> e = data.get(i);
			if(e.key == null) continue;
			r.add(EntryTable.create(e.key, e.value));
		}
		return r;
	}
	
	/**
	 * Devuelve una representacion en cadena de la tabla.
	 * 
	 * @return Representacion textual de la tabla
	 */
	public String toString(){
		boolean first = true;
		String r = "{";
		for(int i =0;i<this.capacityData;i++) {
			EntryData<K,V> e = data.get(i);
			if(e.key == null) continue;
			if(first) first = false;
			else r = r+",";
			r = r+String.format("(%s,%s)",e.key,e.value);
		}
		return r+"}";
	}
	
	/**
	 * <p>Clase interna que representa una entrada de datos en la tabla hash.</p>
	 * 
	 * <p>Almacena la clave, el valor y el indice de la siguiente entrada
	 * en la cadena de colisiones.</p>
	 * 
	 * @author Miguel Toro
	 *
	 * @param <K> Tipo de la clave
	 * @param <V> Tipo del valor
	 */
	public static class EntryData<K,V> {
		/**
		 * Crea una entrada de datos vacia con el indice del siguiente elemento.
		 * 
		 * @param <K> Tipo de la clave
		 * @param <V> Tipo del valor
		 * @param next Indice del siguiente elemento
		 * @return Nueva entrada de datos
		 */
		public static <K,V> EntryData<K,V> create(Integer next){
			return new EntryData<>(null,null,next);
		}
		
		/**
		 * Crea una entrada de datos con clave, valor y siguiente.
		 * 
		 * @param <K> Tipo de la clave
		 * @param <V> Tipo del valor
		 * @param key Clave de la entrada
		 * @param value Valor de la entrada
		 * @param next Indice del siguiente elemento
		 * @return Nueva entrada de datos
		 */
		public static <K,V> EntryData<K,V> create(K key, V value, Integer next){
			return new EntryData<>(key,value,next);
		}
		
		/** Clave de la entrada */
		K key;
		/** Valor de la entrada */
		V value;
		/** Indice del siguiente elemento en la cadena */
		Integer next;
		
		/**
		 * Constructor de entrada de datos.
		 * 
		 * @param key Clave
		 * @param value Valor
		 * @param next Indice del siguiente
		 */
		public EntryData(K key, V value, Integer next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Obtiene la clave de la entrada.
		 * 
		 * @return La clave
		 */
		public K key() {
			return key;
		}
		
		/**
		 * Obtiene el valor de la entrada.
		 * 
		 * @return El valor
		 */
		public V value() {
			return value;
		}
		
		/**
		 * Obtiene el indice del siguiente elemento.
		 * 
		 * @return Indice del siguiente
		 */
		public Integer next() {
			return next;
		}
		
		/**
		 * Representacion en cadena de la entrada.
		 * 
		 * @return Representacion textual
		 */
		public String toString(){
			return "("+key+","+value+","+next+")";
		}
	}
	
	/**
	 * <p>Clase que representa una entrada de tabla hash para uso externo.</p>
	 * 
	 * <p>Contiene solo la clave y el valor, sin informacion de encadenamiento.</p>
	 * 
	 * @author Miguel Toro
	 *
	 * @param <K> Tipo de la clave
	 * @param <V> Tipo del valor
	 */
	public static class EntryTable<K,V> {
		/**
		 * Crea una entrada de tabla con clave y valor.
		 * 
		 * @param <K> Tipo de la clave
		 * @param <V> Tipo del valor
		 * @param key Clave de la entrada
		 * @param value Valor de la entrada
		 * @return Nueva entrada de tabla
		 */
		public static <K,V> EntryTable<K,V> create(K key, V value){
			return new EntryTable<>(key,value);
		}
		
		/** Clave de la entrada */
		K key;
		/** Valor de la entrada */
		V value;
		
		/**
		 * Constructor de entrada de tabla.
		 * 
		 * @param key Clave
		 * @param value Valor
		 */
		public EntryTable(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Obtiene la clave de la entrada.
		 * 
		 * @return La clave
		 */
		public K key() {
			return key;
		}

		/**
		 * Obtiene el valor de la entrada.
		 * 
		 * @return El valor
		 */
		public V value() {
			return value;
		}
		
		/**
		 * Representacion en cadena de la entrada.
		 * 
		 * @return Representacion textual
		 */
		public String toString(){
			return "("+key+","+value+")";
		}
	}
}
