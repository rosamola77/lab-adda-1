package us.lsi.anuncios.datos;

import java.util.*;

import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.common.Set2;
import us.lsi.math.Math2;

/**
 * ListaDeAnunciosAEmitir
 *
 * <p>Representa una solución (parcial o completa) al problema de selección
 * de anuncios para emisión. Gestiona la lista de anuncios seleccionados,
 * calcula propiedades derivadas como el tiempo consumido, valor total y
 * restricciones violadas.</p>
 *
 * <p>Esta clase soporta operaciones de modificación (insertar, eliminar, intercambiar)
 * que devuelven nuevas instancias, manteniendo inmutabilidad.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * ListaDeAnunciosAEmitir lista = ListaDeAnunciosAEmitir.create();
 * lista = lista.insertarUltimo(0);
 * System.out.println("Valor: " + lista.getValor());
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Anuncio
 * @see DatosAnuncios
 */
public class ListaDeAnunciosAEmitir  {

	/**
	 * Tipos de operaciones que se pueden realizar sobre la lista de anuncios.
	 */
	public enum Opcion {
		/** Insertar un nuevo anuncio en la lista. */
		Insertar,
		/** Eliminar un anuncio de la lista. */
		Eliminar,
		/** Intercambiar la posición de dos anuncios. */
		Intercambiar
	};
	
	/** Lista de índices de anuncios decididos para emitir (en orden de emisión). */
	private List<Integer> anunciosDecididosParaEmitir;
	
	/** Conjunto de índices de anuncios decididos para emitir. */
	private Set<Integer> anunciosDecididosParaEmitirSet;
	
	/** Tiempo total consumido por los anuncios seleccionados. */
	private Integer tiempoConsumido;
	
	/** Tiempo restante disponible para más anuncios. */
	private Integer tiempoRestante;
	
	/** Valor total (beneficio) de los anuncios seleccionados. */
	private Double valor;	
	
	/** Conjunto ordenado de índices de anuncios aún disponibles para seleccionar. */
	private SortedSet<Integer> anunciosDisponibles;
	
	/** Número de pares de anuncios incompatibles seleccionados. */
	private Integer numAnunciosIncompatibles;
	
	/** Número de anuncios repetidos en la selección. */
	private Integer numAnunciosRepetidos;	

	/**
	 * Obtiene el número de pares de anuncios incompatibles seleccionados.
	 *
	 * @return el número de incompatibilidades
	 */
	public Integer getNumAnunciosIncompatibles() {
		return numAnunciosIncompatibles;
	}

	/**
	 * Obtiene el número de anuncios repetidos en la selección.
	 *
	 * @return el número de repeticiones
	 */
	public Integer getNumAnunciosRepetidos() {
		return numAnunciosRepetidos;
	}

	/**
	 * Crea una lista de anuncios a emitir a partir de una lista de índices.
	 *
	 * @param anunciosDecididosParaEmitir lista de índices de anuncios
	 * @return una nueva instancia de {@code ListaDeAnunciosAEmitir}
	 */
	public static ListaDeAnunciosAEmitir create(List<Integer> anunciosDecididosParaEmitir) {
		return new ListaDeAnunciosAEmitir(anunciosDecididosParaEmitir);
	}

	/**
	 * Crea una lista válida de anuncios a emitir, verificando que cumple restricciones.
	 *
	 * @param anunciosDecididosParaEmitir lista de índices de anuncios
	 * @return una nueva instancia de {@code ListaDeAnunciosAEmitir}
	 * @throws IllegalArgumentException si la lista no cumple las restricciones
	 */
	public static ListaDeAnunciosAEmitir createValido(List<Integer> anunciosDecididosParaEmitir) {
		ListaDeAnunciosAEmitir la = new ListaDeAnunciosAEmitir(anunciosDecididosParaEmitir);
		if (!la.cumpleRestricciones()) {
			throw new IllegalArgumentException("Estado No válido");
		}
		return la;
	}
	
	/**
	 * Crea una lista vacía de anuncios a emitir.
	 *
	 * @return una nueva instancia vacía de {@code ListaDeAnunciosAEmitir}
	 */
	public static ListaDeAnunciosAEmitir create() {
		return new ListaDeAnunciosAEmitir();
	}
	
	/**
	 * Constructor privado que crea una lista vacía.
	 */
	private  ListaDeAnunciosAEmitir(){	
		this(List2.empty());
	}
	
