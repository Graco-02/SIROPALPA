package Entidades;

import java.sql.Date;
import java.sql.Time;

public class visita {

	private int id,id_lugar,tipo,estado;
	private Persona persona;
	private Date fecha;
	private Time hora_ent,hora_sal;
	private String nota;
	
	public visita(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_lugar() {
		return id_lugar;
	}

	public void setId_lugar(int id_lugar) {
		this.id_lugar = id_lugar;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora_ent() {
		return hora_ent;
	}

	public void setHora_ent(Time hora_ent) {
		this.hora_ent = hora_ent;
	}

	public Time getHora_sal() {
		return hora_sal;
	}

	public void setHora_sal(Time hora_sal) {
		this.hora_sal = hora_sal;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
}
