package tests;

public record DatosE1(Integer varA, String varB, Integer varC, String varD, Integer varE) {
	public static DatosE1 of(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		return new DatosE1(varA, varB, varC, varD, varE);
	}
}
