package us.lsi.afinidad.datos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;

/**
 * DatosAfinidad
 *
 * <p>Clase que gestiona los datos globales para el problema de asignación
 * de clientes a trabajadores maximizando la afinidad. Mantiene las listas
 * de trabajadores y clientes disponibles.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * DatosAfinidad datos = DatosAfinidad.create("clientes.txt");
 * List<Cliente> clientes = DatosAfinidad.clientes;
 * List<String> trabajadores = DatosAfinidad.trabajadores;
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Cliente
 * @see SolucionAfinidad
 */
public class DatosAfinidad {
	
	/** Lista de nombres de trabajadores disponibles. */
	public static List<String> trabajadores;
	
	/** Lista de clientes a atender. */
	public static List<Cliente> clientes;	
	
	/**
	 * Constructor privado.
	 */
	private DatosAfinidad() {
			
	}	
	
	/**
	 * Crea un problema de afinidad de ejemplo con 8 clientes y 3 trabajadores.
	 *
	 * <p>Los clientes de ejemplo tienen distintas franjas horarias y
	 * preferencias de trabajadores.</p>
	 *
	 * @return una nueva instancia de {@code DatosAfinidad} con datos de ejemplo
	 */
	public static DatosAfinidad createEjemplo(){
		clientes= List2.of(
				Cliente.create("Juan", 10, Set2.of("Amparo", "Rosa")),
				Cliente.create("Maria", 10, Set2.of("Rosa")),
				Cliente.create("Sara", 11, Set2.of("Amparo", "Rosa")),
				Cliente.create("Andres", 11, Set2.of("Marco", "Rosa")),
				Cliente.create("Antonio", 11,Set2.of("Marco")),
				Cliente.create("Sonia", 12, Set2.of("Marco")),
				Cliente.create("Marta", 12, Set2.of("Marco")),
				Cliente.create("Ivan", 12, Set2.of("Amparo"))			
				);
		Set<String> trab= new HashSet<>();	
		clientes.stream().forEach(x-> trab.addAll(x.nombresDeTrabajadoresAfines));
		trabajadores = new ArrayList<>(trab);//Elimina repetidos
		clientes.stream().forEach(x->x.calculaTrabajadoresAfines());
		return  new DatosAfinidad();
	}
	
	/**
	 * Crea un problema de afinidad leyendo los datos de un fichero de texto.
	 *
	 * <p>Cada línea del fichero debe tener la estructura:
	 * {@code nombreCliente,franjaHoraria,trabajadoresAfines}</p>
	 *
	 * <p>Los trabajadores afines se separan por punto y coma (;).</p>
	 *
	 * @param file ruta del fichero con los datos
	 * @return una nueva instancia de {@code DatosAfinidad}
	 */
	public static DatosAfinidad create(String file){
		Set<String> trab= new HashSet<>();			
		clientes=new ArrayList<>();
		
		Files2.streamFromFile(file)
			.map(x-> x.replace(" ","")) //quitar espacios en blanco
			.peek(x -> clientes.add(Cliente.create(x))) //crear clientes
			.forEach(x-> trab.addAll(Arrays.asList(x.split(",")[2].split(";")))); //crear trabajadores
			
		trabajadores = new ArrayList<>(trab);//Elimina repetidos		
		clientes.stream().forEach(x->x.calculaTrabajadoresAfines());
		return new DatosAfinidad();
	}
}

