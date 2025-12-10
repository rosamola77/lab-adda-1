package us.lsi.iterables;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * SeqCollector
 *
 * <p>Colector secuencial personalizado para operaciones de reducción sobre secuencias.
 * Similar al concepto de Collector en Streams pero diseñado para iterables y con
 * soporte para condiciones de terminación temprana.</p>
 *
 * <p>Permite definir operaciones de recolección con:
 * <ul>
 * <li>Un proveedor (supplier) para crear el contenedor inicial</li>
 * <li>Un acumulador para agregar elementos al contenedor</li>
 * <li>Una función finalizadora para transformar el resultado</li>
 * <li>Un predicado de terminación para detener la recolección anticipadamente</li>
 * </ul></p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * SeqCollector<Integer, List<Integer>, List<Integer>> collector =
 *     SeqCollector.of(ArrayList::new, (list, e) -> {list.add(e); return list;});
 * }</p>
 *
 * @param <E> tipo de los elementos de entrada
 * @param <B> tipo del contenedor acumulador
 * @param <R> tipo del resultado final
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class SeqCollector<E, B, R> {
	
	/**
	 * Crea un colector secuencial con todos los componentes especificados.
	 *
	 * @param <E> tipo de los elementos de entrada
	 * @param <B> tipo del contenedor acumulador
	 * @param <R> tipo del resultado final
	 * @param supplier proveedor del contenedor inicial
	 * @param accumulator función para agregar elementos al contenedor
	 * @param finisher función para transformar el contenedor en el resultado final
	 * @param isDone predicado que determina si la recolección debe terminar anticipadamente
	 * @return un nuevo SeqCollector configurado
	 */
	public static <E,B,R> SeqCollector<E,B,R> of(
			Supplier<B> supplier, 
			BiFunction<B, E, B> accumulator, 
			Function<B,R> finisher,
			Predicate<B> isDone){
		return new SeqCollector<>(supplier,accumulator,finisher,isDone);
	}
	
	/**
	 * Crea un colector secuencial sin función finalizadora ni condición de terminación.
	 *
	 * @param <E> tipo de los elementos de entrada
	 * @param <B> tipo del contenedor acumulador y resultado
	 * @param supplier proveedor del contenedor inicial
	 * @param accumulator función para agregar elementos al contenedor
	 * @return un nuevo SeqCollector configurado
	 */
	public static <E,B> SeqCollector<E,B,B> of(
			Supplier<B> supplier, 
			BiFunction<B, E, B> accumulator){
		return new SeqCollector<>(supplier,accumulator,x->x,x->false);
	}
	
	/**
	 * Crea un colector secuencial simple con solo un acumulador binario.
	 *
	 * @param <E> tipo de los elementos
	 * @param accumulator operador binario para acumular elementos
	 * @return un nuevo SeqCollector configurado
	 */
	public static <E> SeqCollector<E,E,E> of(
			BinaryOperator<E> accumulator){
		return new SeqCollector<>(()->null,accumulator,x->x,x->false);
	}
	
	/**
	 * Crea un colector secuencial con contenedor mutable y BiConsumer.
	 *
	 * @param <E> tipo de los elementos de entrada
	 * @param <B> tipo del contenedor acumulador mutable
	 * @param <R> tipo del resultado final
	 * @param supplier proveedor del contenedor inicial
	 * @param accumulator consumidor que modifica el contenedor con cada elemento
	 * @param finisher función para transformar el contenedor en el resultado final
	 * @param isDone predicado que determina si la recolección debe terminar
	 * @return un nuevo SeqCollector configurado
	 */
	public static <E,B,R> SeqCollector<E,B,R> ofBaseMutable(
			Supplier<B> supplier, 
			BiConsumer<B, E> accumulator, 
			Function<B, R> finisher,
			Predicate<B> isDone){
		return SeqCollector.of(supplier,(b,e)->{accumulator.accept(b, e);return b;},finisher,isDone);
	}
	
	private Supplier<B> supplier;;
	private BiFunction<B, E, B> accumulator;;
	private Function<B,R> finisher;
	private Predicate<B> isDone;
	
	public SeqCollector(Supplier<B> supplier, 
			BiFunction<B, E, B> accumulator, 
			Function<B, R> finisher,
			Predicate<B> isDone) {
		super();
		this.supplier = supplier;
		this.accumulator = accumulator;
		this.finisher = finisher;
		this.isDone = isDone;
	}
	
	public Supplier<B> supplier() {
		return supplier;
	}
	
	public BiFunction<B, E, B> accumulator() {
		return accumulator;
	}
	
	public Function<B, R> finisher() {
		return finisher;
	}
	
	public Predicate<B> isDone() {
		return isDone;
	}
		
}


