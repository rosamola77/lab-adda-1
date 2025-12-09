package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.InSetData;

/**
 * InSetValues
 *
 * <p>Implementación de valores de cromosoma para conjuntos de valores específicos.
 * Cada posición del cromosoma puede tomar valores de un conjunto definido
 * diferente.</p>
 *
 * <p>Esta clase es útil para problemas donde cada variable tiene su propio
 * dominio discreto de valores posibles. Por ejemplo, la primera posición
 * puede tomar valores de {1, 3, 5} mientras la segunda toma valores de {2, 4, 6, 8}.</p>
 *
 * <p>Los valores continuos (en el rango [0,1)) se convierten en valores
 * discretos seleccionados de los conjuntos correspondientes.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * InSetData<Solucion> data = InSetData.of(...);
 * InSetValues<Solucion> values = InSetValues.of(data);
 * List<Integer> decoded = values.decodeValues(valoresContinuos);
 * }</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see InSetData
 * @see ChromosomeValues
 */
public class InSetValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	/**
	 * Crea una instancia de valores de conjuntos para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema de conjuntos
	 * @return una nueva instancia de {@code InSetValues}
	 */
	public static <S> InSetValues<S> of(InSetData<S> data) {
		return new InSetValues<S>(data);
	}

	/**
	 * Datos del problema de conjuntos.
	 */
	private InSetData<S> data;

	/**
	 * Constructor privado que crea valores de conjuntos para el problema dado.
	 *
	 * @param data datos del problema de conjuntos
	 */
	private InSetValues(InSetData<S> data) {
		this.data = data;
	}
	
	/**
	 * Obtiene los datos del problema.
	 *
	 * @return los datos del problema de conjuntos
	 */
	@Override
	public ChromosomeData<List<Integer>, S> data() {
		return data;
	}

	/**
	 * Decodifica valores continuos en valores discretos de los conjuntos.
	 *
	 * <p>Para cada posición i, convierte el valor continuo en un valor
	 * seleccionado del conjunto de valores válidos para esa posición.</p>
	 *
	 * @param ls lista de valores continuos a decodificar
	 * @return lista de valores enteros decodificados, cada uno del conjunto correspondiente
	 */
	@Override
	public List<Integer> decodeValues(List<Double> ls) {
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),data.values(i))).toList();
	}

	/**
	 * Obtiene la dimensión del cromosoma.
	 *
	 * @return el número de posiciones en el cromosoma
	 */
	@Override
	public Integer dimension() {
		return data.size();
	}
	

}
