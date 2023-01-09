package Entidades;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Reservas {

	private int id;
	private int id_solicitante;
	private String nombre_solicitante;
	private byte[] lista_invitados;
	private Date fecha_solicitud;
	private Date fecha_reserva;
	private Time hora_inicio;
	private Time hora_fin;
	private int estado;
	private ArrayList<String> lista_reservas;
	private String invitados;
	
	public Reservas() {
		lista_reservas = new ArrayList<String>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_solicitante() {
		return id_solicitante;
	}

	public void setId_solicitante(int id_solicitante) {
		this.id_solicitante = id_solicitante;
	}

	public String getNombre_solicitante() {
		return nombre_solicitante;
	}

	public void setNombre_solicitante(String nombre_solicitante) {
		this.nombre_solicitante = nombre_solicitante;
	}

	public byte[] getLista_invitados() {
		return lista_invitados;
	}

	public void setLista_invitados(byte[] lista_invitados) {
		this.lista_invitados = lista_invitados;
	}

	public Date getFecha_solicitud() {
		return fecha_solicitud;
	}

	public void setFecha_solicitud(Date fecha_solicitud) {
		this.fecha_solicitud = fecha_solicitud;
	}

	public Date getFecha_reserva() {
		return fecha_reserva;
	}

	public void setFecha_reserva(Date fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}

	public Time getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(Time hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public Time getHora_fin() {
		return hora_fin;
	}

	public void setHora_fin(Time hora_fin) {
		this.hora_fin = hora_fin;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public void set_Add(String reserva){
		this.lista_reservas.add(reserva);
	}
	
	public String get_seleccion(int pos){
		return this.lista_reservas.get(pos);
	}
	
	public void vacear_lista(){
		this.lista_reservas.clear();
	}
	
	public void set_lista_seleeciones(ArrayList<String> lista){
		lista_reservas = lista;
	}
	
	public ArrayList<String> get_lista_reservaciones(){
		return this.lista_reservas;
	}

	public String getInvitados() {
		return invitados;
	}

	public void setInvitados(String invitados) {
		this.invitados = invitados;
	}

	public void setInvitados() {
		// TODO Auto-generated method stub
		this.invitados = invitados;
			
	}

}
