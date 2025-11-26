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

public class Ejercicio1 {

    record Tupla (List<Integer> camino, Integer prod) {
	}

    public static List<Integer> caminoMaximo(BinaryTree<Integer> tree) {
        return caminoMaximoAux(tree, new ArrayList<>(), 1, new Tupla(new ArrayList<>(), 1)).camino();
    }

    private static Tupla caminoMaximoAux(BinaryTree<Integer> tree, List<Integer> currentPath, Integer currentProd, Tupla max) {
        return switch (tree) {
            case BEmpty() -> max;
            case BLeaf(var lb) -> lb * currentProd > max.prod()
                    ? new Tupla(List2.addLast(currentPath, lb), lb * currentProd)
                    : max;
            case BTree(var lb, var lt, var rt) -> {
                max = caminoMaximoAux(lt, List2.addLast(currentPath, lb), currentProd * lb, max);
                yield caminoMaximoAux(rt, List2.addLast(currentPath, lb), currentProd * lb, max);
            }
        };
    }

    public static List<Integer> caminoMaximo(Tree<Integer> tree) {
        return caminoMaximoAux2(tree, new ArrayList<>(), 1, new Tupla(new ArrayList<>(), 1)).camino();
    }

    private static Tupla caminoMaximoAux2(Tree<Integer> tree, List<Integer> currentPath, Integer currentProd, Tupla max) {
        return switch (tree) {
            case TEmpty() -> max;
            case TLeaf(var lb) -> lb * currentProd > max.prod()
                    ? new Tupla(List2.addLast(currentPath, lb), lb * currentProd)
                    : max;
            case TNary(var lb, var children) -> {
                List<Integer> newPath = List2.addLast(currentPath, lb);
                int newProd = currentProd * lb;

                Tupla currentMax = max;

                for (Tree<Integer> child : children) {
                    currentMax = caminoMaximoAux2(child, newPath, newProd, currentMax);
                }

                yield currentMax;
            }
        };
    }
}