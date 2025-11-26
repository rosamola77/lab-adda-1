package ejercicio4;

import java.util.Objects;

public class Interseccion {

	public static Interseccion of() {
		return new Interseccion(-1);
	}

	public static Interseccion ofFormat(String[] formato) {
		return new Interseccion(formato);
	}

	public static Interseccion ofId(Integer id) {
		return new Interseccion(id);
	}
	
	private Integer id = null;
	private Boolean monumento = null;
	private Integer relevancia = null;
	private String nombre = null;

	private Interseccion(Integer id) {
		super();
		this.id = id;
		this.relevancia = null;
		this.nombre = null;
		this.monumento = false;
	}

	private Interseccion(String[] formato){
		super();
		this.id = Integer.parseInt(formato[0]);
		this.monumento = Boolean.parseBoolean(formato[1]);
		this.nombre = formato[2];				
		if (formato[3].replaceAll(" ","").trim().equals("-1")) {
		   this.relevancia = -1;
		} else {
		   this.relevancia = Integer.parseInt(formato[3].replaceAll("int","").trim());
		}
	}

	public Integer getId() {
		return id;
	}

	public Boolean hasMonumento() {
		return monumento;
	}

	public Integer getRelevancia() {
		return relevancia;
	}

	public String getNombre() {
		return nombre;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, monumento, nombre, relevancia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interseccion other = (Interseccion) obj;
		return Objects.equals(id, other.id) && Objects.equals(monumento, other.monumento)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(relevancia, other.relevancia);
	}

	@Override
	public String toString() {
		return "int-" + id;
	}
	
	
	
}
