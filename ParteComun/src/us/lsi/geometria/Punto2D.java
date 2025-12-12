package us.lsi.geometria;

/**
 * Punto2D
 * 
 * <p>Representa un punto en el plano cartesiano bidimensional con coordenadas (x, y).
 * Implementa operaciones geométricas básicas como distancias, traslaciones, rotaciones,
 * homotecias, proyecciones y simetrías.</p>
 * 
 * <p>El record proporciona métodos para:
 * <ul>
 *   <li>Calcular distancias entre puntos</li>
 *   <li>Determinar el cuadrante del punto</li>
 *   <li>Realizar transformaciones geométricas (traslación, rotación, homotecia)</li>
 *   <li>Operar con vectores (suma, resta)</li>
 *   <li>Proyecciones y simetrías respecto a rectas</li>
 * </ul></p>
 * 
 * @param x coordenada x del punto
 * @param y coordenada y del punto
 * 
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * 
 * @see Vector2D
 * @see Recta2D
 * @see ObjetoGeometrico2D
 */
public record Punto2D(Double x,Double y) implements ObjetoGeometrico2D, Comparable<Punto2D> {
	
	private static Punto2D cero = Punto2D.of(0.,0.);
	
	/**
	 * Obtiene el punto origen de coordenadas (0, 0).
	 * 
	 * @return el punto origen
	 */
	public static Punto2D origen(){
		return Punto2D.cero;
	}
	
	/**
	 * Crea un punto con las coordenadas especificadas.
	 * 
	 * @param x coordenada x del punto
	 * @param y coordenada y del punto
	 * @return un nuevo punto con las coordenadas dadas
	 */
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x, y);
	}

	/**
	 * Parsea un punto desde su representación textual "(x,y)".
	 * 
	 * @param text texto con formato "(x,y)"
	 * @return el punto parseado
	 */
	public static Punto2D parse(String text) {
		String text2 = text.substring(1, text.length()-1);
		String[] campos = text2.split(",");
		return new Punto2D(Double.parseDouble(campos[0]), Double.parseDouble(campos[1]));
	}
    
	/**
	 * Determina el cuadrante en el que se encuentra el punto.
	 * 
	 * @return el cuadrante (PRIMER, SEGUNDO, TERCER o CUARTO)
	 */
    public Cuadrante cuadrante(){
    	Cuadrante c;
		if(this.x() >=0 && this.y() >=0) c = Cuadrante.PRIMER_CUADRANTE;
		else if(this.x() <=0 && this.y() >=0) c = Cuadrante.SEGUNDO_CUADRANTE;
		else if(this.x() <=0 && this.y() <=0) c =  Cuadrante.TERCER_CUADRANTE;
		else c = Cuadrante.CUARTO_CUADRANTE;
    	return c;
	}
    
	/**
	 * Calcula la distancia euclidiana entre dos puntos.
	 * 
	 * @param p1 primer punto
	 * @param p2 segundo punto
	 * @return la distancia entre los puntos
	 */
    public static Double distanciaA(Punto2D p1, Punto2D p2) {
    	Double dx = p1.x()-p2.x();
    	Double dy = p1.y()-p2.y();
		return Math.sqrt(dx*dx+dy*dy);
    }
    
	/**
	 * Calcula la distancia euclidiana desde este punto a otro punto.
	 * 
	 * @param p punto destino
	 * @return la distancia al punto p
	 */
    public Double distanciaA(Punto2D p) {
    	Double dx = this.x()-p.x();
    	Double dy = this.y()-p.y();
		return Math.sqrt(dx*dx+dy*dy);
	}
    
	/**
	 * Calcula la distancia de este punto al origen de coordenadas.
	 * 
	 * @return la distancia al origen
	 */
	public Double distanciaAlOrigen() {
		return distanciaA(Punto2D.origen());
	} 
	
	/**
	 * Verifica si este punto domina a otro punto (coordenadas mayores o iguales).
	 * 
	 * @param p punto a comparar
	 * @return true si este punto domina a p, false en caso contrario
	 */
	public boolean dominaA(Punto2D p){
		return !this.equals(p) && this.x() >= p.x() && this.y() >= p.y();
	}
	
	/**
	 * Suma un vector a este punto.
	 * 
	 * @param v vector a sumar
	 * @return el punto resultante de la suma
	 */
	public Punto2D add(Vector2D v){
    	return Punto2D.of(this.x+v.x(),this.y+v.y());
    }
	
	/**
	 * Resta un vector de este punto.
	 * 
	 * @param v vector a restar
	 * @return el punto resultante de la resta
	 */
	public Punto2D minus(Vector2D v){
    	return Punto2D.of(this.x-v.x(),this.y-v.y());
    }
    
	/**
	 * Calcula el vector diferencia entre este punto y otro punto.
	 * 
	 * @param p punto a restar
	 * @return el vector resultante
	 */
    public Vector2D minus(Punto2D p){
    	return Vector2D.of(this.x-p.x(),this.y-p.y());
    }
    
	/**
	 * Obtiene el vector de posición desde el origen hasta este punto.
	 * 
	 * @return el vector de posición
	 */
    public Vector2D vector() {
		return Vector2D.of(this.x, this.y);
	}	
    @Override
	public Punto2D traslada(Vector2D v){
		return this.add(v);
	}
    
	@Override
	public Punto2D rota(Punto2D p, Double angulo){
		Vector2D v = this.minus(p).rota(angulo);
		return p.add(v);
	}	
	
	@Override
	public Punto2D homotecia(Punto2D p, Double factor) {
		return p.add(Vector2D.of(p, this).multiply(factor));
	}
	
	@Override
	public Punto2D proyectaSobre(Recta2D r) {
		return r.punto().add(this.minus(r.punto()).proyectaSobre(r.vector()));
	}
	
	@Override
	public Punto2D simetrico(Recta2D r) {
		Punto2D p = this.proyectaSobre(r);
		return p.vector().multiply(2.).minus(this.vector()).punto();
	}	
		
	public String toString() {
    	return String.format("(%.2f,%.2f)",this.x(),this.y());
    }
	
	@Override
	public int compareTo(Punto2D p) {
		if(p==null || this.x() ==null || this.y() == null|| p.x() ==null || p.y() == null ){
	           throw new NullPointerException();
	    }
		return this.distanciaAlOrigen().compareTo(p.distanciaAlOrigen());
	}

	//854.400375,1204.159458
	public static void main(String[] args) {
		Punto2D p1 = Punto2D.of(200.00,100.00);
		Punto2D p2 = Punto2D.of(1000.00,400.00);
		System.out.println(p1.distanciaA(p2));
//		System.out.println(p1.cuadrante());
		Double distanciaCentros = Math.sqrt(
				Math.pow(p1.x()-p2.x(),2)+
				Math.pow(p1.y()-p2.y(),2));
		System.out.println(distanciaCentros);
		Double distanciaCentros2 = Math.sqrt(
			    (p1.x()-p2.x())*(p1.x()-p2.x())+
			    (p1.y()-p2.y())*(p1.y()-p2.y()));
		System.out.println(distanciaCentros2);
		System.out.println(distanciaA(p1,p2));
	}


}
