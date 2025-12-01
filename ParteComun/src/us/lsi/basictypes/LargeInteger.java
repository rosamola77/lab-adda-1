package us.lsi.basictypes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Preconditions;

/**
 * <p>Representa un entero de precision arbitraria.</p>
 * 
 * <p>Esta clase permite trabajar con numeros enteros de cualquier tamano,
 * implementando operaciones aritmeticas basicas como suma, resta,
 * multiplicacion y potencia usando algoritmos eficientes.</p>
 * 
 * @author Miguel Toro
 *
 * @param positive Indica si el numero es positivo
 * @param digits Lista de digitos del numero
 */
public record LargeInteger(Boolean positive, List<Long> digits) implements Comparable<LargeInteger>{
//	private static Long base = 2147483648L;
	/** Base numerica utilizada para los calculos */
	/** Base numerica utilizada para los calculos */
	private static Long base = 10L;
	
	/**
	 * Crea un LargeInteger a partir de una cadena de digitos.
	 * 
	 * @param s Cadena con los digitos del numero
	 * @return Un nuevo LargeInteger
	 */
	public static LargeInteger of(String s) {
		List<Long> ls = IntStream.range(0,s.length()).boxed()
				.map(i->0L+Character.getNumericValue(s.charAt(i)))
				.toList();
		return new LargeInteger(true,ls);
	}
	
	/**
	 * Crea un LargeInteger a partir de un signo y una lista de digitos.
	 * 
	 * @param neg Indica si el numero es positivo
	 * @param ls Lista de digitos
	 * @return Un nuevo LargeInteger
	 */
	public static LargeInteger ofLong(Boolean neg, List<Long> ls) {
		return new LargeInteger(neg,new ArrayList<>(ls));
	}
	
	/**
	 * Crea un LargeInteger positivo a partir de una lista de digitos.
	 * 
	 * @param ls Lista de digitos
	 * @return Un nuevo LargeInteger positivo
	 */
	public static LargeInteger ofLong(List<Long> ls) {
		return new LargeInteger(true,new ArrayList<>(ls));
	}
	
	/**
	 * Crea un LargeInteger con valor cero.
	 * 
	 * @return LargeInteger con valor 0
	 */
	public static LargeInteger zero() {
		return new LargeInteger(true,List.of(0L));
	}
	
	/**
	 * Crea un LargeInteger con valor uno.
	 * 
	 * @return LargeInteger con valor 1
	 */
	public static LargeInteger one() {
		return new LargeInteger(true,List.of(1L));
	}
	
	/**
	 * Crea un LargeInteger a partir de una lista de enteros.
	 * 
	 * @param ls Lista de enteros
	 * @return Un nuevo LargeInteger
	 */
	public static LargeInteger ofInteger(List<Integer> ls) {
		return new LargeInteger(true,ls.stream().map(e->e.longValue()).toList());
	}
	
	/**
	 * Devuelve el numero de digitos del numero.
	 * 
	 * @return Numero de digitos
	 */
	public Integer size() {
		return digits.size();
	}
	
	/**
	 * Obtiene el digito en la posicion especificada.
	 * 
	 * @param i Indice del digito
	 * @return El digito en la posicion i
	 */
	public Long digit(int i) {
		return this.digits().get(i);
	}
	
	/**
	 * Comprueba si el numero es cero.
	 * 
	 * @return true si todos los digitos son cero
	 */
	public Boolean isZero() {
		return this.digits().stream().allMatch(e->e.equals(0L));
	}
	
	/**
	 * Comprueba si el numero es uno.
	 * 
	 * @return true si el numero es exactamente 1
	 */
	public Boolean isOne() {
		LargeInteger z = this.removeZerosLeft();
		return z.positive() && z.digits().stream().allMatch(e->e.equals(1L));
	}
	
