package us.lsi.basictypes;

import us.lsi.common.Preconditions;

/**
 * <p>Una implementacion de una lista enlazada simple.</p>
 * 
 * <p>Esta clase proporciona una lista enlazada que almacena elementos
 * en nodos conectados secuencialmente. Mantiene referencias al primer
 * y ultimo nodo para operaciones eficientes de insercion al final.</p>
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los elementos de la lista
 */
public class LList<E> {
	/** Referencia al primer nodo de la lista */
	private Entry<E> first;
	/** Referencia al ultimo nodo de la lista */
	private Entry<E> last;	
	/** Numero de elementos en la lista */
	private int size;
	//invariant size ==0   <=> first == null && last == null
	
	/**
	 * Crea una lista enlazada vacia.
	 */
	public LList() {
		super();
		this.first = null;
		this.last = null;
		this.size=0;
	}

	/**
	 * Devuelve el numero de elementos en la lista.
	 * 
	 * @return Numero de elementos
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Comprueba si la lista esta vacia.
	 * 
	 * @return true si la lista no tiene elementos, false en caso contrario
	 */
	public boolean isEmpty(){
		return size==0;
	}
	
	/**
	 * Obtiene el elemento en la posicion especificada.
	 * 
	 * @param index Indice del elemento a obtener
	 * @return El elemento en la posicion indicada
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public E get(int index){
		return entryInPos(index).key();
	}
	
	/**
	 * Establece el elemento en la posicion especificada.
	 * 
	 * @param index Indice donde establecer el elemento
	 * @param e Elemento a establecer
	 * @return El elemento anterior en esa posicion
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public E set(int index, E e){
		Entry<E> e1 = entryInPos(index);
		E r = e1.key();
		e1.setKey(e);
		return r;
	}
	
	/**
	 * Anade un elemento al final de la lista.
	 * 
	 * @param e Elemento a anadir
	 * @return true siempre (operacion exitosa)
	 */
	public boolean add(E e){
		Entry<E> e1 = new Entry<E>(e);
		if(last==null){
			first = e1;
			last=e1;
		}else{
			last.setNext(e1);
			last = e1;
		}
		size++;
		return true;
	}
	
	/**
	 * Inserta un elemento en la posicion especificada.
	 * 
	 * @param index Posicion donde insertar el elemento
	 * @param e Elemento a insertar
	 * @return true siempre (operacion exitosa)
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public boolean add(int index, E e){
		Preconditions.checkPositionIndex(index, size);
		Entry<E> ne = new Entry<E>(e);
		if(index==size){
			add(e);
		} else if(index==0){
			ne.setNext(first);
			first = ne;
		} else {
			Entry<E> pe = entryInPos(index-1);
			ne.setNext(pe.next());
			pe.setNext(ne);
		}
		size++;
		return true;
	}
	
	/**
	 * Busca el nodo en la posicion especificada.
	 * 
	 * @param index Indice del nodo a buscar
	 * @return El nodo en la posicion indicada
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	private Entry<E> entryInPos(int index){
		Preconditions.checkElementIndex(index, size);
	    Entry<E> pe = first;
	    for(int p = 0 ; p < index; p++){
	    	pe = pe.next();
	    }
		return pe;
	}
	
	/**
	 * Elimina el elemento en la posicion especificada.
	 * 
	 * @param index Indice del elemento a eliminar
	 * @return El elemento eliminado
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public E remove(int index){
		Preconditions.checkElementIndex(index, size);
		Entry<E> e = null;
		E element;
		if(index==0){
			e = first;
			first = first.next();
			element = e.key();
		} else {
			Entry<E> pe = entryInPos(index-1);
			element = pe.next().key();
			if(index == size-1){
				last = pe;
			}else{
				pe.setNext(pe.next().next());
			}
		}
		size--;
		return element;
	}
	
	
	
	/**
	 * Devuelve una representacion en cadena de la lista.
	 * 
	 * @return Representacion textual de la lista en formato {e1,e2,...}
	 */
	public String toString(){
		String s = "{";
		boolean prim = true;
		for(Entry<E> e = first;e!=null;e = e.next()){
			if(prim){
				prim = false;
				s = s+e.key();
			}else{
				s = s+","+e.key();
			}
		}
		s = s+"}";
		return s;
	}
	
	/**
	 * <p>Clase interna que representa un nodo de la lista enlazada.</p>
	 * 
	 * <p>Cada nodo contiene un elemento (clave) y una referencia al siguiente nodo.</p>
	 * 
	 * @author Miguel Toro
	 *
	 * @param <F> Tipo del elemento almacenado en el nodo
	 */
	public class Entry<F> {
		/** Elemento almacenado en el nodo */
		private F key;
		/** Referencia al siguiente nodo */
		private Entry<F> next;
		
		/**
		 * Crea un nodo con el elemento y la referencia al siguiente.
		 * 
		 * @param element Elemento a almacenar
		 * @param next Referencia al siguiente nodo
		 */
		public Entry(F element, Entry<F> next) {
			super();
			this.key = element;
			this.next = next;
		}
		
		/**
		 * Crea un nodo con el elemento, sin siguiente.
		 * 
		 * @param element Elemento a almacenar
		 */
		public Entry(F element) {
			super();
			this.key = element;
			this.next = null;
		}
		
		/**
		 * Obtiene el elemento del nodo.
		 * 
		 * @return El elemento almacenado
		 */
		public F key() {
			return key;
		}
		
		/**
		 * Establece el elemento del nodo.
		 * 
		 * @param key Nuevo elemento
		 */
		public void setKey(F key) {
			this.key = key;
		}
		
		/**
		 * Obtiene la referencia al siguiente nodo.
		 * 
		 * @return El siguiente nodo o null si es el ultimo
		 */
		public Entry<F> next() {
			return next;
		}
		
		/**
		 * Establece la referencia al siguiente nodo.
		 * 
		 * @param next Nuevo siguiente nodo
		 */
		public void setNext(Entry<F> next) {
			this.next = next;
		}	
	}

	
}
