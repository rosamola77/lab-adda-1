package us.lsi.mochila_test;

import java.util.List;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.solve.AuxGrammar;

/**
 * DataMochila
 *
 * <p>Clase de datos para el problema de la mochila en modelos PLI.
 * Proporciona acceso a los datos del problema (capacidad, objetos)
 * mediante métodos estáticos que pueden ser invocados desde modelos
 * PLI generados con ANTLR.</p>
 *
 * <p>Esta clase actúa como bridge entre el modelo declarativo PLI
 * y los datos del problema de la mochila cargados desde archivo.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see us.lsi.mochila.datos.DatosMochila
 */
public class DataMochila {
	
	/**
	 * Capacidad inicial de la mochila.
	 */
	public static Integer CI;
	
	/**
	 * Lista de objetos disponibles.
	 */
	public static List<ObjetoMochila>  objetos;
	
	/**
	 * Número de objetos.
	 */
	public static int n;
	
	/**
	 * Obtiene la capacidad inicial de la mochila.
	 *
	 * @return la capacidad inicial
	 */
	public static Integer getCI() {
		return CI;
	}
	
	/**
	 * Obtiene el número de objetos.
	 *
	 * @return el número de objetos
	 */
	public static Integer getN() {
		return n;
	}
	
	/**
	 * Obtiene el valor del objeto en la posición i.
	 *
	 * @param i índice del objeto
	 * @return el valor del objeto
	 */
	public static Integer getValor(Integer i) {
		return objetos.get(i).valor();
	}
	
	/**
	 * Obtiene el peso del objeto en la posición i.
	 *
	 * @param i índice del objeto
	 * @return el peso del objeto
	 */
	public static Integer getPeso(Integer i) {
		return objetos.get(i).peso();
	}
	
	/**
	 * Obtiene el número máximo de unidades del objeto i.
	 *
	 * @param i índice del objeto
	 * @return el número máximo de unidades
	 */
	public static Integer getNMU(Integer i) {
		return objetos.get(i).numMaxDeUnidades();
	}	
	
	/**
	 * Función auxiliar de suma.
	 *
	 * @param a primer sumando
	 * @param b segundo sumando
	 * @param c tercer sumando
	 * @return la suma de los tres valores
	 */
	public static Integer s(Integer a, Integer b, Integer c) {
		return a+b+c;
	}
	
	/**
	 * Inicializa los datos del problema de la mochila.
	 */
	public static void iniMochila() {
		AuxGrammar.dataClass = DataMochila.class;
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		DataMochila.CI = DatosMochila.capacidadInicial;
		DataMochila.objetos = DatosMochila.getObjetos();
		DataMochila.n = DataMochila.objetos.size();
	}

}
