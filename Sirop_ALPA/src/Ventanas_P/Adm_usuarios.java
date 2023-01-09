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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controladores.Action_log;
import Controladores.Lugar_control;
import Controladores.Usuario_control;
import Entidades.Usuario;
import Entidades.log_accion;
import Formularios.Usuario_form;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Usuario_Data;

public class Adm_usuarios extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private Usuario_form user_form;
	private Usuario user;
	private JButton boton_eliminar;
	protected log_accion log_action;
	

	public Adm_usuarios() {
		log_action = new log_accion();
		
		this.setTitle("ADMINISTRADOR DE USUARIOS");
        this.setSize(700,500);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_panel_contnedor());
        this.setVisible(true);
	}

	private JPanel get_panel_contnedor() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_panel_norte(),BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel get_panel_norte(){
		user_form = new Usuario_form();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(),BorderLayout.CENTER);
		panel.add(get_panel_funciones(),BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel get_panel_centro(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(),BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel get_panel_funciones(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_G());
		panel.add(get_boton_Eliminar());
		panel.add(get_boton_limpiar());
		
		return panel;
	}
	
	 private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_Usuarios();
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {

	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
				try {
					
					user = new Usuario_control().get_usuario(
							Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0))));
					
				} catch (NumberFormatException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				boton_eliminar.setEnabled(true);
	        	
	          if (e.getClickCount() == 2){

	            	try {
						try {
							

							new Usuario_Data(user);
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
	            	
	            	} catch (IllegalStateException e1) {
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

	 private JButton get_boton_G(){
		 JButton boton = new JButton("Add",
	    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
	    	
		 boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				new Usuario_Data();
				
				
				set_limpiar();
			}
		});
		 
		 return boton;
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
	 
	 private JButton get_boton_Eliminar(){
	    	boton_eliminar = new JButton("Borrar", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/delete.png", 50, 50)));
	    	boton_eliminar.setEnabled(false);
	    	boton_eliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de borrar el usuario "+ user.getUser()) == 0){ 
						log_action.setLog("Elimino Datos del usuario ..:" + user.getUser());
						log_action.setFecha(new Fecha().get_fecha_sql());
						log_action.setHora(new Fecha().get_hora_sql());
						set_log();
						
						new Usuario_control().set_Eliminar(user);
						set_limpiar();
					}
				}
			});
	    	return boton_eliminar;
	 }
	 
	 private void set_limpiar(){
		 
		 try {
			
			this.resultado.setQuery(new ManejadorReportes().get_query_usuarios());
			this.user = null;
			//this.user_form.set_limpiar();
			this.boton_eliminar.setEnabled(false);
		 } catch (IllegalStateException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	 protected void set_log(){
			new Action_log().set_insert(log_action);
		}
}
