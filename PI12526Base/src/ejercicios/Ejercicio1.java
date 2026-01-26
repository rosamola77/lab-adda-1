package ejercicios;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Ejercicio1 {

	// Del enunciado:
	public static Map<Integer,List<String>> solucionFuncional(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		UnaryOperator<EnteroCadena> nx = elem -> {
			return EnteroCadena.of(elem.a()+2,
				elem.a()%3==0?
					elem.s()+elem.a().toString():
					elem.s().substring(elem.a()%elem.s().length()));
		};					
		return Stream.iterate(EnteroCadena.of(varA,varB), elem -> elem.a() < varC, nx)
					.map(elem -> elem.s() + varD)
					.filter(nom -> nom.length() < varE)
					.collect(Collectors.groupingBy(String::length));
	}
	
	public static Map<Integer,List<String>> solucionIterativa(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		return null;
	}
	
	public static Map<Integer,List<String>> solucionRecursivaFinal(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		return null;
	}
}
