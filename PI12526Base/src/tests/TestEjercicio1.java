package tests;

import java.util.List;
import java.util.function.Function;

import ejercicios.Ejercicio1;
import us.lsi.common.Files2;

public class TestEjercicio1 {
	public static void main(String[] args) {
		Function<String, DatosE1> parseDatosE1 = s -> {
			String[] v = s.split(",");
			return DatosE1.of(Integer.valueOf(v[0]), v[1], Integer.valueOf(v[2]), v[3], Integer.valueOf(v[4]));
		};
		String file = "resources/datos/entrada/Ejercicio1DatosEntrada.txt";
		List<DatosE1> ls = Files2.streamFromFile(file).map(parseDatosE1).toList();

		ls.forEach(t -> {
			System.out.println("1) Solucion Funcional:  " + Ejercicio1.solucionFuncional(t.varA(), t.varB(), t.varC(), t.varD(), t.varE()));
			System.out.println("2) Solucion Iterativa:  " + Ejercicio1.solucionIterativa(t.varA(), t.varB(), t.varC(), t.varD(), t.varE()));
			System.out.println("3) Solucion Rec. Final: " + Ejercicio1.solucionRecursivaFinal(t.varA(), t.varB(), t.varC(), t.varD(), t.varE()));
			System.out.println("_________________________________________________________________________________________________");
		});
		System.out.println(".............................................................................................................................");
	}
	

}
