/**
 * 
 */
package Entidades;

import java.sql.Date;

/**
 * @author FFerr
 *
 */
public class Paquete {

	/**
	 * 
	 */
	
	private int id, id_receptor, id_usuario, estado;
	private Persona depositante;
	private String descripcion;
	private Date fecha_entra,fecha_sale;
	private Foto foto;
	
	public Paquete() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_receptor() {
		return id_receptor;
	}

	public void setId_receptor(int id_receptor) {
		this.id_receptor = id_receptor;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Persona getDepositante() {
		return depositante;
	}

	public void setDepositante(Persona depositante) {
		this.depositante = depositante;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha_entra() {
		return fecha_entra;
	}

	public void setFecha_entra(Date fecha_entra) {
		this.fecha_entra = fecha_entra;
	}

	public Date getFecha_sale() {
		return fecha_sale;
	}

	public void setFecha_sale(Date fecha_sale) {
		this.fecha_sale = fecha_sale;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	
}
