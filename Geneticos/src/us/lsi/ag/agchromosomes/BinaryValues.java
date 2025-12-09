package us.lsi.ag.agchromosomes;

import java.util.List;
import us.lsi.ag.BinaryData;

/**
 * BinaryValues
 *
 * <p>Implementación de valores de cromosoma binario. Representa cromosomas
 * cuya codificación y decodificación consisten en una lista de enteros
 * (típicamente 0s y 1s).</p>
 *
 * <p>Esta clase es utilizada para problemas en los que la representación
 * natural de la solución es una secuencia binaria.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * BinaryData<Solucion> data = BinaryData.of(...);
 * BinaryValues<Solucion> values = BinaryValues.of(data);
 * List<Integer> decoded = values.decodeValues(cromosoma);
 * }</p>
 *
 * @param <S> tipo de la solución del problema
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see BinaryData
 * @see ChromosomeValues
 */
public class BinaryValues<S> implements ChromosomeValues<List<Integer>, List<Integer>, S> {
	
	/**
	 * Crea una instancia de valores binarios para el problema dado.
	 *
	 * @param <S> tipo de la solución
	 * @param data datos del problema binario
	 * @return una nueva instancia de {@code BinaryValues}
	 */
	public static <S> BinaryValues<S> of(BinaryData<S> data) {
		return new BinaryValues<S>(data);
	}

	/** Datos del problema binario. */
	private BinaryData<S> data;

	/**
	 * Constructor privado que crea valores binarios para el problema dado.
	 *
	 * @param data datos del problema binario
	 */
	BinaryValues(BinaryData<S> data) {
		this.data = data;
	}
	
	/**
	 * Obtiene los datos del problema.
	 *
	 * @return los datos del problema binario
	 */
	@Override
	public BinaryData<S> data() {
		return data;
	}

	/**
	 * Decodifica los valores del cromosoma.
	 *
	 * <p>En cromosomas binarios, la decodificación es la identidad
	 * (devuelve la misma lista).</p>
	 *
	 * @param ls lista de valores del cromosoma
	 * @return la misma lista sin transformación
	 */
	@Override
	public List<Integer> decodeValues(List<Integer> ls) {
		return ls;
	}

	/**
	 * Obtiene la dimensión del cromosoma.
	 *
	 * @return el tamaño del cromosoma (número de genes)
	 */
	@Override
	public Integer dimension() {
		return data.size();
	}
	
}
