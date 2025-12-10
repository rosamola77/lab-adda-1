package us.lsi.iterables;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Test
 *
 * <p>Clase de prueba para verificar el funcionamiento de los iteradores
 * IteratorFilter e IteratorFlatMap.</p>
 *
 * <p>Demuestra:
 * <ul>
 * <li>Filtrado de elementos negativos con IteratorFilter</li>
 * <li>Aplanamiento de colecciones anidadas con IteratorFlatMap</li>
 * </ul></p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class Test {

	public static void main(String[] args) {
		List<Double> ls = List.of(1.,2.,56.,123.,-45.,567.,-2.,-89.,55.,67.);
		Iterator<Double> i1 = ls.stream().iterator();
		Iterator<Double> i2 = IteratorFilter.of(i1,e->e<0.);
		i2.forEachRemaining(x->System.out.print(x+","));
		System.out.println(" __________ ");
		List<Set<Integer>> ls1 = List.of(Set.of(23,45),Set.of(),Set.of(23,45),Set.of(23,45),Set.of(23,45));
		Iterator<Set<Integer>> h1 = ls1.stream().iterator();
		Iterator<Integer> h2 = IteratorFlatMap.of(h1,x->x);
		h2.forEachRemaining(x->System.out.print(x+","));
		System.out.println(" __________ ");
	}

}

