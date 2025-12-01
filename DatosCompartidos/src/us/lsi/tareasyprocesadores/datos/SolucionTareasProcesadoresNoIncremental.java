package us.lsi.tareasyprocesadores.datos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.streams.Stream2;

/**
 * SolucionTareasProcesadoresNoIncremental
 *
 * <p>Implementación no incremental de {@link SolucionTareasProcesadores}.
 * Calcula las cargas de los procesadores bajo demanda sumando las
 * duraciones de las tareas asignadas.</p>
 *
 * <p>Esta implementación es más simple pero menos eficiente que
 * {@link SolucionTareasProcesadoresIncremental} para operaciones frecuentes.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see SolucionTareasProcesadores
 * @see SolucionTareasProcesadoresIncremental
 */
public class SolucionTareasProcesadoresNoIncremental implements SolucionTareasProcesadores  {

	/** Número de procesadores disponibles. */
	static Integer numeroDeProcesadores;
	
	/** Lista de tareas asignadas a cada procesador. */
	private List<List<Tarea>> tareasEnProcesador;

	/**
	 * Constructor que crea una solución a partir de una asignación existente.
	 *
	 * @param tareasEnProcesador lista de tareas por procesador
	 */
	SolucionTareasProcesadoresNoIncremental(List<List<Tarea>> tareasEnProcesador) {
		this.tareasEnProcesador = tareasEnProcesador;
	}
	
	/**
	 * Constructor que crea una solución vacía con el número de procesadores dado.
	 *
	 * @param np número de procesadores
	 */
	SolucionTareasProcesadoresNoIncremental(Integer np) {
		super();
		numeroDeProcesadores = np;
		this.tareasEnProcesador = IntStream.range(0,np)
				.boxed()
				.map(x->new ArrayList<Tarea>())
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getObjetivo() {
		return getTiempoDelMasCargado();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<Tarea>> getTareasEnProcesador() {
		return tareasEnProcesador;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Tarea> getTareasDeProcesador(int i) {
		return tareasEnProcesador.get(i);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>Esta implementación calcula la suma de duraciones bajo demanda.</p>
	 */
	@Override
	public Double getCargaProcesador(int i){
		return this.tareasEnProcesador.get(i)
				.stream()
				.mapToDouble(t->t.getDuracion())
				.sum();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>Esta implementación calcula todas las cargas bajo demanda.</p>
	 */
	@Override
	public List<Double> getCargaProcesadores() {
		return IntStream.range(0, numeroDeProcesadores)
				 .mapToDouble(x->getCargaProcesador(x))
				 .boxed()
				 .collect(Collectors.toList());		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTareaAProcesador(Integer p, Integer t) {
		this.tareasEnProcesador.get(p).add(Tarea.tareas.get(t));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeTareaAProcesador(Integer p, Integer t) {
		this.tareasEnProcesador.get(p).remove(Tarea.tareas.get(t));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getTiempoDelMasCargado() {
		return IntStream.range(0,numeroDeProcesadores)
				.mapToDouble(x->getCargaProcesador(x))
				.max()
				.getAsDouble();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SolucionTareasProcesadores copy() {
		return new SolucionTareasProcesadoresNoIncremental(this.tareasEnProcesador.stream()
				   .map(x->List2.ofCollection(x))
				   .collect(Collectors.toList()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double nuevoObjetivo(Integer p, Integer t) {
		List<Double> ls = new ArrayList<>(this.getCargaProcesadores());
		ls.set(p,ls.get(p)+Tarea.tareas.get(t).getDuracion());
		return ls.stream().max(Comparator.naturalOrder()).get();
	}
	
	/**
	 * Devuelve una representación en cadena de la solución.
	 *
	 * @return representación textual con las tareas por procesador y sus cargas
	 */
	@Override
	public String toString() {
		var s = Stream2.enumerate(this.getTareasEnProcesador().stream());
		return s.map(
				x -> "    (" + x.counter() + "=" + x.value().toString() + "," + this.getCargaProcesador(x.counter()) + ")")
				.collect(Collectors.joining("\n", "Solucion, Objetivo = " + getObjetivo() + " {\n", "\n}\n"));
	}

}
