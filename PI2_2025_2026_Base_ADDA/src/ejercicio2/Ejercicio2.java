package ejercicio2;

import java.util.stream.IntStream;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BLeaf;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;


public class Ejercicio2 {
	
	record Tupla(Boolean dePutaMadre, Long nv) {          // dePutaMadre = da true, es decir, el numero total de vocales del nodo izquierdo es igual al derecho; nv = numero vocales
	
	}
	
	private static Long vocales(String s) {						// Método para contar vocales, sacado de google
		String s1 = "aeiou", s2 = s.toLowerCase();
		return IntStream.range(0, s.length()).filter(i -> s1.contains(s2.charAt(i) + "")).count();
	}
	
	// Solucion para Árbol Binario
	public static Boolean solucion_recursiva(BinaryTree<String> tree) {
		return solucion_binaryAux(tree).dePutaMadre();
	}
	
	private static Tupla solucion_binaryAux(BinaryTree<String> tree) {
		return switch (tree) {
		
		// Caso 1: Nodo vacío (Caso Base)
		case BEmpty() -> new Tupla(true, 0L);
		
		// Caso 2: Hoja (Nodo sin hijos) (Caso Base)
		case BLeaf(var lb) -> new Tupla(true, vocales(lb));
		
		// Caso 3: Nodo Interno (Tiene 2 hijos, uno a la izquierda y otro a la derecha) (Caso Recursivo)
		case BTree(var lb, var lt, var rt) -> {
			var t1 = solucion_binaryAux(lt);
			if (t1.dePutaMadre().equals(true)) {
				var t2 = solucion_binaryAux(rt);
				yield new Tupla(t2.dePutaMadre() && t1.nv() == t2.nv(), 2*t1.nv() + vocales(lb));  // 2* porque tiene 2 hijos con el mismo numero de vocales, y le suma las vocales del nodo padre
			} else {
				yield new Tupla(false, null);   // Porque si ya el arbol izquierdo es dalso, me da igual el derecho, este es falso siempre.
			}
		}
		
		};
	}
	
	

	// Solucion para Árbol N-ario
	public static Boolean solucion_recursiva(Tree<String> tree) {
		return solucion_recursivaAux(tree).dePutaMadre();
	}
	
	private static Tupla solucion_recursivaAux(Tree<String> tree) {
		return switch (tree) {
			// Caso 1: Nodo vacío (Caso Base)
			case TEmpty() -> new Tupla(true, 0L);
		
			// Caso 2: Hoja (Nodo sin hijos) (Caso Base)
			case TLeaf(var lb) -> new Tupla(true, vocales(lb));
		
			// Caso 3: Nodo Interno (Tiene 2 hijos, uno a la izquierda y otro a la derecha) (Caso Recursivo)
			case TNary(var lb, var children) -> {
				var t1 = solucion_recursivaAux(children.get(0));
			
				for (int i = 1; i < children.size() && t1.dePutaMadre(); i ++) {
					var t2 = solucion_recursivaAux(children.get(i));
					t1 = new Tupla(t2.dePutaMadre() && t1.nv().equals(t2.nv()), t1.nv());
				}
				yield new Tupla(t1.dePutaMadre(), children.size() * t1.nv() + vocales(lb));
			}
		};
	}
}