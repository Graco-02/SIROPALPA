/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.ArrayList;

/**
 *
 * @author FGFH
 */
public class Contacto {
    
    private int id;
    private ArrayList<Telefono> telefonos_list;
    private ArrayList<Correo> correos_list;
    
    public Contacto(){
        this.telefonos_list = new ArrayList<>();
        this.correos_list = new ArrayList<>();
    }
    
    public void set_id(int id){
        this.id = id;
    }
    
    public int get_id(){
        return this.id;
    }
    
    public void add_Telefono(Telefono tel){
        this.telefonos_list.add(tel);
    }
    
    public void add_correo(Correo correo){
        this.correos_list.add(correo);
    }
    
    public Telefono get_Telefono(int pos){
        return this.telefonos_list.get(pos);
    }
    
    public Correo get_Correo(int pos){
        return this.correos_list.get(pos);
    }
    
    public void limpiar(){
        this.correos_list.clear();
        this.telefonos_list.clear();
    }
    
    public ArrayList<Telefono> get_lista_telefonos(){
        return this.telefonos_list;
    }
    
    public ArrayList<Correo> get_lista_correos(){
        return this.correos_list;
    }
    
    public void set_lista_correos(ArrayList<Correo> lista_correo){
    	this.correos_list = lista_correo;
    }
    
    public void set_lista_Telefonos(ArrayList<Telefono> lista_telefono){
    	this.telefonos_list = lista_telefono;
    }
}
