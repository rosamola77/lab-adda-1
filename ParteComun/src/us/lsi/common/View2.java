package us.lsi.common;

/**
 * <p>Vista de tipo 2: divide en dos partes.</p>
 * 
 * <p>Util para patrones de divide y venceras donde se divide
 * la estructura en dos subestructuras.</p>
 * 
 * @author Miguel Toro
 *
 * @param <D> Tipo de la estructura de datos
 * @param left Parte izquierda
 * @param right Parte derecha
 */
public record View2<D>(D left, D right) {
	
	/**
	 * Crea una vista de tipo 2.
	 * 
	 * @param <D> Tipo de la estructura de datos
	 * @param left Parte izquierda
	 * @param right Parte derecha
	 * @return Una nueva View2
	 */
	public static <D> View2<D> of(D left, D right) {
		return new View2<D>(left, right);
	}

}
