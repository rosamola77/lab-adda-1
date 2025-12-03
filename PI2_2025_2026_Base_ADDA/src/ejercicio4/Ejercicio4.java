package ejercicio4;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;


public class Ejercicio4 {

	public static GraphPath<Interseccion,Calle> getShortestPathBetweenMonument_EJ4A(String startMonument, String endMonument, Graph<Interseccion,Calle> g, String outputFile) {
		Interseccion a = null;
		Interseccion b = null;
		for (Interseccion e : g.vertexSet()) {
			if (e.getNombre().equals(startMonument)) {
				a = e;
			} else if (e.getNombre().equals(endMonument)) {
				b = e;
			}
		}
		for (Calle e : g.edgeSet()) {
			g.setEdgeWeight(e, e.getDuracion());
		}
		var alg = new DijkstraShortestPath<>(g);
		var res = alg.getPath(a, b);
		GraphColors.toDot(g, "ficheros/grafos/" + outputFile,
				 v-> v.toString(),
				 e-> String.valueOf(e.getDuracion()),
				 v -> GraphColors.colorIf(Color.blue, res.getVertexList().contains(v)),
				 e -> GraphColors.colorIf(Color.green, res.getEdgeList().contains(e)));
		return res;
	}
	
	public static GraphPath<Interseccion,Calle> getRecorrido_E4B(Graph <Interseccion, Calle> g) {
		for (Calle e : g.edgeSet()) {
			g.setEdgeWeight(e, e.getEsfuerzo());
		}
		var tsp = new HeldKarpTSP<Interseccion, Calle>();
		var res = tsp.getTour(g);
		GraphColors.toDot(g, "ficheros/grafos/EJ4B.gv",
				 v-> v.toString(),
				 e-> String.valueOf(e.getEsfuerzo()),
				 v -> GraphColors.colorIf(Color.blue, res.getVertexList().contains(v)),
				 e -> GraphColors.colorIf(Color.green, res.getEdgeList().contains(e)));
        return res;
	}
	
	public static Graph<Interseccion,Calle> getRecorridoMaxRelevante_E4C(Set<Calle> cs,Graph <Interseccion, Calle> g, String ftest) {
		g.removeAllEdges(cs);
		var conInspector = new ConnectivityInspector<Interseccion, Calle>(g);
		var conComp = conInspector.connectedSets();
		int count = 0;
		if (conComp.size() == 1) {
			GraphColors.toDot(g, "ficheros/grafos/" + ftest,
					 v-> v.toString(),
					 e-> String.valueOf(e.toString()),
					 v -> GraphColors.colorIf(Color.blue, true),
					 e -> GraphColors.colorIf(Color.green, false));
			return g;
		} else {
			for (Set<Interseccion> l : conComp) {
				boolean hasMonumento = false;
				for (Interseccion i : l) {
					if (i.hasMonumento()) {
						hasMonumento = true;
					}
				}
				if (hasMonumento) {
					count += 1;
				}
			}
			if (count > 1) {
				record Tupla(Set<Interseccion> s, Integer relevancia) {
				}
				List<Tupla> lista = new ArrayList<>();
				for (Set<Interseccion> l : conComp) {
					Integer rel = 0;
					for (Interseccion i : l) {
						if (i.hasMonumento()) {
							rel += i.getRelevancia();
						}
					}
					lista.add(new Tupla(l, rel));
				}
				Comparator<Tupla> cmp = Comparator.comparing(e -> e.relevancia);
				lista.sort(cmp.reversed());
				GraphColors.toDot(g, "ficheros/grafos/" + ftest,
						 v-> v.toString(),
						 e-> String.valueOf(e.toString()),
						 v -> GraphColors.colorIf(Color.blue, lista.get(0).s.contains(v)),
						 e -> GraphColors.colorIf(Color.green, false));
				return g;
			} else {
				GraphColors.toDot(g, "ficheros/grafos/" + ftest,
						 v-> v.toString(),
						 e-> String.valueOf(e.toString()),
						 v -> GraphColors.colorIf(Color.blue, v.hasMonumento()),
						 e -> GraphColors.colorIf(Color.green, false));
				return g;
			}
			
		}
	}
		
}