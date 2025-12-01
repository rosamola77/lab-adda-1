package us.lsi.common;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>Clase de utilidades para operaciones con cadenas de texto.</p>
 * 
 * <p>Proporciona metodos para formatear, escribir a fichero y consola,
 * y transformar cadenas.</p>
 * 
 * @author Miguel Toro
 */
public class String2 {
	
	/**
	 * Crea una linea repitiendo un caracter n veces.
	 * 
	 * @param c Caracter a repetir
	 * @param n Numero de repeticiones
	 * @return Cadena con el caracter repetido
	 */
	public static String line(String c, Integer n) {
		return IntStream.range(0, n).boxed().map(i->c).collect(Collectors.joining(""));
	}
	
	/**
	 * Escribe una cadena a un fichero.
	 * 
	 * @param s Cadena a escribir
	 * @param file Ruta del fichero
	 * @throws IllegalArgumentException si no se puede crear el fichero
	 */
	public static void toFile(String s, String file){
		try {
			final PrintWriter f = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
				f.println(s);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	/**
	 * Convierte una cadena en un array de tokens.
	 * 
	 * @param s Cadena de entrada
	 * @param delim Delimitador
	 * @return Array de tokens
	 */
	public static String[] toArray(String s, String delim){
		return Arrays.<String>stream(s.split(delim))
				.<String>map((String x) -> x.trim())
				.filter((String x) -> x.length() > 0)
				.toArray((int x)-> new String[x]);
	}
	
	/**
	 * Crea una linea de 100 guiones bajos.
	 * 
	 * @return Linea separadora
	 */
	public static String linea() {
		return IntStream.range(0, 100).mapToObj(i->"_").collect(Collectors.joining());
	}
	
	/**
	 * Imprime una cadena por consola.
	 * 
	 * @param s Cadena a imprimir
	 */
	public static void toConsole(String s){
		System.out.println(s);
	}
	
	/**
	 * Imprime texto formateado por consola.
	 * 
	 * @param <E> Tipo auxiliar
	 * @param format Formato de la cadena
	 * @param elements Elementos a formatear
	 */
	public static <E> void toConsole(String format, Object... elements){
		toConsole(String.format(format,elements));
	}
	
	/**
	 * Imprime una coleccion por consola con formato personalizado.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param c Coleccion a imprimir
	 * @param f Funcion de formateo
	 * @param sp Separador
	 */
	public static <E> void toConsole(Collection<E> c, Function<E,String> f, String sp) {
		String r = c.stream().map(f).collect(Collectors.joining(sp));
		System.out.println(r);
	}
	
	/**
	 * Formatea una coleccion como cadena.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param c Coleccion a formatear
	 * @param f Funcion de formateo
	 * @param sp Separador
	 * @return Cadena formateada
	 */
	public static <E> String format(Collection<E> c, Function<E,String> f, String sp) {
		return c.stream()
		  .map(f)
		  .collect(Collectors.joining(sp));
	}
	
	/**
	 * Imprime una coleccion por consola con titulo.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param c Coleccion a imprimir
	 * @param titulo Titulo para la coleccion
	 */
	public static <E> void toConsole(Collection<E> c, String titulo){
		String r = c.stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n   ",titulo+" = {\n   " ,"\n}"));
		System.out.println(r);
	}
	
	/**
	 * Formatea una coleccion como cadena con titulo.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param c Coleccion a formatear
	 * @param titulo Titulo para la coleccion
	 * @return Cadena formateada
	 */
	public static <E> String format(Collection<E> c, String titulo){
		return c.stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n   ",titulo+" = {\n   " ,"\n}"));
	}
	
	/**
	 * Transforma una cadena aplicando reglas de sustitucion.
	 * 
	 * <p>Sustituye ocurrencias de {nombre} por el texto asociado
	 * a nombre en el mapa de reglas.</p>
	 * 
	 * @param in String de entrada
	 * @param reglas Conjunto de reglas de sustitucion
	 * @return String transformado
	 */
	public static String transform(String in, Map<String,String> reglas) {
		String out = in;
		Pattern pattern;
		for(String p:reglas.keySet()) {
			pattern = Pattern.compile("\\{"+p+"\\}");
			out = pattern.matcher(out).replaceAll(reglas.get(p));
		}
		return out;
	}
	
}
