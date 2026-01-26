package utilidades;

/**
 * Tupla genérica inmutable usada en los ejercicios para devolver pares de valores.
 *
 * <p>Esta implementación utiliza el nuevo constructo {@code record} de Java para
 * representar un par tipado {@code Tupla<V,E>} con acceso directo a los componentes
 * {@code e1} y {@code e2}.</p>
 *
 * <p>Uso típico:
 * {@code Tupla<List<Integer>, Integer> t = new Tupla<>(List.of(1,2,3), 6);}
 *</p>
 *
 * @param <V> tipo del primer componente (e1)
 * @param <E> tipo del segundo componente (e2)
 */
public record Tupla<V, E>(V e1, E e2) {

}