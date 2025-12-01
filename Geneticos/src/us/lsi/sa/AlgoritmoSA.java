package us.lsi.sa;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.random.JDKRandomGenerator;
import us.lsi.math.Math2;

/**
 * AlgoritmoSA
 *
 * <p>Implementación del Algoritmo de Simulated Annealing (Recocido Simulado).
 * Es una metaheurística para resolver problemas de optimización inspirada
 * en el proceso de recocido de metales.</p>
 *
 * <p>Para usar esta técnica hay que considerar un conjunto de estados y
 * unas alternativas para pasar de unos a otros. El estado que minimice
 * el objetivo debe ser alcanzable desde el estado inicial a través de
 * una secuencia de alternativas.</p>
 *
 * <p>El algoritmo acepta soluciones peores con cierta probabilidad
 * (controlada por la temperatura) para escapar de mínimos locales.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * StateSa<V,G,S> estadoInicial = ...;
 * AlgoritmoSA<V,G,S> sa = AlgoritmoSA.of(estadoInicial);
 * sa.ejecuta();
 * StateSa<V,G,S> mejor = sa.mejorSolucionEncontrada;
 * }</p>
 *
 * @param <V> tipo de los valores decodificados
 * @param <G> tipo del genotipo
 * @param <S> tipo de la solución
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see StateSa
 */
public class AlgoritmoSA<V,G,S> {

	/**
	 * Crea un algoritmo de Simulated Annealing a partir de un estado inicial.
	 *
	 * @param <V> tipo de los valores decodificados
	 * @param <G> tipo del genotipo
	 * @param <S> tipo de la solución
	 * @param estado estado inicial del algoritmo
	 * @return un nuevo algoritmo SA configurado
	 */
	public static <V,G,S> AlgoritmoSA<V,G,S> of(StateSa<V,G,S> estado) {
			return new AlgoritmoSA<>(estado);
	}

	/** Conjunto de soluciones encontradas durante la ejecución. */
	public Set<StateSa<V,G,S>> soluciones;
	
	/** Mejor solución encontrada durante la ejecución. */
	public StateSa<V,G,S> mejorSolucionEncontrada = null;
	
	/**
	 * Número de intentos.
	 * 
	 * <p>En cada intento se parte del estado inicial y se llevan a cabo
	 * un número de iteraciones por intento.</p>
	 */
	public static Integer numeroDeIntentos = 10;
	
	/** Número de iteraciones por intento (n). */
	public static Integer numeroDeIteracionesPorIntento = 300;
	
	/** Número de iteraciones a la misma temperatura (m). */
	public static Integer numeroDeIteracionesALaMismaTemperatura = 10;
	
	/** Temperatura inicial (t0). */
	public static double temperaturaInicial = 1000;
	
	/** Factor de enfriamiento (alfa). */
	public static double alfa = 0.99;

	/** Temperatura actual. */
	private double temperatura;
	
	/** Estado actual. */
	private StateSa<V,G,S> estado;
	
	/** Estado candidato siguiente. */
	private StateSa<V,G,S> nextEstado;

	/**
	 * Constructor privado que inicializa el algoritmo.
	 *
	 * @param estado estado inicial
	 */
	private AlgoritmoSA(StateSa<V,G,S> estado) {
		this.estado = estado;
		this.mejorSolucionEncontrada = estado;
		this.soluciones = new HashSet<>();
		JDKRandomGenerator random = new JDKRandomGenerator();
		random.setSeed((int) System.currentTimeMillis());
		Math2.rnd = random;
	}
	
	/**
	 * Calcula el incremento promedio de fitness para estados vecinos.
	 *
	 * <p>Útil para calibrar la temperatura inicial.</p>
	 *
	 * @param n número de muestras a tomar
	 * @return el incremento promedio
	 */
	public Double averageIncrement(int n) {
		Double s = 0.;
		Integer r = 0;
		Double f = this.estado.fitness();
		for(int i=0; i<n;i++) {
			StateSa<V,G,S> e = this.estado.random();
			if(e.fitness() > f) {
				r++;
				s = e.fitness()-f;
			}
		}
		return s/r;
	}

	/**
	 * Ejecuta el algoritmo de Simulated Annealing.
	 *
	 * <p>Realiza múltiples intentos, cada uno con múltiples iteraciones
	 * que van reduciendo la temperatura gradualmente.</p>
	 */
	public void ejecuta() {
		this.mejorSolucionEncontrada = this.estado.random();
		for (Integer n = 0; n < numeroDeIntentos; n++) {
			this.temperatura = temperaturaInicial;
			this.estado = this.estado.random();
			for (int numeroDeIteraciones = 0;
				     numeroDeIteraciones < numeroDeIteracionesPorIntento; numeroDeIteraciones++) {
				for (int s = 0; s < numeroDeIteracionesALaMismaTemperatura; s++) {
					this.nextEstado = this.estado.mutate();
					double incr = nextEstado.fitness() - estado.fitness();
					if (aceptaCambio(incr)) {
						estado = nextEstado;
						actualizaMejorValor();
					}
				}
				this.temperatura = nexTemperatura(numeroDeIteraciones);
			}
			soluciones.add(this.estado);
		}
	}

	/**
	 * Actualiza la mejor solución encontrada si el estado actual es mejor.
	 */
	private void actualizaMejorValor() {
		if (estado.fitness() < mejorSolucionEncontrada.fitness()) {
			mejorSolucionEncontrada = estado;
		}
	}

	/**
	 * Calcula la siguiente temperatura según el esquema de enfriamiento.
	 *
	 * @param numeroDeIteraciones número de iteración actual
	 * @return la nueva temperatura
	 */
	private double nexTemperatura(int numeroDeIteraciones) {
		return alfa * temperatura;
	}

	/**
	 * Determina si se acepta el cambio al nuevo estado.
	 *
	 * <p>Usa el criterio de Boltzmann para aceptar cambios.</p>
	 *
	 * @param incr incremento en el fitness
	 * @return {@code true} si se acepta el cambio
	 */
	private boolean aceptaCambio(double incr) {
		return Math2.aceptaBoltzmann(incr, temperatura);
	}

}
