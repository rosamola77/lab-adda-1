package us.lsi.ag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

/**
 * PermutationData
 *
 * <p>Interfaz que define los datos para cromosomas basados en permutaciones
 * en algoritmos genéticos.</p>
 *
 * <p>Se parte de un conjunto de {@code n} objetos distintos y unas multiplicidades
 * máximas para cada uno de ellos. A partir de esa información se construye
 * la secuencia normal que asumimos de tamaño {@code r}.</p>
 *
 * <p>La secuencia normal asociada al problema está formada por la concatenación de
 * {@code n} sublistas {@code L(i)}. Cada {@code L(i)} está formada por {@code maxMultiplicity(i)}
 * copias del entero {@code i}, con {@code i} en el rango {@code 0..n-1}.</p>
 *
 * <p>Los problemas adecuados para ser modelados con este tipo son aquellos cuya solución
 * es un multiconjunto o una lista, posiblemente con repetición, de los objetos dados.</p>
 *
 * @param <S> tipo de la solución
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 */
public interface PermutationData<S> extends ChromosomeData<List<Integer>,S> {	
	
	/**
	 * Obtiene la multiplicidad máxima del objeto en el índice dado.
	 *
	 * <p>La multiplicidad máxima del objeto {@code i} estará en el rango
	 * {@code 0..maxMultiplicity(i)}.</p>
	 *
	 * @param index índice en la lista de objetos disponibles (0 &le; index &lt; size())
	 * @return la multiplicidad máxima del objeto; por defecto 1
	 */
	default Integer maxMultiplicity(int index){ return 1; }
	
	/**
	 * Obtiene la secuencia normal asociada al problema.
	 *
	 * <p>Siendo {@code n} el número de objetos, la secuencia normal está formada
	 * por la concatenación de {@code n} sublistas {@code L(i)}. Cada {@code L(i)}
	 * está formada por {@code maxMultiplicity(i)} copias del entero {@code i},
	 * con {@code i} en el rango {@code 0..n-1}.</p>
	 *
	 * @return la secuencia normal
	 */
	default List<Integer> normalSequence() {
		List<Integer> r = IntStream.range(0,size())
				.boxed()
				.flatMap(x->List2.nCopies(x,maxMultiplicity(x)).stream())
				.collect(Collectors.toList());
		return r;
	}
	    
	    
}
