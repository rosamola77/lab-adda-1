package ejercicio1;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BLeaf;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;
import us.lsi.tiposrecursivos.Tree;
import utilidades.Tupla;

/**
 * Ejercicio1
 *
 * <p>Proporciona utilidades para calcular el camino desde la raíz hasta una hoja
 * que maximice el producto de los valores almacenados en los nodos. Se soportan
 * árboles binarios ({@code BinaryTree<Integer>}) y árboles n-arios
 * ({@code Tree<Integer>}).</p>
 *
 * <p>Los métodos están pensados para no modificar las estructuras originales y
 * devuelven listas con los valores del camino raíz->hoja que maximizan el producto.</p>
 *
 * @author Álvaro Rosa
 * @version 1.0
 * @since 1.0
 * @see us.lsi.tiposrecursivos.BinaryTree
 * @see us.lsi.tiposrecursivos.Tree
 */
public class Ejercicio1 {

    /**
     * Devuelve el camino raíz->hoja en un árbol binario cuyo producto de valores
     * es máximo.
     *
     * <p>Ejemplo de uso:
     * {@code
     * BinaryTree<Integer> tree = // construir árbol;
     * List<Integer> camino = Ejercicio1.caminoMaximo(tree);
     * System.out.println("Camino máximo: " + camino);
     * }</p>
     *
     * @param tree árbol binario de {@code Integer}; no debe ser {@code null}
     * @return lista de {@code Integer} con los valores del camino raíz->hoja que maximiza el producto; devuelve una lista vacía si el árbol está vacío
     * @throws NullPointerException si {@code tree} es {@code null}
     * @see #caminoMaximoAux(BinaryTree, List, Integer, Tupla)
     * @implSpec La implementación recorre el árbol visitando cada nodo exactamente una vez, por lo que la complejidad es O(n) donde n es el número de nodos.
     */
    public static List<Integer> caminoMaximo(BinaryTree<Integer> tree) {
    	Tupla<List<Integer>, Integer> t = new Tupla<List<Integer>, Integer>(new ArrayList<>(), 1);
        return caminoMaximoAux(tree, new ArrayList<>(), 1, t).e1();
    }

    /**
     * Implementación auxiliar recursiva para calcular el camino de producto máximo
     * en un árbol binario.
     *
     * <p>Este método evalúa los tres casos del tipo sellado {@code BinaryTree}:
     * vacío, hoja y árbol con subárboles izquierdo y derecho. Mantiene el camino
     * acumulado y el producto parcial.</p>
     *
     * @param tree subárbol actual (instancia de {@code BinaryTree<Integer>})
     * @param currentPath camino acumulado desde la raíz hasta el nodo actual (sin incluir hijos aún)
     * @param currentProd producto acumulado de los valores en {@code currentPath}
     * @param max tupla que contiene el mejor camino y producto encontrado hasta ahora; se actualizará si se encuentra un camino mejor
     * @return tupla con el mejor camino y su producto tras procesar {@code tree}
     * @throws NullPointerException si alguno de los parámetros requeridos por la lógica es {@code null}
     * @see #caminoMaximo(BinaryTree)
     * @implNote Método privado usado internamente; no modifica el árbol original.
     */
    private static Tupla<List<Integer>, Integer> caminoMaximoAux(BinaryTree<Integer> tree, List<Integer> currentPath, Integer currentProd, Tupla<List<Integer>, Integer> max) {
        return switch (tree) {
            case BEmpty() -> max;
            case BLeaf(var lb) -> lb * currentProd > max.e2()
                    ? new Tupla<List<Integer>, Integer>(List2.addLast(currentPath, lb), lb * currentProd)
                    : max;
            case BTree(var lb, var lt, var rt) -> {
                max = caminoMaximoAux(lt, List2.addLast(currentPath, lb), currentProd * lb, max);
                yield caminoMaximoAux(rt, List2.addLast(currentPath, lb), currentProd * lb, max);
            }
        };
    }

    /**
     * Devuelve el camino raíz->hoja en un árbol n-ario cuyo producto de valores
     * es máximo.
     *
     * <p>Soporta la jerarquía sellada {@code Tree<Integer>} con casos {@code TEmpty},
     * {@code TLeaf} y {@code TNary}.</p>
     *
     * <p>Ejemplo de uso:
     * {@code
     * Tree<Integer> tree = // construir árbol n-ario;
     * List<Integer> camino = Ejercicio1.caminoMaximo(tree);
     * System.out.println("Camino máximo (n-ario): " + camino);
     * }</p>
     *
     * @param tree árbol n-ario de {@code Integer}; no debe ser {@code null}
     * @return lista de {@code Integer} con el camino raíz->hoja que maximiza el producto; lista vacía si el árbol está vacío
     * @throws NullPointerException si {@code tree} es {@code null}
     * @see #caminoMaximoAux2(Tree, List, Integer, Tupla)
     * @implSpec La implementación visita cada nodo una vez; complejidad O(n) en número de nodos.
     */
    public static List<Integer> caminoMaximo(Tree<Integer> tree) {
        return caminoMaximoAux2(tree, new ArrayList<>(), 1, new Tupla<List<Integer>, Integer>(new ArrayList<>(), 1)).e1();
    }

    /**
     * Implementación auxiliar recursiva para {@link #caminoMaximo(Tree)}.
     *
     * <p>Para el caso {@code TNary} construye el nuevo camino y producto parcial,
     * y recorre cada hijo actualizando el máximo encontrado.</p>
     *
     * @param tree subárbol actual (instancia de {@code Tree<Integer>})
     * @param currentPath camino acumulado hasta el nodo actual
     * @param currentProd producto acumulado de la ruta actual
     * @param max tupla con el mejor camino y producto encontrado hasta el momento
     * @return tupla que contiene el mejor camino y producto tras procesar {@code tree}
     * @throws NullPointerException si alguno de los parámetros necesarios es {@code null}
     * @see #caminoMaximo(Tree)
     * @implNote Método privado usado internamente; itera sobre la colección de hijos de {@code TNary}.
     */
    private static Tupla<List<Integer>, Integer> caminoMaximoAux2(Tree<Integer> tree, List<Integer> currentPath, Integer currentProd, Tupla<List<Integer>, Integer> max) {
        return switch (tree) {
            case TEmpty() -> max;
            case TLeaf(var lb) -> lb * currentProd > max.e2()
                    ? new Tupla<List<Integer>, Integer>(List2.addLast(currentPath, lb), lb * currentProd)
                    : max;
            case TNary(var lb, var children) -> {
                List<Integer> newPath = List2.addLast(currentPath, lb);
                int newProd = currentProd * lb;

                Tupla<List<Integer>, Integer> currentMax = max;

                for (Tree<Integer> child : children) {
                    currentMax = caminoMaximoAux2(child, newPath, newProd, currentMax);
                }

                yield currentMax;
            }
        };
    }
}