	/**
	 * Completa con ceros a la izquierda hasta alcanzar el tamano especificado.
	 * 
	 * @param n Tamano deseado
	 * @return Un nuevo LargeInteger con ceros a la izquierda
	 * @throws IllegalArgumentException si n es menor que el tamano actual
	 */
	public LargeInteger completeZerosLeft(Integer n) {
		Preconditions.checkArgument(n>this.size(),"Demasiado largo");
		List<Long> r = new ArrayList<>(this.digits);
		for(int i = this.size();i<n;i++) r.add(0,0L);
		return LargeInteger.ofLong(r);
	}
	
	/**
	 * Elimina los ceros a la izquierda del numero.
	 * 
	 * @return Un nuevo LargeInteger sin ceros a la izquierda
	 */
	public LargeInteger removeZerosLeft() {
		List<Long> ls = this.digits().stream().dropWhile(e->e.equals(0L)).toList();
		return LargeInteger.ofLong(ls);
	}
	
	/**
	 * Anade ceros a la derecha del numero.
	 * 
	 * @param n Numero de ceros a anadir
	 * @return Un nuevo LargeInteger con ceros a la derecha
	 */
	public LargeInteger addZerosRight(Integer n) {
		List<Long> r = new ArrayList<>(this.digits);
		for(int i = 0;i<n;i++) r.add(0L);
		return LargeInteger.ofLong(r);
	}
	
	/**
	 * Compara este LargeInteger con otro.
	 * 
	 * @param other El otro LargeInteger a comparar
	 * @return Valor negativo, cero o positivo segun este sea menor, igual o mayor
	 */
	@Override
	public int compareTo(LargeInteger other) {
		return compare(this,other);
	}
	
	/**
	 * Compara dos LargeInteger.
	 * 
	 * @param x Primer numero
	 * @param y Segundo numero
	 * @return Valor negativo, cero o positivo segun x sea menor, igual o mayor que y
	 */
	public int compare(LargeInteger x, LargeInteger y) {
		Integer n1 = x.size();
		Integer n2 = y.size();
		if(n1<n2) x = x.completeZerosLeft(n2);
		if(n1>n2) y = y.completeZerosLeft(n1);
		Integer n = x.size();
		int d = -1;
		for(int i=0;i<n;i++) {
			if(x.digit(i) != y.digit(i)) {
				d = i;
				break;
			}
		}
		int r;
		if(d==-1) r = 0;
		else r = x.digits().get(d) < y.digits().get(d)? -1 :1;
		return r;
	}
	
	/**
	 * Suma dos LargeInteger (metodo estatico).
	 * 
	 * @param x Primer sumando
	 * @param y Segundo sumando
	 * @return La suma de x e y
	 */
	public static LargeInteger sum(LargeInteger x, LargeInteger y) {
		if(y.isZero()) return x;
		if(x.isZero()) return y;
		Integer n1 = x.size();
		Integer n2 = y.size();
		if(n1<n2) x = x.completeZerosLeft(n2);
		if(n1>n2) y = y.completeZerosLeft(n1);
		Integer n = x.size();
		List<Long> r = new ArrayList<>();
		Long ac = 0L;
		for(int i=n-1;i>=0;i--) {
			Long sm = ac+x.digit(i)+y.digit(i);
			Long d = sm%base;
			ac = sm/base;
			r.add(0,d);
		}
		r.add(0,ac);
		return LargeInteger.ofLong(r);
	}
	
	/**
	 * Suma este LargeInteger con otro.
	 * 
	 * @param e El otro sumando
	 * @return La suma
	 */
	public LargeInteger sum(LargeInteger e) {
		return LargeInteger.sum(this, e);
	}
	
	/**
	 * Resta dos LargeInteger (metodo estatico).
	 * 
	 * @param x Minuendo
	 * @param y Sustraendo
	 * @return La resta de x menos y
	 */
	public static LargeInteger minus(LargeInteger x, LargeInteger y) {
		Boolean positive = true;
		if(x.compareTo(y) < 0) {
			LargeInteger a = x;
			x = y;
			y = a;
			positive = false;
		}
		if(y.isZero()) return LargeInteger.ofLong(positive,x.digits());
		Integer n1 = x.size();
		Integer n2 = y.size();
		if(n1<n2) x = x.completeZerosLeft(n2);
		if(n1>n2) y = y.completeZerosLeft(n1);
		Integer n = x.size();
		List<Long> r = new ArrayList<>();
		Long ac = 0L;
		for(int i=n-1;i>=0;i--) {
			Long d;
			if(x.digit(i) >= (y.digit(i)+ac)) {
				d = x.digit(i) - (y.digit(i)+ac);
				ac = 0L;
			} else {
				d = LargeInteger.base+x.digit(i) - (y.digit(i)+ac);
				ac = 1L;
			}
			r.add(0,d);
		}
		return LargeInteger.ofLong(positive,r);
	}
	
