package us.lsi.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>Interfaz con metodos de utilidad para comparaciones.</p>
 * 
 * <p>Proporciona metodos estaticos para comparar elementos, verificar
 * ordenes, encontrar maximos y minimos, y comprobar si elementos
 * estan en intervalos.</p>
 * 
 * @author Miguel Toro
 *
 * @param <T> El tipo de los elementos sobre los que se establece el orden
 */
public interface Comparator2<T> {
	
	/**
	 * Enumeracion que representa el resultado de una comparacion.
	 */
	public enum Type {
		/** Igualdad */
		EQ,
		/** Menor que */
		LT,
		/** Mayor que */
		GT
	}
	
	/**
	 * Compara dos elementos comparables.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return EQ, LT o GT segun e1 sea igual, menor o mayor que e2
	 */
	public static <T extends Comparable<? super T>> Type compare(T e1, T e2){
		Type r;
		if(e1.compareTo(e2) == 0) {
			r = Type.EQ;
		} else if(e1.compareTo(e2) < 0) {
			r = Type.LT;
		} else {
			r = Type.GT;
		}
		return r;
	}
	
	/**
	 * Comprueba si el primer elemento es mayor que el segundo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return true si e1 &gt; e2
	 */
	public static <T extends Comparable<? super T>> boolean isGT(T e1, T e2){
		return e1.compareTo(e2) > 0;
	}
	
	/**
	 * Comprueba si el primer elemento es mayor o igual que el segundo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return true si e1 &gt;= e2
	 */
	public static <T extends Comparable<? super T>> boolean isGE(T e1, T e2){
		return e1.compareTo(e2) >= 0;
	}
	
	/**
	 * Comprueba si el primer elemento es menor que el segundo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return true si e1 &lt; e2
	 */
	public static <T extends Comparable<? super T>> boolean isLT(T e1, T e2){
		return e1.compareTo(e2) < 0;
	}
	
	/**
	 * Comprueba si el primer elemento es menor o igual que el segundo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return true si e1 &lt;= e2
	 */
	public static <T extends Comparable<? super T>> boolean isLE(T e1, T e2){
		return e1.compareTo(e2) <= 0;
	}
	
	/**
	 * Comprueba si e1 &gt; e2, considerando null como infinito negativo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @return true si e2 es null o e1 &gt; e2
	 */
	public static <T extends Comparable<? super T>> boolean isGTNull(T e1, T e2){
		if(e2==null) return true;
		return e1.compareTo(e2) > 0;
	}
	
	/**
	 * Comprueba si e1 &gt;= e2, considerando null como infinito negativo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @return true si e2 es null o e1 &gt;= e2
	 */
	public static <T extends Comparable<? super T>> boolean isGENull(T e1, T e2){
		if(e2==null) return true;
		return e1.compareTo(e2) >= 0;
	}
	
	/**
	 * Comprueba si e1 &lt; e2, considerando null como infinito positivo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @return true si e2 es null o e1 &lt; e2
	 */
	public static <T extends Comparable<? super T>> boolean isLTNull(T e1, T e2){
		if(e2==null) return true;
		return e1.compareTo(e2) < 0;
	}
	
	/**
	 * Comprueba si e1 &lt;= e2, considerando null como infinito positivo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @return true si e2 es null o e1 &lt;= e2
	 */
	public static <T extends Comparable<? super T>> boolean isLENull(T e1, T e2){
		if(e2==null) return true;
		return e1.compareTo(e2) <= 0;
	}
	
	/**
	 * Compara dos elementos usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return EQ, LT o GT segun e1 sea igual, menor o mayor que e2
	 */
	public static <T> Type compare(T e1, T e2, Comparator<? super T> cmp){
		Type r;
		if(cmp.compare(e1,e2) == 0) {
			r = Type.EQ;
		} else if(cmp.compare(e1,e2) < 0) {
			r = Type.LT;
		} else {
			r = Type.GT;
		}
		return r;
	}
	
