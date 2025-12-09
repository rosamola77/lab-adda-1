package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;
import us.lsi.common.List2;

/**
 * PermutationSubListValues
 *
 * <p>Implementación de valores de cromosoma para sublistas de permutaciones.
 * Representa cromosomas cuya decodificación produce una sublista (subconjunto
 * ordenado) de una permutación, donde cada elemento puede ser incluido o excluido.</p>
 *
 * <p>Esta clase es útil para problemas de selección de subconjuntos ordenados,
 * como el problema de la mochila con orden, selección de rutas parciales,
 * planificación de tareas opcionales, etc.</p>
 *
 * <p>El cromosoma tiene el doble de dimensión que la secuencia normal:
 * <ul>
 *   <li>La primera mitad determina una permutación completa de la secuencia</li>
 *   <li>La segunda mitad son valores binarios (0 o 1) que indican si cada
 *       elemento de la permutación debe incluirse en el resultado</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * PermutationData<Solucion> data = PermutationData.of(...);
 * PermutationSubListValues<Solucion> values = PermutationSubListValues.of(data);
 * List<Integer> sublista = values.decodeValues(valoresContinuos);
 * // Si normalSequence = [0,1,2,3]
 * // valores = [0.7, 0.2, 0.9, 0.1, 0.6, 0.3, 0.8, 0.4]
 * // Permutación: [3, 1, 0, 2], bits: [1, 0, 1, 0]
 * // Resultado: [3, 0] (elementos en posiciones con bit=1)
 * }</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see PermutationData
 * @see ChromosomeValues
 */
public class PermutationSubListValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {

	/**
	 * Crea una instancia de valores de sublistas de permutación para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema de permutación
	 * @return una nueva instancia de {@code PermutationSubListValues}
	 */
	public static <S> PermutationSubListValues<S> of(PermutationData<S> data) {
        return new PermutationSubListValues<S>(data);
    }

	/**
	 * Datos del problema de permutación.
	 */
    private PermutationData<S> data;

    /**
     * Constructor privado que crea valores de sublistas para el problema dado.
     *
     * @param data datos del problema de permutación
     */
    private PermutationSubListValues(PermutationData<S> data) {
        this.data = data;
    }
    
    /**
     * Obtiene los datos del problema.
     *
     * @return los datos del problema de permutación
     */
    @Override
    public PermutationData<S> data() {
        return data;
    }

    /**
     * Decodifica valores continuos en una sublista de permutación.
     *
     * <p>La primera mitad de los valores determina la permutación completa.
     * La segunda mitad son valores que se convierten en bits (0 si &lt; 0.5, 1 si &ge; 0.5).
     * Solo se incluyen en el resultado los elementos de la permutación cuyo
     * bit correspondiente es 1.</p>
     *
     * @param ls lista de valores continuos (debe tener 2n elementos)
     * @return sublista de la permutación con elementos seleccionados por los bits
     */
    @Override
    public List<Integer> decodeValues(List<Double> ls){
		Integer n = ls.size()/2;
		List<Integer> rp = AuxiliaryAg.convert(ls.subList(0, n),data.normalSequence());
		List<Integer> bn = IntStream.range(n,2*n).boxed().map(i->ls.get(i)<0.5?0:1).toList();
		List<Integer> r = List2.empty();
		IntStream.range(0, n).boxed().filter(i->bn.get(i)==1).forEach(i->r.add(rp.get(i)));
		return r;
	}

    /**
     * Obtiene la dimensión del cromosoma.
     *
     * @return el doble del tamaño de la secuencia (n para permutación + n para bits)
     */
    @Override
    public Integer dimension() {
        return 2*data.normalSequence().size();
    }
}
