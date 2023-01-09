package Ventanas_S;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Controladores.Action_log;
import Controladores.Contacto_control;
import Controladores.Contratista_control;
import Controladores.Foto_control;
import Controladores.Persona_control;
import Entidades.Contratista;
import Entidades.Persona;
import Entidades.log_accion;
import Formularios.Contactos_frm;
import Formularios.Contratista_frm;
import Formularios.Persona_frm;

public class Contratista_formulario extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Persona_frm persona_frm;
	private Contactos_frm contac_frm;
	private Contratista_frm contratista_frm;
	private Persona datos_personales;
	private Contratista contratista;

	private log_accion log_action;
	
	
	public Contratista_formulario(Contratista contratista) {
		
		
		log_action = new log_accion();

		persona_frm = new Persona_frm();
		
		contac_frm = new Contactos_frm();
		
		if (contratista!=null) {
			this.contratista = contratista;
			datos_personales = this.contratista.getDatos_personales();
			persona_frm.set_persona(datos_personales);
			contac_frm.set_contacto(this.datos_personales.getContacto());
		}
		contratista_frm = new Contratista_frm(this.contratista);

		persona_frm.set_add(get_boton_select());
		
		this.setTitle("FORMULARIO CONTRATISTA");
        this.setSize(700,500);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_contend());
        this.setVisible(true);
	}
	
	private JTabbedPane get_pestanas(){
		JTabbedPane pestanas = new JTabbedPane();
		pestanas.add("Datos Personales", persona_frm);
		pestanas.addTab("Contactos", contac_frm);
		
		return pestanas;
	}
	
	private JPanel get_contend(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_pestanas(), BorderLayout.NORTH);
		panel.add(contratista_frm,BorderLayout.CENTER);
		panel.add(get_Funciones(),BorderLayout.EAST);
		return panel;
	}
	
	
	private JPanel get_Funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_add());
		
		return panel;
	}
	
	private JButton get_boton_select(){
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/", 50, 50)));
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				datos_personales = new Persona_selector().get_persona();
				persona_frm.set_persona(datos_personales);
				contac_frm.set_contacto(datos_personales.getContacto());
				
			}
		});
    	return boton;
    }
	
	private JButton get_boton_add(){
		JButton boton = new JButton("ADD", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/adduser.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(datos_personales==null){
					datos_personales = persona_frm.get_Persona();
					datos_personales.setContacto(contac_frm.get_contacto());
				
					new Contacto_control().set_insert(datos_personales.getContacto());
					new Persona_control().set_insert(datos_personales);
				}
				
				if(persona_frm.get_validar()){
					
				if( contratista == null){
					datos_personales = persona_frm.get_Persona();
					datos_personales.setContacto(contac_frm.get_contacto());
					
					contratista = contratista_frm.get_contratista();
					contratista.setDatos_personales(datos_personales);
					
					new Contratista_control().set_insert(contratista);
					
				}else if(persona_frm.get_validar() && contratista!=null) {
					
					new Contratista_control().set_modificar(contratista_frm.get_contratista());
				
				}else if(persona_frm.get_validar() && datos_personales == null && contratista == null){
					datos_personales = persona_frm.get_Persona();
					datos_personales.setContacto(contac_frm.get_contacto());
					
					contratista = contratista_frm.get_contratista();
					contratista.setDatos_personales(datos_personales);
					new Foto_control().set_insert(datos_personales.getFoto());
					new Contacto_control().set_insert(datos_personales.getContacto());
					new Persona_control().set_insert(datos_personales);
					
					new Contratista_control().set_insert(contratista);
					
				
				}
				JOptionPane.showMessageDialog(null, "Accion Realizada!");
				dispose();
			}
				}
		});
		return boton;
	}	
	
	protected void set_log(){
		log_action.setFecha(new Date(new Fecha().getTime()));
		log_action.setHora(new Time(new Fecha().getTime()));
		new Action_log().set_insert(log_action);
		
		
		dispose();
	}

}
