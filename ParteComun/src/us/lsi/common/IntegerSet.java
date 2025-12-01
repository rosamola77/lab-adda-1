package us.lsi.common;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.streams.Collectors2;
import us.lsi.streams.Stream2;

/**
 * <p>Un conjunto eficiente de enteros basado en BitSet.</p>
 * 
 * <p>Implementa la interfaz Set para enteros, utilizando internamente
 * un BitSet para almacenar los elementos de forma compacta y eficiente.
 * Permite operaciones de conjuntos como union, interseccion y diferencia.</p>
 * 
 * @author Miguel Toro
 */
public class IntegerSet implements Set<Integer> {
	
	/**
	 * Crea un IntegerSet vacio con limite inferior y numero de digitos especificados.
	 * 
	 * @param infLimit Limite inferior del conjunto de enteros
	 * @param numDigits Numero de digitos usados para representar el conjunto
	 * @return Un nuevo IntegerSet vacio
	 */
	public static IntegerSet empty(Integer infLimit, Integer numDigits) {
		return new IntegerSet(infLimit, numDigits);
	}
	
	/**
	 * Crea un IntegerSet vacio con valores por defecto.
	 * 
	 * @return Un nuevo IntegerSet vacio
	 */
	public static IntegerSet empty() {
		return IntegerSet.empty(0, 10);
	}
	
	/**
	 * Crea un IntegerSet con los elementos especificados.
	 * 
	 * @param elems Elementos a incluir
	 * @return Un nuevo IntegerSet con los elementos
	 */
	public static IntegerSet of(Integer... elems) {
		IntegerSet r = IntegerSet.empty();
		r.addAll(Arrays.asList(elems));
		return r;
	}
	
	/**
	 * Crea un IntegerSet a partir de una coleccion.
	 * 
	 * @param elems Coleccion de elementos
	 * @return Un nuevo IntegerSet con los elementos
	 */
	public static IntegerSet of(Collection<Integer> elems) {
		IntegerSet r = IntegerSet.empty();
		for(Integer e:elems) {
			r = r.addF(e);
		}
		return r;
	}
	
	/**
	 * Crea un IntegerSet con el rango [a, b).
	 * 
	 * @param a Limite inferior (incluido)
	 * @param b Limite superior (excluido)
	 * @return Un nuevo IntegerSet con el rango
	 */
	public static IntegerSet range(Integer a, Integer b) {
		IntegerSet r = IntegerSet.empty();
		IntStream.range(a,b).boxed().toList();
		r.addAll(IntStream.range(a,b).boxed().toList());
		return r;
	}
	
	/**
	 * Crea un IntegerSet con el rango [a, b) con paso c.
	 * 
	 * @param a Limite inferior (incluido)
	 * @param b Limite superior (excluido)
	 * @param c Paso
	 * @return Un nuevo IntegerSet con el rango
	 */
	public static IntegerSet range(Integer a, Integer b, Integer c){		
		return Stream2.range(a, b, c).boxed()
			 .collect(Collectors2.toIntegerSet());
	}	
	
	/**
	 * Parsea un IntegerSet desde una cadena.
	 * 
	 * @param s Cadena con los elementos
	 * @param sep Separadores a usar
	 * @return Un nuevo IntegerSet
	 */
	public static IntegerSet parse(String s, String sep) {
		IntegerSet r = IntegerSet.empty();
		Arrays.stream(s.split("[" + sep + "]"))
				.filter(e -> e != null && e.length() > 0)
				.map(e -> Integer.parseInt(e.trim()))
				.forEach(e->r.add(e));
		return r;
	}
	
	/**
	 * Parsea un IntegerSet desde un array de tokens.
	 * 
	 * @param tokens Array de cadenas con los valores
	 * @return Un nuevo IntegerSet
	 */
	public static IntegerSet parse(String[] tokens) {
		IntegerSet r = IntegerSet.empty();
		Arrays.stream(tokens).filter(e -> e != null && e.length() > 0)
				.map(e -> Integer.parseInt(e.trim()))
				.forEach(e -> r.add(e));
		return r;
	}
	
