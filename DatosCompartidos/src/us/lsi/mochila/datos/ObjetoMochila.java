package us.lsi.mochila.datos;

/**
 * ObjetoMochila
 *
 * <p>Esta clase implementa el tipo ObjetoMochila, que representa un objeto
 * disponible para incluir en una mochila en el problema de la mochila.</p>
 *
 * <p>Las propiedades de estos objetos son:</p>
 * <ul>
 *   <li>Código: identificador único del objeto</li>
 *   <li>Valor: beneficio que aporta el objeto</li>
 *   <li>Peso: peso del objeto</li>
 *   <li>Número máximo de unidades: cantidad máxima disponible</li>
 * </ul>
 *
 * <p>Los objetos son comparables por su ratio valor/peso, lo que permite
 * ordenarlos por eficiencia.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * ObjetoMochila obj = ObjetoMochila.of(100, 10, 3);
 * Double ratio = obj.ratioValorPeso();
 * }</p>
 *
 * @param codigo identificador único del objeto
 * @param valor beneficio que aporta el objeto
 * @param peso peso del objeto
 * @param numMaxDeUnidades cantidad máxima disponible de este objeto
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see DatosMochila
 */
public record ObjetoMochila(Integer codigo,Integer valor,Integer peso,Integer numMaxDeUnidades)
	implements Comparable<ObjetoMochila>{
	
	/**
	 * Crea un objeto de mochila con los parámetros especificados.
	 *
	 * <p>El código se asigna automáticamente de forma secuencial.</p>
	 *
	 * @param valor beneficio del objeto
	 * @param peso peso del objeto
	 * @param numMaxDeUnidades cantidad máxima disponible
	 * @return una nueva instancia de {@code ObjetoMochila}
	 */
	public static ObjetoMochila of(Integer valor,Integer peso, Integer numMaxDeUnidades) {
		Integer codigo = nCodigo;
		nCodigo++;
		return new ObjetoMochila(codigo,valor,peso, numMaxDeUnidades);
	}
	
	/** Contador estático para asignar códigos únicos. */
	private static Integer nCodigo = 0;
	
	/**
	 * Crea un objeto de mochila a partir de una línea de texto.
	 *
	 * <p>El formato esperado es: {@code valor peso numMaxDeUnidades}
	 * separados por espacios o comas.</p>
	 *
	 * @param s línea de texto con los datos del objeto
	 * @return una nueva instancia de {@code ObjetoMochila}
	 * @throws IllegalArgumentException si el formato no es correcto
	 */
	public static ObjetoMochila parse (String s){		
		String[] v = s.split("[ ,]");
		Integer ne = v.length;
		if(ne != 3) throw new IllegalArgumentException("Formato no adecuado en l�nea  "+s);	
		Integer valor = Integer.parseInt(v[0].trim());
		Integer peso = Integer.parseInt(v[1].trim());
		Integer numMaxDeUnidades = Integer.parseInt(v[2]);
		return ObjetoMochila.of(valor,peso,numMaxDeUnidades);
	}	
		
	/**
	 * Calcula el ratio valor/peso del objeto.
	 *
	 * <p>Este ratio indica la eficiencia del objeto: cuanto mayor,
	 * más beneficio aporta por unidad de peso.</p>
	 *
	 * @return el ratio valor/peso como {@code Double}
	 */
	public Double ratioValorPeso() {
		return ((double)valor)/peso;
	}
	
	/**
	 * Devuelve una representación en cadena del objeto.
	 *
	 * @return cadena con formato {@code (valor,peso,numMaxUnidades,ratio)}
	 */
	@Override
	public String toString() {
		return String.format("(%d,%d,%d,%.2f)",
				valor,peso,numMaxDeUnidades,ratioValorPeso());
	}

	/**
	 * Compara este objeto con otro por ratio valor/peso.
	 *
	 * @param o el objeto con el que comparar
	 * @return un valor negativo, cero o positivo según este objeto tenga
	 *         menor, igual o mayor ratio que el especificado
	 */
	@Override
	public int compareTo(ObjetoMochila o) {
		return this.ratioValorPeso().compareTo(o.ratioValorPeso());
	}
	
}
