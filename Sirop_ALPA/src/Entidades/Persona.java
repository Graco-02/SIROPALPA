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
public class Persona {
    
    private int id;
    private String nombres;
    private String Apellidos;
    private String identificacion;
    private int tipo_ident;
    private Foto foto;
    private Contacto contacto;
    private Lugar apart_inf;
    
    public Persona(){}

    public String get_nombre_apellidos(){
    	return nombres+","+Apellidos;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getTipo_ident() {
        return tipo_ident;
    }

    public void setTipo_ident(int tipo_ident) {
        this.tipo_ident = tipo_ident;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

	public Lugar getApart_inf() {
		return apart_inf;
	}

	public void setApart_inf(Lugar apart_inf) {
		this.apart_inf = apart_inf;
	}
    
   
}
