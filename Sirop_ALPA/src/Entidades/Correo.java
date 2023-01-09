/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author FGFH
 */
public class Correo {
    
    private int id,id_contacto;
    private String correo;
    
    public Correo(){}
    
    public void set_id(int id){
        this.id = id;
    }
    
    public int get_id(){
        return this.id;
    }
    
    public void set_correo(String correo){
        this.correo = correo;
    }
    
    public String get_correo(){
        return this.correo;
    }

	public int getId_contacto() {
		return id_contacto;
	}

	public void setId_contacto(int id_contacto) {
		this.id_contacto = id_contacto;
	}
}
