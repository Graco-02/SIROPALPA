/**
 * 
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import Controladores.Action_log;
import Controladores.Reg_Visitas_control;
import Entidades.Persona;
import Entidades.log_accion;
import Entidades.visita;
import Formularios.Persona_frm;
import Formularios.Visita_frm;
import Formularios.busqueda_visita_frm;
import Formularios.lugar_frm;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Utilidades.Visita_stick;
import Ventanas_S.Add_lista_negra;
import Ventanas_S.Add_visita;
import Ventanas_S.Persona_selector;

/**
 * @author FFerr
 *
 */
public class Gestor_visitas extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private visita visita;
	private Persona_frm persona_frm;
	private lugar_frm lugar_frm;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private Visita_frm visita_frm;
	private busqueda_visita_frm buscar_visita_frm;
	private JButton boton_betados;
	protected log_accion log_action;
	
	
	public Gestor_visitas(){
		log_action = new log_accion();
		
		this.setTitle("GESTOR DE VISITAS");
        this.setSize(1000,700);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_content());
        this.setVisible(true);
	}
	
	private JPanel get_content(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(get_Tabla(), BorderLayout.CENTER);
		panel.add(get_funciones(),BorderLayout.EAST);
		return panel;
	}
	
	@SuppressWarnings("unused")
	private JPanel get_centro(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(get_pestanas(), BorderLayout.CENTER);
		panel.add(get_funciones(),BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel get_Tabla(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(), BorderLayout.CENTER);
		
		buscar_visita_frm = new busqueda_visita_frm(resultado);
		panel.add(buscar_visita_frm,BorderLayout.NORTH);
		
		return panel;
	}
	
	private JPanel get_funciones(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(4, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_guardar());
		panel.add(get_boton_limpiar());
		panel.add(get_boton_Lista_negra());
		panel.add(get_salir());
		return panel;
	}
	
	private JTabbedPane get_pestanas(){
		
	    	JTabbedPane pestanas = new JTabbedPane();
	    	
			persona_frm = new Persona_frm();
	    	persona_frm.set_add(get_boton_select());
			
	    	lugar_frm = new lugar_frm();
	    	
	    	lugar_frm.swich_2(false);
	    	
	    	visita_frm = new Visita_frm();
	    	
	    	pestanas.setBorder(BorderFactory.createTitledBorder(""));
	    	pestanas.add("Visitante", persona_frm);
	    	pestanas.add("Lugar", lugar_frm);
	    	pestanas.add("Visita", visita_frm);
	    	
	    	
	    	return pestanas;
	} 
	
	 private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_visitas();
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
	            		visita = new Reg_Visitas_control().get_visita_by_id(
	            				Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0)))
	            				);
	            		
	            		/*
	            		lugar_frm.set_lugar(new Lugar_control().get_lugar(visita.getId_lugar()));
	            		persona_frm.set_persona(visita.getPersona());
	            		visita_frm.set_Visita(visita);
	            		*/
	            		
	            		boton_betados.setText(boton_betados.getText()+" @ ");
	            		new Add_visita(visita,resultado);
	            		
	            	} catch (IllegalStateException e1) {
	            		
	            		
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
	 
	 private JButton get_boton_guardar(){
		 JButton boton_g = new JButton("Nueva Visita", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
		 
		 boton_g.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
						
				new Add_visita(resultado);
				
			}
			
		 });
		 
		 return boton_g;
	 }
	 
	 private JButton get_boton_limpiar(){
	    	JButton boton = new JButton("Limpiar", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/limpiar1.png", 50, 50)));
	    	boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					set_limpiar();
				}
			});
	    	return boton;
	 }
	 
	 private JButton get_boton_select(){
	    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/select_p.png", 25, 25)));
	    	boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Persona per = new Persona_selector().get_persona();
					
					if( new Reg_Visitas_control().valida_estado( per.getId() ) ){
						JOptionPane.showMessageDialog(null, "Esta persona no puede ser seleccionada!");
					}else{
						persona_frm.set_persona(per);
					}
				}
			});
	    	return boton;
	 }
	 
	 private void set_limpiar(){
		 try {
			 this.visita = null;
			// this.persona_frm.set_limpiar();
			// this.lugar_frm.set_limpiar();
			// visita_frm.set_limpiar();
			 boton_betados.setText("BETADOS");
			 resultado.setQuery(new ManejadorReportes().get_query_visitas());
		
		 } catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 private JButton get_salir(){
			JButton boton = new JButton("Salir",
					new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/salir.png", 100, 100)));
	     	boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			return boton;
		}
	 
	 private JButton get_boton_Lista_negra(){
		 
	    	boton_betados = new JButton("BETADOS", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/lisn.jpg", 50, 50)));
	    	boton_betados.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(visita!=null){
						log_action.setLog("accedio a la lista negra");
						set_log();
			
						new Add_lista_negra(visita);
					}else{
						log_action.setLog("accedio a la lista negra");
						set_log();

						new Add_lista_negra();
					
					}
				}
			});
	    	
	    	return boton_betados;
	 }
	 
	 protected void set_log(){
			log_action.setFecha(new Fecha().get_fecha_sql());
			log_action.setHora(new Fecha().get_hora_sql());
			
			new Action_log().set_insert(log_action);
	 }
	 
}
