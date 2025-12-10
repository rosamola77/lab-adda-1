package us.lsi.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Printers
 *
 * <p>Utilidades para crear y gestionar PrintStreams hacia archivos.
 * Proporciona métodos simplificados para redirigir la salida a ficheros.</p>
 *
 * <p>Facilita la creación de PrintStreams manejando excepciones
 * y proporcionando mensajes de error claros.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class Printers {

	/**
	 * Crea un PrintStream para escribir en un archivo.
	 *
	 * @param file ruta del archivo de salida
	 * @return PrintStream asociado al archivo
	 * @throws IllegalArgumentException si no se puede abrir el archivo
	 */
	public static PrintStream file(String file) {
		PrintStream p;
		try {
			p = new PrintStream(new File(file));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("No se puede abrir el fichero " + file);
		}
		return p;
	}
}
