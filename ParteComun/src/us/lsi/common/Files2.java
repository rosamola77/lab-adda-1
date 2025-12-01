package us.lsi.common;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import us.lsi.streams.Stream2;

/**
 * Files2
 *
 * <p>Clase de utilidades para manejo de ficheros. Proporciona métodos
 * para leer y escribir ficheros de texto, así como para parsear
 * ficheros CSV.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * // Leer líneas como stream
 * Stream<String> lineas = Files2.streamFromFile("datos.txt");
 * 
 * // Escribir texto a fichero
 * Files2.toFile("contenido", "salida.txt");
 * 
 * // Leer CSV
 * Stream<List<String>> csv = Files2.streamDeCsv("datos.csv");
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class Files2 {
	
	/**
	 * Lee todo el contenido de un fichero como texto.
	 *
	 * @param file ruta del fichero
	 * @return contenido del fichero como una cadena
	 */
	public static String text(String file){
		List<String> lineas = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			lineas = bufferedReader.lines().collect(Collectors.toList());
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return lineas.stream().collect(Collectors.joining("\n"));
	}
	
	/**
	 * Escribe una cadena a un fichero.
	 *
	 * @param s cadena a escribir
	 * @param file ruta del fichero de salida
	 * @throws IllegalArgumentException si no se puede crear el fichero
	 */
	public static void toFile(String s, String file) {
		try {
			final PrintWriter f = 
					new PrintWriter(new BufferedWriter(
							new FileWriter(file)));
			f.println(s);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	/**
	 * Escribe los elementos de un stream a un fichero, uno por línea.
	 *
	 * @param s stream de cadenas a escribir
	 * @param file ruta del fichero de salida
	 * @throws IllegalArgumentException si no se puede crear el fichero
	 */
	public static void toFile(Stream<String> s, String file) {
		try {
			final PrintWriter f = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			s.forEach(x -> {
				f.println(x);
			});
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha podido crear el fichero " + file);
		}
	}
	
	/**
	 * Lee un fichero y devuelve un stream de sus líneas.
	 *
	 * @param file ruta del fichero
	 * @return stream de líneas del fichero
	 * @throws IllegalArgumentException si no se encuentra el fichero
	 */
	public static Stream<String> streamFromFile(String file) {
		return streamFromFile(file, Charset.defaultCharset());
	}
	
	/**
	 * Lee un fichero con un charset específico y devuelve un stream de sus líneas.
	 *
	 * @param file ruta del fichero
	 * @param charset charset a utilizar
	 * @return stream de líneas del fichero
	 * @throws IllegalArgumentException si no se encuentra el fichero
	 */
	public static Stream<String> streamFromFile(String file, Charset charset) {
		Stream<String> r = null;
		try {
			r = Files.lines(Paths.get(file), charset);
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha encontrado el fichero " + file);
		}
		return r;
	}
	
	/**
	 * Lee un fichero y devuelve una lista de sus líneas.
	 *
	 * @param file ruta del fichero
	 * @return lista de líneas del fichero
	 * @throws IllegalArgumentException si no se encuentra el fichero
	 */
	public static List<String> linesFromFile(String file) {
		List<String> r = null;
		try {
			r = Files.readAllLines(Paths.get(file), Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha encontrado el fichero " + file);
		}
		return r;
	}
	
	/**
	 * Lee un fichero CSV y devuelve un stream de registros.
	 *
	 * <p>Usa coma como delimitador por defecto.</p>
	 *
	 * @param file ruta del fichero CSV
	 * @return stream donde cada elemento es una lista de campos
	 */
	public static Stream<List<String>> streamDeCsv(String file) {
		return streamDeCsv(file,",");
	}
	
	/**
	 * Lee un fichero CSV con un delimitador específico.
	 *
	 * <p>Más información sobre la lectura de ficheros CSV en 
	 * https://commons.apache.org/proper/commons-csv/index.html</p>
	 *
	 * @param file ruta del fichero CSV
	 * @param delimiter delimitador de campos
	 * @return stream donde cada elemento es una lista de campos
	 */
	public static Stream<List<String>> streamDeCsv(String file,String delimiter) {
		CSVParser parser=null;
		try {
			BufferedReader csvData = new BufferedReader(new FileReader(file));
			CSVFormat csvFormat = CSVFormat.Builder.create()
					.setSkipHeaderRecord(false)
					.setDelimiter(delimiter)
					.setTrim(true)
					.build();
			parser = csvFormat.parse(csvData);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return Stream2.ofIterator(parser.iterator()).map(r->r.toList());
	}
	
	/**
	 * Lee un fichero CSV y devuelve una lista de registros.
	 *
	 * @param file ruta del fichero CSV
	 * @return lista donde cada elemento es una lista de campos
	 */
	public static List<List<String>> lineasDeCsv(String file) {
		return lineasDeCsv(file,",");
	}
	
	/**
	 * Lee un fichero CSV con un delimitador específico.
	 *
	 * @param file ruta del fichero CSV
	 * @param delimiter delimitador de campos
	 * @return lista donde cada elemento es una lista de campos
	 */
	public static List<List<String>> lineasDeCsv(String file, String delimiter) {
		CSVParser parser=null;
		try {
			BufferedReader csvData = new BufferedReader(new FileReader(file));
			CSVFormat csvFormat = CSVFormat.Builder.create()
					.setSkipHeaderRecord(false)
					.setDelimiter(delimiter)
					.setTrim(true)
					.build();
			parser = csvFormat.parse(csvData);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return parser.getRecords().stream().map(r->r.toList()).toList();
	}
	
	/**
	 * Lee un fichero CSV con cabecera y devuelve un mapa.
	 *
	 * <p>Las claves son los nombres de las columnas y los valores
	 * son listas con los valores de cada columna.</p>
	 *
	 * @param file ruta del fichero CSV
	 * @return mapa columna -> lista de valores
	 */
	public static Map<String,List<String>> mapDeCsv(String file) {
		return mapDeCsv(file,",");
	}
	
	/**
	 * Lee un fichero CSV con cabecera y delimitador específico.
	 *
	 * @param file ruta del fichero CSV
	 * @param delimiter delimitador de campos
	 * @return mapa columna -> lista de valores
	 */
	public static Map<String,List<String>> mapDeCsv(String file,String delimiter) {
		Map<String,List<String>> rt = new HashMap<>();
		Iterable<CSVRecord> records = null;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			CSVFormat csvFormat = CSVFormat.Builder.create()
					.setHeader()
					.setSkipHeaderRecord(true)
					.setDelimiter(delimiter)
					.setTrim(true)
					.build();
			records = csvFormat.parse(in);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	    for (CSVRecord rd : records) {
	        for(String name:rd.toMap().keySet()) {	        	
	        	if(rt.keySet().contains(name)) {
	        		rt.get(name).add(rd.get(name));
	        	} else {
	        		List<String> ls = new ArrayList<>();
	        		ls.add(rd.get(name));
	        		rt.put(name,ls);	
	        	}
	        }    
	    }	    
	    return rt;
	}
	
	/**
	 * Obtiene un OutputStream para un fichero.
	 *
	 * @param file ruta del fichero
	 * @return OutputStream para escribir al fichero
	 */
	public static OutputStream getOutputStream(String file) {
		OutputStream r = null;
		try {
			r = new FileOutputStream(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}

	/** Writer global para uso compartido. */
	public static PrintWriter writer = null;
	
	/**
	 * Obtiene un PrintWriter para un fichero.
	 *
	 * @param file ruta del fichero
	 * @return PrintWriter para escribir al fichero
	 */
	public static PrintWriter getWriter(String file) {
		PrintWriter r = null;
		try {
			r = new PrintWriter(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * Obtiene el writer global.
	 *
	 * @return el PrintWriter global
	 */
	public static PrintWriter getWriter() {
		return writer;
	}

	/**
	 * Configura el writer global.
	 *
	 * @param file ruta del fichero para el writer
	 */
	public static void setPrintWriter(String file) {
		PrintWriter r = null;
		try {
			r = new PrintWriter(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer = r;
	}
}

