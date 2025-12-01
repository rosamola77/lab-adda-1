package us.lsi.colors;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import us.lsi.common.Files2;
import us.lsi.common.Map2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.ast.Ast;
import us.lsi.tiposrecursivos.ast.Block;
import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Vertex;


public class GraphColors {
	
	public enum Color {
		 green, yellow, red, gray, cyan, orange, magenta, blue, black,  blank
	}
	
	public enum ArrowHead {
		none, normal, dot, inv, crow, tee, vee, diamond, box, curve, icurve
	}
	
	public enum Style {
		dotted, bold, filled, solid, invis, arrowhead
	}
	
	public enum Shape {
		box, polygon, ellipse, point, triangle, doublecircle
	}
	
	/**
	 * @param c color
	 * @return Un Map para ser a�adido en un exportToDot.
	 */
	public static Map<String,Attribute> color(Color c) {
		String cl = c == Color.blank? "" : c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));
		return m;
	}
	
	/**
	 * @param c �ndice del color en el enum Color
	 * @return Un Map para ser a�adido en un exportToDot
	 */
	public static Map<String,Attribute> color(Integer c) {
		return color(Color.values()[c]);
	}
	
	/**
	 * @param test Lista de condiciones booleanas
	 * @param colors Lista de colores correspondientes a cada condici�n
	 * @return Un Map con el color de la primera condici�n verdadera, o negro si ninguna es verdadera
	 */
	public static Map<String,Attribute> colorIf(List<Boolean> test, List<Color> colors) {	
		OptionalInt n = IntStream.range(0, test.size()).filter(i->test.get(i).equals(true)).findFirst();
		Color c = Color.black;
		if(n.isPresent()) c = colors.get(n.getAsInt());
		String cl = c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));		
		return m;
	}
	
	/**
	 * @param yesColor Color a usar si la condici�n es verdadera
	 * @param noColor Color a usar si la condici�n es falsa
	 * @param test Condici�n booleana a evaluar
	 * @return Un Map con el color seg�n el resultado de la condici�n
	 */
	public static Map<String,Attribute> colorIf(Color yesColor, Color noColor, Boolean test) {		
		Color c;
		if(test) c = yesColor;
		else c = noColor;
		String cl = c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));		
		return m;
	}
	
	/**
	 * @param yesColor Color a usar si la condici�n es verdadera
	 * @param test Condici�n booleana a evaluar
	 * @return Un Map con el color si la condici�n es verdadera, o vac�o si es falsa
	 */
	public static Map<String,Attribute> colorIf(Color yesColor, Boolean test) {		
		Map<String,Attribute> m = new HashMap<>();
		if(test) m = Map.of("color", DefaultAttribute.createAttribute(yesColor.toString()));
		return m;
	}
	
	/**
	 * @param label Etiqueta a asignar
	 * @return Un Map con la etiqueta para ser a�adido en un exportToDot, o vac�o si la etiqueta es vac�a
	 */
	public static Map<String, Attribute> label(String label) {
		if(label.equals("")) return new HashMap<>();
		return Map.of("label", DefaultAttribute.createAttribute(label));
	}
	
	/**
	 * @param style Estilo a asignar
	 * @return Un Map con el estilo para ser a�adido en un exportToDot
	 */
	public static Map<String, Attribute> style(Style style) {
		return Map.of("style", DefaultAttribute.createAttribute(style.name()));
	}
	
	/**
	 * @param shape Forma a asignar
	 * @return Un Map con la forma para ser a�adido en un exportToDot
	 */
	public static Map<String, Attribute> shape(Shape shape) {
		return Map.of("shape", DefaultAttribute.createAttribute(shape.name()));
	}
	
	/**
	 * @param style Estilo a asignar si la condici�n es verdadera
	 * @param test Condici�n booleana a evaluar
	 * @return Un Map con el estilo indicado si la condici�n es verdadera, o solid si es falsa
	 */
	public static Map<String, Attribute> styleIf(Style style, Boolean test) {
		if(!test) style = Style.solid;
		return Map.of("style", DefaultAttribute.createAttribute(style.name()));
	}
	
	/**
	 * @param value �ndice del estilo en el enum Style
	 * @return Un Map con el estilo para ser a�adido en un exportToDot
	 */
	public static Map<String,Attribute> style(Integer value) {		
		return Map.of("style", DefaultAttribute.createAttribute(Style.values()[value].toString()));
	}
	
	/**
	 * @param shape Forma a asignar si la condici�n es verdadera
	 * @param test Condici�n booleana a evaluar
	 * @return Un Map con la forma indicada si la condici�n es verdadera, o ellipse si es falsa
	 */
	public static Map<String, Attribute> shapeIf(Shape shape, Boolean test) {
		if(!test) shape = Shape.ellipse;
		return Map.of("shape", DefaultAttribute.createAttribute(shape.name()));
	}
	
	/**
	 * @param value �ndice de la forma en el enum Shape
	 * @return Un Map con la forma para ser a�adido en un exportToDot
	 */
	public static Map<String,Attribute> shape(Integer value) {		
		return Map.of("shape", DefaultAttribute.createAttribute(Shape.values()[value].toString()));
	}
	
	/**
	 * @param head Tipo de punta de flecha a asignar
	 * @return Un Map con el tipo de punta de flecha para ser a�adido en un exportToDot
	 */
	public static Map<String,Attribute> arrowHead(ArrowHead head) {		
		return Map.of("arrowhead", DefaultAttribute.createAttribute(head.name()));
	}
	
	/**
	 * @param properties Mapas de propiedades a combinar
	 * @return Un Map que combina todas las propiedades proporcionadas
	 */
	@SafeVarargs
	public static Map<String, Attribute> all(Map<String, Attribute>... properties){
		final Map<String, Attribute> r = new HashMap<>();
		for(Map<String, Attribute> f:properties)
			r.putAll(f);
		return r;
	}
	
	/**
	 * @param <V> El tipo de los elementos del �rbol
	 * @param tree �rbol binario a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 */
	public static <V> void toDot(BinaryTree<V> tree, String file) {
		SimpleDirectedGraph<BinaryTree<V>, DefaultEdge> g2 = tree.toGraph();
		GraphColors.toDot(g2,file,
			v->v.isEmpty()?"_":v.optionalLabel().get().toString(),
			e->"");
	}
	
	/**
	 * @param <V> El tipo de los elementos del �rbol
	 * @param tree �rbol n-ario a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 */
	public static <V> void toDot(Tree<V> tree, String file) {
		SimpleDirectedGraph<Tree<V>, DefaultEdge> g2 = tree.toGraph();
		GraphColors.toDot(g2,file,
			v->v.isEmpty()?"_":v.optionalLabel().get().toString(),
			e->"");
	}
	
	/**
	 * @param <V> El tipo de los elementos del AST
	 * @param ast �rbol de sintaxis abstracta a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 */
	public static <V> void toDot(Ast ast, String file) {
		SimpleDirectedGraph<Vertex, DefaultEdge> g2 = ast.toGraph();
		GraphColors.toDot(g2,file,
			v->v.label(),
			e->"",
			v->v.styleAndShape(),
			e->Map.of());
	}
	
	/**
	 * @param <V> El tipo de los elementos del bloque
	 * @param block Bloque de c�digo a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 */
	public static <V> void toDot(Block block, String file) {
		SimpleDirectedGraph<Vertex, DefaultEdge> g2 = block.toGraph();
		GraphColors.toDot(g2,file,
			v->v.label(),
			e->"");
	}
	
	/**
	 * @param <V> El tipo de los elementos de la expresi�n
	 * @param exp Expresi�n a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 */
	public static <V> void toDot(Exp exp, String file) {
		SimpleDirectedGraph<Vertex, DefaultEdge> g2 = exp.toGraph();
		GraphColors.toDot(g2,file,
			v->v.label(),
			e->"");
	}

	/**
	 * @param <V> El tipo de los v�rtices del grafo
	 * @param <E> El tipo de las aristas del grafo
	 * @param graph Grafo a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 */
	public static <V,E> void toDot(Graph<V,E> graph, String file) {		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		de.setVertexAttributeProvider(v->GraphColors.label(v.toString()));
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
	/**
	 * @param <V> El tipo de los v�rtices del grafo
	 * @param <E> El tipo de las aristas del grafo
	 * @param graph Grafo a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 * @param vertexLabel Funci�n que proporciona la etiqueta de cada v�rtice
	 */
	public static <V,E> void toDot(Graph<V,E> graph, String file, Function<V,String> vertexLabel) {	
		DOTExporter<V,E> de = new DOTExporter<V,E>();	
		de.setVertexAttributeProvider(v->GraphColors.label(vertexLabel.apply(v)));
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
	/**
	 * @param <V> El tipo de los v�rtices del grafo
	 * @param <E> El tipo de las aristas del grafo
	 * @param graph Grafo a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 * @param vertexLabel Funci�n que proporciona la etiqueta de cada v�rtice
	 * @param edgeLabel Funci�n que proporciona la etiqueta de cada arista
	 */
	public static <V,E> void toDot(Graph<V,E> graph, String file, 
			Function<V,String> vertexLabel,
			Function<E,String> edgeLabel) {		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		de.setVertexAttributeProvider(v->GraphColors.label(vertexLabel.apply(v)));
		de.setEdgeAttributeProvider(e->GraphColors.label(edgeLabel.apply(e)));		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
		
	
	/**
	 * @param <V> El tipo de los v�rtices del grafo
	 * @param <E> El tipo de las aristas del grafo
	 * @param graph Grafo a exportar
	 * @param file Ruta del fichero de salida en formato DOT
	 * @param vertexLabel Funci�n que proporciona la etiqueta de cada v�rtice
	 * @param edgeLabel Funci�n que proporciona la etiqueta de cada arista
	 * @param vertexAttribute Funci�n que proporciona atributos adicionales para cada v�rtice
	 * @param edgeAttribute Funci�n que proporciona atributos adicionales para cada arista
	 */
	public static <V,E> void toDot(Graph<V,E> graph, String file, 
			Function<V,String> vertexLabel,
			Function<E,String> edgeLabel,
			Function<V,Map<String,Attribute>> vertexAttribute,
			Function<E,Map<String,Attribute>> edgeAttribute) {
		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		
		Function<V,Map<String,Attribute>> m1 = 
			v->Map2.merge(GraphColors.label(vertexLabel.apply(v)),vertexAttribute.apply(v));
		Function<E,Map<String,Attribute>> m2 = 
			e->Map2.merge(GraphColors.label(edgeLabel.apply(e)),edgeAttribute.apply(e));
		
		de.setVertexAttributeProvider(m1);
		de.setEdgeAttributeProvider(m2);
		
		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
}
