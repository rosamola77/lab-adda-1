package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;

/**
 * PermutationPrefixValues
 *
 * <p>Implementación de valores de cromosoma para prefijos de permutaciones.
 * Representa cromosomas cuya decodificación produce un prefijo (subcadena inicial)
 * de una permutación de la secuencia normal.</p>
 *
 * <p>Esta clase es útil para problemas donde se necesita seleccionar un subconjunto
 * ordenado de elementos de tamaño variable, como rutas parciales, selección de
 * tareas prioritarias, etc.</p>
 *
 * <p>El cromosoma tiene una dimensión extra: los primeros n valores determinan
 * la permutación completa, y el último valor determina la longitud del prefijo
 * a extraer (entre 0 y n-1).</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * PermutationData<Solucion> data = PermutationData.of(...);
 * PermutationPrefixValues<Solucion> values = PermutationPrefixValues.of(data);
 * List<Integer> prefijo = values.decodeValues(valoresContinuos);
 * // Si normalSequence = [0,1,2,3], valores = [0.7, 0.2, 0.9, 0.1, 0.5]
 * // Permutación completa: [3, 1, 0, 2], último valor indica n=2
 * // Resultado: [3, 1] (prefijo de longitud 2)
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
public class PermutationPrefixValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	/**
	 * Crea una instancia de valores de prefijos de permutación para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema de permutación
	 * @return una nueva instancia de {@code PermutationPrefixValues}
	 */
	public static <S> PermutationPrefixValues<S> of(PermutationData<S> data) {
        return new PermutationPrefixValues<S>(data);
    }

	/**
	 * Datos del problema de permutación.
	 */
    private PermutationData<S> data;

    /**
     * Constructor privado que crea valores de prefijos para el problema dado.
     *
     * @param data datos del problema de permutación
     */
    private PermutationPrefixValues(PermutationData<S> data) {
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
	 * Decodifica valores continuos en un prefijo de permutación.
	 *
	 * <p>Los primeros n-1 valores determinan la permutación completa de la
	 * secuencia normal. El último valor determina la longitud del prefijo
	 * a extraer (un entero entre 0 y n-1).</p>
	 *
	 * @param ls lista de valores continuos (debe tener n+1 elementos)
	 * @return prefijo de la permutación con longitud determinada por el último gen
	 */
	@Override
    public List<Integer> decodeValues(List<Double> ls) {
    	List<Integer> la = AuxiliaryAg.convert(ls.subList(0,ls.size()-1),data.normalSequence());  
    	Integer n= AuxiliaryAg.convert(ls.get(ls.size()-1),0,ls.size()-1);	
    	System.out.println("=== "+n);
    	return la.subList(0,n);
    }

    /**
     * Obtiene la dimensión del cromosoma.
     *
     * @return el tamaño de la secuencia más 1 (para el gen de longitud)
     */
    @Override
    public Integer dimension() {
        return data.normalSequence().size()+1;
    }

}
