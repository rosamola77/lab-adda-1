package us.lsi.tareasyprocesadores.datos;

import java.util.List;

/**
 * SolucionTareasProcesadores
 *
 * <p>Interfaz que define el contrato para soluciones al problema de
 * asignación de tareas a procesadores. El objetivo es minimizar el
 * tiempo del procesador más cargado (makespan).</p>
 *
 * <p>Proporciona métodos para consultar y modificar la asignación
 * de tareas a procesadores.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * SolucionTareasProcesadores sol = SolucionTareasProcesadores.create(4);
 * sol.addTareaAProcesador(0, 0);
 * Double objetivo = sol.getObjetivo();
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Tarea
 * @see SolucionTareasProcesadoresNoIncremental
 * @see SolucionTareasProcesadoresIncremental
 */
public interface SolucionTareasProcesadores {

	/**
	 * Crea una nueva solución vacía con el número de procesadores especificado.
	 *
	 * @param np número de procesadores
	 * @return una nueva instancia de {@code SolucionTareasProcesadores}
	 */
	public static SolucionTareasProcesadores create(Integer np) {
		return new SolucionTareasProcesadoresNoIncremental(np);
	}
	
	/**
	 * Obtiene el valor objetivo de la solución (tiempo del procesador más cargado).
	 *
	 * @return el valor objetivo
	 */
	Double getObjetivo();

	/**
	 * Obtiene la lista de tareas asignadas a cada procesador.
	 *
	 * @return lista de listas de tareas
	 */
	List<List<Tarea>> getTareasEnProcesador();

	/**
	 * Obtiene las tareas asignadas a un procesador específico.
	 *
	 * @param i índice del procesador
	 * @return lista de tareas del procesador
	 */
	List<Tarea> getTareasDeProcesador(int i);

	/**
	 * Obtiene la carga (suma de duraciones) de un procesador.
	 *
	 * @param i índice del procesador
	 * @return la carga del procesador
	 */
	Double getCargaProcesador(int i);

	/**
	 * Obtiene la carga de todos los procesadores.
	 *
	 * @return lista de cargas
	 */
	List<Double> getCargaProcesadores();

	/**
	 * Añade una tarea a un procesador.
	 *
	 * @param p índice del procesador
	 * @param t índice de la tarea
	 */
	void addTareaAProcesador(Integer p, Integer t);

	/**
	 * Elimina una tarea de un procesador.
	 *
	 * @param p índice del procesador
	 * @param t índice de la tarea
	 */
	void removeTareaAProcesador(Integer p, Integer t);

	/**
	 * Obtiene el tiempo del procesador más cargado.
	 *
	 * @return el tiempo máximo
	 */
	Double getTiempoDelMasCargado();

	/**
	 * Crea una copia de la solución.
	 *
	 * @return una copia independiente
	 */
	SolucionTareasProcesadores copy();

	/**
	 * Calcula el nuevo objetivo si se añadiera una tarea a un procesador.
	 *
	 * @param p índice del procesador
	 * @param t índice de la tarea
	 * @return el nuevo valor objetivo hipotético
	 */
	Double nuevoObjetivo(Integer p, Integer t);

}