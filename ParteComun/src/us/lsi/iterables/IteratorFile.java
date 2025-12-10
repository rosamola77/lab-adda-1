package us.lsi.iterables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * IteratorFile
 *
 * <p>Iterador que lee líneas de un archivo de texto de forma secuencial.
 * Implementa tanto Iterator como Iterable para permitir su uso en bucles for-each.</p>
 *
 * <p>Permite recorrer las líneas de un archivo sin cargar todo el contenido en memoria,
 * lo cual es útil para archivos grandes. Cada llamada a next() devuelve la siguiente
 * línea del archivo.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * IteratorFile iter = new IteratorFile("datos.txt");
 * for (String linea : iter) {
 *     System.out.println(linea);
 * }
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class IteratorFile implements Iterator<String>, Iterable<String> {
	
	private BufferedReader bf;
	private String file;
	private String nextLine;

	/**
	 * Construye un iterador para leer líneas del archivo especificado.
	 *
	 * @param file ruta del archivo a leer
	 */
	public IteratorFile(String file) {
		super();
		this.file = file;			
		try {
			this.bf = new BufferedReader(new FileReader(file));
			this.nextLine = this.bf.readLine();
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	@Override
	public Iterator<String> iterator() {
		return new IteratorFile(file);
	}

	@Override
	public boolean hasNext() {
		return this.nextLine != null;
	}

	@Override
	public String next() {
		String r = this.nextLine;
		try {
			this.nextLine = bf.readLine();
		} catch (IOException e) {
			System.err.println(e);
		}
		return r;
	}

}

