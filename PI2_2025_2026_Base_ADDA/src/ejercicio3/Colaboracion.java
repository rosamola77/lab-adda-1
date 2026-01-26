package ejercicio3;

import java.util.Objects;

public class Colaboracion {
	
	public static Colaboracion of() {
		return new Colaboracion();
	}

	public static  Colaboracion ofFormat(String[] formato) {
		return new Colaboracion(formato);
	}

	private static int num;
	private int id;
	private int nColaboraciones;

	
	public Colaboracion() {
		this.nColaboraciones= 0;
		this.id = num;
		num++;
	} 
	
	private Colaboracion(String[] nombre) {
		this.nColaboraciones =  Integer.parseInt(nombre[2]);
		this.id = num;
		num++;
	}

	public Double getNColaboraciones() {
		return (double)nColaboraciones;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nColaboraciones);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaboracion other = (Colaboracion) obj;
		return id == other.id && nColaboraciones == other.nColaboraciones;
	}

	@Override
	public String toString() {
		return "[Col-" + id + ", " + nColaboraciones + "]";
	}

}
