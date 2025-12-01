package us.lsi.ag;

import java.util.List;
import java.util.stream.IntStream;

/**
 * RangeDoubleData
 *
 * <p>Interfaz que define los datos para cromosomas con valores de punto flotante
 * en rangos. Extiende {@link RangeData} con funcionalidad de decodificación
 * específica para valores Double.</p>
 *
 * <p>Convierte valores continuos en el rango [0,1) a valores Double en el rango
 * [min(i), max(i)) para cada gen.</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RangeData
 */
public interface RangeDoubleData<S> extends RangeData<Double, S> {
	
	/**
	 * Decodifica una lista de valores continuos a valores Double en los rangos dados.
	 *
	 * @param ls lista de valores en el rango [0,1)
	 * @return lista de valores Double en los rangos correspondientes
	 */
	default List<Double> decode(List<Double> ls){
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),this.min(i),this.max(i)))
				.toList();
	}

}
