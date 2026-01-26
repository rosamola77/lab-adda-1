package tests;

import java.util.List;
import java.util.function.Function;

import ejercicios.Ejercicio2;
import us.lsi.common.Files2;

public class TestEjercicio2 {
	
	public static void main(String[] args) {
		testsEjercicio2("Ejercicio2DatosEntrada.txt");
	}
	
	public static void testsEjercicio2(String file) {
		
		Function<String, ParEnteros> parseParEnteros = s -> {
			String[] v = s.split(",");
			return ParEnteros.of(Integer.valueOf(v[0]), Integer.valueOf(v[1]));
		};
		List<ParEnteros> ls = Files2.streamFromFile("resources/datos/entrada/"+file).map(parseParEnteros).toList();

		System.out.println("EJERCICIO 2. Datos de entrada: " + ls);
		tests(ls);
	}
	
	public static void tests(List<ParEnteros> ls) {
		ls.forEach(t -> {
			System.out.println(); 
			System.out.println("1) Solucion Rec. No Final: " + Ejercicio2.f_RNF(t.a(), t.b()));
			System.out.println("2) Solucion Iterativo: " + Ejercicio2.f_it(t.a(), t.b()));
			System.out.println("3) Solucion Rec. Final: " + Ejercicio2.f_RF(t.a(), t.b()));
			System.out.println("4) Solucion Funcional: " + Ejercicio2.f_funcional(t.a(), t.b()));
		});
		
	}

}
