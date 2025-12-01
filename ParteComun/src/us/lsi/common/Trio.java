package us.lsi.common;

/**
 * <p>Representa un trio generico de valores.</p>
 * 
 * <p>Permite agrupar tres valores de tipos potencialmente diferentes
 * en una unica estructura.</p>
 * 
 * @author Miguel Toro
 *
 * @param <A> Tipo del primer elemento
 * @param <B> Tipo del segundo elemento
 * @param <C> Tipo del tercer elemento
 * @param first Primer elemento
 * @param second Segundo elemento
 * @param third Tercer elemento
 */
public record Trio<A, B, C>(A first,B second,C third) {

	/**
	 * Crea un Trio con los tres valores especificados.
	 * 
	 * @param <A> Tipo del primer elemento
	 * @param <B> Tipo del segundo elemento
	 * @param <C> Tipo del tercer elemento
	 * @param first Primer valor
	 * @param second Segundo valor
	 * @param third Tercer valor
	 * @return Un nuevo Trio
	 */
	public static <A, B, C> Trio<A, B, C> of(A first, B second, C third) {
		return new Trio<A, B, C>(first, second, third);
	}

	/**
	 * Representacion en cadena del trio.
	 * 
	 * @return Cadena con formato "(first,second,third)"
	 */
	@Override
	public String toString() {
		return String.format("(%s,%s,%s)",first,second,third);
	}
	
}
