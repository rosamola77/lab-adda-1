package us.lsi.common;

/**
 * <p>Interfaz funcional para funciones de tres argumentos.</p>
 * 
 * <p>Representa una funcion que acepta tres argumentos y produce un resultado.</p>
 * 
 * @author Miguel Toro
 *
 * @param <S1> Tipo del primer argumento
 * @param <S2> Tipo del segundo argumento
 * @param <S3> Tipo del tercer argumento
 * @param <T> Tipo del resultado
 */
public interface TriFunction<S1,S2,S3,T> {
	
	/**
	 * Aplica la funcion a los tres argumentos.
	 * 
	 * @param op1 Primer argumento
	 * @param op2 Segundo argumento
	 * @param op3 Tercer argumento
	 * @return Resultado de aplicar la funcion
	 */
	T apply(S1 op1,S2 op2,S3 op3);
}
