package us.lsi.common;

/**
 * <p>Tipo de union discriminada (sum type).</p>
 * 
 * <p>Representa un valor que puede ser de tipo A o de tipo B,
 * pero no ambos simultaneamente.</p>
 * 
 * @author Miguel Toro
 *
 * @param <A> Primer tipo posible
 * @param <B> Segundo tipo posible
 * @param a Valor de tipo A (null si es de tipo B)
 * @param b Valor de tipo B (null si es de tipo A)
 */
public record Union<A, B> (A a, B b) {
	
	/**
	 * Crea una Union conteniendo un valor de tipo A.
	 * 
	 * @param <A> Tipo del valor
	 * @param <B> Tipo alternativo
	 * @param a Valor a contener
	 * @return Una nueva Union con valor de tipo A
	 */
	public static <A, B> Union<A, B> ofA(A a){
		return new Union<>(a,null);
	}
	
	/**
	 * Crea una Union conteniendo un valor de tipo B.
	 * 
	 * @param <A> Tipo alternativo
	 * @param <B> Tipo del valor
	 * @param b Valor a contener
	 * @return Una nueva Union con valor de tipo B
	 */
	public static <A, B> Union<A, B> ofB(B b){
		return new Union<>(null,b);
	}
	
	/**
	 * Comprueba si contiene un valor de tipo A.
	 * 
	 * @return true si contiene un valor de tipo A
	 */
	public Boolean isA() {
		return this.b() == null;
	}
	
	/**
	 * Comprueba si contiene un valor de tipo B.
	 * 
	 * @return true si contiene un valor de tipo B
	 */
	public Boolean isB() {
		return this.a() == null;
	}
	
	/**
	 * Representacion en cadena del valor contenido.
	 * 
	 * @return Representacion textual del valor
	 */
	@Override
	public String toString() {
		return this.isA()? this.a().toString() : this.b().toString();
	} 
}
