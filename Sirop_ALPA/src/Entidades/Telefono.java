/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author FGFH
 * 
 * 
 * esta es la clase entidad que contiene los atributos relacionados al telefono
 * esta moldeada segun los campos de la DDBB
 */
public class Telefono {
    
    private int id,id_contacto;
    private String telefono;
    
    public Telefono(){}
    
    public void set_id(int id){
        this.id = id;
    }
    
    public int get_id(){
        return this.id;
    }
    
    public void set_telefono(String telefono){
        this.setTelefono(telefono);
    }

	public int getId_contacto() {
		return id_contacto;
	}

	public void setId_contacto(int id_contacto) {
		this.id_contacto = id_contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
