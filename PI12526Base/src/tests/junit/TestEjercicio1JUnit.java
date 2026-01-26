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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tests.DatosE1;

public class TestEjercicio1JUnit {
	private static List<DatosE1> 					input;
	private static List<Map<Integer,List<String>>>	expectedOutput;
	private	static String 							inputFileName 	= "/datos/entrada/Ejercicio1DatosEntrada.txt";
	private	static String 							outputFileName 	= "/datos/salida/Ejercicio1DatosSalida.txt";
	private static Class<TestEjercicio1JUnit> 		thisClass 		= TestEjercicio1JUnit.class;
	
	@BeforeAll
	private static void readInput() {
		Function<String, DatosE1> parseDatosE1 = s -> {
			String[] v = s.split(",");
			return DatosE1.of(Integer.valueOf(v[0]), v[1], Integer.valueOf(v[2]), v[3], Integer.valueOf(v[4]));
		};
		InputStream is = thisClass.getResourceAsStream(inputFileName);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);        
		input = reader.lines().map(parseDatosE1).toList();
	}
	
	@BeforeAll
	private static void readOutput() {
		Function<String, Map<Integer,List<String>>> parseDatosSalida = s -> {
			Map<Integer, List<String>> res = new HashMap<Integer, List<String>>();
			String noSpacesLine = s.replaceAll(" ", "");
			String[] v = noSpacesLine.split("],");
			for(String entrada: v) {
				String[] entry = entrada.split("=",2);
			    Integer key = Integer.parseInt(entry[0]);
                String valuesString = entry[1].replace("]", "");
                valuesString = valuesString.replace("[", "");
                List<String> valuesList = new ArrayList<>(Arrays.asList(valuesString.split(",")));
	            res.put(key, valuesList);
	        }
			return res; 
		};
		InputStream is = thisClass.getResourceAsStream(outputFileName);
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		expectedOutput = reader.lines().map(parseDatosSalida).toList();
	}
	
	@ParameterizedTest
	@CsvSource({ 
		  "solucionFuncional, 0", 
		  "solucionIterativa, 0",
		  "solucionRecursivaFinal, 0",
		  "solucionFuncional, 1", 
		  "solucionIterativa, 1",
		  "solucionRecursivaFinal, 1",
		  "solucionFuncional, 2", 
		  "solucionIterativa, 2",
		  "solucionRecursivaFinal, 2",
		  "solucionFuncional, 3", 
		  "solucionIterativa, 3",
		  "solucionRecursivaFinal, 3",
		  "solucionFuncional, 4", 
		  "solucionIterativa, 4",
		  "solucionRecursivaFinal, 4",
		  "solucionFuncional, 5", 
		  "solucionIterativa, 5",
		  "solucionRecursivaFinal, 5"
		})
	void testOutput(String methodName, int version) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Class<?> 	ejercicioClass 	= Class.forName("ejercicios.Ejercicio1"); 
        Class<?>[] 	paramTypes 		= {Integer.class,String.class,Integer.class,String.class,Integer.class};
        Method 		ejercicioMethod = ejercicioClass.getMethod(methodName, paramTypes);
        Map<Integer,List<String>> output = (Map<Integer,List<String>>) ejercicioMethod.invoke(null, input.get(version).varA(), input.get(version).varB(), input.get(version).varC(), input.get(version).varD(), input.get(version).varE()); 
        assertEquals(expectedOutput.get(version),output,"Expected output: "+expectedOutput.get(version)+"-"+methodName+" output: "+output);
	}

}