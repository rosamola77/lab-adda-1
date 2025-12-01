package us.lsi.common;

/**
 * <p>Representa un par de valores Integer.</p>
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
public record IntPair(Integer first,Integer second) {

	
	/**
	 * Crea un IntPair a partir de dos valores.
	 * 
	 * @param a Primer valor
	 * @param b Segundo valor
	 * @return Un nuevo IntPair
	 */
	public static IntPair of(Integer a, Integer b) {
		return new IntPair(a, b);
	}
	
	/**
	 * Crea un IntPair a partir de un Pair generico.
	 * 
	 * @param p Par de Integer
	 * @return Un nuevo IntPair
	 */
	public static IntPair of(Pair<Integer,Integer> p) {
		return new IntPair(p.first(), p.second());
	}
	
	/**
	 * Parsea un IntPair desde una cadena.
	 * 
	 * @param s Cadena con formato "(a,b)"
	 * @return Un nuevo IntPair
	 */
	public static IntPair parse(String s) {
		String[] partes = s.split("[(),]");
		return new IntPair(Integer.parseInt(partes[0].trim()), Integer.parseInt(partes[1].trim()));
	}
	
	/**
	 * Representacion en cadena del par.
	 * 
	 * @return Cadena con formato "(first,second)"
	 */
	@Override
	public String toString() {
		return String.format("(%d,%d)",this.first(),this.second());
	}

	/**
	 * Suma este par con otro.
	 * 
	 * @param p Par a sumar
	 * @return Nuevo par con la suma componente a componente
	 */
	public IntPair add(IntPair p) {
		return IntPair.of(this.first()+p.first(), this.second()+p.second());
	}

	/**
	 * Resta otro par de este.
	 * 
	 * @param p Par a restar
	 * @return Nuevo par con la resta componente a componente
	 */
	public IntPair minus(IntPair p) {
		return IntPair.of(this.first()-p.first(), this.second()-p.second());
	}
	
	/**
	 * Multiplica este par por un escalar.
	 * 
	 * @param e Escalar multiplicador
	 * @return Nuevo par multiplicado por el escalar
	 */
	public IntPair multiply(Integer e) {
		return IntPair.of(e*this.first(), e*this.second());
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
	public Integer manhattan(IntPair p) {
		IntPair r = this.minus(p);
		return Math.abs(r.first())+Math.abs(r.second());
	}
	
	/**
	 * Calcula el tamano del intervalo (second - first).
	 * 
	 * @return Tamano del intervalo
	 */
	public Integer size() {
		return this.second()-this.first();
	}
	
	/**
	 * Calcula el centro del intervalo.
	 * 
	 * @return (first + second) / 2
	 */
	public Integer center() {
		return (this.second()+this.first())/2;
	}
	
	/**
	 * Crea una vista de tipo 1 extrayendo el primer elemento.
	 * 
	 * @return Vista con el primer elemento y el resto del intervalo
	 */
	public View1<IntPair,Integer> view1() {
		return View1.of(this.first(),IntPair.of(this.first()+1,this.second()));
	}
	
	/**
	 * Crea una vista de tipo 2E dividiendo el intervalo por el centro.
	 * 
	 * @return Vista con el centro y los dos subintervalos sin solape
	 */
	public View2E<IntPair,Integer> view2e() {
		Integer k = (this.second()+this.first())/2;
		return View2E.of(k,IntPair.of(this.first(),k),IntPair.of(k,this.second()));
	}
	
	/**
	 * Crea una vista de tipo 2E con solape dividiendo el intervalo por el centro.
	 * 
	 * @return Vista con el centro y los dos subintervalos con solape
	 */
	public View2E<IntPair,Integer> view2eOverlap() {
		Integer k = (this.second()+this.first())/2;
		return View2E.of(k,IntPair.of(this.first(),k+1),IntPair.of(k,this.second()));
	}
}
