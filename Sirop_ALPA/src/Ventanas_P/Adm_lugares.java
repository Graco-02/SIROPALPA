/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas_P;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.naming.LimitExceededException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import Controladores.Action_log;
import Controladores.Contacto_control;
import Controladores.Lugar_control;
import Entidades.Lugar;
import Entidades.log_accion;
import Formularios.Contactos_frm;
import Formularios.lugar_frm;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Persona_form;

/**
 *
 * @author FGFH
 */
public class Adm_lugares extends JFrame{
    
    private Lugar lugar;
    private lugar_frm lugar_frm;
    private Contactos_frm contacto_frm;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private JPanel panel_funciones;
	private JButton boton_addPersona;
	protected log_accion log_action;
	
	
    public Adm_lugares(){
    	log_action = new log_accion();
    	
        this.setTitle("ADMINISTRADOR DE LUGARES");
        this.setSize(1000,700);
       // this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_panel_contnedor());
        this.setVisible(true);
    }
    
    private JPanel get_panel_contnedor(){
       JPanel panel = new JPanel();
       panel.setLayout(new BorderLayout());
       panel.add(getTabla(),BorderLayout.NORTH);
       panel.add(get_panel_centro(), BorderLayout.CENTER);
       return panel;
    }
    
    private JTabbedPane get_pestanas(){
    	JTabbedPane pestanas = new JTabbedPane();
    	lugar_frm = new lugar_frm(this.resultado);
    	contacto_frm = new Contactos_frm();
    	
    	pestanas.setBorder(BorderFactory.createTitledBorder(""));
    	pestanas.add("Lugar", lugar_frm);
    	pestanas.add("Contactos", contacto_frm);
    	return pestanas;
    } 
    
    private JPanel get_panel_centro(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(""));
        panel.add(get_pestanas(),BorderLayout.CENTER);
        panel.add(get_panel_funciones(),BorderLayout.EAST);
        return panel;
     }
     
    private JPanel get_panel_funciones(){
        panel_funciones = new JPanel();
        panel_funciones.setLayout(new GridLayout(4, 1));
        panel_funciones.setBorder(BorderFactory.createTitledBorder(""));
        panel_funciones.setBackground(Color.DARK_GRAY);
        
        panel_funciones.add(boton_guardar());
        panel_funciones.add(boton_Eliminar());
        panel_funciones.add(boton_Limpiar());
        panel_funciones.add(get_boton_personaADD());
        
        return panel_funciones;
     }
     
    private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_Lugares();
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
						
	            		if(lugar!=null){
	            			set_limpiar();
	            		}
						
						lugar = new Lugar_control().get_lugar(
		            			Integer.parseInt( String.valueOf( tabla.getValueAt(row, 0) ) ) );
		            	
		            	lugar_frm.set_lugar(lugar);
		            	contacto_frm.set_contacto(lugar.getContacto());
					
		            	if(lugar.getAcceso() == 1){
		            		boton_addPersona.setEnabled(true);
		            	}
		            	
		            	JOptionPane.showMessageDialog(null, "Lugar Seleccionado");
	            	} catch (IllegalStateException | SQLException e1) {
						e1.printStackTrace();
					}
	          }//
	        }
	      });
	    }
	    catch (IllegalStateException e)
	    {
	      e.printStackTrace();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    Box box = Box.createHorizontalBox(); 
	    JScrollPane barra = new JScrollPane(tabla);
	    box.add(barra);
	    box.setBorder(BorderFactory.createTitledBorder(""));
	    return box;
	  }
    
    
    private JButton boton_guardar(){
    	JButton boton = new JButton("Add",
    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(lugar == null && lugar_frm.get_validar()
						){//valido que la instancia del lugar esta vacia para proceder a procesarla como un nuebo registro
					
					try {
						
						lugar = lugar_frm.get_lugar();
						if(lugar.getAcceso() == 1){
							if(contacto_frm.get_contacto().get_lista_telefonos().size() > 0 && contacto_frm.get_contacto().get_lista_correos().size() > 0){
								lugar.setContacto(contacto_frm.get_contacto());

								new Contacto_control().set_insert(lugar.getContacto());
								new Lugar_control().set_insert(lugar);
							
								set_limpiar();
							}else{
								JOptionPane.showMessageDialog(null, "Faltan Datos! favor revisar los datos del contacto ");
							}
						}else{
							lugar.setContacto(contacto_frm.get_contacto());

							new Contacto_control().set_insert(lugar.getContacto());
							new Lugar_control().set_insert(lugar);
						
							set_limpiar();
						}
						
					} catch (IllegalStateException | SQLException e1) {
						e1.printStackTrace();
					}
					
				}else if(lugar!=null && JOptionPane.showConfirmDialog(null, "Esta seguro de modificar el registro?")==0 && lugar_frm.get_validar()){
					
					try {
						lugar = lugar_frm.get_lugar();
						lugar.setContacto(contacto_frm.get_contacto());
						/*
						log_action.setLog("MODIFICO DATOS DEL LUGAR ..: "+lugar.getTitulo());
						log_action.setFecha(new Fecha().get_fecha_sql());
						log_action.setHora(new Fecha().get_hora_sql());
						set_log();
						*/
						new Lugar_control().set_modificar(lugar);
						new Contacto_control().set_tel_corre(lugar.getContacto());
						
						set_limpiar();
					
					} catch (IllegalStateException | SQLException e1) {
						e1.printStackTrace();
					}
					
				}
				
			}//fin del evento
		});
    	
    	return boton;
    }
    
    private JButton boton_Eliminar(){
    	JButton boton = new JButton("Eliminar",
    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/delete.png", 50, 50)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lugar!=null && JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar la informacion?")==0){
					
					try {
						log_action.setLog("ELIMINO DATOS DEL LUGAR ..: "+lugar.getTitulo());
						log_action.setFecha(new Fecha().get_fecha_sql());
						log_action.setHora(new Fecha().get_hora_sql());
						set_log();
						
						new Lugar_control().set_Eliminar(lugar);
						set_limpiar();
					
					} catch (IllegalStateException | SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
    	
    	return boton;
    }
    
    private JButton boton_Limpiar(){
    	JButton boton = new JButton("Limpiar",
    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/limpiar1.png", 50, 50)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					set_limpiar();
				} catch (IllegalStateException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
    	
    	return boton;
    }
    
    private JButton get_boton_personaADD(){
    	boton_addPersona = new JButton("ADM Habitantes", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/addUser.png", 50, 50)));
    	boton_addPersona.setEnabled(false);
    	boton_addPersona.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					

					new Persona_form(lugar);
					
					set_limpiar();
				
				} catch (IllegalStateException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    	return boton_addPersona;
    }
    
    private void set_limpiar() throws IllegalStateException, SQLException{
    	
    	this.lugar = null;
    	
    	this.contacto_frm.set_limpiar();
    	
    	this.lugar_frm.set_limpiar();
    	
    	resultado.setQuery("SELECT id,titulo"
    		+ " from lugares");
    	
    	 boton_addPersona.setEnabled(false);
    	
    }
    
	protected void set_log(){
		new Action_log().set_insert(log_action);
	}

}
