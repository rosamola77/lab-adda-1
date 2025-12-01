package us.lsi.mochila.datos;


import java.util.Comparator;
import java.util.List;



import java.util.stream.Collectors;

import us.lsi.common.Files2;


/**
 * DatosMochila
 *
 * <p>Esta clase implementa el tipo ProblemaMochila. Gestiona los datos globales
 * para el problema generalizado de la mochila, incluyendo la lista de objetos
 * disponibles y la capacidad inicial.</p>
 *
 * <p>Las propiedades de estos problemas son:</p>
 * <ul>
 *   <li>Capacidad: peso máximo que puede contener la mochila</li>
 *   <li>Objetos Disponibles: lista de objetos que se pueden incluir</li>
 * </ul>
 *
 * <p>Los objetos se ordenan por ratio valor/peso descendente para
 * facilitar algoritmos voraces y de ramificación y poda.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * DatosMochila.iniDatos("objetos.txt");
 * DatosMochila.capacidadInicial = 50;
 * ObjetoMochila obj = DatosMochila.getObjeto(0);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see ObjetoMochila
 */
public class DatosMochila {
	
	/** Lista de objetos disponibles, ordenados por ratio valor/peso descendente. */
	private static List<ObjetoMochila> objetosDisponibles;
	
	/** Comparador para ordenar los objetos. */
	private static Comparator<ObjetoMochila> ordenObjetos;
	
	/** Capacidad inicial de la mochila. */
	public static Integer capacidadInicial;
	
	/** Número total de objetos disponibles. */
	public static Integer numeroDeObjetos;
	
	/** Alias para el número de objetos. */
	public static Integer n;

	/**
	 * Lee los objetos desde un fichero y los ordena por ratio valor/peso descendente.
	 *
	 * <p>El fichero debe contener un objeto por línea con formato:
	 * {@code valor peso numMaxUnidades}</p>
	 *
	 * @param fichero ruta del fichero con los objetos
	 */
	public static void iniDatos(String fichero) {
		ordenObjetos = Comparator.reverseOrder();
		objetosDisponibles = Files2.streamFromFile(fichero)
				.map(s -> ObjetoMochila.parse(s))
				.sorted(ordenObjetos)
				.collect(Collectors.<ObjetoMochila> toList());
		numeroDeObjetos = objetosDisponibles.size();
		n = numeroDeObjetos;
	}
	
	/**
	 * Obtiene la lista de todos los objetos disponibles.
	 *
	 * @return lista de objetos
	 */
	public static List<ObjetoMochila> getObjetos() {
		return objetosDisponibles;
	}
	
	/**
	 * Obtiene el comparador usado para ordenar los objetos.
	 *
	 * @return el comparador
	 */
	public static Comparator<ObjetoMochila> getOrdenObjetos() {
		return ordenObjetos;
	}	

	/**
	 * Obtiene un objeto por su índice.
	 *
	 * @param index índice del objeto
	 * @return el objeto en la posición especificada
	 */
	public static ObjetoMochila getObjeto(int index){
		return DatosMochila.getObjetos().get(index);
	}
	
	/**
	 * Obtiene el valor de un objeto por su índice.
	 *
	 * @param index índice del objeto
	 * @return el valor del objeto
	 */
	public static Integer getValor(int index){
		return DatosMochila.getObjetos().get(index).valor();
	}
	
	/**
	 * Obtiene el peso de un objeto por su índice.
	 *
	 * @param index índice del objeto
	 * @return el peso del objeto
	 */
	public static Integer getPeso(int index){
		return DatosMochila.getObjetos().get(index).peso();
	}
	
	/**
	 * Obtiene el número máximo de unidades de un objeto.
	 *
	 * @param index índice del objeto
	 * @return el número máximo de unidades
	 */
	public static Integer getNumMaxDeUnidades(int index){
		return DatosMochila.getObjetos().get(index).numMaxDeUnidades();
	}
	
	/**
	 * Calcula el número de unidades que caben de un objeto dada una capacidad.
	 *
	 * @param index índice del objeto
	 * @param capacidad capacidad disponible
	 * @return el mínimo entre unidades máximas y las que caben por peso
	 */
	public static Integer getNumUnidadesPosibles(int index, Integer capacidad){
		return Math.min(DatosMochila.getNumMaxDeUnidades(index),capacidad/DatosMochila.getPeso(index));
	}
	
	/**
	 * Calcula la cantidad (posiblemente fraccionaria) que cabe de un objeto.
	 *
	 * @param index índice del objeto
	 * @param capacidad capacidad disponible
	 * @return la cantidad posible
	 */
	public static Double getCantidadPosible(int index, Double capacidad){
		return Math.min(DatosMochila.getNumMaxDeUnidades(index),capacidad/DatosMochila.getPeso(index));
	}
	
	/**
	 * Verifica si una capacidad cumple las restricciones.
	 *
	 * @param c capacidad a verificar
	 * @return {@code true} si la capacidad es no negativa
	 */
	public static Boolean restricciones(Integer c) {
		return c >=0;
	}

	/**
	 * Calcula una cota superior del valor alcanzable desde un índice dado.
	 *
	 * <p>Usa relajación continua: permite fracciones de objetos para
	 * obtener una cota superior al valor óptimo entero.</p>
	 *
	 * @param index índice a partir del cual calcular
	 * @param cr capacidad restante
	 * @return cota superior del valor alcanzable
	 */
	public static Integer getCotaSuperior(Integer index, Integer cr) {
		Double r = 0.;
		int ind = index;
		int n = getObjetos().size();
		Double capacidadRestante = (double)cr;
		Double nu =0.;	
		while(ind < n && capacidadRestante > 0) {	
			nu = getCantidadPosible(ind,capacidadRestante);
			r = r+nu*getValor(ind);
			capacidadRestante = capacidadRestante-nu*getPeso(ind);			
			ind++;		
		} 
		return (int)Math.ceil(r);
	}
}
