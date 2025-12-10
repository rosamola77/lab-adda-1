package us.lsi.iterables;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.math.Math2;

/**
 * Test2
 *
 * <p>Clase de prueba para verificar el funcionamiento de IteratorRangeInteger
 * y demostrar operaciones con streams.</p>
 *
 * <p>Incluye métodos auxiliares para:
 * <ul>
 * <li>Generar cuadrados en un rango</li>
 * <li>Filtrar múltiplos de un número</li>
 * <li>Aplanar colecciones de conjuntos</li>
 * <li>Generar números primos</li>
 * </ul></p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class Test2 {
	
	public static IntStream cuadrados(Integer a, Integer b){
		return IntStream.range(a,b).map(e->e*e);
	}
	
	public static IntStream multiplos(Integer a, Integer b, Integer m){
		return IntStream.range(a,b).filter(e->e%m==0);
	}

	public static Stream<Integer> elems(List<Set<Integer>> ls){
		return ls.stream()
	 	 	    .flatMap(e->e.stream())
	 	 	    .map(Integer::intValue);
	}
	
	public static Stream<Long> primerosPrimos(Integer n){
		return Stream.iterate(2L,e->true,e->Math2.siguientePrimo(e)).limit(n);
	}



	public static void main(String[] args) {
		for(Integer e: IteratorRangeInteger.of(200,300,7)) {
			System.out.println(e);
		}

	}

}