	/**
	 * Resta otro LargeInteger de este.
	 * 
	 * @param e El sustraendo
	 * @return La resta
	 */
	public LargeInteger minus(LargeInteger e) {
		return LargeInteger.minus(this,e);
	}
	
	/**
	 * Multiplica dos LargeInteger de un solo digito.
	 * 
	 * @param x Primer factor
	 * @param y Segundo factor
	 * @return El producto
	 */
	public static LargeInteger multiplyOneDigit(LargeInteger x, LargeInteger y) {
		Long sm = x.digit(0)*y.digit(0);
		Long d = sm%base;
		Long ac = sm/base;
		return LargeInteger.ofLong(List.of(ac,d));
	}
	
	/**
	 * Multiplica dos LargeInteger usando el algoritmo de Karatsuba.
	 * 
	 * @param x Primer factor
	 * @param y Segundo factor
	 * @return El producto de x por y
	 */
	public static LargeInteger multiply(LargeInteger x, LargeInteger y) {
		if(x.isZero() || y.isZero()) return LargeInteger.zero();
		Integer n1 = x.size();
		Integer n2 = y.size();
		if(n1<n2) x = x.completeZerosLeft(n2);
		if(n1>n2) y = y.completeZerosLeft(n1);
		Integer n = x.size();
		LargeInteger r;
		if(n==1) r = LargeInteger.multiplyOneDigit(x,y);
		else {
			Integer k = n/2;
			Integer m = n - k;
			LargeInteger x1 = LargeInteger.ofLong(x.digits().subList(0, k));
			LargeInteger x0	= LargeInteger.ofLong(x.digits().subList(k, n));
			LargeInteger y1	= LargeInteger.ofLong(y.digits().subList(0, k));
			LargeInteger y0	= LargeInteger.ofLong(y.digits().subList(k, n));
			LargeInteger z2 = x1.multiply(y1);
			LargeInteger z0 = x0.multiply(y0);
			LargeInteger z1 = x1.sum(x0).multiply(y1.sum(y0)).minus(z2.sum(z0));
			z2 = z2.addZerosRight(2*m);
			z1 = z1.addZerosRight(m);
			r = z2.sum(z1).sum(z0);
		}
		return r;
	}
	
	/**
	 * Multiplica este LargeInteger por otro.
	 * 
	 * @param e El otro factor
	 * @return El producto
	 */
	public LargeInteger multiply(LargeInteger e) {
		return LargeInteger.multiply(this,e);
	}
	
	/**
	 * Calcula la potencia de un LargeInteger.
	 * 
	 * @param x Base
	 * @param n Exponente
	 * @return x elevado a n
	 */
	public static LargeInteger pow(LargeInteger x, Integer n) {
		LargeInteger r = x;
		LargeInteger u = LargeInteger.one();
		while(n > 0){
	       if(n%2==1){
			     u = u.multiply(r);
		   }
		   r = r.multiply(r);
		   n = n/2;
		}
		return u;
	}
	
	/**
	 * Calcula la potencia de este LargeInteger.
	 * 
	 * @param n Exponente
	 * @return Este numero elevado a n
	 */
	public LargeInteger pow(Integer n) {
		return LargeInteger.pow(this, n);
	}
	
	/**
	 * Devuelve una representacion en cadena del numero.
	 * 
	 * @return Representacion textual del numero
	 */
	@Override
	public String toString() {
		LargeInteger r = this.removeZerosLeft();
		return (r.positive?"":"-")+r.digits.stream().map(d->d.toString()).collect(Collectors.joining(""));
	}
	
	
}
