package us.lsi.iterables;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 * SeqCollect
 *
 * <p>Utilidades para realizar operaciones de recolección (collect) y
 * reducción (reduce) sobre iteradores de forma secuencial.</p>
 *
 * <p>Proporciona implementaciones tanto iterativas como recursivas de
 * acumulación por la izquierda y por la derecha, así como operaciones
 * de reducción usando operadores binarios.</p>
 *
 * <p>Operaciones disponibles:</p>
 * <ul>
 * <li>seqCollectLeft: Acumulación secuencial por la izquierda (iterativa)</li>
 * <li>seqCollectLeftRecursivo: Acumulación por la izquierda (recursiva)</li>
 * <li>seqCollectRight: Acumulación secuencial por la derecha</li>
 * <li>reduceLeft: Reducción por la izquierda</li>
 * <li>reduceRight: Reducción por la derecha</li>
 * </ul>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Iterator<Integer> nums = List.of(1, 2, 3, 4, 5).iterator();
 * Optional<Integer> suma = SeqCollect.reduceLeft(nums, Integer::sum);
 * // Resultado: 15
 * }</p>
 *
 * @author Miguel Toro
 */
public class SeqCollect {
	
	/**
	 * Acumula secuencialmente por la izquierda de forma iterativa.
	 *
	 * @param <E> tipo de elementos del iterador
	 * @param <B> tipo del acumulador intermedio
	 * @param <R> tipo del resultado final
	 * @param s el iterador de entrada
	 * @param a el acumulador secuencial
	 * @return el resultado de acumular secuencialmente por la izquierda
	 */
	public static <E,B,R> R seqCollectLeft(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = a.supplier().get();
		while(s.hasNext() && !a.isDone().test(b)){
		   E e = s.next();
		   b = a.accumulator().apply(b,e);
		}
		return a.finisher().apply(b);
	}
	
	/**
	 * Acumula secuencialmente por la izquierda de forma recursiva.
	 *
	 * @param <E> tipo de elementos del iterador
	 * @param <B> tipo del acumulador intermedio
	 * @param <R> tipo del resultado final
	 * @param s el iterador de entrada
	 * @param a el acumulador secuencial
	 * @return el resultado de acumular secuencialmente por la izquierda
	 */
	public static <E,B,R> R seqCollectLeftRecursivo(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = a.supplier().get();
		b = seqCollectLeftRecursivo(s,a,b);
		return a.finisher().apply(b);
	}
	
	private static <E,B,R> B seqCollectLeftRecursivo(Iterator<E> s, SeqCollector<E,B,R> a, B b) {
		if(s.hasNext() && !a.isDone().test(b)){
		   E e = s.next();
		   b = a.accumulator().apply(b,e);
		   b = seqCollectLeftRecursivo(s,a,b);
		}
		return b;
	}
	
	/**
	 * Reduce por la izquierda secuencialmente usando un operador binario.
	 *
	 * @param <E> tipo de elementos
	 * @param s el iterador de entrada
	 * @param op el operador binario para la reducción
	 * @return Optional con el resultado de la reducción, o vacío si el iterador está vacío
	 */
	public static <E> Optional<E> reduceLeft(Iterator<E> s, BinaryOperator<E> op) {
		if(!s.hasNext()) return Optional.empty();
		E b = s.next();
		while(s.hasNext()){
		   E e = s.next();
		   b = op.apply(b,e);
		}
		return Optional.of(b);
	}
	
	/**
	 * Acumula secuencialmente por la derecha.
	 *
	 * @param <E> tipo de elementos del iterador
	 * @param <B> tipo del acumulador intermedio
	 * @param <R> tipo del resultado final
	 * @param s el iterador de entrada
	 * @param a el acumulador secuencial
	 * @return el resultado de acumular secuencialmente por la derecha
	 */
	public static <E,B,R> R seqCollectRight(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = seqCollectRightP(s,a);
		return a.finisher().apply(b);
	}

	private static <E,B,R> B seqCollectRightP(Iterator<E> s, SeqCollector<E,B,R> a) {
		B b = a.supplier().get(); 
		if(s.hasNext()){
			E e = s.next();
			b = seqCollectRightP(s,a);
			if(!a.isDone().test(b)){
	 	 	   b = a.accumulator().apply(b, e);
	 		}
		} 
		return b;
	}

	/**
	 * Reduce por la derecha secuencialmente usando un operador binario.
	 *
	 * @param <E> tipo de elementos
	 * @param s el iterador de entrada
	 * @param op el operador binario para la reducción
	 * @return Optional con el resultado de la reducción, o vacío si el iterador está vacío
	 */
	public static <E> Optional<E> reduceRight(Iterator<E> s, BinaryOperator<E> op) {
		if(!s.hasNext()) return Optional.empty();
		E b = reduceRightP(s,op);
		return Optional.of(b);
	}

	private static <E,R> E reduceRightP(Iterator<E> s, BinaryOperator<E> op) {
		E b = null;
		if (s.hasNext()) {
			E e = s.next();
			b = reduceRightP(s,op);
			if (b==null) b = e;
			else {
				b = op.apply(b, e);
			}
		} 
		return b;
	}

}
