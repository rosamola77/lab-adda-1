package us.lsi.geometria;

/**
 * Recta2D
 *
 * <p>Representa una recta en el plano bidimensional mediante un punto
 * y un vector director. Utiliza la forma paramétrica de la recta.</p>
 *
 * <p>Una recta se define como el conjunto de puntos P tales que
 * P = punto + t * vector, donde t es un parámetro real.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Punto2D p = Punto2D.of(1.0, 2.0);
 * Vector2D v = Vector2D.of(3.0, 4.0);
 * Recta2D recta = Recta2D.of(p, v);
 * }</p>
 *
 * @param punto punto por el que pasa la recta
 * @param vector vector director de la recta
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Punto2D
 * @see Vector2D
 */
public record Recta2D(Punto2D punto, Vector2D vector)  {
	
	/**
	 * Crea una recta dados un punto y un vector director.
	 *
	 * @param punto punto por el que pasa la recta
	 * @param vector vector director
	 * @return la recta definida
	 */
	public static Recta2D of(Punto2D punto, Vector2D vector) {
		return new Recta2D(punto, vector);
	}
	
	/**
	 * Crea una recta que pasa por dos puntos.
	 *
	 * @param p1 primer punto
	 * @param p2 segundo punto
	 * @return la recta que pasa por ambos puntos
	 */
	public static Recta2D of(Punto2D p1, Punto2D p2) {
		return new Recta2D(p1, p2.minus(p1));
	}
	
	@Override
	public String toString() {
		return String.format("(%.2f,%.2f)",this.punto, this.vector).toString();
	}

}
