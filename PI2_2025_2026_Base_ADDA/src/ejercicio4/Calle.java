package ejercicio4;

import java.util.Objects;

public class Calle {
	
	public static Calle of() {
		return new Calle();
	}

	public static  Calle ofFormat(String[] formato) {
		return new Calle(formato);
	}

	private static int num;
	private int id;
	private int duracion;
	private int esfuerzo;


	public Calle() {
		this.id = num;
		num++;
		this.duracion = 0;
		this.esfuerzo = 0;
	} 
	
	private Calle(String[] nombre) {
		
		this.id = num;
		num++;
		this.duracion =  Integer.parseInt(nombre[2].replaceAll("min","").trim());
		this.esfuerzo =  Integer.parseInt(nombre[3].replaceAll("esf","").trim());
	}

	public double getDuracion() {
		return duracion;
	}

	public double getEsfuerzo() {
		return esfuerzo;
	}

	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duracion, esfuerzo, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calle other = (Calle) obj;
		return duracion == other.duracion && esfuerzo == other.esfuerzo && id == other.id;
	}

	@Override
	public String toString() {
		return "Calle-" + id + "";
	}



	
	
}
