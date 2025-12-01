package us.lsi.anuncios.datos;

import java.util.*;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.common.Set2;
import us.lsi.streams.Stream2;

/**
 * DatosAnuncios
 *
 * <p>Clase que gestiona los datos globales de anuncios para problemas de
 * optimización de emisión de publicidad. Proporciona acceso a la lista
 * de anuncios disponibles, el tiempo total de emisión y las restricciones
 * de incompatibilidad entre anuncios.</p>
 *
 * <p>Los datos se cargan desde un fichero con formato específico donde
 * los anuncios están separados de las restricciones por un marcador '#'.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * DatosAnuncios.tiempoTotal = 30;
 * DatosAnuncios.leeYOrdenaAnuncios("anuncios.txt");
 * Anuncio a = DatosAnuncios.getAnuncio(0);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Anuncio
 * @see ListaDeAnunciosAEmitir
 */
public class DatosAnuncios {

	/** Lista de todos los anuncios disponibles, ordenados por precio unitario descendente. */
	public static List<Anuncio> todosLosAnunciosDisponibles;
	
	/** Tiempo total disponible para la emisión de anuncios. */
	public static Integer tiempoTotal;
	
	/** Conjunto de pares de anuncios incompatibles (no pueden emitirse juntos). */
	public static Set<IntPair> restricciones;
	
	/** Conjunto de índices de todos los anuncios disponibles. */
	public static Set<Integer> todosLosAnuncios; 
	
	/**
	 * Constructor por defecto.
	 */
	public DatosAnuncios() {
		super();
	}
	
	/**
	 * Lee los anuncios desde un fichero y los ordena por precio unitario descendente.
	 *
	 * <p>El fichero debe tener el siguiente formato:</p>
	 * <ul>
	 *   <li>Líneas de anuncios: {@code codigo,duracion,precioBase}</li>
	 *   <li>Separador: {@code #}</li>
	 *   <li>Líneas de restricciones: {@code codigo1,codigo2}</li>
	 * </ul>
	 *
	 * <p>Las restricciones son simétricas: si (a,b) es una restricción,
	 * también se añade (b,a).</p>
	 *
	 * @param file ruta del fichero con los datos de anuncios
	 * @throws IllegalArgumentException si el formato de alguna línea es incorrecto
	 * @see Files2#streamFromFile(String)
	 */
	public static void leeYOrdenaAnuncios(String file){	
		List<String> ls = Files2.streamFromFile(file)
				.collect(Collectors.toList());
		int index = ls.indexOf("#");
		List<String> ls1 = ls.subList(0, index);
		List<String> ls2 = ls.subList(index+1, ls.size());
		todosLosAnunciosDisponibles = List2.empty();
		Anuncio a;
		for(String s : ls1){
			String[] at = Stream2.split(s, ",").<String>toArray((int x)->new String[x]);
			Preconditions.checkArgument(at.length==3);
			a = Anuncio.create(at);
			todosLosAnunciosDisponibles.add(a);
		}
		restricciones = new HashSet<>();
		for(String s : ls2){
			String[] at = Stream2.split(s, ",").<String>toArray((int e)->new String[e]);
			Preconditions.checkArgument(at.length==2);
			Integer n1 = Integer.parseInt(at[0]);
			Integer n2 = Integer.parseInt(at[1]);
			restricciones.add(IntPair.of(n1, n2));
			restricciones.add(IntPair.of(n2, n1));
		}
		Collections.sort(DatosAnuncios.todosLosAnunciosDisponibles, Comparator.<Anuncio>naturalOrder().reversed());
		todosLosAnuncios = Set2.range(0, DatosAnuncios.todosLosAnunciosDisponibles.size());
	}

	/**
	 * Crea una nueva instancia de {@code DatosAnuncios}.
	 *
	 * @return una nueva instancia vacía
	 */
	public static DatosAnuncios create() {		
		return new DatosAnuncios();
	}

	/**
	 * Obtiene un anuncio por su índice en la lista de anuncios disponibles.
	 *
	 * @param i índice del anuncio en la lista ordenada
	 * @return el anuncio en la posición especificada
	 * @throws IndexOutOfBoundsException si el índice está fuera de rango
	 */
	public static Anuncio getAnuncio(int i){
		return todosLosAnunciosDisponibles.get(i);
	}
	
}
