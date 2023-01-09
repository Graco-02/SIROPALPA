package Entidades;

import java.sql.Date;

public class Reparaciones {

	private int id;
	private Contratista contratista;
	private Averias averia;
	private Date fecha_ini;
	private Date fecha_fin;
	private int estado;
	
	public Reparaciones() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Contratista getContratista() {
		return contratista;
	}

	public void setContratista(Contratista contratista) {
		this.contratista = contratista;
	}

	public Averias getAveria() {
		return averia;
	}

	public void setAveria(Averias averia) {
		this.averia = averia;
	}

	public Date getFecha_ini() {
		return fecha_ini;
	}

	public void setFecha_ini(Date fecha_ini) {
		this.fecha_ini = fecha_ini;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

}
