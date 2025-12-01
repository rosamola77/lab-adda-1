package us.lsi.grafos.datos;

/**
 * Carretera
 *
 * <p>Representa una carretera que conecta dos ciudades, con información
 * sobre su longitud en kilómetros y opcionalmente su nombre.
 * Esta clase se utiliza como arista en grafos de ciudades.</p>
 *
 * <p>Cada carretera tiene un identificador único asignado automáticamente
 * de forma secuencial.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Carretera a4 = Carretera.of(120.5, "A-4");
 * Carretera sinNombre = Carretera.of(50.0);
 * }</p>
 *
 * @param id identificador único de la carretera
 * @param km longitud de la carretera en kilómetros
 * @param nombre nombre de la carretera; puede ser {@code null}
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public record Carretera(Integer id, Double km, String nombre) {

	/**
	 * Contador estático para asignar identificadores únicos a las carreteras.
	 */
	private static int num =0;
	
	/**
	 * Crea una carretera a partir de un array de cadenas con formato específico.
	 *
	 * <p>El formato esperado es: {@code [_, _, nombre, km]} donde los dos primeros
	 * elementos se ignoran (típicamente representan ciudades origen y destino).</p>
	 *
	 * @param formato array de {@code String} con los datos de la carretera;
	 *        {@code formato[2]} es el nombre y {@code formato[3]} es la longitud en km
	 * @return una nueva instancia de {@code Carretera} con identificador único autoasignado
	 * @throws NumberFormatException si {@code formato[3]} no es un número válido
	 * @throws ArrayIndexOutOfBoundsException si el array no tiene al menos 4 elementos
	 */
	public static Carretera ofFormat(String[] formato) {
		Double km = Double.parseDouble(formato[3]);
		String nomb = formato[2];		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}
	
	/**
	 * Crea una carretera con la longitud especificada y sin nombre.
	 *
	 * @param kms longitud de la carretera en kilómetros
	 * @return una nueva instancia de {@code Carretera} con nombre {@code null}
	 */
	public static Carretera of(Double kms) {
		Double km = kms;
		String nomb = null;		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}
	
	/**
	 * Crea una carretera con la longitud y nombre especificados.
	 *
	 * @param kms longitud de la carretera en kilómetros
	 * @param nombre nombre de la carretera
	 * @return una nueva instancia de {@code Carretera}
	 */
	public static Carretera of(Double kms, String nombre) {
		Double km = kms;
		String nomb = nombre;		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}

	/**
	 * Devuelve una representación en cadena de la carretera.
	 *
	 * @return una cadena con formato {@code (nombre,km)} o {@code (km)} si no tiene nombre
	 */
	@Override
	public String toString() {
		String nn = this.nombre==null?"":this.nombre+",";
		return "("+nn+this.km+")";
	}
	
}
