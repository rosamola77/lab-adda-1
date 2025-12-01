package us.lsi.gurobi;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/* Copyright 2020, Gurobi Optimization, LLC */

/* This example reads an LP model from a file and solves it.
   If the model is infeasible or unbounded, the example turns off
   presolve and solves the model again. If the model is infeasible,
   the example computes an Irreducible Inconsistent Subsystem (IIS),
   and writes it to a file */

import gurobi.*;

/**
 * GurobiLp
 *
 * <p>Clase que proporciona integración con el solver Gurobi para
 * resolver modelos de programación lineal entera (PLI).</p>
 *
 * <p>Lee modelos en formato LP desde ficheros y los resuelve
 * utilizando la API de Gurobi, manejando casos especiales como
 * modelos infactibles o no acotados.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * GurobiLp.solve("modelo.lp");
 * // o para obtener la solución programáticamente:
 * GurobiSolution sol = GurobiLp.solveSolution("modelo.lp");
 * System.out.println("Objetivo: " + sol.objVal);
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see GurobiSolution
 */
public class GurobiLp {
  
	/**
	 * Resuelve un modelo LP y muestra el resultado por consola.
	 *
	 * @param file ruta del fichero con el modelo en formato LP
	 */
	public static void solve(String file) {
		Locale.setDefault(Locale.of("en", "US"));
		Optional<GurobiSolution> solution = GurobiLp.gurobi(file);
		if (!solution.isPresent()) {
			System.out.println("Error in solving the model.");
			return;
		}
		GurobiSolution s = solution.get();
		System.out.println("\n\n\n\n");
		System.out.println(String.format("Objetivo : %.2f",s.objVal));
		System.out.println("\n\n");
		System.out.println(s.values.keySet()
				.stream()
				.filter(e->s.values.get(e)>0.)
				.map(e->String.format("%s == %.1f",e,s.values.get(e)))
				.collect(Collectors.joining("\n")));
	}
	
	/**
	 * Resuelve un modelo LP y devuelve la solución.
	 *
	 * @param file ruta del fichero con el modelo en formato LP
	 * @return la solución del modelo
	 * @throws java.util.NoSuchElementException si no se puede resolver el modelo
	 */
	public static GurobiSolution solveSolution(String file) {
		Locale.setDefault(Locale.of("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi(file).get();
		return solution;
	}
	
	/**
	 * Resuelve un modelo LP usando Gurobi.
	 *
	 * <p>Maneja casos especiales como modelos infactibles o no acotados,
	 * desactivando el preprocesamiento y reintentando si es necesario.</p>
	 *
	 * @param file ruta del fichero con el modelo en formato LP
	 * @return Optional con la solución si se encuentra, vacío en caso contrario
	 */
	public static Optional<GurobiSolution> gurobi(String file) {
		GRBModel model = null;
		Double objval = null;
		GRBVar[] vars = null;
		Map<String, Double> map = null;
		try {
			GRBEnv env = new GRBEnv();
			env.set("OutputFlag", "0"); // Suppress output
			model = new GRBModel(env, file);

			model.optimize();

			int optimstatus = model.get(GRB.IntAttr.Status);

			if (optimstatus == GRB.Status.INF_OR_UNBD) {
				model.set(GRB.IntParam.Presolve, 0);
				model.optimize();
				optimstatus = model.get(GRB.IntAttr.Status);
			}

			if (optimstatus == GRB.Status.OPTIMAL || optimstatus == GRB.Status.SUBOPTIMAL) {
				objval = model.get(GRB.DoubleAttr.ObjVal);
				vars = model.getVars();
				map = new HashMap<>();
				for (GRBVar v : vars) {
					map.put(v.get(GRB.StringAttr.VarName), v.get(GRB.DoubleAttr.X));
				}

				// Dispose of model and environment
				model.dispose();
				env.dispose();
			}

		} catch (GRBException e) {
			return Optional.empty();
		}
		if (model == null || objval == null || map == null) {
			return Optional.empty();
		}
		return Optional.of(GurobiSolution.of(objval, map));
	}
}
