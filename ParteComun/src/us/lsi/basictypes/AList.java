package us.lsi.basictypes;


import java.lang.reflect.Array;
import java.util.Arrays;

import us.lsi.common.Preconditions;


/**
 * <p>Una implementacion de un array de tamano variable.</p>
 * 
 * <p>Esta clase proporciona una lista basada en arrays que crece
 * dinamicamente segun se anaden elementos. Implementa las operaciones
 * basicas de una lista: añadir, obtener, establecer y eliminar elementos.</p>
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los elementos de la lista
 */
public class AList<E> {
	
	/**
	 * Crea una lista vacia con capacidad inicial por defecto.
	 * 
	 * @param <E> Tipo de los elementos
	 * @return Una nueva lista vacia
	 */
	public static <E> AList<E> empty() {
		return new AList<E>();
	}

	/**
	 * Crea una lista vacia con la capacidad especificada.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param capacity Capacidad inicial de la lista
	 * @return Una nueva lista vacia con la capacidad indicada
	 */
	public static <E> AList<E> of(int capacity) {
		return new AList<E>(capacity);
	}

	/**
	 * Crea una copia de la lista proporcionada.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param a Lista a copiar
	 * @return Una nueva lista con los mismos elementos que la original
	 */
	public static <E> AList<E> of(AList<E> a) {
		return new AList<E>(a);
	}

	/**
	 * Crea una lista a partir de un array.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param a Array con los elementos iniciales
	 * @return Una nueva lista con los elementos del array
	 */
	public static <E> AList<E> of(E[] a) {
		return new AList<E>(a);
	}

	/** Capacidad actual del array interno */
	private int capacity;
	/** Numero de elementos en la lista */
	private int size;
	/** Array interno que almacena los datos */
	private E[] data;
	/** Capacidad inicial por defecto */
	private final int INITIAL_CAPACITY = 10;
	
	/**
	 * Constructor privado que crea una lista vacia con capacidad inicial por defecto.
	 */
	private AList() {
		super();
		this.capacity = INITIAL_CAPACITY;
		this.size = 0;
		this.data = null;
	}
	
	/**
	 * Constructor privado que crea una lista vacia con la capacidad especificada.
	 * 
	 * @param capacity Capacidad inicial
	 */
	private AList(int capacity) {
		super();
		this.capacity = capacity;
		this.size = 0;
		this.data = null;
	}
	
	/**
	 * Constructor de copia que crea una nueva lista a partir de otra.
	 * 
	 * @param a Lista original a copiar
	 */
	private AList(AList<E> a) {
		super();
		this.capacity = a.capacity;
		this.size = a.size();
		this.data = Arrays.copyOf(a.data,a.capacity);
	}
	
	/**
	 * Constructor que crea una lista a partir de un array.
	 * 
	 * @param a Array con los elementos iniciales
	 */
	private AList(E[] a) {
		super();
		this.capacity = a.length;
		this.size = capacity;
		this.data = Arrays.copyOf(a, capacity);
	}

	/**
	 * Duplica la capacidad del array interno cuando esta lleno.
	 */
    private void grow(){
    	if(size==capacity){
    		E[] oldElements = data;
    		capacity = capacity*2;
    		data = Arrays.copyOf(oldElements, capacity);
    	}
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
    	return size == 0;
    }
    
    /**
     * Obtiene el elemento en la posicion especificada.
     * 
     * @param index Indice del elemento a obtener
     * @return El elemento en la posicion indicada
     * @throws IndexOutOfBoundsException si el indice esta fuera de rango
     */
	public E get(int index) {
    	Preconditions.checkElementIndex(index, size);
		return data[index];
	}
    
	/**
	 * Establece el elemento en la posicion especificada.
	 * 
	 * @param index Indice donde establecer el elemento
	 * @param e Elemento a establecer
	 * @return El elemento anterior en esa posicion
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	@SuppressWarnings("unchecked")
	public E set(int index, E e){	
		Preconditions.checkPositionIndex(index,this.size);
		if(this.data == null) this.data = (E[]) Array.newInstance(e.getClass(), capacity);
		if(index == this.size) {
			this.size = this.size +1;
			grow();
		}
		E r = get(index);
		data[index]= e;
		return r;
	}
	
	/**
	 * Anade un elemento al final de la lista.
	 * 
	 * @param e Elemento a anadir
	 * @return true siempre (operacion exitosa)
	 */
	@SuppressWarnings("unchecked")
	public boolean add(E e) {
		if(this.data == null) this.data = (E[]) Array.newInstance(e.getClass(), capacity);
		grow();
		data[size] = e;
		size++;   	
		return true;
	}
	
	/**
	 * Inserta un elemento en la posicion especificada.
	 * 
	 * @param index Posicion donde insertar el elemento
	 * @param e Elemento a insertar
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public void add(int index, E e) {
		Preconditions.checkPositionIndex(index, size);
		add(e);
		// size ya ha quedado aumentado
		for(int i = size-1; i > index; i--){
			data[i]= data[i-1];
		}
		data[index]=e;
	}
	
	/**
	 * Elimina el elemento en la posicion especificada.
	 * 
	 * @param index Indice del elemento a eliminar
	 * @return El elemento eliminado
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public E remove(int index) {
		Preconditions.checkElementIndex(index, size);
		E e = data[index];
		for(int i = index; i < size-1; i++){
			data[i]= data[i+1];
		}
		size --;
		return e;
	}
	
	/**
	 * Convierte la lista a un array.
	 * 
	 * @return Un array con todos los elementos de la lista
	 */
	public E[] toArray(){
		E[] r = Arrays.copyOf(this.data, size);
		return r;
	}
	
	/**
	 * Devuelve una representacion en cadena de la lista.
	 * 
	 * @return Representacion textual de la lista en formato {e1,e2,...}
	 */
	public String toString(){
		String s = "{";
		boolean prim = true;
		for(int i=0; i<size; i++){
			if(prim){
				prim = false;
				s = s+data[i];
			}else{
				s = s+","+data[i];
			}
		}
		s = s+"}";
		return s;
	}
}
