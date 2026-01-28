package ejercicios;

public record EnteroCadena(Integer a, String s) {

	public static EnteroCadena of(Integer varA, String varB) {
		return new EnteroCadena(varA, varB);
	}

}
