package us.lsi.common;

/**
 * <p>Vista de tipo 4: divide en cuatro partes.</p>
 * 
 * <p>Util para patrones de divide y venceras donde se divide
 * la estructura en cuatro subestructuras, como en matrices.</p>
 * 
 * @author Miguel Toro
 *
 * @param <D> Tipo de la estructura de datos
 * @param a Primera parte
 * @param b Segunda parte
 * @param c Tercera parte
 * @param d Cuarta parte
 */
public record View4<D>(D a,D b, D c,D d) {
	
	/**
	 * Crea una vista de tipo 4.
	 * 
	 * @param <D> Tipo de la estructura de datos
	 * @param a Primera parte
	 * @param b Segunda parte
	 * @param c Tercera parte
	 * @param d Cuarta parte
	 * @return Una nueva View4
	 */
	public static <D> View4<D> of(D a, D b, D c, D d) {
		return new View4<D>(a, b, c, d);
	}

}