	/**
	 * Crea una copia de un IntegerSet.
	 * 
	 * @param s Conjunto a copiar
	 * @return Una nueva copia del conjunto
	 */
	public static IntegerSet copy(IntegerSet s) {
		return new IntegerSet(s);
	}
	
	/** Limite inferior del rango de enteros */
	private final Integer infLimit;
	/** BitSet interno para almacenar los elementos */
	private final BitSet bits;

	private IntegerSet(Integer infLimit, Integer numDigits) {
		super();
		this.infLimit = infLimit;
		this.bits = new BitSet(numDigits);
		this.bits.clear();
	}

	private IntegerSet(IntegerSet s) {
		super();
		this.infLimit = s.infLimit;
		this.bits = s.bits.get(0, s.bits.length());
	}
	
	public IntegerSet(BitSet bits, Integer infLimit) {
		this.infLimit = infLimit;
		this.bits = (BitSet) bits.clone();
	}

	public IntegerSet copy() {
		return new IntegerSet(this);
	}

	@Override
	public int size() {
		return this.bits.cardinality();
	}

	@Override
	public boolean isEmpty() {
		return this.bits.isEmpty();
	}

	@Override
	public boolean contains(Object obj) {
		Integer e = (int) obj;
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango "+e);
		Integer ne = e-infLimit;
		return this.bits.get(ne);
	}

	@Override
	public Iterator<Integer> iterator() {
		return IteratorSet.of(this);
	}

	@Override
	public Object[] toArray() {
		return this.stream().toArray();
	}
	
	
	@Override
	public <T> T[] toArray(T[] a) {		
		@SuppressWarnings("unchecked")
		T[] r = (T[]) this.stream().toArray();
		for(int i = 0 ; i <r.length; i++) {
			a[i] = r[i];
		}
		return r;
	}

	@Override
	public boolean add(Integer e) {
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		Boolean c = this.bits.get(ne);
		this.bits.set(ne, true);
		return c != this.bits.get(ne);
	}
	
	public IntegerSet addF(Integer e) {
		IntegerSet cp = this.copy();
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		this.bits.get(ne);
		cp.bits.set(ne, true);
		return cp;
	}

	@Override
	public boolean remove(Object ob) {
		Integer e = (int) ob;
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		Boolean c = this.bits.get(ne);
		this.bits.set(ne, false);
		return c != this.bits.get(ne);
	}
	
	public IntegerSet removeF(Object ob) {
		IntegerSet cp = this.copy();
		Integer e = (int) ob;
		Preconditions.checkArgument(e>=infLimit, "Fuera de rango");
		Integer ne = e-infLimit;
		cp.bits.set(ne, false);
		return cp;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(x->this.contains(x));
	}
	
