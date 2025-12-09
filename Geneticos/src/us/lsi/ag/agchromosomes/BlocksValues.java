package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.BlocksData;

/**
 * BlocksValues
 *
 * <p>Implementación de valores de cromosoma por bloques. Representa cromosomas
 * divididos en bloques donde cada bloque puede tener diferentes límites y
 * valores posibles.</p>
 *
 * <p>Esta clase es útil para problemas donde la solución tiene una estructura
 * natural de bloques, cada uno con su propio conjunto de valores válidos.</p>
 *
 * <p>Los valores continuos (en el rango [0,1)) se convierten en valores
 * discretos específicos para cada bloque según los valores iniciales
 * definidos en el problema.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * BlocksData<Solucion> data = BlocksData.of(...);
 * BlocksValues<Solucion> values = BlocksValues.of(data);
 * List<Integer> decoded = values.decodeValues(valoresContinuos);
 * }</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see BlocksData
 * @see ChromosomeValues
 */
public class BlocksValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {

	/**
	 * Crea una instancia de valores por bloques para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema por bloques
	 * @return una nueva instancia de {@code BlocksValues}
	 */
	public static <S> BlocksValues<S> of(BlocksData<S> data) {
        return new BlocksValues<S>(data);
    }

	/** Datos del problema por bloques. */
    private BlocksData<S> data;

    /**
     * Constructor privado que crea valores por bloques para el problema dado.
     *
     * @param data datos del problema por bloques
     */
    private BlocksValues(BlocksData<S> data) {
        this.data = data;
    }
    
    /**
     * Obtiene los datos del problema.
     *
     * @return los datos del problema por bloques
     */
    @Override
    public BlocksData<S> data() {
        return data;
    }

    /**
     * Decodifica valores continuos en valores discretos por bloques.
     *
     * <p>Convierte una lista de valores continuos en el rango [0,1) a una
     * lista de enteros, procesando cada bloque de forma independiente
     * según los límites y valores iniciales definidos.</p>
     *
     * @param r lista de valores continuos a decodificar
     * @return lista de valores enteros decodificados
     */
    @Override
    public List<Integer> decodeValues(List<Double> r) {
    	List<Integer> s = new ArrayList<>();
		List<Integer> p = data.blocksLimits();
		Integer pn = p.size();
		for(int i=0; i<pn-1;i++) {
			List<Double> rp = r.subList(p.get(i),p.get(i+1));
			List<Integer> values = data.initialValues().subList(p.get(i),p.get(i+1));
			List<Integer> v = AuxiliaryAg.convert(rp,values);			
			s.addAll(v);			
		}
		return s;
    }

    /**
     * Obtiene la dimensión del cromosoma.
     *
     * @return el tamaño total del cromosoma (suma de todos los bloques)
     */
    @Override
    public Integer dimension() {
        return data.size();
    }
}
