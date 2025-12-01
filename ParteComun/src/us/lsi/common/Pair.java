package us.lsi.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Pair
 *
 * <p>Registro genérico que representa un par ordenado de dos elementos.
 * Útil para agrupar dos valores relacionados sin necesidad de crear
 * una clase específica.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Pair<String, Integer> par = Pair.of("clave", 42);
 * String primero = par.first();
 * Integer segundo = par.second();
 * }</p>
 *
 * @param <A> tipo del primer elemento
 * @param <B> tipo del segundo elemento
 * @param first primer elemento del par
 * @param second segundo elemento del par
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public record Pair<A, B>(A first,B second) {
	
	/**
	 * Crea un nuevo par con los elementos dados.
	 *
	 * @param <A> tipo del primer elemento
	 * @param <B> tipo del segundo elemento
	 * @param a primer elemento
	 * @param b segundo elemento
	 * @return un nuevo Pair
	 */
	public static <A,B>  Pair<A,B> of(A a, B b){
		return new Pair<>(a,b);
	}
	
	/**
	 * Parsea un par desde una cadena de texto.
	 *
	 * @param <A> tipo del primer elemento
	 * @param <B> tipo del segundo elemento
	 * @param text texto a parsear
	 * @param delimiters expresión regular de delimitadores
	 * @param first función para parsear el primer elemento
	 * @param second función para parsear el segundo elemento
	 * @return un nuevo Pair con los valores parseados
	 */
	public static <A,B>  Pair<A,B> parse(String text, String delimiters, 
			Function<String,A> first,Function<String,B> second){
		String[] partes = text.split(delimiters);
		List<String> partes2 = Arrays.stream(partes).filter(p->!p.isEmpty()).toList();
		A a = first.apply(partes2.get(0));
		B b = second.apply(partes2.get(1));
		return new Pair<>(a,b);
	}

	/**
	 * Devuelve una representación en cadena del par.
	 *
	 * @return cadena con formato {@code (first,second)}
	 */
	@Override
	public String toString() {
		return String.format("(%s,%s)",first,second);
	}
	
	/**
	 * Método principal para pruebas.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 * @throws IOException si hay error de E/S
	 */
	public static void main(String[] args) throws IOException {
		System.out.println(Pair.parse("[23,45","[\\[,]",s->Integer.parseInt(s),s->Integer.parseInt(s)));
	}
		
}

