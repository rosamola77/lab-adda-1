package us.lsi.bufete.datos;

import java.util.List;

import us.lsi.common.List2;

/**
 * Abogado
 *
 * <p>Representa un abogado en el problema de asignación de casos.
 * Cada abogado tiene un nombre y una lista de horas estimadas
 * para resolver cada caso disponible.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Abogado a = Abogado.parse("Juan: 10,20,15");
 * String nombre = a.getNombre();
 * Integer horasCaso0 = a.getHoras(0);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see DatosBufete
 */
public class Abogado {

	/**
	 * Crea un abogado a partir de una cadena con formato específico.
	 *
	 * <p>El formato esperado es: {@code nombre: hora1,hora2,hora3,...}</p>
	 *
	 * @param s cadena con los datos del abogado
	 * @return una nueva instancia de {@code Abogado}
	 * @throws ArrayIndexOutOfBoundsException si el formato es incorrecto
	 * @throws NumberFormatException si las horas no son números válidos
	 */
	public static Abogado parse(String s) {
		String[] tokens = s.split(":");
		return new Abogado(tokens[0].trim(),
				List2.parse(tokens[1].trim().split(","), Integer::parseInt));
	}
	
	/** Nombre del abogado. */
	private final String nombre;
	
	/** Lista de horas estimadas para cada caso. */
	private final List<Integer> horas;
	
	/**
	 * Constructor privado que crea un abogado con los datos especificados.
	 *
	 * @param nombre nombre del abogado
	 * @param ls lista de horas estimadas para cada caso
	 */
	private Abogado(String nombre, List<Integer> ls) {
		this.nombre = nombre;
		horas = ls;
	}

	/**
	 * Obtiene el nombre del abogado.
	 *
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene la lista completa de horas estimadas para todos los casos.
	 *
	 * @return lista de horas
	 */
	public List<Integer> getHoras() {
		return horas;
	}
	
	/**
	 * Obtiene las horas estimadas para un caso específico.
	 *
	 * @param index índice del caso
	 * @return las horas estimadas para ese caso
	 * @throws IndexOutOfBoundsException si el índice está fuera de rango
	 */
	public Integer getHoras(int index) {
		return horas.get(index);
	}	
	
	/**
	 * Devuelve una representación en cadena del abogado.
	 *
	 * @return cadena con formato {@code nombre -> [horas]}
	 */
	@Override
	public String toString() {		
		return nombre+" -> "+horas;
//		return nombre;
	}

	/**
	 * Calcula el código hash del abogado.
	 *
	 * @return el código hash basado en el nombre
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	/**
	 * Compara este abogado con otro objeto para determinar igualdad.
	 *
	 * <p>Dos abogados son iguales si tienen el mismo nombre.</p>
	 *
	 * @param obj el objeto con el que comparar
	 * @return {@code true} si son iguales; {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abogado other = (Abogado) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	
}

