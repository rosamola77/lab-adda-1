package us.lsi.geometria;

import us.lsi.common.Preconditions;

/**
 * Vector2D
 * 
 * <p>Representa un vector bidimensional con componentes (x, y).
 * Proporciona operaciones vectoriales clásicas y transformaciones geométricas.</p>
 * 
 * <p>Operaciones soportadas:
 * <ul>
 *   <li>Suma y resta de vectores</li>
 *   <li>Multiplicación escalar y vectorial</li>
 *   <li>Cálculo de módulo y ángulo</li>
 *   <li>Rotación y proyección</li>
 *   <li>Vectores unitarios y ortogonales</li>
 * </ul></p>
 * 
 * @param x componente x del vector
 * @param y componente y del vector
 * 
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * 
 * @see Punto2D
 */
public record Vector2D(Double x,Double y) {

	/**
	 * Obtiene el vector base del eje X (1, 0).
	 * 
	 * @return el vector unitario en dirección X
	 */
	public static Vector2D baseX() {
		return new Vector2D(1., 0.);
	}
	
	/**
	 * Obtiene el vector base del eje Y (0, 1).
	 * 
	 * @return el vector unitario en dirección Y
	 */
	public static Vector2D baseY() {
		return new Vector2D(0., 1.);
	}
	
	/**
	 * Crea un vector con las componentes especificadas.
	 * 
	 * @param x componente x del vector
	 * @param y componente y del vector
	 * @return un nuevo vector con las componentes dadas
	 */
	public static Vector2D of(Double x, Double y) {
		return new Vector2D(x, y);
	}

	/**
	 * Crea el vector que va desde el punto p1 hasta el punto p2.
	 * 
	 * @param p1 punto inicial
	 * @param p2 punto final
	 * @return el vector p2 - p1
	 */
	public static Vector2D of(Punto2D p1, Punto2D p2) {
		return p2.minus(p1);
	}
	
	/**
	 * Crea una copia del vector especificado.
	 * 
	 * @param p vector a copiar
	 * @return una copia del vector
	 */
	public static Vector2D copy(Vector2D p) {
		return new Vector2D(p.x(), p.y());
	}
	
	/**
	 * Crea un vector a partir de su módulo y ángulo en grados.
	 * 
	 * @param modulo módulo del vector (debe ser > 0)
	 * @param angulo ángulo en grados respecto al eje X positivo
	 * @return un nuevo vector con el módulo y ángulo especificados
	 */
	public static Vector2D ofGrados(Double modulo, Double angulo){
		Preconditions.checkArgument(modulo > 0, String.format("El módulo debe ser mayor o igual a cero y es %.2f",modulo));
		return ofRadianes(modulo, Math.toRadians(angulo));
	}
	
	/**
	 * Crea un vector a partir de su módulo y ángulo en radianes.
	 * 
	 * @param modulo módulo del vector (debe ser >= 0)
	 * @param angulo ángulo en radianes respecto al eje X positivo
	 * @return un nuevo vector con el módulo y ángulo especificados
	 */
	public static Vector2D ofRadianes(Double modulo, Double angulo){
		Preconditions.checkArgument(modulo >= 0, String.format("El módulo debe ser mayor o igual a cero y es %.2f",modulo));
		return of(modulo*Math.cos(angulo),modulo*Math.sin(angulo));		
	}
	
	/**
	 * Calcula el módulo (longitud) del vector.
	 * 
	 * @return el módulo del vector
	 */
	public Double modulo() {
		return Math.abs(Math.hypot(x, y));
	}

	/**
	 * Calcula el ángulo del vector en radianes respecto al eje X positivo.
	 * 
	 * @return el ángulo en radianes
	 */
	public Double angulo() {
		return Math.atan2(y, x);
	}
	
	/**
	 * Calcula el ángulo del vector en grados respecto al eje X positivo.
	 * 
	 * @return el ángulo en grados
	 */
	public Double anguloEnGrados() {
		return Math.toDegrees(this.angulo());
	}

	public Double angulo(Vector2D v) {
		return Math.asin(this.multiplicaVectorial(v)/(this.modulo()*v.modulo()));
	}
	
	public Double anguloEnGrados(Vector2D v) {
		return Math.toDegrees(angulo(v));
	}
	
	public Vector2D proyectaSobre(Vector2D v){
		Vector2D u = v.unitario();
		return u.multiply(this.multiplicaEscalar(u));
	}	
	
	public Punto2D punto() {
		return Punto2D.of(this.x, this.y);
	}
	
	public Vector2D ortogonal() {
		return Vector2D.of(-this.y, this.x);
	}
	
	public Vector2D unitario() {
		return Vector2D.ofRadianes(1.,this.angulo());
	}
	
	public Vector2D opuesto() {
		return Vector2D.of(-x, -y);
	}
	
	public Vector2D add(Vector2D v) {
		return Vector2D.of(this.x+v.x,this.y+v.y);
	}
	
	public Vector2D minus(Vector2D v) {
		return Vector2D.of(this.x-v.x,this.y-v.y);
	}
	
	public Vector2D rota(Double angulo) {
		return Vector2D.ofRadianes(this.modulo(),this.angulo()+angulo);
	}
		
	public Vector2D multiply(Double factor) {
		return Vector2D.of(this.x*factor,this.y*factor);
	}
	
	public Double multiplicaVectorial(Vector2D v) {
		return this.x()*v.y()-this.y()*v.x();
	}
	
	public Double multiplicaEscalar(Vector2D v) {
		return this.x()*v.x()+this.y()*v.y();
	}
	
	
	@Override
	public String toString() {
		return String.format("(%.2f,%.2f)",this.x, this.y);
	}
	
}
