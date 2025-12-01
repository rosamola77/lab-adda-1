package us.lsi.common;

/**
 * <p>Interfaz funcional para predicados de tres argumentos.</p>
 * 
 * <p>Representa un predicado que acepta tres argumentos y produce un valor booleano.</p>
 * 
 * @author Miguel Toro
 *
 * @param <A> Tipo del primer argumento
 * @param <B> Tipo del segundo argumento
 * @param <C> Tipo del tercer argumento
 */
public interface TriPredicate<A,B,C> {
	
	/**
	 * Evalua el predicado con los tres argumentos.
	 * 
	 * @param e1 Primer argumento
	 * @param e2 Segundo argumento
	 * @param e3 Tercer argumento
	 * @return Resultado de la evaluacion
	 */
	Boolean test(A e1, B e2, C e3);

}
