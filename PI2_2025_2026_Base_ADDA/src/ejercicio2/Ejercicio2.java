package ejercicio2;

import java.util.stream.IntStream;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;
import utilidades.Tupla;
import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BLeaf;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;

/**
 * Ejercicio2
 *
 * <p>Utilidades para verificar propiedades sobre árboles recursivos
 * etiquetados con {@code String}. En particular, se comprueba que:
 * <ul>
 *   <li>En un árbol binario, para cada nodo interno el número total de vocales
 *       del subárbol izquierdo es igual al del subárbol derecho.</li>
 *   <li>En un árbol n-ario, para cada nodo interno todos los hijos tienen el
 *       mismo número total de vocales.</li>
 * </ul>
 * </p>
 *
 * <p>Los métodos públicos actúan como puntos de entrada y delegan en auxiliares
 * recursivos que devuelven información combinada (validez y número de vocales).</p>
 *
 * @author Álvaro Rosa y Adrián Jiménez
 * @version 1.0
 * @since 1.0
 * @see us.lsi.tiposrecursivos.BinaryTree
 * @see us.lsi.tiposrecursivos.Tree
 */
public class Ejercicio2 {
	
	/**
	 * Cuenta el número de vocales en la cadena dada.
	 *
	 * <p>Se consideran vocales las letras {@code a, e, i, o, u} tanto en mayúsculas
	 * como en minúsculas.</p>
	 *
	 * @param s la cadena de entrada; no puede ser {@code null}
	 * @return número de vocales presentes en {@code s} como {@code Long}
	 * @throws NullPointerException si {@code s} es {@code null}
	 *
	 * Ejemplo de uso:
	 * {@code
	 * long c = Ejercicio2.vocales("Hola"); // c == 2
	 * }
	 *
	 * @implNote Implementación basada en {@link IntStream} para claridad y concisión.
	 */
	private static Long vocales(String s) {
		if (s == null) throw new NullPointerException("La cadena de entrada no puede ser null");
		String s1 = "aeiou", s2 = s.toLowerCase();
		return IntStream.range(0, s.length()).filter(i -> s1.contains(s2.charAt(i) + "")).count();
	}
	
	/**
	 * Comprueba la propiedad en un árbol binario:
	 * en todo nodo interno, el número total de vocales del subárbol izquierdo
	 * es igual al del subárbol derecho.
	 *
	 * @param tree árbol binario a verificar; no puede ser {@code null}
	 * @return {@code true} si la propiedad se cumple en todo el árbol; {@code false} en caso contrario
	 * @throws NullPointerException si {@code tree} es {@code null}
	 * @see #solucion_binaryAux(BinaryTree)
	 *
	 * Ejemplo de uso:
	 * {@code
	 * BinaryTree<String> bt = ...;
	 * Boolean ok = Ejercicio2.solucion_recursiva(bt);
	 * }
	 */
	public static Boolean solucion_recursiva(BinaryTree<String> tree) {
		if (tree == null) throw new NullPointerException("El árbol no puede ser null");
		return solucion_binaryAux(tree).e1();
	}
	
	/**
	 * Auxiliar recursivo para árboles binarios.
	 *
	 * <p>Devuelve una {@link Tupla} que contiene:
	 * <ul>
	 *   <li>si el subárbol satisface la propiedad</li>
	 *   <li>el número total de vocales del subárbol</li>
	 * </ul>
	 * </p>
	 *
	 * @param tree subárbol binario; no puede ser {@code null}
	 * @return {@link Tupla} con el resultado local y el número total de vocales
	 * @throws NullPointerException si {@code tree} es {@code null}
	 *
	 * Ejemplo de uso (interno):
	 * {@code
	 * Tupla t = Ejercicio2.solucion_binaryAux(subtree);
	 * boolean ok = t.valido();
	 * long nv = t.nv();
	 * }
	 */
	private static Tupla<Boolean, Long> solucion_binaryAux(BinaryTree<String> tree) {
		if (tree == null) throw new NullPointerException("El subárbol no puede ser null");
		return switch (tree) {
		
		// Caso 1: Nodo vacío (Caso Base)
		case BEmpty() -> new Tupla<Boolean, Long>(true, 0L);
		
		// Caso 2: Hoja (Nodo sin hijos) (Caso Base)
		case BLeaf(var lb) -> new Tupla<Boolean, Long>(true, vocales(lb));
		
		// Caso 3: Nodo Interno (Tiene 2 hijos) (Caso Recursivo)
		case BTree(var lb, var lt, var rt) -> {
			var t1 = solucion_binaryAux(lt);
			if (t1.e1().equals(true)) {
				var t2 = solucion_binaryAux(rt);
				// Para el nodo padre, si ambos hijos son válidos y tienen el mismo nv,
				// entonces el subárbol es válido y su nv total es la suma de los dos hijos más las vocales del label.
				yield new Tupla<Boolean, Long>(t2.e1() && t1.e2().equals(t2.e2()), t1.e2() + t2.e2() + vocales(lb));
			} else {
				// Si el subárbol izquierdo no cumple, el resultado es inválido.
				yield new Tupla<Boolean, Long>(false, null);
			}
		}
		
		};
	}
	
