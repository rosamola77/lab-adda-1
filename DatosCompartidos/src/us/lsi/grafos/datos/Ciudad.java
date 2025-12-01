package us.lsi.grafos.datos;

/**
 * Ciudad
 *
 * <p>Representa una ciudad con su nombre y número de habitantes.
 * Esta clase se utiliza como vértice en grafos de ciudades conectadas
 * por carreteras.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Ciudad sevilla = Ciudad.of("Sevilla", 700000);
 * Ciudad madrid = Ciudad.ofFormat(new String[]{"Madrid", "3200000"});
 * }</p>
 *
 * @param nombre el nombre de la ciudad; no debe ser {@code null}
 * @param habitantes el número de habitantes de la ciudad
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public record Ciudad(String nombre, Integer habitantes)  {

	/**
	 * Crea una ciudad a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [nombre, habitantes]}</p>
	 *
	 * @param formato array de {@code String} con los datos de la ciudad;
	 *        {@code formato[0]} es el nombre y {@code formato[1]} es el número de habitantes
	 * @return una nueva instancia de {@code Ciudad} con los datos proporcionados
	 * @throws NumberFormatException si {@code formato[1]} no es un número válido
	 * @throws ArrayIndexOutOfBoundsException si el array no tiene al menos 2 elementos
	 */
	public static Ciudad ofFormat(String[] formato) {
		String nombre = formato[0];
		Integer habitantes = Integer.parseInt(formato[1]);
		return new Ciudad(nombre,habitantes);
	}
	
	/**
	 * Crea una ciudad con el nombre y número de habitantes especificados.
	 *
	 * @param nombre el nombre de la ciudad
	 * @param habitantes el número de habitantes
	 * @return una nueva instancia de {@code Ciudad}
	 */
	public static Ciudad of(String nombre, Integer habitantes) {
		return new Ciudad(nombre,habitantes);
	}
	
	/**
	 * Devuelve una representación en cadena de la ciudad.
	 *
	 * @return el nombre de la ciudad
	 */
	@Override
	public String toString() {
		return this.nombre;
	}
}
