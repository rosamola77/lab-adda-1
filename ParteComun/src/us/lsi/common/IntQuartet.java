package us.lsi.common;

/**
 * <p>Representa un cuarteto de valores Integer.</p>
 * 
 * <p>Util para representar cuatro valores enteros relacionados,
 * como coordenadas en 4D o cuatro parametros asociados.</p>
 * 
 * @author Miguel Toro
 *
 * @param first Primer componente
 * @param second Segundo componente
 * @param third Tercer componente
 * @param fourth Cuarto componente
 */
public record IntQuartet(Integer first, Integer second, Integer third, Integer fourth) {
	
	/**
	 * Crea un IntQuartet a partir de cuatro valores.
	 * 
	 * @param first Primer valor
	 * @param second Segundo valor
	 * @param third Tercer valor
	 * @param fourth Cuarto valor
	 * @return Un nuevo IntQuartet
	 */
	public static IntQuartet of(Integer first, Integer second, Integer third, Integer fourth) {
		return new IntQuartet(first,second,third,fourth);
	}
	
	/**
	 * Parsea un IntQuartet desde una cadena.
	 * 
	 * @param s Cadena con formato "(a,b,c,d)"
	 * @return Un nuevo IntQuartet
	 */
	public static IntQuartet parse(String s) {
		String[] partes = s.split("[(),]");
		return new IntQuartet(Integer.parseInt(partes[0].trim()), 
				Integer.parseInt(partes[1].trim()),
				Integer.parseInt(partes[2].trim()),
				Integer.parseInt(partes[3].trim()));
	}

	/**
	 * Representacion en cadena del cuarteto.
	 * 
	 * @return Cadena con formato "(first,second,third,fourth)"
	 */
	@Override
	public String toString() {
		return String.format("(%d,%d,%d,%d)",this.first(),this.second(),this.third(),this.fourth());
	}

}
