package us.lsi.iterables;

import java.util.Iterator;
import java.util.stream.Stream;

import us.lsi.common.Preconditions;
import us.lsi.streams.Stream2;

/**
 * IteratorRangeInteger
 *
 * <p>Iterador que genera una secuencia aritmética de enteros.
 * Produce enteros desde un valor inicial hasta un valor final (no incluido),
 * con un incremento especificado.</p>
 *
 * <p>Permite crear rangos crecientes (incremento positivo) o decrecientes
 * (incremento negativo).</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * // Rango creciente: 0, 5, 10, 15, 20
 * Iterable<Integer> rango1 = IteratorRangeInteger.of(0, 25, 5);
 * 
 * // Rango decreciente: 100, 93, 86, ..., 37
 * Iterable<Integer> rango2 = IteratorRangeInteger.of(100, 35, -7);
 * }</p>
 *
 * @author Miguel Toro
 */
public class IteratorRangeInteger implements Iterator<Integer>, Iterable<Integer> {
	
	/**
	 * Crea un iterador de rango de enteros.
	 *
	 * @param a valor inicial (incluido)
	 * @param b valor final (no incluido)
	 * @param c incremento (positivo para creciente, negativo para decreciente)
	 * @return el iterador de rango
	 * @throws IllegalArgumentException si los parámetros no son consistentes
	 */
	public static IteratorRangeInteger of(Integer a, Integer b, Integer c) {
		return new IteratorRangeInteger(a, b, c);
	}
	
	private Integer i;
	private Integer a;
	private Integer b;
	private Integer c;
	
	private IteratorRangeInteger(Integer a, Integer b, Integer c) {
		Preconditions.checkArgument((b>=a && c>0) || (b<=a && c < 0),String.format("Valores a=%d,b=%d,c=%d no validos",a,b,c));
		this.a = a;
		this.b = b;
		this.c = c;
		this.i = a;
	}

	@Override
	public Iterator<Integer> iterator() {
		return of(a, b, c);
	}

	@Override
	public boolean hasNext() {
		return (c >0 && i < b) || (c < 0 && i>b);
	}

	@Override
	public Integer next() {
		Integer e = i;
		i = i+c;
		return e;
	}
	
	public static void main(String[] args) {
		Stream<Integer> s = Stream2.of((Iterable<Integer>)IteratorRangeInteger.of(100, 35,-7));
		System.out.println(s.toList());
	}

}
