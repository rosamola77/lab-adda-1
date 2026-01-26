package tests;

public record ParEnteros(Integer a, Integer b) {
	public static ParEnteros of(Integer a, Integer b) {
		return new ParEnteros(a, b);
	}

}
