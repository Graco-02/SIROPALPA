package Ventanas_P;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controladores.Action_log;
import Controladores.Usuario_control;
import Entidades.Usuario;
import Entidades.log_accion;
import Formularios.Loggin_form;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Ventanas_S.Inicio;

public class Loggin extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel etiqueta_img;
	private Loggin_form log_form;
	protected log_accion log_action;
	
	public Loggin() {
		log_form= new Loggin_form();
		log_action = new log_accion();
		
		this.setTitle("Acceso a Usuario");
		this.setSize(600, 300);
		this.setModal(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_panel());
		
	    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    manager.addKeyEventDispatcher(new MyDispatcher());
	    
		this.setVisible(true);
	}

	private JPanel get_panel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_Etiqueta_Img(),BorderLayout.WEST);
		panel.add(get_panel_Funciones(),BorderLayout.SOUTH);
		panel.add(get_panel_Datos(),BorderLayout.CENTER);
		return panel;
	}

	private JPanel get_panel_Funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.DARK_GRAY);
		return panel;
	}
	
	private JPanel get_panel_Datos(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(""));
		log_form.add(get_logBt());
		panel.add(log_form, BorderLayout.CENTER);
		return panel;
	}
	
	private JLabel get_Etiqueta_Img(){
		etiqueta_img = new JLabel();
		etiqueta_img.setIcon(new ImageIcon(
				new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/acceso.png", 300, 250)));
		return etiqueta_img;
	}
	
	private JButton get_logBt(){
		
		JButton bt = new JButton("Accesar",new ImageIcon("imagenes/ingresar.png"));
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
                  set_proceso();
			}
		});
		
		return bt;
	}//fin del metodo
	
	
	protected void set_log(){
		new Action_log().set_insert(log_action);
	}

	protected void set_proceso(){
		if(log_form.validar_campos()){// valido que los campos esten completados
			Usuario.setUser(log_form.get_usuario().getUser());//agrego el nombre de usuario 
			Usuario.setClave(log_form.get_usuario().getClave());//agrego la clave del usuario
			Usuario.setFecha(log_form.get_usuario().getFecha());//agrego la fecha
			
			
			// valido que exista un usuario registrado con ese nombre y clave
			
			if(new Usuario_control().valida_usuario()){
				try {
					dispose();
					new Ventana_Principal();
				       
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{// en caso de que no se encuentre ninguna coincidencia deniega el acceso
                JOptionPane.showMessageDialog(null, "Acceso denegado");
			}
			
		}// fin del primer if
	}

	
	private class MyDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent(KeyEvent e) {
        	int opcion = e.getKeyCode();
			//System.out.print("PULSO" + opcion);
        	if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if(opcion == KeyEvent.VK_ENTER){
                    	set_proceso();
                    }
            }
    		
            return false;
        }
    }//fin de la clase alterna
	
	
}
