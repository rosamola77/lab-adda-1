package us.lsi.ag;

import java.util.List;
import java.util.stream.IntStream;


/**
 * InSetData
 *
 * <p>Interfaz que define los datos para cromosomas donde cada gen toma
 * valores de un conjunto discreto en algoritmos genéticos.</p>
 *
 * <p>Es útil para problemas donde cada variable de decisión puede tomar
 * valores de un conjunto finito de opciones.</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ChromosomeData
 */
public interface InSetData<S> extends ChromosomeData<List<Integer>,S> {
	
	/**
	 * Obtiene el conjunto de valores posibles para el gen i.
	 *
	 * @param i índice del gen (0 &le; i &lt; size())
	 * @return lista de valores posibles para el gen i
	 */
	List<Integer> values(Integer i);

	/**
	 * Decodifica una lista de valores continuos a valores del conjunto.
	 *
	 * @param ls lista de valores en el rango [0,1)
	 * @return lista de valores seleccionados de los conjuntos correspondientes
	 */
	default List<Integer> decode(List<Double> ls){
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),this.values(i))).toList();
	}

}
