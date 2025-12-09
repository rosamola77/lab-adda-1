package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.RangeIntegerData;

/**
 * RangeIntegerValues
 *
 * <p>Implementación de valores de cromosoma para rangos de enteros.
 * Cada posición del cromosoma puede tomar valores enteros dentro de un
 * rango específico [min, max) definido para esa posición.</p>
 *
 * <p>Esta clase es útil para problemas donde cada variable tiene un dominio
 * continuo de valores enteros. Por ejemplo, la primera posición puede tomar
 * valores en [0, 10) mientras la segunda toma valores en [5, 20).</p>
 *
 * <p>Los valores continuos (en el rango [0,1)) se convierten en valores
 * enteros dentro de los rangos correspondientes mediante transformación lineal.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * RangeIntegerData<Solucion> data = RangeIntegerData.of(...);
 * RangeIntegerValues<Solucion> values = RangeIntegerValues.of(data);
 * List<Integer> decoded = values.decodeValues(valoresContinuos);
 * }</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RangeIntegerData
 * @see ChromosomeValues
 */
public class RangeIntegerValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	/**
	 * Crea una instancia de valores de rangos enteros para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema con rangos enteros
	 * @return una nueva instancia de {@code RangeIntegerValues}
	 */
	public static <S> RangeIntegerValues<S> of(RangeIntegerData<S> data) {
		return new RangeIntegerValues<S>(data);
	}

	/**
	 * Datos del problema con rangos enteros.
	 */
	private RangeIntegerData<S> data;

	/**
	 * Constructor privado que crea valores de rangos enteros para el problema dado.
	 *
	 * @param data datos del problema con rangos enteros
	 */
	RangeIntegerValues(RangeIntegerData<S> data) {
		this.data = data;
	}
	
	/**
	 * Obtiene los datos del problema.
	 *
	 * @return los datos del problema con rangos enteros
	 */
	@Override
	public ChromosomeData<List<Integer>, S> data() {
		return data;
	}

	/**
	 * Decodifica valores continuos en valores enteros dentro de los rangos.
	 *
	 * <p>Para cada posición i, convierte el valor continuo en un entero
	 * dentro del rango [min(i), max(i)) mediante transformación lineal.</p>
	 *
	 * @param ls lista de valores continuos a decodificar
	 * @return lista de valores enteros decodificados dentro de sus rangos
	 */
	@Override
	public List<Integer> decodeValues(List<Double> ls) {
		return IntStream.range(0, ls.size()).boxed()
				.map(i -> AuxiliaryAg.convert(ls.get(i), data.min(i), data.max(i))).toList();
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
