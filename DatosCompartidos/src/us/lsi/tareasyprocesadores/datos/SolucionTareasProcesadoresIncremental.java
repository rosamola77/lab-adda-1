package us.lsi.tareasyprocesadores.datos;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * SolucionTareasProcesadoresIncremental
 *
 * <p>Implementación incremental de {@link SolucionTareasProcesadores} que
 * mantiene las cargas de los procesadores actualizadas de forma incremental,
 * lo que mejora el rendimiento al evitar recalcular sumas completas.</p>
 *
 * <p>Extiende {@link SolucionTareasProcesadoresNoIncremental} añadiendo
 * una lista de cargas que se actualiza al añadir o eliminar tareas.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SolucionTareasProcesadores
 * @see SolucionTareasProcesadoresNoIncremental
 */
public class SolucionTareasProcesadoresIncremental extends SolucionTareasProcesadoresNoIncremental
		implements SolucionTareasProcesadores {

	/** Lista de cargas actuales de cada procesador. */
	List<Double> cargaProcesadores;
	
	/**
	 * Constructor que crea una solución vacía con el número de procesadores dado.
	 *
	 * @param np número de procesadores
	 */
	SolucionTareasProcesadoresIncremental(Integer np) {
		super(np);
		this.cargaProcesadores = IntStream.range(0,np)
				.boxed()
				.map(x->0.)
				.collect(Collectors.toList());
	}

	/**
	 * Constructor que crea una solución a partir de una asignación existente.
	 *
	 * @param tareasEnProcesador lista de tareas por procesador
	 * @param cargaProcesadores lista de cargas por procesador
	 */
	SolucionTareasProcesadoresIncremental(List<List<Tarea>> tareasEnProcesador,
			List<Double> cargaProcesadores) {
		super(tareasEnProcesador);
		this.cargaProcesadores = cargaProcesadores;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>Esta implementación devuelve directamente el valor almacenado.</p>
	 */
	@Override
	public Double getCargaProcesador(int i){
		return cargaProcesadores.get(i);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>Esta implementación devuelve directamente la lista almacenada.</p>
	 */
	@Override
	public List<Double> getCargaProcesadores() {
		return this.cargaProcesadores;		
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>Esta implementación actualiza incrementalmente la carga del procesador.</p>
	 */
	@Override
	public void addTareaAProcesador(Integer p, Integer t) {
		super.addTareaAProcesador(p, t);
		this.cargaProcesadores.set(p,this.cargaProcesadores.get(p)+Tarea.tareas.get(t).getDuracion());
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>Esta implementación actualiza incrementalmente la carga del procesador.</p>
	 */
	@Override
	public void removeTareaAProcesador(Integer p, Integer t) {
		super.removeTareaAProcesador(p, t);
		this.cargaProcesadores.set(p,this.cargaProcesadores.get(p)-Tarea.tareas.get(t).getDuracion());
	}
}
