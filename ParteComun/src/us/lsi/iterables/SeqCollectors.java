package us.lsi.iterables;

import java.util.function.Predicate;

/**
 * SeqCollectors
 *
 * <p>Factoría de colectores secuenciales para operaciones sobre secuencias.
 * Proporciona métodos estáticos para crear colectores comunes utilizados
 * en el procesamiento de secuencias.</p>
 *
 * <p>Los colectores secuenciales son similares a los colectores de streams
 * pero optimizados para procesamiento secuencial de elementos.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SeqCollector
 */
public class SeqCollectors {
	
	/**
	 * Crea un colector que verifica si todos los elementos cumplen un predicado.
	 *
	 * @param <E> tipo de elementos
	 * @param p predicado a evaluar
	 * @return colector que devuelve {@code true} si todos cumplen el predicado
	 */
	public static <E> SeqCollector<E,Boolean,Boolean> all(Predicate<E> p){
		return SeqCollector.of(()->true, (b,e)->p.test(e), x->x,b->b);
	}
	
	
}
