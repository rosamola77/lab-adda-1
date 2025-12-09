package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeDoubleData;

/**
 * RangeDoubleValues
 *
 * <p>Implementación de valores de cromosoma para rangos de números reales (Double).
 * Cada posición del cromosoma puede tomar valores reales dentro de un
 * rango específico [min, max) definido para esa posición.</p>
 *
 * <p>Esta clase es útil para problemas de optimización continua donde cada
 * variable tiene un dominio continuo de valores reales. Por ejemplo, optimización
 * de funciones matemáticas, problemas de ingeniería, etc.</p>
 *
 * <p>Los valores continuos (en el rango [0,1)) se escalan linealmente a los
 * rangos específicos de cada posición.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * RangeDoubleData<Solucion> data = RangeDoubleData.of(...);
 * RangeDoubleValues<Solucion> values = RangeDoubleValues.of(data);
 * List<Double> decoded = values.decodeValues(valoresContinuos);
 * }</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RangeDoubleData
 * @see ChromosomeValues
 */
public class RangeDoubleValues<S> implements ChromosomeValues<List<Double>, List<Double>, S> {
	
	/**
	 * Crea una instancia de valores de rangos reales para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema con rangos reales
	 * @return una nueva instancia de {@code RangeDoubleValues}
	 */
	public static <S> RangeDoubleValues<S> of(RangeDoubleData<S> data) {
		return new RangeDoubleValues<S>(data);
	}

	/**
	 * Datos del problema con rangos reales.
	 */
	private RangeDoubleData<S> data;

	/**
	 * Constructor privado que crea valores de rangos reales para el problema dado.
	 *
	 * @param data datos del problema con rangos reales
	 */
	RangeDoubleValues(RangeDoubleData<S> data) {
		this.data = data;
	}
	
	/**
	 * Obtiene los datos del problema.
	 *
	 * @return los datos del problema con rangos reales
	 */
	@Override
	public RangeDoubleData<S> data() {
		return data;
	}

	/**
	 * Decodifica valores continuos escalándolos a los rangos específicos.
	 *
	 * <p>Para cada posición i, escala el valor continuo del rango [0,1) al
	 * rango [min(i), max(i)) mediante transformación lineal.</p>
	 *
	 * @param ls lista de valores continuos a decodificar
	 * @return lista de valores reales escalados a sus rangos correspondientes
	 */
	@Override
	public List<Double> decodeValues(List<Double> ls) {
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

