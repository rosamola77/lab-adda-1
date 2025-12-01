package us.lsi.common;

/**
 * <p>Representa un par de valores Long.</p>
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
public record LongPair(Long first,Long second) {

	
	/**
	 * Crea un LongPair a partir de dos valores.
	 * 
	 * @param a Primer valor
	 * @param b Segundo valor
	 * @return Un nuevo LongPair
	 */
	public static LongPair of(Long a, Long b) {
		return new LongPair(a, b);
	}
	
	/**
	 * Crea un LongPair a partir de un Pair generico.
	 * 
	 * @param p Par de Long
	 * @return Un nuevo LongPair
	 */
	public static LongPair of(Pair<Long,Long> p) {
		return new LongPair(p.first(), p.second());
	}
	
	/**
	 * Parsea un LongPair desde una cadena.
	 * 
	 * @param s Cadena con formato "(a,b)"
	 * @return Un nuevo LongPair
	 */
	public static LongPair parse(String s) {
		String[] partes = s.split("[(),]");
		return new LongPair(Long.parseLong(partes[0].trim()), Long.parseLong(partes[1].trim()));
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
	public LongPair add(LongPair p) {
		return LongPair.of(this.first()+p.first(), this.second()+p.second());
	}

	/**
	 * Resta otro par de este.
	 * 
	 * @param p Par a restar
	 * @return Nuevo par con la resta componente a componente
	 */
	public LongPair minus(LongPair p) {
		return LongPair.of(this.first()-p.first(), this.second()-p.second());
	}
	
	/**
	 * Multiplica este par por un escalar.
	 * 
	 * @param e Escalar multiplicador
	 * @return Nuevo par multiplicado por el escalar
	 */
	public LongPair multiply(Long e) {
		return LongPair.of(e*this.first(), e*this.second());
	}
	
	/**
	 * Calcula la suma de los valores absolutos de las componentes.
	 * 
	 * @return |first| + |second|
	 */
	public Long sumAbs() {
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
	public Long manhattan(LongPair p) {
		return this.minus(p).sumAbs();
	}
	
	/**
	 * Calcula el tamano del intervalo (second - first).
	 * 
	 * @return Tamano del intervalo
	 */
	public Long size() {
		return this.second()-this.first();
	}
	
	/**
	 * Calcula el centro del intervalo.
	 * 
	 * @return (first + second) / 2
	 */
	public Long center() {
		return (this.second()+this.first())/2;
	}
	
	/**
	 * Crea una vista de tipo 1 extrayendo el primer elemento.
	 * 
	 * @return Vista con el primer elemento y el resto del intervalo
	 */
	public View1<LongPair,Long> view1() {
		return View1.of(this.first(),LongPair.of(this.first()+1,this.second()));
	}
	
	/**
	 * Crea una vista de tipo 2E dividiendo el intervalo por el centro.
	 * 
	 * @return Vista con el centro y los dos subintervalos sin solape
	 */
	public View2E<LongPair,Long> view2e() {
		Long k = (this.second()+this.first())/2;
		return View2E.of(k,LongPair.of(this.first(),k),LongPair.of(k,this.second()));
	}
	
	/**
	 * Crea una vista de tipo 2E con solape dividiendo el intervalo por el centro.
	 * 
	 * @return Vista con el centro y los dos subintervalos con solape
	 */
	public View2E<LongPair,Long> view2eOverlap() {
		Long k = (this.second()+this.first())/2;
		return View2E.of(k,LongPair.of(this.first(),k+1),LongPair.of(k,this.second()));
	}
}
