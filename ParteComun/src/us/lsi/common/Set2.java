package us.lsi.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.streams.Stream2;

/**
 * <p>Clase de utilidades para operaciones con conjuntos.</p>
 * 
 * <p>Proporciona metodos estaticos para crear, copiar y operar
 * con conjuntos (Set) de forma funcional.</p>
 * 
 * @author Miguel Toro
 */
public class Set2 {	
	
	/**
	 * Parsea un conjunto desde una cadena.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s Cadena con los elementos
	 * @param sep Separadores a usar
	 * @param fMap Funcion para convertir cada token
	 * @return Un nuevo conjunto
	 */
	public static <E> Set<E> parse(String s, String sep, Function<String,E> fMap) {
		return Arrays.stream(s.split("["+sep+"]"))
		.filter(e->e!=null && e.length()>0)
		.map(e->fMap.apply(e.trim()))
		.collect(Collectors.toSet());
	}
	
	/**
	 * Parsea un conjunto desde un array de tokens.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param tokens Array de cadenas
	 * @param fMap Funcion para convertir cada token
	 * @return Un nuevo conjunto
	 */
	public static <E> Set<E> parse(String[] tokens, Function<String,E> fMap) {
		return Arrays.stream(tokens)
		.filter(e->e!=null && e.length()>0)
		.map(e->fMap.apply(e.trim())).collect(Collectors.toSet());
	}

	/**
	 * Crea un conjunto con el rango [a, b) con paso c.
	 * 
	 * @param a Limite inferior (incluido)
	 * @param b Limite superior (excluido)
	 * @param c Paso
	 * @return Un nuevo conjunto con el rango
	 */
	public static Set<Integer> range(Integer a, Integer b, Integer c){		
		return Stream2.range(a, b, c).boxed()
			 .collect(Collectors.toSet());
	}	
	
	/**
	 * Crea un conjunto con el rango [a, b).
	 * 
	 * @param a Limite inferior (incluido)
	 * @param b Limite superior (excluido)
	 * @return Un nuevo conjunto con el rango
	 */
	public static Set<Integer> range(Integer a, Integer b){
		return IntStream.range(a,b).boxed().collect(Collectors.toSet());
	}
	
	/**
	 * Crea un conjunto vacio.
	 * 
	 * @param <T> Tipo de los elementos
	 * @return Un nuevo conjunto vacio
	 */
	public static <T> Set<T> empty(){
		return new HashSet<>();
	}
	
	/**
	 * Crea una copia de una coleccion como conjunto.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param c Coleccion a copiar
	 * @return Un nuevo conjunto con los elementos
	 */
	public static <T> Set<T> copy(Collection<T> c){
		return new HashSet<>(c);
	}
	
	/**
	 * Crea un TreeSet vacio para elementos comparables.
	 * 
	 * @param <T> Tipo de los elementos
	 * @return Un nuevo TreeSet vacio
	 */
	public static <T extends Comparable<? super T>> SortedSet<T> newTreeSet(){
		return new TreeSet<>();
	}
	
	/**
	 * Crea un TreeSet vacio con un comparador personalizado.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param cmp Comparador a utilizar
	 * @return Un nuevo TreeSet vacio
	 */
	public static <T> SortedSet<T> newTreeSet(Comparator<T> cmp){
		return new TreeSet<>(cmp);
	}
	
	/**
	 * Crea un conjunto con los elementos especificados.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param e Elementos a incluir
	 * @return Un nuevo conjunto
	 */
	@SafeVarargs
	public static <E> Set<E> of(E... e){
		return Arrays.stream(e).collect(Collectors.toSet());
	}
	
	/**
	 * Crea una copia del conjunto con un elemento anadido.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s Conjunto original
	 * @param e Elemento a anadir
	 * @return Un nuevo conjunto con el elemento
	 */
	public static <E> Set<E> add(Set<E> s, E e) {
		Set<E> s1 = new HashSet<>(s);
		s1.add(e);
		return s1;
	}
	
	/**
	 * Crea una copia del conjunto sin un elemento.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s Conjunto original
	 * @param e Elemento a eliminar
	 * @return Un nuevo conjunto sin el elemento
	 */
	public static <E> Set<E> remove(Set<E> s, E e) {
		Set<E> s1 = new HashSet<>(s);
		s1.remove(e);
		return s1;
	}

	/**
	 * Crea un conjunto a partir de una coleccion.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param <U> Tipo de la coleccion
	 * @param elements Coleccion de elementos
	 * @return Un nuevo conjunto
	 */
	public static <E,U extends Collection<E>> Set<E> of(U elements){
		return elements.stream().collect(Collectors.toSet());
	}
	
	/**
	 * Calcula la diferencia de dos colecciones (s1 - s2).
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s1 Primera coleccion
	 * @param s2 Segunda coleccion
	 * @return Elementos en s1 que no estan en s2
	 */
	public static <E> Set<E> difference(Collection<E> s1,  Collection<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.removeAll(s2);
		return s;
	}
	
	/**
	 * Calcula la diferencia simetrica de dos colecciones.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s1 Primera coleccion
	 * @param s2 Segunda coleccion
	 * @return Elementos que estan en una pero no en ambas
	 */
	public static <E> Set<E> symmetricDifference(Collection<E> s1,  Collection<E> s2){
		Set<E> symmetricDiff = new HashSet<E>(s1);
	    symmetricDiff.addAll(s2);
	    Set<E> tmp = new HashSet<E>(s1);
	    tmp.retainAll(s2);
	    symmetricDiff.removeAll(tmp);
	    return symmetricDiff;
	}
	
	/**
	 * Calcula la union de dos colecciones.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s1 Primera coleccion
	 * @param s2 Segunda coleccion
	 * @return Union de s1 y s2
	 */
	public static <E> Set<E> union(Collection<E> s1,  Collection<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.addAll(s2);
		return s;
	}
	
	/**
	 * Calcula la interseccion de dos colecciones.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param s1 Primera coleccion
	 * @param s2 Segunda coleccion
	 * @return Elementos comunes a s1 y s2
	 */
	public static <E> Set<E> intersection(Collection<E> s1,  Collection<E> s2){
		Set<E> s = new HashSet<>(s1);
		s.retainAll(s2);
		return s;
	}
}
