package us.lsi.common;

/**
 * <p>Contenedor mutable para un valor de tipo inmutable.</p>
 * 
 * <p>Permite modificar el valor contenido, util para patrones
 * donde se necesita una referencia mutable en contextos
 * funcionales o de streams.</p>
 * 
 * @author Miguel Toro
 *
 * @param <T> Un tipo inmutable contenido
 */
public class MutableType<T> {

	/**
	 * Crea un MutableType con el valor especificado.
	 * 
	 * @param <T> Tipo del valor
	 * @param e Valor inicial
	 * @return Un nuevo MutableType
	 */
	public static <T> MutableType<T> of(T e) {
		return new MutableType<T>(e);
	}
	
	/** Valor contenido */
	protected T value;
	
	/**
	 * Constructor protegido.
	 * 
	 * @param e Valor inicial
	 */
	protected MutableType(T e) {
		super();
		this.value = e;
	}
	
	/**
	 * Establece un nuevo valor.
	 * 
	 * @param e Nuevo valor
	 * @return Valor anterior
	 */
	public T setValue(T e) {
		T old = this.value;
		this.value = e;
		return old;
	}
	
	/**
	 * Obtiene el valor contenido.
	 * 
	 * @return El valor actual
	 */
	public T value() {
		return value;
	}
	
	/**
	 * Representacion en cadena del valor.
	 * 
	 * @return Representacion textual del valor contenido
	 */
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MutableType))
			return false;
		MutableType<?> other = (MutableType<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	/**
	 * <p>Version mutable de Integer con operacion de incremento.</p>
	 * 
	 * @author Miguel Toro
	 */
	public static class MutableInteger extends MutableType<Integer> {

		/**
		 * Constructor privado.
		 * 
		 * @param e Valor inicial
		 */
		private MutableInteger(Integer e) {
			super(e);
		}
		
		/**
		 * Crea un MutableInteger con el valor especificado.
		 * 
		 * @param e Valor inicial
		 * @return Un nuevo MutableInteger
		 */
		public static MutableInteger of(Integer e) {
			return new MutableInteger(e);
		}
		
		/**
		 * Crea un MutableInteger con valor 0.
		 * 
		 * @return Un nuevo MutableInteger con valor 0
		 */
		public static MutableInteger of() {
			return new MutableInteger(0);
		}
		
		/**
		 * Incrementa el valor y devuelve el valor anterior.
		 * 
		 * @return Valor antes del incremento
		 */
		public Integer valueInc() {
			Integer oldValue = super.value;
			super.value++;	
			return oldValue;
		}
		
	}
	
}
