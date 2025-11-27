package ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.graphs.views.SubGraphView;


public class Ejercicio3 {
	
	public static Graph<Investigador,Colaboracion> getSubgraph_EJ3A(Graph <Investigador, Colaboracion> g ) {
		/*1) CREAR UN PREDICADO PARA EL TIPO INVESTIGADOR
		 * 2) CREAR UN PREDICADO PARA EL TIPO COLABORADOR
		 * 3)EXPORTAR EL GRAFO USANDO AMBOS PREDICADOS
		 * 4) DEVOLVER LA LSTA USANDO AMBOS PREDICADOS
		 */
		
		Predicate<Colaboracion> pe = e -> e.getNColaboraciones() > 5;											// "Las colaboraciones más relevantes (número de artículos mayor que 5)"
		Predicate<Investigador> pv = v -> v.getFNacimiento() < 1982 || g.edgesOf(v).stream().anyMatch(pe);		// "Los investigadores nacidos antes de 1982..." y "...o que tengan más de 5 artículos con alguno de sus coautores."
		
		GraphColors.toDot(g,"ficheros/grafos/EJ3A.gv",					// Muestre el grafo configurando su apariencia de forma que se resalten los vértices y las aristas de la vista. 
				 v-> v.toString() + " " + v.getFNacimiento(),			// LO QUE APARECE DENTRO DE CADA VERTICE EN EL DIBUJO DEL GRAFO
				 e-> e.getNColaboraciones().intValue()+"",				// LO QUE APARECE EN LO ALTO DE LA ARISTA EN EL DIBUJO DEL GRAFO
				 v -> GraphColors.colorIf(Color.blue, pv.test(v)), 		// PINTAMOS LOS VERTICES SI CUMPLEN LA CONDICION DEL PREDICADO pv
				 e -> GraphColors.colorIf(Color.blue, pe.test(e)));		// PINTAMOS LAS ARISTAS SI CUMPLEN LA CONDICION DEL PREDICADO pa
		
		return SubGraphView.of(g, pv, pe);
	}
	
	public static Set<Investigador> getMayoresColaboradores_E3B (Graph<Investigador,Colaboracion> g) {
		/*1) CREAR UN COMPARATOR CMP de DE Investigador según el grado (nº aristas)
		 * 2) ORDENAR TODOS LOS vertices descendentemente según CMP
		 * 3) SELECCIONAR LOS CINCO PRIMEROS
		 */
		
		Comparator<Investigador> cmp = Comparator.comparing(v -> g.degreeOf(v));	// Ya que queremos el los 5 investigadores con el mayor numero de colaboradores, es decir, el mayor numero de aristas incidentes
		var ls= g.vertexSet().stream().sorted(cmp.reversed()).limit(5).toList();	// Los ordenamos de mayor a menor y nos quedamos con los 5 mejores que los pasamos a una lista
		
		GraphColors.toDot(g,"ficheros/grafos/EJ3B.gv",
				 v-> v.toString(),											// Etiqueta de vertices
				 e-> e.getNColaboraciones().intValue()+"",					// Etiqueta de aristas
				 v -> GraphColors.colorIf(Color.blue, ls.contains(v)), 		// Coloreado de vertices
				 e -> GraphColors.color(Color.black));						// Coloreado de aristas
		return Set.copyOf(ls);
	}

	public static Map<Investigador,List<Investigador>> getMapListaColabroradores_E3C (Graph<Investigador,Colaboracion> g) {
		/*
		 * 1) CREAR UN COMPARATOR CMP DE LAS ARISTAS SEGUN EL NUMERO DE COLABORACIONES
		 * 2) OBTENER LOS VERTICES DEL GRAFO Y PARA CADA VERTICE OBTENER SUS VECINOS ORDENADOS SEGUN CMP
		 * 3) CREAR EL MAPA DONDE LA CLAVE ES EL VERTICE Y EL VALOR LA LISTA DE VECINOS ORDENADOS
		 * */
		Map<Investigador,List<Investigador>> res = new HashMap<>();                                            //CREAR EL MAP RESULTADO 
		Map<Investigador,List<Colaboracion>> col = new HashMap<>(); 										   //MAP PARA COLOREADO
		Comparator<Colaboracion> cmp = Comparator.comparing(v -> v.getNColaboraciones());                      //CREAR EL COMPARATOR DEL NºCOLABS
		
		for (Investigador i : g.vertexSet()) {                                                                 //RECORRER TODOS LOS VERTICES
			List<Colaboracion> lsCol = new ArrayList<>();                                                      //LISTA DE COLABS POR CADA VERTICE
			List<Investigador> lsIn = new ArrayList<>();                                                       //LISTA DE VERTICES VECINOS
			for (Colaboracion c : g.edgesOf(i)) {                                                              //RECORRER LAS ARISTAS DEL VERTICE Y METERLAS EN LA LISTA DE COLABS
				lsCol.add(c);
			}
			lsCol.sort(cmp.reversed());                                                                                   //ORDENAR COLABS POR CMP
			for (Colaboracion c : lsCol) {                                                                     //TRANSFORMAR LISTA DE COLABS ORDENADA EN LISTA ORDENADA DE INVESTIGADORES
				if (g.getEdgeTarget(c) != i) {
					lsIn.add(g.getEdgeTarget(c));
				}
				if (g.getEdgeSource(c) != i) {
					lsIn.add(g.getEdgeSource(c));
				}
			}
			res.put(i, lsIn);                                                                                  //AÑADIR A RES CADA INVESTIGADOR CON SU LISTA 
			col.put(i, lsCol);
		}
		
		List<Colaboracion> top = new ArrayList<>();
		for (List<Colaboracion> l : col.values()) {
			top.add(l.get(0));
		}
		
		GraphColors.toDot(g,"ficheros/grafos/EJ3C.gv",
				 v-> v.toString(),											// Etiqueta de vertices
				 e-> e.getNColaboraciones().intValue()+"",					// Etiqueta de aristas
				 v -> GraphColors.color(Color.black), 		                // Coloreado de vertices
				 e -> GraphColors.colorIf(Color.black, top.contains(e)));	// Coloreado de aristas
		
        return res;
	}
	
	public static Pair<Investigador,Investigador> getParMasLejano_E3D (Graph<Investigador,Colaboracion> g) {
	   return null;  
		
	}
	
	public static List<Set<Investigador>> getReuniones_E3E (Graph<Investigador,Colaboracion> g) {
		return null;
	}

}