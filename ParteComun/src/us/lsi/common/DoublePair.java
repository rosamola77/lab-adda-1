package us.lsi.common;

/**
 * <p>Representa un par de valores Double.</p>
 * 
 * <p>Proporciona operaciones aritmeticas y geometricas basicas
 * como suma, resta, multiplicacion por escalar, modulo y
 * distancia Manhattan.</p>
 * 
 * @author Miguel Toro
 *
 * @param first Primer componente del par
 * @param second Segundo componente del par
 */
public record DoublePair(Double first,Double second) {

	
	/**
	 * Crea un DoublePair a partir de dos valores.
	 * 
	 * @param a Primer valor
	 * @param b Segundo valor
	 * @return Un nuevo DoublePair
	 */
	public static DoublePair of(Double a, Double b) {
		return new DoublePair(a, b);
	}
	
	/**
	 * Crea un DoublePair a partir de un Pair generico.
	 * 
	 * @param p Par de Double
	 * @return Un nuevo DoublePair
	 */
	public static DoublePair of(Pair<Double,Double> p) {
		return new DoublePair(p.first(), p.second());
	}
	
	/**
	 * Parsea un DoublePair desde una cadena.
	 * 
	 * @param s Cadena con formato "(a,b)"
	 * @return Un nuevo DoublePair
	 */
	public static DoublePair parse(String s) {
		String[] partes = s.split("[(),]");
		return new DoublePair(Double.parseDouble(partes[0].trim()), Double.parseDouble(partes[1].trim()));
	}
	
	/**
	 * Representacion en cadena del par.
	 * 
	 * @return Cadena con formato "(first,second)"
	 */
	@Override
	public String toString() {
		return String.format("(%f,%f)",this.first(),this.second());
	}

	/**
	 * Suma este par con otro.
	 * 
	 * @param p Par a sumar
	 * @return Nuevo par con la suma componente a componente
	 */
	public DoublePair add(DoublePair p) {
		return DoublePair.of(this.first()+p.first(), this.second()+p.second());
	}

	/**
	 * Resta otro par de este.
	 * 
	 * @param p Par a restar
	 * @return Nuevo par con la resta componente a componente
	 */
	public DoublePair minus(DoublePair p) {
		return DoublePair.of(this.first()-p.first(), this.second()-p.second());
	}
	
	/**
	 * Multiplica este par por un escalar.
	 * 
	 * @param e Escalar multiplicador
	 * @return Nuevo par multiplicado por el escalar
	 */
	public DoublePair multiply(Double e) {
		return DoublePair.of(e*this.first(), e*this.second());
	}
	
	/**
	 * Calcula la suma de los valores absolutos de las componentes.
	 * 
	 * @return |first| + |second|
	 */
	public Double sumAbs() {
		return Math.abs(this.first())+Math.abs(this.second());
	}
	
	/**
	 * Calcula el modulo (norma euclidiana) del par.
	 * 
	 * @return sqrt(first^2 + second^2)
	 */
	public Double module() {
		return Math.sqrt(this.first()*this.first()+this.second()*this.second());
	}
	
	/**
	 * Calcula la distancia Manhattan a otro par.
	 * 
	 * @param p Otro par
	 * @return Distancia Manhattan entre los pares
	 */
	public Double manhattan(DoublePair p) {
		return this.minus(p).sumAbs();
	}
	
	/**
	 * Calcula el tamano del intervalo (second - first).
	 * 
	 * @return Tamano del intervalo
	 */
	public Double size() {
		return this.second()-this.first();
	}
	
	/**
	 * Calcula el centro del intervalo.
	 * 
	 * @return (first + second) / 2
	 */
	public Double center() {
		return (this.second()+this.first())/2;
	}
	
	/**
	 * Crea una vista de tipo 2E dividiendo el intervalo por el centro.
	 * 
	 * @return Vista con el centro y los dos subintervalos
	 */
	public View2E<DoublePair,Double> view2e() {
		Double k = (this.second()+this.first())/2;
		return View2E.of(k,DoublePair.of(this.first(),k),DoublePair.of(k,this.second()));
	}
}

