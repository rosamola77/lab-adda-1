package us.lsi.afinidad.datos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cliente
 *
 * <p>Representa un cliente en el problema de asignación de clientes a trabajadores.
 * Cada cliente tiene un nombre, una franja horaria en la que debe ser atendido,
 * y un conjunto de trabajadores con los que tiene afinidad.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Cliente c = Cliente.create("Juan,10,Amparo;Rosa");
 * String nombre = c.nombre;
 * int franja = c.franjaHoraria;
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see DatosAfinidad
 */
public class Cliente {
	
	/**
	 * Crea un cliente a partir de una línea de texto.
	 *
	 * <p>El formato esperado es: {@code nombre,franjaHoraria,trabajador1;trabajador2;...}</p>
	 *
	 * @param cl línea de texto con los datos del cliente
	 * @return una nueva instancia de {@code Cliente}
	 * @throws IllegalArgumentException si el formato es incorrecto
	 */
	public static Cliente create(String cl) {
		return new Cliente(cl);
	}
	
	/**
	 * Crea un cliente con los parámetros especificados.
	 *
	 * @param nombre nombre del cliente
	 * @param franjaHoraria franja horaria de atención
	 * @param trabajadoresAfines conjunto de nombres de trabajadores afines
	 * @return una nueva instancia de {@code Cliente}
	 */
	public static Cliente create(String nombre, int franjaHoraria,
			Set<String> trabajadoresAfines) {
		return new Cliente(nombre, franjaHoraria, trabajadoresAfines);
	}
	
	/** Nombre del cliente. */
	public String nombre;
	
	/** Franja horaria en la que el cliente debe ser atendido. */
	public int franjaHoraria;
	
	/** Conjunto de nombres de trabajadores con afinidad. */
	Set<String> nombresDeTrabajadoresAfines;
	
	/** Conjunto de índices de trabajadores afines (calculado posteriormente). */
	public Set<Integer> trabajadoresAfines;
	
	/**
	 * Constructor privado que crea un cliente con los datos especificados.
	 *
	 * @param nombre nombre del cliente
	 * @param franjaHoraria franja horaria
	 * @param trabajadoresAfines conjunto de nombres de trabajadores afines
	 */
	private Cliente(String nombre, int franjaHoraria, Set<String> trabajadoresAfines) {
		super();
		this.nombre = nombre;
		this.franjaHoraria = franjaHoraria;
		this.nombresDeTrabajadoresAfines = trabajadoresAfines;
		this.trabajadoresAfines = new HashSet<>();
	}
	
	/**
	 * Constructor privado que crea un cliente a partir de una línea de texto.
	 *
	 * @param cl línea de texto con formato {@code nombre,franja,trabajadores}
	 * @throws IllegalArgumentException si el formato es incorrecto
	 */
	private Cliente(String cl) {
		//System.out.println(l);
		String[] data= cl.split(",");
		if(data.length!=3) throw new IllegalArgumentException("Incorrecto formato para un Cliente "+cl+". Deber�a ser nombre,franjaHoraria,trabajadoresAfines.");
		
		this.nombre= data[0];
		this.franjaHoraria= Integer.parseInt(data[1]);
		this.nombresDeTrabajadoresAfines = Arrays.asList(data[2].split(";")).stream().collect(Collectors.toSet());
		this.trabajadoresAfines = new HashSet<>();
	}
	
	/**
	 * Calcula los índices de los trabajadores afines.
	 *
	 * <p>Este método debe llamarse después de que se haya inicializado
	 * la lista global de trabajadores en {@link DatosAfinidad}.</p>
	 */
	public void calculaTrabajadoresAfines(){
		this.nombresDeTrabajadoresAfines.stream()
			.forEach(x->this.trabajadoresAfines.add(DatosAfinidad.trabajadores.indexOf(x)));
	}
	
	/**
	 * Devuelve una representación en cadena del cliente.
	 *
	 * @return representación textual con nombre, franja y trabajadores afines
	 */
	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", franjaHoraria=" + franjaHoraria + ", trabajadoresAfines="
				+ trabajadoresAfines + "]";
	}	
}

