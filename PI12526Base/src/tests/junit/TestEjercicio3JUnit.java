
package tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import us.lsi.common.Trio;

public class TestEjercicio3JUnit {
	private static List<Trio<Integer,Integer,Integer>>					input;
	private static List<Long>					expectedOutput;
	private	static String 						inputFileName 	= "/datos/entrada/Ejercicio3DatosEntrada.txt";
	private	static String 						outputFileName 	= "/datos/salida/Ejercicio3DatosSalida.txt";
	private static Class<TestEjercicio3JUnit> 	thisClass 		= TestEjercicio3JUnit.class;
	
	@BeforeAll
	private static void readInput() {
		InputStream is = thisClass.getResourceAsStream(inputFileName);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		Function<String, Trio<Integer,Integer,Integer>> parseTupla = s -> {
			String[] v = s.split(",");
			return Trio.of(Integer.valueOf(v[0]), Integer.valueOf(v[1]), Integer.valueOf(v[2]));
		};
		input = reader.lines().map(parseTupla).toList();
	}

	@BeforeAll
	private static void readOuput() {		
		InputStream is = thisClass.getResourceAsStream(outputFileName);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);	        
		expectedOutput = reader.lines().map(Long::parseLong).toList();
	}
	
	@ParameterizedTest
	@CsvSource({ 
		  "recursivo_sin_memoria, 0", 
		  "recursivo_sin_memoria, 1", 
		  "recursivo_sin_memoria, 2", 
		  "recursivo_sin_memoria, 3", 
		  "recursivo_sin_memoria, 4", 
		  "recursivo_sin_memoria, 5",
		  "recursivo_con_memoria, 0", 
		  "recursivo_con_memoria, 1", 
		  "recursivo_con_memoria, 2",
		  "recursivo_con_memoria, 3", 
		  "recursivo_con_memoria, 4", 
		  "recursivo_con_memoria, 5", 
		  "iterativo, 0", 
		  "iterativo, 1",
		  "iterativo, 2",
		  "iterativo, 3", 
		  "iterativo, 4",
		  "iterativo, 5"
		})
	void testOutput(String methodName, int i) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?> 	ejercicioClass 		= Class.forName("ejercicios.Ejercicio3"); 
        Class<?>[] 	paramTypes 			= {Integer.class, Integer.class, Integer.class};
        Method 		ejercicioMethod 	= ejercicioClass.getMethod(methodName, paramTypes);
		Trio<Integer,Integer,Integer> t = input.get(i);			
        Long output = (Long) ejercicioMethod.invoke(null, t.first(), t.second(), t.third()); // pass args
        assertEquals(expectedOutput.get(i),output,"Expected output: "+expectedOutput.get(i)+"-"+methodName+" output: "+output);
	}
}
