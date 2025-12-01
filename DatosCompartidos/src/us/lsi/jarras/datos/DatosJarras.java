package us.lsi.jarras.datos;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.IntPair;

/**
 * DatosJarras
 *
 * <p>Clase que gestiona los datos globales para el problema de las jarras de agua.
 * El problema consiste en alcanzar una cantidad específica de agua en las jarras
 * mediante una serie de operaciones permitidas (llenar, vaciar, trasvasar).</p>
 *
 * <p>Esta clase mantiene las capacidades de las jarras, las cantidades iniciales
 * y finales deseadas, así como las operaciones disponibles.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * DatosJarras.capacidadJarra1 = 4;
 * DatosJarras.capacidadJarra2 = 3;
 * DatosJarras.iniDatos("operaciones.txt");
 * Operacion op = DatosJarras.getAccion(0);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class DatosJarras {
	
	/** Cantidad final deseada en la jarra 1. */
	public static Integer cantidadFinalEnJarra1;
	
	/** Cantidad final deseada en la jarra 2. */
	public static Integer cantidadFinalEnJarra2;
	
	/** Cantidad inicial de agua en la jarra 1. */
	public static Integer cantidadInicialEnJarra1;
	
	/** Cantidad inicial de agua en la jarra 2. */
	public static Integer cantidadInicialEnJarra2;
	
	/** Capacidad máxima de la jarra 1. */
	public static Integer capacidadJarra1;
	
	/** Capacidad máxima de la jarra 2. */
	public static Integer capacidadJarra2;
	
	/** Número máximo de acciones permitidas. */
	public static Integer numMaxAcciones;

	/** Lista de operaciones disponibles. */
	private static List<Operacion> acciones = null;
	
	/**
	 * Obtiene la lista de todas las operaciones disponibles.
	 *
	 * @return lista de operaciones
	 */
	public static List<Operacion> getAcciones(){
		return acciones;
	}
	
	/**
	 * Obtiene una operación por su índice.
	 *
	 * @param index índice de la operación
	 * @return la operación en la posición especificada
	 * @throws IndexOutOfBoundsException si el índice está fuera de rango
	 */
	public static Operacion getAccion(int index){
		return acciones.get(index);
	}
	
	/**
	 * Inicializa los datos del problema desde un fichero.
	 *
	 * <p>Lee las operaciones desde el fichero y configura los predicados
	 * de aplicabilidad y las funciones de actualización para cada operación.</p>
	 *
	 * <p>Las operaciones típicas incluyen:
	 * <ul>
	 *   <li>Vaciar jarra 1</li>
	 *   <li>Trasvasar de jarra 1 a jarra 2</li>
	 *   <li>Llenar jarra 1</li>
	 *   <li>Llenar jarra 2</li>
	 *   <li>Vaciar jarra 2</li>
	 *   <li>Trasvasar de jarra 2 a jarra 1</li>
	 * </ul>
	 * </p>
	 *
	 * @param file ruta del fichero con las operaciones
	 */
	public static void iniDatos(String file){
		if(acciones==null) {
			acciones = Files2.streamFromFile(file)
				.map((String s) -> Operacion.create(s))
				.collect(Collectors.toList());
			
			DatosJarras.acciones.get(0).aplicable = (x1,x2)->x1>0 ;
			DatosJarras.acciones.get(1).aplicable = (x1,x2)->x1>0 ;
			DatosJarras.acciones.get(2).aplicable = (x1,x2)->x1>0 ;
			DatosJarras.acciones.get(3).aplicable = (x1,x2)->x1 < capacidadJarra1 ;
			DatosJarras.acciones.get(4).aplicable = (x1,x2)->x2 < capacidadJarra1 ;
			DatosJarras.acciones.get(5).aplicable = (x1,x2)->x2>0 ;
			DatosJarras.acciones.get(6).aplicable = (x1,x2)->x2>0 ;
			DatosJarras.acciones.get(7).aplicable = (x1,x2)->x2>0 ;
			
			DatosJarras.acciones.get(0).actualiza = (x1,x2)-> IntPair.of(0, x2);
			DatosJarras.acciones.get(1).actualiza = (x1,x2)-> IntPair.of(0, Math.min(x1+x2,capacidadJarra2)) ;
			DatosJarras.acciones.get(2).actualiza = (x1,x2)-> 
			IntPair.of(Math.max(0,x1+x2-capacidadJarra2), Math.min(x1+x2,capacidadJarra2));
			DatosJarras.acciones.get(3).actualiza = (x1,x2)-> IntPair.of(capacidadJarra1, x2);
			DatosJarras.acciones.get(4).actualiza = (x1,x2)-> IntPair.of(x1,capacidadJarra2);
			DatosJarras.acciones.get(5).actualiza = (x1,x2)-> IntPair.of(x1, 0);;
			DatosJarras.acciones.get(6).actualiza = (x1,x2)-> IntPair.of(Math.min(x1+x2,capacidadJarra1),0) ; 
			DatosJarras.acciones.get(7).actualiza = (x1,x2)->  
			IntPair.of(Math.min(x1+x2,capacidadJarra1), Math.max(0,x1+x2-capacidadJarra1));		
		
		}
	}
	
	/**
	 * Operacion
	 *
	 * <p>Representa una operación que se puede realizar sobre las jarras.
	 * Cada operación tiene un identificador único, una descripción y
	 * funciones para determinar su aplicabilidad y efecto.</p>
	 *
	 * @author Miguel Toro
	 * @version 1.0
	 * @since 1.0
	 */
	public static class Operacion {
		
		/** Contador estático para asignar identificadores únicos. */
		private static Integer n = 0;
		
		/** Identificador único de la operación. */
		private Integer id;
		
		/** Descripción corta de la operación. */
		private String descripcionCorta;
		
		/** Descripción detallada de la operación. */
		private String descripcion;
		
		/** Predicado que indica si la operación es aplicable dado el estado actual. */
		private BiPredicate<Integer,Integer> aplicable;
		
		/** Función que calcula el nuevo estado tras aplicar la operación. */
		private BiFunction<Integer,Integer,IntPair> actualiza;
		
		/**
		 * Crea una operación a partir de una línea de texto.
		 *
		 * @param s línea con formato {@code id,descripcionCorta,descripcion}
		 * @return una nueva instancia de {@code Operacion}
		 */
		private static Operacion create(String s) {
			return new Operacion(s);
		}
		
		/**
		 * Constructor privado que parsea una línea de texto.
		 *
		 * @param s línea con formato {@code id,descripcionCorta,descripcion}
		 * @throws IllegalArgumentException si el formato no es correcto
		 */
		private Operacion(String s){
			String[] v = s.split(",");
			Integer ne = v.length;
			if(ne != 3) throw new IllegalArgumentException("Formato no adecuado en l�nea  "+s);	
			this.id = n;
			n++;
			this.descripcionCorta = v[1];
			this.descripcion = v[2];		
		}
		
		/**
		 * Obtiene el identificador de la operación.
		 *
		 * @return el identificador
		 */
		public int getId() {
			return this.id;
		}

		/**
		 * Obtiene la descripción corta de la operación.
		 *
		 * @return la descripción corta
		 */
		public String getDescripcionCorta() {
			return descripcionCorta;
		}

		/**
		 * Obtiene la descripción detallada de la operación.
		 *
		 * @return la descripción completa
		 */
		public String getDescripcion() {
			return descripcion;
		}

		/**
		 * Comprueba si la operación es aplicable dado el estado actual de las jarras.
		 *
		 * @param j1 cantidad actual de agua en la jarra 1
		 * @param j2 cantidad actual de agua en la jarra 2
		 * @return {@code true} si la operación puede aplicarse; {@code false} en caso contrario
		 */
		public Boolean isAplicable(Integer j1, Integer j2) {
			return aplicable.test(j1, j2);
		}
		
		/**
		 * Calcula el resultado de aplicar la operación al estado actual.
		 *
		 * @param j1 cantidad actual de agua en la jarra 1
		 * @param j2 cantidad actual de agua en la jarra 2
		 * @return par con las nuevas cantidades en ambas jarras
		 */
		public IntPair result(Integer j1, Integer j2) {
			return actualiza.apply(j1, j2);
		}

		/**
		 * Calcula el código hash de la operación.
		 *
		 * @return el código hash basado en el identificador
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		/**
		 * Compara esta operación con otro objeto para determinar igualdad.
		 *
		 * @param obj el objeto con el que comparar
		 * @return {@code true} si son iguales; {@code false} en caso contrario
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Operacion))
				return false;
			Operacion other = (Operacion) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		/**
		 * Devuelve una representación en cadena de la operación.
		 *
		 * @return cadena con formato {@code (id=X,descripcionCorta)}
		 */
		@Override
		public String toString() {
			return "(id=" + id + "," + descripcionCorta + ")";
		}
	}
	
}
