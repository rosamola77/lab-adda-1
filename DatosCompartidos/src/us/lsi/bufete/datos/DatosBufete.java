package us.lsi.bufete.datos;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

/**
 * DatosBufete
 *
 * <p>Clase que gestiona los datos globales para el problema de asignación
 * de abogados a casos en un bufete. Proporciona acceso a la lista de
 * abogados y sus horas estimadas para cada caso.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * DatosBufete.iniDatos("ficheros/datos.txt");
 * Abogado a = DatosBufete.getAbogado(0);
 * Integer horas = DatosBufete.getHoras(0, 1);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Abogado
 */
public class DatosBufete {
	
	/** Número total de casos a asignar. */
	public static int NUM_CASOS;
	
	/** Número total de abogados disponibles. */
	public static int NUM_ABOGADOS;
	
	/** Lista de abogados. */
	private static List<Abogado> abogados; 
	
	/**
	 * Inicializa los datos del problema desde un fichero.
	 *
	 * <p>El fichero debe contener una línea por abogado con formato:
	 * {@code nombre: hora1,hora2,hora3,...}</p>
	 *
	 * @param fichero ruta del fichero con los datos
	 */
	public static void iniDatos(String fichero) {
		abogados = Files2.streamFromFile(fichero)
		.map(s->Abogado.parse(s)).collect(Collectors.toList());
		
		NUM_ABOGADOS = abogados.size();
		NUM_CASOS = abogados.get(0).getHoras().size();		
	}
	
	/**
	 * Obtiene la lista de todos los abogados.
	 *
	 * @return lista de abogados
	 */
	public static List<Abogado> getAbogados() {
		return abogados;
	}

	/**
	 * Obtiene un abogado por su índice.
	 *
	 * @param index índice del abogado
	 * @return el abogado en la posición especificada
	 * @throws IndexOutOfBoundsException si el índice está fuera de rango
	 */
	public static Abogado getAbogado(int index) {
		return abogados.get(index);
	}
	
	/**
	 * Obtiene las horas que tardaría un abogado en resolver un caso.
	 *
	 * @param i índice del abogado
	 * @param j índice del caso
	 * @return las horas estimadas
	 * @throws IndexOutOfBoundsException si algún índice está fuera de rango
	 */
	public static Integer getHoras(int i, int j) {
		return abogados.get(i).getHoras(j);
	}
	
	/**
	 * Muestra los datos cargados por consola.
	 */
	public static void toConsole() {
		System.out.println("Num. de casos: "+NUM_CASOS);
		abogados.forEach(System.out::println);		
	}
	
	/**
	 * Método principal para pruebas de la lectura del fichero.
	 *
	 * @param args argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		iniDatos("ficheros/PI6Ej2DatosEntrada1.txt");
		toConsole();
	}
	
}

