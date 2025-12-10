package us.lsi.geometria;

/**
 * Cuadrante
 *
 * <p>Enumeración que representa los cuatro cuadrantes del plano cartesiano.</p>
 *
 * <p>Los cuadrantes se numeran tradicionalmente en sentido antihorario:
 * <ul>
 *   <li>PRIMER_CUADRANTE: x &gt; 0, y &gt; 0</li>
 *   <li>SEGUNDO_CUADRANTE: x &lt; 0, y &gt; 0</li>
 *   <li>TERCER_CUADRANTE: x &lt; 0, y &lt; 0</li>
 *   <li>CUARTO_CUADRANTE: x &gt; 0, y &lt; 0</li>
 * </ul>
 * </p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 */
public enum Cuadrante {
	/** Primer cuadrante (x &gt; 0, y &gt; 0) */
	PRIMER_CUADRANTE, 
	/** Segundo cuadrante (x &lt; 0, y &gt; 0) */
	SEGUNDO_CUADRANTE, 
	/** Tercer cuadrante (x &lt; 0, y &lt; 0) */
	TERCER_CUADRANTE, 
	/** Cuarto cuadrante (x &gt; 0, y &lt; 0) */
	CUARTO_CUADRANTE
}