	/**
	 * Constructor privado que crea una lista a partir de los índices dados.
	 *
	 * @param anunciosDecididosParaEmitir lista de índices de anuncios
	 */
	private  ListaDeAnunciosAEmitir(List<Integer> anunciosDecididosParaEmitir){
		this.anunciosDecididosParaEmitir = List2.ofCollection(anunciosDecididosParaEmitir);
		this.anunciosDecididosParaEmitirSet = Set2.of(anunciosDecididosParaEmitir);
		calculaPropiedadesDerivadas();		
		calculaAnunciosDisponibles();
	}
	
	/**
	 * Crea una copia de una lista de anuncios existente.
	 *
	 * @param ls la lista a copiar
	 * @return una nueva instancia con los mismos anuncios
	 */
	public static ListaDeAnunciosAEmitir create(ListaDeAnunciosAEmitir ls) {
		return new ListaDeAnunciosAEmitir(ls.anunciosDecididosParaEmitir);
	}

	/**
	 * Verifica si la lista cumple todas las restricciones del problema.
	 *
	 * <p>Una lista es válida si:
	 * <ul>
	 *   <li>No hay anuncios incompatibles seleccionados</li>
	 *   <li>No hay anuncios repetidos</li>
	 *   <li>El tiempo restante es no negativo</li>
	 * </ul>
	 * </p>
	 *
	 * @return {@code true} si cumple todas las restricciones; {@code false} en caso contrario
	 */
	public boolean cumpleRestricciones() {
		return this.numAnunciosIncompatibles == 0 && this.numAnunciosRepetidos == 0 && this.tiempoRestante >=0 ;		
	}

	/**
	 * Calcula las propiedades derivadas de la lista actual.
	 *
	 * <p>Actualiza el tiempo consumido, valor, tiempo restante,
	 * número de incompatibilidades y número de repeticiones.</p>
	 */
	private void calculaPropiedadesDerivadas(){			
		this.tiempoConsumido = 0;
		this.valor = 0.;
		for(int i=0; i< anunciosDecididosParaEmitir.size();i++){
			Integer a = anunciosDecididosParaEmitir.get(i);
			this.valor = this.valor+DatosAnuncios.getAnuncio(a).getPrecio(this.tiempoConsumido+1);
			this.tiempoConsumido=this.tiempoConsumido+DatosAnuncios.getAnuncio(a).getDuracion();
		}
		this.tiempoRestante = DatosAnuncios.tiempoTotal-this.tiempoConsumido;	
		this.numAnunciosIncompatibles = 0;				
		for(IntPair p: DatosAnuncios.restricciones){
			if(this.anunciosDecididosParaEmitirSet.contains(p.first()) && 
					this.anunciosDecididosParaEmitirSet.contains(p.second())){
				this.numAnunciosIncompatibles = this.numAnunciosIncompatibles +1;
			}
		}
		this.numAnunciosRepetidos =  this.anunciosDecididosParaEmitir.size() - this.anunciosDecididosParaEmitirSet.size();	
	}
	
	/**
	 * Calcula el conjunto de anuncios disponibles para añadir.
	 *
	 * <p>Un anuncio está disponible si:
	 * <ul>
	 *   <li>No ha sido seleccionado</li>
	 *   <li>No es incompatible con ningún anuncio seleccionado</li>
	 *   <li>Su duración no excede el tiempo restante</li>
	 * </ul>
	 * </p>
	 */
	private void calculaAnunciosDisponibles(){		
		Set<Integer> disponibles = Set2.of(DatosAnuncios.todosLosAnuncios);	
		disponibles.removeAll(this.anunciosDecididosParaEmitirSet);
		for(IntPair p: DatosAnuncios.restricciones){
			if(this.anunciosDecididosParaEmitirSet.contains(p.first())){
				disponibles.remove(p.second());
			}
		}
		Set<Integer> quitar = Set2.empty();
		for(Integer e : disponibles){
			if(DatosAnuncios.getAnuncio(e).getDuracion()>this.tiempoRestante){
				quitar.add(e);
			}
		}
		disponibles.removeAll(quitar);
		Comparator<Integer> cmp = Comparator.<Integer,Anuncio>comparing(x->DatosAnuncios.getAnuncio(x), Comparator.<Anuncio>reverseOrder());
		this.anunciosDisponibles = Set2.newTreeSet(cmp);
		this.anunciosDisponibles.addAll(disponibles);
	}
	
	/**
	 * Inserta un anuncio en una posición específica de la lista.
	 *
	 * @param pos posición donde insertar (0 &lt;= pos &lt;= tamaño)
	 * @param e índice del anuncio a insertar
	 * @return una nueva lista con el anuncio insertado
	 * @throws IndexOutOfBoundsException si la posición está fuera de rango
	 * @throws IllegalArgumentException si el anuncio ya está en la lista
	 */
	public ListaDeAnunciosAEmitir insertar(int pos, Integer e){
		Preconditions.checkPositionIndex(pos, this.anunciosDecididosParaEmitir.size());
		Preconditions.checkArgument(!this.anunciosDecididosParaEmitirSet.contains(e));
		List<Integer> ls = List2.ofCollection(this.anunciosDecididosParaEmitir);
		ls.add(pos, e);
		return create(ls);
	}
	
