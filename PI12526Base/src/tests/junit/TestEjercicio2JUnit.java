
package tests.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tests.ParEnteros;

public class TestEjercicio2JUnit {
	private static List<ParEnteros>				input;
	private static List<List<Integer>>			expectedOutput;
	private	static String 						inputFileName 	= "/datos/entrada/Ejercicio2DatosEntrada.txt";
	private	static String 						outputFileName 	= "/datos/salida/Ejercicio2DatosSalida.txt";
	private static Class<TestEjercicio2JUnit> 	thisClass 		= TestEjercicio2JUnit.class;
	
	@BeforeAll
	private static void readInput() {
		Function<String, ParEnteros> parseParEnteros = s -> {
			String[] v = s.split(",");
			return ParEnteros.of(Integer.valueOf(v[0]), Integer.valueOf(v[1]));
		};
		InputStream is = thisClass.getResourceAsStream(inputFileName);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);        
		input = reader.lines().map(parseParEnteros)
				.toList();
	}
	
	@BeforeAll
	private static void readOutput() {
		Function<String, List<Integer>> parseDatosSalida = s -> {
			String noSpacesLine = s.replaceAll(" ", "");
		    List<String>  cadenaList = new ArrayList<>(Arrays.asList(noSpacesLine.split(",")));
		    List<Integer> valuesList = cadenaList.stream().map(Integer::parseInt).collect(Collectors.toList());
	        return valuesList; 
		};
		InputStream is = thisClass.getResourceAsStream(outputFileName);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);        
		expectedOutput = reader.lines().map(parseDatosSalida).toList();
	}
	
	@ParameterizedTest
	@CsvSource({ 
		  "f_RNF, 0", 
		  "f_RNF, 1", 
		  "f_RNF, 2", 
		  "f_RNF, 3", 
		  "f_RNF, 4", 
		  "f_RNF, 5", 
		  "f_RF, 0", 
		  "f_RF, 1", 
		  "f_RF, 2", 
		  "f_RF, 3", 
		  "f_RF, 4", 
		  "f_RF, 5", 
		  "f_it, 0", 
		  "f_it, 1", 
		  "f_it, 2", 
		  "f_it, 3", 
		  "f_it, 4", 
		  "f_it, 5", 
		  "f_funcional, 0", 
		  "f_funcional, 1",
		  "f_funcional, 2", 
		  "f_funcional, 3",
		  "f_funcional, 4", 
		  "f_funcional, 5"
		})
	void testOutput(String methodName, int i) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?> 	ejercicioClass 	= Class.forName("ejercicios.Ejercicio2"); 
        Class<?>[] 	paramTypes 		= {Integer.class, Integer.class};
        Method 		ejercicioMethod = ejercicioClass.getMethod(methodName, paramTypes); 			
        List<String> output = (List<String>)ejercicioMethod.invoke(null, input.get(i).a(), input.get(i).b()); // pass args
        assertEquals(expectedOutput.get(i),output,"Expected output: "+expectedOutput.get(i)+"-"+methodName+" output: "+output);
	}
}
