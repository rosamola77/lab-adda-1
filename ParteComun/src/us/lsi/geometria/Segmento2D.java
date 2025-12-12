package us.lsi.geometria;

/**
 * Segmento2D
 * 
 * <p>Representa un segmento de recta en el plano bidimensional definido por dos puntos extremos.
 * Proporciona operaciones para calcular la longitud y realizar transformaciones geométricas.</p>
 * 
 * <p>Operaciones soportadas:
 * <ul>
 *   <li>Cálculo de longitud del segmento</li>
 *   <li>Traslación, rotación y homotecia</li>
 *   <li>Proyección y simetría respecto a rectas</li>
 * </ul></p>
 * 
 * @param p1 primer punto extremo del segmento
 * @param p2 segundo punto extremo del segmento
 * 
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * 
 * @see Punto2D
 * @see Vector2D
 * @see ObjetoGeometrico2D
 */
public record Segmento2D(Punto2D p1, Punto2D p2) implements ObjetoGeometrico2D {
	
	/**
	 * Crea un segmento con los puntos extremos especificados.
	 * 
	 * @param p1 primer punto extremo
	 * @param p2 segundo punto extremo
	 * @return un nuevo segmento
	 */
	public static Segmento2D of(Punto2D p1, Punto2D p2) {
		return new Segmento2D(p1, p2);
	}

	/**
	 * Obtiene el vector director del segmento (de p1 a p2).
	 * 
	 * @return el vector director del segmento
	 */
	public Vector2D vector() {
		return Vector2D.of(this.p1, this.p2);
	}

	/**
	 * Calcula la longitud del segmento.
	 * 
	 * @return la longitud del segmento
	 */
	public Double longitud() {
		return p1.distanciaA(p2);
	}

	@Override
	public Segmento2D rota(Punto2D p, Double angulo) {
		return Segmento2D.of(this.p1.rota(p, angulo), this.p2.rota(p, angulo));
	}

	@Override
	public Segmento2D traslada(Vector2D v) {
		return Segmento2D.of(this.p1.traslada(v), this.p2.traslada(v));
	}

	@Override
	public Segmento2D homotecia(Punto2D p, Double factor) {
		return Segmento2D.of(this.p1.homotecia(p, factor), this.p2.homotecia(p, factor));
	}

	@Override
	public Segmento2D proyectaSobre(Recta2D r) {
		return Segmento2D.of(this.p1.proyectaSobre(r), this.p2.proyectaSobre(r));
	}

	@Override
	public Segmento2D simetrico(Recta2D r) {
		return Segmento2D.of(this.p1.simetrico(r), this.p2.simetrico(r));
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", this.p1, this.p2);
	}

}