	/**
	 * Inserta un anuncio al final de la lista.
	 *
	 * @param e índice del anuncio a insertar
	 * @return una nueva lista con el anuncio añadido al final
	 */
	public ListaDeAnunciosAEmitir insertarUltimo(Integer e){
		return insertar(this.anunciosDecididosParaEmitir.size(),e);
	}
	
	/**
	 * Elimina el anuncio en la posición especificada.
	 *
	 * @param pos posición del anuncio a eliminar
	 * @return una nueva lista sin el anuncio eliminado
	 * @throws IndexOutOfBoundsException si la posición está fuera de rango
	 */
	public ListaDeAnunciosAEmitir eliminar(int pos){
		Preconditions.checkElementIndex(pos, this.anunciosDecididosParaEmitir.size());
		List<Integer> ls = List2.ofCollection(this.anunciosDecididosParaEmitir);
		ls.remove(pos);
		return create(ls);
	}
	
	/**
	 * Elimina el último anuncio de la lista.
	 *
	 * @return una nueva lista sin el último anuncio
	 * @throws IllegalArgumentException si la lista está vacía
	 */
	public ListaDeAnunciosAEmitir eliminarUltimo(){
		Preconditions.checkArgument(!this.anunciosDecididosParaEmitir.isEmpty());
		return eliminar(this.anunciosDecididosParaEmitir.size());
	}

	/**
	 * Intercambia dos anuncios de posición en la lista.
	 *
	 * @param i posición del primer anuncio
	 * @param j posición del segundo anuncio
	 * @return una nueva lista con los anuncios intercambiados
	 * @throws IndexOutOfBoundsException si alguna posición está fuera de rango
	 * @throws IllegalArgumentException si las posiciones son iguales
	 */
	public ListaDeAnunciosAEmitir intercambiar(int i, int j){
		Preconditions.checkElementIndex(i, this.anunciosDecididosParaEmitir.size());
		Preconditions.checkElementIndex(j, this.anunciosDecididosParaEmitir.size());
		Preconditions.checkArgument(i!=j);
		List<Integer> ls = List2.ofCollection(this.anunciosDecididosParaEmitir);
		List2.intercambia(ls, i, j);
		return create(ls);
	}
	
	/**
	 * Obtiene la lista de anuncios seleccionados como objetos {@code Anuncio}.
	 *
	 * @return lista de anuncios seleccionados
	 */
	public List<Anuncio> getAnunciosDecididosParaEmitir() {
		List<Anuncio> ls = List2.empty();
		for(Integer e: this.anunciosDecididosParaEmitir){
			ls.add(DatosAnuncios.getAnuncio(e));
		}
		return ls;
	}

	/**
	 * Obtiene la lista de índices de anuncios seleccionados.
	 *
	 * @return lista de índices
	 */
	public List<Integer> getAnunciosDecididosParaEmitirInt() {
		return this.anunciosDecididosParaEmitir;
	}
	
	/**
	 * Obtiene el conjunto de índices de anuncios seleccionados.
	 *
	 * @return conjunto de índices
	 */
	public Set<Integer> getAnunciosDecididosParaEmitirSet() {
		return anunciosDecididosParaEmitirSet;
	}

	/**
	 * Obtiene el tiempo total consumido por los anuncios seleccionados.
	 *
	 * @return tiempo consumido en segundos
	 */
	public Integer getTiempoConsumido() {
		return tiempoConsumido;
	}

	/**
	 * Obtiene el tiempo restante disponible para más anuncios.
	 *
	 * @return tiempo restante en segundos
	 */
	public Integer getTiempoRestante() {
		return tiempoRestante;
	}

	/**
	 * Obtiene el valor total (beneficio) de los anuncios seleccionados.
	 *
	 * @return el valor total
	 */
	public Double getValor() {
		return valor;
	}
	
	/**
	 * Obtiene el valor objetivo de la solución (alias de {@link #getValor()}).
	 *
	 * @return el valor objetivo
	 */
	public Double getObjetivo() {
		return valor;
	}
	
	/**
	 * Obtiene el número de anuncios en la lista.
	 *
	 * @return número de anuncios seleccionados
	 */
	public Integer getNumAnunciosAEmitir(){
		return this.anunciosDecididosParaEmitir.size();
	}
	
