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
public class Foto {
    
    private int id;
    private String ruta;
    private byte[] archivo;
    
    public Foto(){
        
    }
    
    public void set_id(int id){
        this.id = id;
    }
    
    public int get_id(){
        return this.id;
    }
    
    public void set_ruta_foto(String ruta){
        this.ruta = ruta;
    }
    
    public String get_ruta_foto(){
        return this.ruta;
    }

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
}
