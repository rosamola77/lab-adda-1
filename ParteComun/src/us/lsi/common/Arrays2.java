package us.lsi.common;

import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * <p>Clase de utilidades para operaciones con arrays.</p>
 * 
 * <p>Proporciona metodos estaticos para crear, copiar y manipular
 * arrays de diversos tipos.</p>
 * 
 * @author Miguel Toro
 */
public class Arrays2 {
	
	/**
	 * Crea un nuevo array a partir de elementos variables.
	 * 
	 * @param <E> Tipo de los elementos
	 * @param elements Elementos del array
	 * @return Un nuevo array con los elementos proporcionados
	 */
	@SafeVarargs
	public static <E> E[] newArray(E... elements) {
		return elements;
	}
	
	/**
	 * Crea un array de doubles con un valor inicial.
	 * 
	 * @param n Tamano del array
	 * @param v Valor inicial para todas las posiciones
	 * @return Un array de n elementos inicializados a v
	 */
	public  static double[] getArrayDouble(int n, double v){
		double[] r = new double[n];
		for(int i=0;i<r.length;i++){
			r[i]= v;
		}
		return r;
	}
	
	/**
	 * Crea una copia de un array de Integer.
	 * 
	 * @param d Array original
	 * @return Una copia del array
	 */
	public static Integer[] copyArray(Integer d[]){
		Integer n = d.length;
		Integer[] r = new Integer[n];
		IntStream.range(0,n).boxed().forEach(i->{r[i]=d[i];});
		return r;
	}
	
	/**
	 * Crea una copia de un array de Double.
	 * 
	 * @param d Array original
	 * @return Una copia del array
	 */
	public static Double[] copyArray(Double d[]){
		Integer n = d.length;
		Double[] r = new Double[n];
		IntStream.range(0,n).boxed().forEach(i->{r[i]=d[i];});
		return r;
	}
	
	/**
	 * Convierte un array unidimensional en uno bidimensional.
	 * 
	 * @param d Datos de entrada
	 * @param n Numero de filas
	 * @param m Numero de columnas
	 * @return El array bidimensional
	 */
	public static Integer[][] toMultiArray(Integer d[], Integer n, Integer m) {
		Integer[][] r = new Integer[n][m];
		IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.forEach(p -> {
					r[p.first()][p.second()] = d[p.first() * n + p.second()];
				});
		return r;
	}

	/**
	 * Busca la posicion de un elemento que cumpla el predicado dado.
	 * 
	 * @param d Array bidimensional de busqueda
	 * @param pd Predicado de busqueda
	 * @return Par con la posicion (fila, columna) o null si no se encuentra
	 */
	public static IntPair findPosition(Integer d[][], Predicate<Integer> pd) {
		Integer n = d.length;
		Integer m = d[0].length;
		return IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.filter(p -> pd.test(d[p.first()][p.second()])).findFirst().orElse(null);
	}

	/**
	 * Crea una copia de un array bidimensional de Integer.
	 * 
	 * @param d Array original
	 * @return Una copia del array
	 */
	public static Integer[][] copyArray(Integer d[][]) {
		Integer n = d.length;
		Integer m = d[0].length;
		Integer[][] r = new Integer[n][m];
		IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.forEach(p -> {
					r[p.first()][p.second()] = d[p.first()][p.second()];
				});
		return r;
	}

	/**
	 * Crea una copia de un array bidimensional de Double.
	 * 
	 * @param d Array original
	 * @return Una copia del array
	 */
	public static Double[][] copyArray(Double d[][]) {
		Integer n = d.length;
		Integer m = d[0].length;
		Double[][] r = new Double[n][m];
		IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.forEach(p -> {
					r[p.first()][p.second()] = d[p.first()][p.second()];
				});
		return r;
	}

}
