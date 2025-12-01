package us.lsi.ag;

import java.util.List;
import java.util.stream.IntStream;

/**
 * RangeIntegerData
 *
 * <p>Interfaz que define los datos para cromosomas con valores enteros
 * en rangos. Extiende {@link RangeData} con funcionalidad de decodificación
 * específica para valores enteros.</p>
 *
 * <p>Convierte valores continuos en el rango [0,1) a enteros en el rango
 * [min(i), max(i)) para cada gen.</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RangeData
 */
public interface RangeIntegerData<S> extends RangeData<Integer, S> {
	
	/**
	 * Decodifica una lista de valores continuos a valores enteros.
	 *
	 * @param ls lista de valores en el rango [0,1)
	 * @return lista de valores enteros en los rangos correspondientes
	 */
	default List<Integer> decode(List<Double> ls){
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),this.min(i),this.max(i)))
				.toList();
	}

}
