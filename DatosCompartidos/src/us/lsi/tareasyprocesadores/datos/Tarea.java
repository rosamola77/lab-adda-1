package us.lsi.tareasyprocesadores.datos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

/**
 * Tarea
 *
 * <p>Representa una tarea con una duración específica en el problema de
 * asignación de tareas a procesadores. Cada tarea tiene un identificador
 * único asignado automáticamente.</p>
 *
 * <p>La clase también gestiona la lista global de tareas disponibles
 * y proporciona métodos estáticos para acceder a ellas.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Tarea.leeTareas("tareas.txt");
 * Tarea t = Tarea.getTarea(0);
 * Double duracion = t.getDuracion();
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SolucionTareasProcesadores
 */
public class Tarea {

	/** Lista de todas las tareas disponibles, ordenadas por duración descendente. */
	public static List<Tarea> tareas;
	
	/** Número total de tareas. */
	public static Integer n;
	
	/**
	 * Lee las tareas desde un fichero y las ordena por duración descendente.
	 *
	 * <p>El fichero debe contener una duración por línea.</p>
	 *
	 * @param fichero ruta del fichero con las duraciones
	 */
	public static void leeTareas(String fichero){
		nId =0;
		tareas = Files2.streamFromFile(fichero)
				.map(s ->Tarea.create(s))
				.sorted(Comparator.<Tarea,Double>comparing(Tarea::getDuracion).reversed())
				.collect(Collectors.toList());
		n = tareas.size();
	}
	
	/**
	 * Crea una tarea a partir de una cadena con su duración.
	 *
	 * @param s cadena con la duración de la tarea
	 * @return una nueva instancia de {@code Tarea}
	 */
	public static Tarea create(String s) {
		return new Tarea(s);
	}

	/**
	 * Crea una tarea con la duración especificada.
	 *
	 * @param duracion duración de la tarea
	 * @return una nueva instancia de {@code Tarea}
	 */
	public static Tarea create(Double duracion) {
		return new Tarea(duracion);
	}
	
	/** Contador estático para asignar identificadores únicos. */
	private static Integer nId;
	
	/** Duración de la tarea. */
	private Double duracion;
	
	/** Identificador único de la tarea. */
	private Integer id;
	
	/**
	 * Constructor privado que crea una tarea con la duración especificada.
	 *
	 * @param duracion duración de la tarea
	 */
	private Tarea(Double duracion) {
		super();
		this.duracion = duracion;
		this.id = nId;
		nId++;
	}
	
	/**
	 * Constructor privado que crea una tarea a partir de una cadena.
	 *
	 * @param s cadena con la duración
	 */
	private Tarea(String s) {
		super();
		this.duracion = Double.parseDouble(s.trim());
		this.id = nId;
		nId++;
	}
	
	/**
	 * Obtiene la duración de la tarea.
	 *
	 * @return la duración
	 */
	public Double getDuracion() {
		return duracion;
	}
	
	/**
	 * Obtiene la duración de una tarea por su índice.
	 *
	 * @param i índice de la tarea
	 * @return la duración de la tarea
	 */
	public static Double getDuracion(int i) {
//		if(i >= Tarea.n) return 0.;
		return Tarea.tareas.get(i).getDuracion();
	}
	
	/**
	 * Obtiene el identificador de la tarea.
	 *
	 * @return el identificador
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Obtiene una tarea por su índice.
	 *
	 * @param i índice de la tarea
	 * @return la tarea en la posición especificada
	 */
	public static Tarea getTarea(int i) {
		return Tarea.tareas.get(i);
	}
	
	/**
	 * Calcula el código hash de la tarea.
	 *
	 * @return el código hash
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((duracion == null) ? 0 : duracion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	/**
	 * Compara esta tarea con otro objeto para determinar igualdad.
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
		if (!(obj instanceof Tarea))
			return false;
		Tarea other = (Tarea) obj;
		if (duracion == null) {
			if (other.duracion != null)
				return false;
		} else if (!duracion.equals(other.duracion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**
	 * Devuelve una representación en cadena de la tarea.
	 *
	 * @return cadena con formato {@code (id,duracion)}
	 */
	@Override
	public String toString() {
		return "("+id + "," + duracion + ")";
	}
	
	

}