	/**
	 * Comprueba si e1 &gt; e2 usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return true si e1 &gt; e2
	 */
	public static <T> boolean isGT(T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e1,e2) > 0;
	}
	
	/**
	 * Comprueba si e1 &gt;= e2 usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return true si e1 &gt;= e2
	 */
	public static <T> boolean isGE(T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e1,e2) >= 0;
	}
	
	/**
	 * Comprueba si e1 &lt; e2 usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return true si e1 &lt; e2
	 */
	public static <T> boolean isLT(T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e1,e2) < 0;
	}
	
	/**
	 * Comprueba si e1 &lt;= e2 usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return true si e1 &lt;= e2
	 */
	public static <T> boolean isLE(T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e1,e2) <= 0;
	}
	
	/**
	 * Comprueba si e1 == e2 usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return true si e1 == e2
	 */
	public static <T> boolean isEQ(T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e1,e2) == 0;
	}
	
	/**
	 * Comprueba si e1 &gt; e2 con comparador, considerando null como infinito negativo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @param cmp Comparador a utilizar
	 * @return true si e2 es null o e1 &gt; e2
	 */
	public static <T> boolean isGTNull(T e1, T e2, Comparator<? super T> cmp){
		if(e2==null) return true;
		return cmp.compare(e1,e2) > 0;
	}
	
	/**
	 * Comprueba si e1 &gt;= e2 con comparador, considerando null como infinito negativo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @param cmp Comparador a utilizar
	 * @return true si e2 es null o e1 &gt;= e2
	 */
	public static <T> boolean isGENull(T e1, T e2, Comparator<? super T> cmp){
		if(e2==null) return true;
		return cmp.compare(e1,e2) >= 0;
	}
	
	/**
	 * Comprueba si e1 &lt; e2 con comparador, considerando null como infinito positivo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @param cmp Comparador a utilizar
	 * @return true si e2 es null o e1 &lt; e2
	 */
	public static <T> boolean isLTNull(T e1, T e2, Comparator<? super T> cmp){
		if(e2==null) return true;
		return cmp.compare(e1,e2) < 0;
	}
	
	/**
	 * Comprueba si e1 &lt;= e2 con comparador, considerando null como infinito positivo.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @param cmp Comparador a utilizar
	 * @return true si e2 es null o e1 &lt;= e2
	 */
	public static <T> boolean isLENull(T e1, T e2, Comparator<? super T> cmp){
		if(e2==null) return true;
		return cmp.compare(e1,e2) <= 0;
	}
	
	/**
	 * Comprueba si e1 == e2 con comparador, considerando null como diferente.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento (puede ser null)
	 * @param cmp Comparador a utilizar
	 * @return false si e2 es null, true si e1 == e2
	 */
	public static <T> boolean isEQNull(T e1, T e2, Comparator<? super T> cmp){
		if(e2==null) return false;
		return cmp.compare(e1,e2) == 0;
	}
	
	/**
	 * Devuelve el maximo de dos elementos comparables.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return El mayor de los dos
	 */
	public static <T extends Comparable<? super T>> T max(T e1, T e2){
		return isGE(e1,e2)?e1:e2;
	}
	
	/**
	 * Devuelve el maximo de dos elementos usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return El mayor de los dos
	 */
	public static <T> T max(T e1, T e2, Comparator<? super T> cmp){
		return isGE(e1,e2,cmp)?e1:e2;
	}
	
	/**
	 * Devuelve el maximo de varios elementos comparables.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param elements Elementos a comparar
	 * @return El mayor de todos
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> T max(T... elements){
		return Arrays.stream(elements).max(Comparator.naturalOrder()).get();
	}
	
	/**
	 * Devuelve el maximo de varios elementos usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param cmp Comparador a utilizar
	 * @param elements Elementos a comparar
	 * @return El mayor de todos
	 */
	@SuppressWarnings("unchecked")
	public static <T> T max(Comparator<? super T> cmp, T... elements){
		return Arrays.stream(elements).max(cmp).get();
	}
	
	/**
	 * Devuelve el minimo de dos elementos comparables.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @return El menor de los dos
	 */
	public static <T extends Comparable<? super T>> T min(T e1, T e2){
		return isLE(e1,e2)?e1:e2;
	}
	
	/**
	 * Devuelve el minimo de dos elementos usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e1 Primer elemento
	 * @param e2 Segundo elemento
	 * @param cmp Comparador a utilizar
	 * @return El menor de los dos
	 */
	public static <T> T min(T e1, T e2, Comparator<? super T> cmp){
		return isLE(e1,e2,cmp)?e1:e2;
	}
	
	/**
	 * Devuelve el minimo de varios elementos comparables.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param elements Elementos a comparar
	 * @return El menor de todos
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> T min(T... elements){
		return Arrays.stream(elements).min(Comparator.naturalOrder()).get();
	}
	
	/**
	 * Devuelve el minimo de varios elementos usando un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param cmp Comparador a utilizar
	 * @param elements Elementos a comparar
	 * @return El menor de todos
	 */
	@SafeVarargs
	public static <T> T min(Comparator<? super T> cmp, T... elements){
		return Arrays.stream(elements).min(cmp).get();
	}
	
	/**
	 * Comprueba si un elemento esta en un intervalo abierto.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e Elemento a comprobar
	 * @param e1 Limite inferior
	 * @param e2 Limite superior
	 * @param cmp Comparador a utilizar
	 * @return true si e1 &lt; e &lt; e2
	 */
	public static <T> boolean isInOpenInterval(T e, T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e,e1) > 0 && cmp.compare(e,e2) < 0;
	}
	
	/**
	 * Comprueba si un elemento comparable esta en un intervalo abierto.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e Elemento a comprobar
	 * @param e1 Limite inferior
	 * @param e2 Limite superior
	 * @return true si e1 &lt; e &lt; e2
	 */
	public static <T extends Comparable<? super T>> boolean isInOpenInterval(T e, T e1, T e2){
		return e.compareTo(e1) > 0 && e.compareTo(e2) < 0;
	}
	
	/**
	 * Comprueba si un elemento esta en un intervalo cerrado.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e Elemento a comprobar
	 * @param e1 Limite inferior
	 * @param e2 Limite superior
	 * @param cmp Comparador a utilizar
	 * @return true si e1 &lt;= e &lt;= e2
	 */
	public static <T> boolean isInClosedInterval(T e, T e1, T e2, Comparator<? super T> cmp){
		return cmp.compare(e,e1) >= 0 && cmp.compare(e,e2) <= 0;
	}
	
	/**
	 * Comprueba si un elemento comparable esta en un intervalo cerrado.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param e Elemento a comprobar
	 * @param e1 Limite inferior
	 * @param e2 Limite superior
	 * @return true si e1 &lt;= e &lt;= e2
	 */
	public static <T extends Comparable<? super T>> boolean isInClosedInterval(T e, T e1, T e2){
		return e.compareTo(e1) >= 0 && e.compareTo(e2) <= 0;
	}

	/**
	 * Comprueba si una lista esta ordenada segun un comparador.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param ls Lista a comprobar
	 * @param cmp Comparador a utilizar
	 * @return true si la lista esta ordenada
	 */
	public static <T> boolean isOrdered(List<T> ls, Comparator<? super T> cmp){
		boolean r = true;
		 for (int j = 0; j < ls.size()-1; j++) {
			r = Comparator2.isLE(ls.get(j), ls.get(j + 1), cmp);
			if(!r) break;
		}
		return r;
	}
	
	/**
	 * Comprueba si una lista de elementos comparables esta ordenada.
	 * 
	 * @param <T> Tipo de los elementos
	 * @param ls Lista a comprobar
	 * @return true si la lista esta ordenada
	 */
	public static <T extends Comparable<? super T>> boolean isOrdered(List<T> ls){
		return isOrdered(ls,Comparator.naturalOrder());
	}
	
	/**
	 * Crea una copia ordenada de una lista.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param lista Lista a ordenar
	 * @param comparator Comparador a utilizar
	 * @return Una nueva lista ordenada
	 */
	public static <E> List<E> sortedCopy(List<E> lista, Comparator<E> comparator) {
		return lista.stream().sorted(comparator).collect(Collectors.toList());
	}
	
}
