package ejercicio3;

import java.util.Objects;

public class Investigador {

	public static Investigador of() {
		return new Investigador(-1);
	}

	public static Investigador ofFormat(String[] formato) {
		return new Investigador(formato);
	}

	public static Investigador ofId(Integer id) {
		return new Investigador(id);
	}
	
	private Integer id = null;
	private Integer anyoNacimiento = null;
	private String universidad = null;

	private Investigador(Integer id) {
		super();
		this.id = id;
		this.anyoNacimiento = null;
		this.universidad = null;
	}

	private Investigador(String[] formato){
		super();
		this.id = Integer.parseInt(formato[0]);
		this.anyoNacimiento = Integer.parseInt(formato[1]);
		this.universidad = formato[2];
	}

	public Integer getId() {
		return id;
	}

	public Integer getFNacimiento() {
		return anyoNacimiento;
	}

	public String getUniversidad() {
		return universidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anyoNacimiento, id, universidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigador other = (Investigador) obj;
		return Objects.equals(anyoNacimiento, other.anyoNacimiento) && Objects.equals(id, other.id)
				&& Objects.equals(universidad, other.universidad);
	}

	@Override
	public String toString() {
		return "inv-" + id;
	}
	
	
	
}
