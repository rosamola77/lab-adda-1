package tests;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ejercicios.Ejercicio3;
import us.lsi.common.Files2;

public class TestEjercicio3 {

	public static void main(String[] args) {	
		String fichero = "resources/datos/entrada/Ejercicio3DatosEntrada.txt";
		System.out.println("##########################################");
		System.out.println("#\tEjercicio 3\t\t\t#");
		System.out.println(String.format("#\t%s\t#", fichero));
		System.out.println("##########################################\n");
		Function<String, Integer> stringToInt = input_string -> Integer.parseInt(input_string);
		Function<String, List<Integer>> ft = cadena -> Arrays.stream(cadena.split(","))
				.map(e -> stringToInt.apply(e.trim())).collect(Collectors.toList());
		List<List<Integer>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(ft)
				.collect(Collectors.toList());
		input.stream().forEach(linea -> System.out
				.println(String.format("%s", ejercicio3(linea.get(0), linea.get(1), linea.get(2)))));
		System.out.println("##########################################\n");

	}
	
	public static String ejercicio3(Integer x, Integer y, Integer z) {
		String res = "";
		res += String.format("Entrada: %s, %s, %s\n", x, y, z);
		res += "1. Iterativo: " + Ejercicio3.iterativo(x, y, z) + "\n";
		res += "2. Recursivo sin memoria: " + Ejercicio3.recursivo_sin_memoria(x, y, z) + "\n";
		res += "3. Recursivo con memoria: " + Ejercicio3.recursivo_con_memoria(x, y, z) + "\n";
		return res;
	}

}