	public boolean containsAll(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));		
		return c.difference(this).isEmpty();
	}

	public boolean intersect(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		return this.bits.intersects(c.bits);
	}
	
	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		MutableType<Boolean> change = MutableType.of(false);
		c.stream().forEach(x->{Boolean r = this.add(x); change.setValue(change.value() || r);});
		return change.value();
	}
	
	public boolean addAll(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		Integer n = this.bits.cardinality();
		this.bits.or(c.bits);
		return n != this.bits.cardinality();
	}

	public IntegerSet union(Collection<? extends Integer> c) {
		IntegerSet r = this.copy();
		r.addAll(c);
		return r;
	}
	
	public IntegerSet union(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.or(c.bits);
		return new IntegerSet(cp, this.infLimit);
	}

	public boolean addAll(Integer... elems) {
		MutableType<Boolean> change = MutableType.of(false);
		Arrays.stream(elems).forEach(x->{Boolean r = this.add(x); change.setValue(change.value() || r);});
		return change.value();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		MutableType<Boolean> change = MutableType.of(false);
		IntegerSet cp = this.copy();
		cp.stream().filter(x->!c.contains(x))
			.forEach(x->{Boolean r = this.remove(x); change.setValue(change.value() || r);});
		return change.value();
	}
	
	public boolean retainAll(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		Integer n = this.bits.cardinality();
		this.bits.and(c.bits);
		return n != this.bits.cardinality();
	}
	
	public IntegerSet intersection(Collection<? extends Integer> c) {
		IntegerSet r = this.copy();
		r.retainAll(c);
		return r;
	}
	
	public IntegerSet intersection(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.and(c.bits);
		return new IntegerSet(cp, this.infLimit);
	}
	
	public boolean retainAll(Integer... elems) {
		MutableType<Boolean> change = MutableType.of(false);		
		IntStream.range(0,bits.length()).map(x->x+infLimit).boxed()
			.filter(x->!this.contains(x))
			.forEach(x->{Boolean r = this.remove(x); change.setValue(change.value() || r);});
		return change.value();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		MutableType<Boolean> change = MutableType.of(false);
		c.stream().forEach(x->{Boolean r = this.remove(x); change.setValue(change.value() || r);});
		return change.value();
	}
	
	public boolean removeAll(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		Integer n = this.bits.cardinality();
		this.bits.andNot(c.bits);
		return n != this.bits.cardinality();
	}
	
	public IntegerSet difference(Collection<? extends Integer> c) {
		IntegerSet r = this.copy();
		r.removeAll(c);
		return r;
	}
	
	public IntegerSet difference(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.andNot(c.bits);
		return new IntegerSet(cp, this.infLimit);
	}
	
	public IntegerSet simetricDifference(IntegerSet c) {
		Preconditions.checkArgument(this.infLimit == c.infLimit,
				String.format("Sets no compatibles %d %d",this.infLimit,c.infLimit));
		BitSet cp = (BitSet) this.bits.clone();
		cp.xor(c.bits);
		return new IntegerSet(cp, this.infLimit);
	}
	
	public boolean removeAll(Integer... elems) {
		MutableType<Boolean> change = MutableType.of(false);
		Arrays.stream(elems).forEach(x->{Boolean r = this.remove(x); change.setValue(change.value() || r);});
		return change.value();
	}

	@Override
	public void clear() {
		this.bits.clear();	
	}
	
	@Override
	public String toString() {
		return this.stream().map(x->x.toString()).collect(Collectors.joining(",","{","}"));
	}

	public static void main(String[] args) {
		IntegerSet s1 = IntegerSet.of(20,25,43,457);
		IntegerSet s2 = IntegerSet.of(25,43,20);
		IntegerSet s3 = IntegerSet.of(25,45);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s3.addF(54));
		System.out.println("Union");
		System.out.println(s2.union(s1));
		System.out.println("Interseccion");
		System.out.println(s2.intersection(s1));
		System.out.println("Difference");
		System.out.println(s1.difference(s2));
		System.out.println("SimetricDifference");
		System.out.println(s1.simetricDifference(s2));	
		System.out.println("ContainsAll");
		System.out.println(s1.containsAll(s2));
		System.out.println("Difference");
		System.out.println(s2.difference(s1));
		System.out.println("simetricDifference");
		System.out.println(s2.simetricDifference(s1));	
		System.out.println("ContainsAll");
		System.out.println(s2.containsAll(s1));
		Object[] a = new Integer[s2.size()];
		System.out.println(Arrays.asList(s2.toArray(a)));
		System.out.println(Arrays.asList(a));
		System.out.println(IntegerSet.parse("{0,4,5,4,7,18}","{,}"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((infLimit == null) ? 0 : infLimit.hashCode());
		result = prime * result + ((bits == null) ? 0 : bits.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerSet other = (IntegerSet) obj;
		if (infLimit == null) {
			if (other.infLimit != null)
				return false;
		} else if (!infLimit.equals(other.infLimit))
			return false;
		if (bits == null) {
			if (other.bits != null)
				return false;
		} else if (!bits.equals(other.bits))
			return false;
		return true;
	}



	static class IteratorSet implements Iterator<Integer> {
		
		public static IteratorSet of(IntegerSet s) {
			return new IteratorSet(s);
		}	
		
		private int index;
		private IntegerSet s;
		
		public IteratorSet(IntegerSet s) {
			super();
			this.index = 0;
			this.s = s;
		}

		@Override
		public boolean hasNext() {
			return this.index < s.bits.length();
		}

		@Override
		public Integer next() {
			Integer r = s.bits.nextSetBit(index);
			this.index = r+1;
			return r+s.infLimit;
		}
		
	}
}