	/**
	 * Comprueba la propiedad en un árbol n-ario:
	 * en todo nodo interno, todos los hijos tienen el mismo número total de vocales.
	 *
	 * @param tree árbol n-ario a verificar; no puede ser {@code null}
	 * @return {@code true} si la propiedad se cumple en todo el árbol; {@code false} en caso contrario
	 * @throws NullPointerException si {@code tree} es {@code null}
	 * @see #solucion_recursivaAux(Tree)
	 *
	 * Ejemplo de uso:
	 * {@code
	 * Tree<String> tn = ...;
	 * Boolean ok = Ejercicio2.solucion_recursiva(tn);
	 * }
	 */
	public static Boolean solucion_recursiva(Tree<String> tree) {
		if (tree == null) throw new NullPointerException("El árbol no puede ser null");
		return solucion_recursivaAux(tree).e1();
	}
	
	/**
	 * Auxiliar recursivo para árboles n-arios.
	 *
	 * <p>Devuelve una {@link Tupla} con:
	 * <ul>
	 *   <li>la validez del subárbol (todos los hijos, recursivamente, cumplen la igualdad de vocales),</li>
	 *   <li>el número total de vocales del subárbol.</li>
	 * </ul>
	 * Para un nodo interno {@code TNary(label, children)} se calcula:
	 * {@code total = children.size() * nv_hijo + vocales(label)} (si todos los hijos tienen {@code nv_hijo} iguales).
	 * </p>
	 *
	 * @param tree subárbol n-ario; no puede ser {@code null}
	 * @return {@link Tupla} con la validez y el número total de vocales del subárbol
	 * @throws NullPointerException si {@code tree} es {@code null}
	 *
	 * Ejemplo de uso (interno):
	 * {@code
	 * Tupla t = Ejercicio2.solucion_recursivaAux(subTreeNary);
	 * boolean ok = t.valido();
	 * long nv = t.nv();
	 * }
	 */
	private static Tupla<Boolean, Long> solucion_recursivaAux(Tree<String> tree) {
		if (tree == null) throw new NullPointerException("El subárbol no puede ser null");
		return switch (tree) {
			// Caso 1: Nodo vacío (Caso Base)
			case TEmpty() -> new Tupla<Boolean, Long>(true, 0L);
		
			// Caso 2: Hoja (Nodo sin hijos) (Caso Base)
			case TLeaf(var lb) -> new Tupla<Boolean, Long>(true, vocales(lb));
		
			// Caso 3: Nodo Interno n-ario (Caso Recursivo)
			case TNary(var lb, var children) -> {
				// Se asume que la lista de hijos no está vacía para este caso.
				var t1 = solucion_recursivaAux(children.get(0));
				
				// Comparar recursivamente con los demás hijos mientras se mantenga la validez.
				for (int i = 1; i < children.size() && t1.e1(); i++) {
					var t2 = solucion_recursivaAux(children.get(i));
					t1 = new Tupla<Boolean, Long>(t2.e1() && t1.e2().equals(t2.e2()), t1.e2());
				}
				// Si todos los hijos son válidos y tienen el mismo nv, calcular el total.
				yield new Tupla<Boolean, Long>(t1.e1(), children.size() * t1.e2() + vocales(lb));
			}
		};
	}
}