package us.lsi.common;

/**
 * <p>Clase de utilidades para validacion de precondiciones.</p>
 * 
 * <p>Proporciona metodos para verificar condiciones antes de ejecutar
 * codigo, lanzando excepciones apropiadas cuando las condiciones no se cumplen.</p>
 * 
 * @author Miguel Toro
 */
public class Preconditions {

	/**
	 * Verifica que una condicion sea verdadera.
	 * 
	 * @param condition Condicion a verificar
	 * @throws IllegalArgumentException si la condicion es falsa
	 */
	public static void checkArgument(boolean condition){
		if(!condition){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Verifica que una condicion sea verdadera con mensaje de error.
	 * 
	 * @param condition Condicion a verificar
	 * @param message Mensaje de error si falla
	 * @throws IllegalArgumentException si la condicion es falsa
	 */
	public static void checkArgument(boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Verifica un estado del objeto.
	 * 
	 * @param condition Condicion a verificar
	 * @throws IllegalArgumentException si la condicion es falsa
	 */
	public static void checkState(boolean condition){
		if(!condition){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Verifica un estado del objeto con mensaje de error.
	 * 
	 * @param condition Condicion a verificar
	 * @param message Mensaje a imprimir si falla
	 * @throws IllegalArgumentException si la condicion es falsa
	 */
	public static void checkState(boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Verifica que un valor no sea null.
	 * 
	 * @param <T> Tipo del elemento
	 * @param reference Parametro a comprobar
	 * @return El parametro si no es null
	 * @throws NullPointerException si es null
	 */
	public static <T> T checkNotNull(T reference){
		if(reference == null){
			throw new NullPointerException(String.format("Es nulo %s", reference));
		}
		return reference;
	}
	
	/**
	 * Verifica que un valor no sea null con mensaje personalizado.
	 * 
	 * @param <T> Tipo del elemento
	 * @param reference Parametro a comprobar
	 * @param mensaje Mensaje de error si es null
	 * @return El parametro si no es null
	 * @throws NullPointerException si es null
	 */
	public static <T> T checkNotNull(T reference, String mensaje){
		if(reference == null){
			throw new NullPointerException(mensaje);
		}
		return reference;
	}
		
	/**
	 * Verifica que un indice sea valido para acceder a un elemento.
	 * 
	 * <p>Un indice de elemento valido esta en el rango [0, size).</p>
	 * 
	 * @param index Indice a verificar
	 * @param size Tamano de la lista, string o array
	 * @return El indice si es valido
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public static int checkElementIndex(int index, int size){
		if(!(index>=0 && index<size)){
			throw new IndexOutOfBoundsException(String.format("Index = %d, size %d", index,size));
		}
		return index;
	}
	
	/**
	 * Verifica que un indice sea una posicion valida.
	 * 
	 * <p>Un indice de posicion valido esta en el rango [0, size].</p>
	 * 
	 * @param index Indice a verificar
	 * @param size Tamano de la lista, string o array
	 * @return El indice si es valido
	 * @throws IndexOutOfBoundsException si el indice esta fuera de rango
	 */
	public static int checkPositionIndex(int index, int size){
		if(!(index>=0 && index<=size)){
			throw new IndexOutOfBoundsException(String.format("Index = %d, size %d", index,size));
		}
		return index;
	}
	
	
}
