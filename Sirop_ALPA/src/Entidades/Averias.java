package Entidades;

import java.sql.Date;

public class Averias {

	private Lugar solicitante;
	private String descripcion;
	private String titulo;
	private Date fecha;
	private int estado;
	private int id;
	
	public Averias() {
		// TODO Auto-generated constructor stub
	}

	public Lugar getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Lugar solicitante) {
		this.solicitante = solicitante;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
