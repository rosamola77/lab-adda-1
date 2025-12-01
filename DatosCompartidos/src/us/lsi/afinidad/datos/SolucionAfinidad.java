package us.lsi.afinidad.datos;

import java.util.HashMap;
import java.util.Map;

/**
 * SolucionAfinidad
 *
 * <p>Representa una solución al problema de asignación de clientes a trabajadores
 * maximizando la afinidad. Contiene la asignación de cada cliente a un trabajador
 * y la afinidad total acumulada.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Map<String, String> asignacion = new HashMap<>();
 * asignacion.put("Cliente1", "Trabajador1");
 * SolucionAfinidad sol = SolucionAfinidad.create(asignacion, 10);
 * System.out.println("Afinidad: " + sol.getAfinidad());
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see DatosAfinidad
 * @see Cliente
 */
public class SolucionAfinidad {

	/**
	 * Crea una solución con la asignación y afinidad especificadas.
	 *
	 * @param sol mapa de asignación cliente -> trabajador
	 * @param afinidadAcum afinidad total acumulada
	 * @return una nueva instancia de {@code SolucionAfinidad}
	 */
	public static SolucionAfinidad create(Map<String, String> sol,
			Integer afinidadAcum) {
		return new SolucionAfinidad(sol, afinidadAcum);
	}

	/** Mapa de asignación cliente -> trabajador. */
	private Map<String, String> sol;
	
	/** Afinidad total acumulada. */
	private Integer afinidadAcum;
	
	/**
	 * Constructor privado que crea la solución.
	 *
	 * @param sol mapa de asignación
	 * @param afinidadAcum afinidad total
	 */
	private SolucionAfinidad(Map<String, String> sol, Integer afinidadAcum) {
		super();
		this.sol = new HashMap<>(sol);;
		this.afinidadAcum = afinidadAcum;
	}

	/**
	 * Obtiene el mapa de asignación cliente -> trabajador.
	 *
	 * @return el mapa de asignación
	 */
	public Map<String, String> getAsignacion() {
		return sol;
	}

	/**
	 * Obtiene la afinidad total acumulada.
	 *
	 * @return la afinidad
	 */
	public Integer getAfinidad() {
		return afinidadAcum;
	}

	/**
	 * Obtiene el valor objetivo de la solución.
	 *
	 * @return la afinidad como {@code Double}
	 */
	public Double getObjetivo() {
		return (double) this.afinidadAcum;
	}	
	
	/**
	 * Calcula el código hash de la solución.
	 *
	 * @return el código hash
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((afinidadAcum == null) ? 0 : afinidadAcum.hashCode());
		result = prime * result + ((sol == null) ? 0 : sol.hashCode());
		return result;
	}

	/**
	 * Compara esta solución con otro objeto para determinar igualdad.
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
		if (!(obj instanceof SolucionAfinidad))
			return false;
		SolucionAfinidad other = (SolucionAfinidad) obj;
		if (afinidadAcum == null) {
			if (other.afinidadAcum != null)
				return false;
		} else if (!afinidadAcum.equals(other.afinidadAcum))
			return false;
		if (sol == null) {
			if (other.sol != null)
				return false;
		} else if (!sol.equals(other.sol))
			return false;
		return true;
	}

	/**
	 * Devuelve una representación en cadena de la solución.
	 *
	 * @return representación textual con la afinidad y la asignación
	 */
	@Override
	public String toString() {
		return "Afinidad = "+afinidadAcum +", Asignacion =" + sol;
	}	

}
