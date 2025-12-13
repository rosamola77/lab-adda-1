package us.lsi.geometria;

import us.lsi.common.Preconditions;

/**
 * Circulo2D
 *
 * <p>Representa un círculo en el plano bidimensional definido por su centro y radio.
 * Implementa operaciones geométricas como rotación, traslación, homotecia y proyección.</p>
 *
 * <p>El círculo es inmutable (record) y proporciona métodos para cálculo de área,
 * perímetro y transformaciones geométricas que devuelven nuevos círculos.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * Punto2D centro = Punto2D.of(0.0, 0.0);
 * Circulo2D circulo = Circulo2D.of(centro, 5.0);
 * Double area = circulo.area(); // π * 25
 * }</p>
 *
 * @param centro el centro del círculo
 * @param radio el radio del círculo (debe ser >= 0)
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see Punto2D
 * @see ObjetoGeometrico2D
 */
public record Circulo2D(Punto2D centro,Double radio)  implements ObjetoGeometrico2D {
	
	/**
	 * Crea un nuevo círculo con el centro y radio especificados.
	 *
	 * @param centro el centro del círculo
	 * @param radio el radio del círculo (debe ser >= 0)
	 * @return un nuevo círculo
	 * @throws IllegalArgumentException si el radio es negativo
	 */
	public static Circulo2D of(Punto2D centro, Double radio) {
		Preconditions.checkArgument(radio>=0, String.format("El radio debe ser mayor o igual a cero y es %.2f",radio));
		return new Circulo2D(centro, radio);
	}
	
	/**
	 * Calcula el área del círculo.
	 *
	 * @return el área del círculo (π * r²)
	 */
	public Double area() {
		return Math.PI*this.radio*this.radio;
	}

	/**
	 * Calcula el perímetro (longitud de la circunferencia) del círculo.
	 *
	 * @return el perímetro del círculo (2 * π * r)
	 */
	public Double perimetro() {
		return 2*Math.PI*this.radio;
	}

	@Override
	public Circulo2D rota(Punto2D p, Double angulo) {		
		return Circulo2D.of(this.centro.rota(p,angulo), this.radio);
	}

	@Override
	public Circulo2D traslada(Vector2D v) {
		return Circulo2D.of(this.centro.traslada(v), this.radio);
	}
	
	@Override
	public Circulo2D homotecia(Punto2D p, Double factor) {
		return Circulo2D.of(this.centro.homotecia(p,factor), this.radio*factor);
	}
	
	@Override
	public Segmento2D proyectaSobre(Recta2D r) {
		Punto2D pc = this.centro.proyectaSobre(r);
		Vector2D u = r.vector().unitario();
		return Segmento2D.of(pc.add(u.multiply(this.radio)),pc.add(u.multiply(-this.radio)));
	}
	
	@Override
	public Circulo2D simetrico(Recta2D r) {
		return Circulo2D.of(this.centro.simetrico(r), this.radio);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%.2f)",this.centro,this.radio);
	}

}
