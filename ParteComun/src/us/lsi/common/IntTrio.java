package us.lsi.common;

/**
 * <p>Representa un trio de valores Integer.</p>
 * 
 * <p>Util para representar tres valores enteros relacionados,
 * como coordenadas en 3D o tres parametros asociados.</p>
 * 
 * @author Miguel Toro
 *
 * @param first Primer componente
 * @param second Segundo componente
 * @param third Tercer componente
 */
public record IntTrio(Integer first, Integer second, Integer third) {

	/**
	 * Crea un IntTrio a partir de tres valores.
	 * 
	 * @param first Primer valor
	 * @param second Segundo valor
	 * @param third Tercer valor
	 * @return Un nuevo IntTrio
	 */
	public static IntTrio of(Integer first, Integer second, Integer third) {
		return new IntTrio(first, second, third);
	}
	
	/**
	 * Parsea un IntTrio desde una cadena.
	 * 
	 * @param s Cadena con formato "(a,b,c)"
	 * @return Un nuevo IntTrio
	 */
	public static IntTrio parse(String s) {
		String[] partes = s.split("[(),]");
		return new IntTrio(Integer.parseInt(partes[0].trim()), 
				Integer.parseInt(partes[1].trim()),
				Integer.parseInt(partes[2].trim()));
	}

	/**
	 * Representacion en cadena del trio.
	 * 
	 * @return Cadena con formato "(first,second,third)"
	 */
	@Override
	public String toString() {
		return String.format("(%d,%d,%d)",this.first(),this.second(),this.third());
	}
	
}
