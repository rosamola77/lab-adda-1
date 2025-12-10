package us.lsi.graphs.manual;

import java.util.ArrayList;
import java.util.List;

/**
 * AgregadoLineal
 *
 * <p>Clase base abstracta para estructuras de datos lineales que agregan
 * y eliminan elementos. Proporciona operaciones comunes para estructuras
 * como colas, pilas, y listas ordenadas.</p>
 *
 * <p>Define el comportamiento básico para agregar, eliminar y consultar
 * elementos de una colección lineal. Las subclases deben implementar
 * la política específica de inserción de elementos.</p>
 *
 * @param <E> tipo de elementos almacenados
 *
 * @author Miguel Toro
 */
public abstract class AgregadoLineal<E> {

	protected List<E> elements;

	protected AgregadoLineal() {
		this.elements = new ArrayList<>();
	}

	/**
	 * Añade un elemento al agregado según la política de la subclase.
	 *
	 * @param e el elemento a añadir
	 */
	abstract void add(E e);

	/**
	 * Elimina y devuelve el primer elemento del agregado.
	 *
	 * @return el elemento eliminado
	 */
	public E remove() {
		assert !this.elements.isEmpty();
		return this.elements.remove(0);
	}

	/**
	 * Añade todos los elementos de una lista al agregado.
	 *
	 * @param ls la lista de elementos a añadir
	 */
	public void addAll(List<E> ls) {
		ls.stream().forEach(e -> this.add(e));
	}

	/**
	 * Elimina y devuelve todos los elementos del agregado.
	 *
	 * @return lista con todos los elementos eliminados
	 */
	public List<E> removeAll() {
		List<E> ls = new ArrayList<>();
		while (!this.isEmpty()) {
			ls.add(this.remove());
		}
		return ls;
	}

	/**
	 * Devuelve el número de elementos en el agregado.
	 *
	 * @return el tamaño del agregado
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * Verifica si el agregado está vacío.
	 *
	 * @return true si no contiene elementos, false en caso contrario
	 */
	public Boolean isEmpty() {
		return this.elements.isEmpty();
	}

}
