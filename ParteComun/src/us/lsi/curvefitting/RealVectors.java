package us.lsi.curvefitting;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * RealVectors
 *
 * <p>Utilidades para trabajar con vectores reales de Apache Commons Math.
 * Proporciona métodos de conversión y manipulación de RealVector.</p>
 *
 * <p>Facilita la conversión entre arrays de doubles y RealVector
 * para uso en operaciones matemáticas y ajuste de curvas.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see RealVector
 */
public class RealVectors {
	
	/**
	 * Convierte un array de doubles a RealVector.
	 *
	 * @param d array de valores double
	 * @return RealVector con los valores del array
	 */
	public static RealVector toRealVector(double[] d) {
		RealVector rv = new ArrayRealVector();
		for(int i=0;i<d.length;i++)
			rv = rv.append(d[i]);
		return rv;
	}

}
