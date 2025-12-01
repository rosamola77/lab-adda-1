package us.lsi.common;

import java.util.function.BinaryOperator;

/**
 * <p>Extension de BinaryOperator que maneja valores null.</p>
 * 
 * <p>Esta clase envuelve un BinaryOperator y le anade la capacidad
 * de manejar operandos null de forma segura.</p>
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los operandos y resultado
 */
public class BinaryOperator2<E> implements BinaryOperator<E> {
	
	/**
	 * Crea un BinaryOperator2 a partir de un BinaryOperator.
	 * 
	 * @param <E> Tipo de los operandos
	 * @param op Operador binario base
	 * @return Un nuevo BinaryOperator2
	 */
	public static <E> BinaryOperator2<E> of(BinaryOperator<E> op)  {
		return new BinaryOperator2<E>(op);
	}

	/** Operador binario interno */
	private BinaryOperator<E> op;

	/**
	 * Constructor privado.
	 * 
	 * @param op Operador binario base
	 */
	private BinaryOperator2(BinaryOperator<E> op) {
		super();
		this.op = op;
	}

	/**
	 * Aplica el operador a dos valores, manejando nulls.
	 * 
	 * <p>Si uno de los operandos es null, devuelve el otro.
	 * Si ambos son null, devuelve null.
	 * Si ninguno es null, aplica el operador.</p>
	 * 
	 * @param t Primer operando
	 * @param u Segundo operando
	 * @return Resultado de la operacion
	 */
	@Override
	public E apply(E t, E u) {
		if(t == null && u != null) return u;
		if(t != null && u == null) return t;
		if(t != null && u != null) return op.apply(t,u);
		else return null;
	}
	
}
