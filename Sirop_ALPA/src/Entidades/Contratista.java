package Entidades;

public class Contratista {

	private Persona datos_personales;
	private String descripcion;
	private int id;
	
	public Contratista() {
		// TODO Auto-generated constructor stub
	}

	public Persona getDatos_personales() {
		return datos_personales;
	}

	public void setDatos_personales(Persona datos_personales) {
		this.datos_personales = datos_personales;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
