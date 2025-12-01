package us.lsi.reinas.datos;

/**
 * Reina
 *
 * <p>Representa una reina en el problema de las N-Reinas.
 * Cada reina tiene una posición en el tablero definida por
 * coordenadas (x, y).</p>
 *
 * <p>La clase proporciona métodos para obtener las diagonales
 * principal y secundaria, lo cual es útil para verificar
 * conflictos entre reinas.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Reina.numeroDeReinas = 8;
 * Reina r = Reina.create(0, 3);
 * Integer diag = r.getDiagonalPrincipal();
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public class Reina {
	
	/** Coordenada Y (fila) de la reina en el tablero. */
	private Integer y;
	
	/** Coordenada X (columna) de la reina en el tablero. */
	private Integer x;
	
	/** Número total de reinas (tamaño del tablero). Valor por defecto: 8. */
	public static int numeroDeReinas = 8;
	
	/**
	 * Crea una nueva reina en la posición especificada.
	 *
	 * @param x coordenada X (columna)
	 * @param y coordenada Y (fila)
	 * @return una nueva instancia de {@code Reina}
	 */
	public static Reina create(int x, int y) {
		return new Reina(x, y);
	}
	
	/**
	 * Constructor que crea una reina en la posición especificada.
	 *
	 * @param x coordenada X (columna)
	 * @param y coordenada Y (fila)
	 */
	Reina(int x, int y) {
		super();		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Obtiene la coordenada Y (fila) de la reina.
	 *
	 * @return la coordenada Y
	 */
	public Integer getY() {
		return y;
	}
	
	/**
	 * Obtiene la coordenada X (columna) de la reina.
	 *
	 * @return la coordenada X
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * Calcula el identificador de la diagonal principal.
	 *
	 * <p>Las reinas en la misma diagonal principal tienen el mismo valor
	 * de {@code y - x}. Esto es útil para detectar conflictos diagonales.</p>
	 *
	 * @return el identificador de la diagonal principal
	 */
	public Integer getDiagonalPrincipal(){
		return y-x;
	}
	
	/**
	 * Calcula el identificador de la diagonal secundaria.
	 *
	 * <p>Las reinas en la misma diagonal secundaria tienen el mismo valor
	 * de {@code y + x}. Esto es útil para detectar conflictos diagonales.</p>
	 *
	 * @return el identificador de la diagonal secundaria
	 */
	public Integer getDiagonalSecundaria(){
		return y+x;
	}
	
	/**
	 * Devuelve una representación en cadena de la reina.
	 *
	 * @return cadena con formato {@code [x,y]}
	 */
	@Override
	public String toString() {
		return "[" + x + ","+ y + "]";
	}

	/**
	 * Calcula el código hash de la reina.
	 *
	 * @return el código hash basado en las coordenadas
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	/**
	 * Compara esta reina con otro objeto para determinar igualdad.
	 *
	 * <p>Dos reinas son iguales si tienen las mismas coordenadas.</p>
	 *
	 * @param obj el objeto con el que comparar
	 * @return {@code true} si son iguales; {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reina other = (Reina) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
}
