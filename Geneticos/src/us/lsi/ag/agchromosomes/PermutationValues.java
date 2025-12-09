package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;

/**
 * PermutationValues
 *
 * <p>Implementación de valores de cromosoma para permutaciones.
 * Representa cromosomas cuya decodificación produce una permutación
 * de una secuencia normal predefinida.</p>
 *
 * <p>Esta clase es útil para problemas de ordenamiento y secuenciación
 * como el problema del viajante (TSP), scheduling, asignación de tareas,
 * etc., donde la solución es una permutación de elementos.</p>
 *
 * <p>Los valores continuos (claves aleatorias) se utilizan para ordenar
 * la secuencia normal, generando así una permutación. Valores continuos
 * más altos resultan en posiciones más tardías en la permutación.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * PermutationData<Solucion> data = PermutationData.of(...);
 * PermutationValues<Solucion> values = PermutationValues.of(data);
 * List<Integer> permutacion = values.decodeValues(valoresContinuos);
 * // Si normalSequence = [0,1,2,3] y valores = [0.7, 0.2, 0.9, 0.1]
 * // Resultado: [3, 1, 0, 2]
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
public class PermutationValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	/**
	 * Crea una instancia de valores de permutación para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema de permutación
	 * @return una nueva instancia de {@code PermutationValues}
	 */
	public static <S> PermutationValues<S> of(PermutationData<S> data) {
        return new PermutationValues<S>(data);
    }

	/**
	 * Datos del problema de permutación.
	 */
    private PermutationData<S> data;

    /**
     * Constructor privado que crea valores de permutación para el problema dado.
     *
     * @param data datos del problema de permutación
     */
    PermutationValues(PermutationData<S> data) {
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
     * Decodifica valores continuos en una permutación de la secuencia normal.
     *
     * <p>Ordena los elementos de la secuencia normal según los valores continuos
     * proporcionados. Los elementos con valores más bajos aparecen primero en
     * la permutación resultante.</p>
     *
     * @param ls lista de valores continuos (claves aleatorias) para ordenar
     * @return permutación de la secuencia normal ordenada según los valores
     */
    @Override
    public List<Integer> decodeValues(List<Double> ls) {
    	return AuxiliaryAg.convert(ls,data.normalSequence());
    }

    /**
     * Obtiene la dimensión del cromosoma.
     *
     * @return el tamaño de la secuencia a permutar
     */
    @Override
    public Integer dimension() {
        return data.normalSequence().size();
    }

}