	/**
	 * Obtiene el conjunto ordenado de anuncios disponibles para seleccionar.
	 *
	 * @return conjunto de índices de anuncios disponibles
	 */
	public SortedSet<Integer> getAnunciosDisponibles(){
		return this.anunciosDisponibles;
	}
	
	/**
	 * Obtiene el número de anuncios disponibles para seleccionar.
	 *
	 * @return número de anuncios disponibles
	 */
	public int getNumAnunciosDisponibles(){
		return this.anunciosDisponibles.size();
	}
	
	/**
	 * Obtiene una posición aleatoria para eliminar un anuncio.
	 *
	 * @return índice aleatorio dentro del rango válido
	 */
	public Integer getAlternativaEliminar(){
		return Math2.getEnteroAleatorio(0, this.anunciosDecididosParaEmitir.size());
	}	
	
	/**
	 * Obtiene una alternativa aleatoria para insertar un anuncio.
	 *
	 * @return par con la posición de inserción y el índice del anuncio
	 * @throws IllegalStateException si no hay anuncios disponibles
	 */
	public IntPair getAlternativaInsertar() {
		Preconditions.checkState(!this.anunciosDisponibles.isEmpty());
		Integer pos = Math2.getEnteroAleatorio(0,this.anunciosDecididosParaEmitir.size() + 1);
		List<Integer> ls = List2.ofCollection(this.anunciosDisponibles);
		Integer r = Math2.getEnteroAleatorio(0,ls.size());
		return IntPair.of(pos, ls.get(r));		
	}

	/**
	 * Obtiene dos posiciones aleatorias distintas para intercambiar anuncios.
	 *
	 * @return par con las dos posiciones a intercambiar
	 */
	public IntPair getAlternativaIntercambiar(){
		return Math2.getParAleatorioYDistinto(0, this.anunciosDecididosParaEmitir.size());	
	}

	/**
	 * Obtiene la lista de tipos de operaciones posibles en el estado actual.
	 *
	 * @return lista de opciones válidas según el estado actual
	 */
	public List<Opcion> getTiposDeOpcionesAlternativasPosibles(){
		List<Opcion> ls = List2.empty();
		for(Opcion op : Opcion.values()){
			switch(op){
			case Insertar :
				if(!this.getAnunciosDisponibles().isEmpty()){
					ls.add(op);
				}
				break;
			case Eliminar :
				if(!this.getAnunciosDecididosParaEmitir().isEmpty()){
					ls.add(op);
				}
				break;
			case Intercambiar :
				if(this.getAnunciosDecididosParaEmitir().size() >=2){
					ls.add(op);
				}
				break;
			}			
		}
		return ls;
	}
	
	/**
	 * Genera una solución voraz para el problema.
	 *
	 * <p>Añade iterativamente el mejor anuncio disponible hasta que
	 * no queden anuncios que puedan añadirse.</p>
	 *
	 * @return una lista de anuncios construida vorazmente
	 */
	public static ListaDeAnunciosAEmitir getSolucionVoraz(){
		ListaDeAnunciosAEmitir e = ListaDeAnunciosAEmitir.create();
		while(!e.getAnunciosDisponibles().isEmpty()){
			Integer a = e.getAnunciosDisponibles().first();
			e = e.insertarUltimo(a);
		}
		return e;
	}
	
	/**
	 * Devuelve una representación en cadena de la lista de anuncios.
	 *
	 * @return representación textual con los anuncios, valor, tiempo restante y restricciones
	 */
	@Override
	public String toString() {
		return anunciosDecididosParaEmitir+ "\n Valor=" + valor 
				+ "\n TiempoRestante=" + tiempoRestante
				+"\n NumAnunciosIncompatibles ="+ this.numAnunciosIncompatibles
				+"\n NumAnuncios Repetidos = "+ this.numAnunciosRepetidos
				+ "\n AnunciosDisponibles=" + anunciosDisponibles + "]";
	}
	
	/**
	 * Método principal para pruebas.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		DatosAnuncios.tiempoTotal = 30;
		DatosAnuncios.leeYOrdenaAnuncios("anuncios.txt");
		for(Anuncio a:DatosAnuncios.todosLosAnunciosDisponibles){
			System.out.println(a.getCodigo()+","+a.getPrecioUnitario());
		}		
		System.out.println(DatosAnuncios.todosLosAnunciosDisponibles);
		System.out.println(DatosAnuncios.restricciones);
		ListaDeAnunciosAEmitir ls = ListaDeAnunciosAEmitir.getSolucionVoraz();
		System.out.println(ls);
	}
}
