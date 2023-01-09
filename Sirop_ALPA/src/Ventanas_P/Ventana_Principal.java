/**
 * 
 */
package Ventanas_P;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.FeatureDescriptor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controladores.Action_log;
import Entidades.Usuario;
import Entidades.log_accion;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Ventanas_S.Add_lista_negra;
import Ventanas_S.reserva_formulario;

/**
 * @author Graco
 *
 */
public class Ventana_Principal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	

	protected log_accion log_action;
	
	public Ventana_Principal() {
		log_action = new log_accion();
		
		this.setTitle("SIROP_ALPA 1.0/2018");
		this.setSize(1000,700);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_PanelP());
		
		 
	    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    manager.addKeyEventDispatcher(new MyDispatcher());
	  //  this.pack(); 
	    this.setVisible(true);
		
	}

	private JPanel get_PanelP() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_Panel_funciones(),BorderLayout.WEST);
		panel.add(get_Panel_Centro(),BorderLayout.CENTER);
		panel.add(get_Panel_Info(),BorderLayout.NORTH);
		return panel;
	}
	
	private JPanel get_Panel_funciones() {
		JPanel panel = new JPanel();
		
		if(Usuario.getLlave() == 0){
			panel.setLayout(new GridLayout(8, 0));
		}else{
			panel.setLayout(new GridLayout(3, 0));
		}
		
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.DARK_GRAY);
		
		if(Usuario.getLlave() == 0){
			panel.add(get_boton());
			panel.add(get_boton2());
			panel.add(get_boton3()); //gestion de visitas
			panel.add(get_boton4()); // gestion de paquetes
			panel.add(get_boton6()); 
			panel.add(get_boton7());
			panel.add(get_boton8());
		}else{
			panel.add(get_boton3());
			panel.add(get_boton4());
		}
		
		panel.add(get_boton5());
		
		return panel;
	}
	
	private JPanel get_Panel_Centro() {
		JLabel et = new JLabel();
		et.setIcon(new ImageIcon(
				new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/alco2.jpg", 1200, 600)));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.WHITE);
		panel.add(et,BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel get_Panel_Info() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.add(get_etiqueta(new Fecha().getFechaSystemaDDMMYY()));
		panel.add(get_etiqueta(Usuario.getUser()));
		
		return panel;
	}

	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		etiqueta.setBorder(BorderFactory.createTitledBorder(""));
		return etiqueta;
	}
	
	private JButton get_boton(){
		JButton boton = new JButton("F1-USUARIO",
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/acceso-usuarios.png", 50, 50)));
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				log_action.setLog("Acceso Adm_usuarios");
    			set_log();
    			new Adm_usuarios();
			}
		});
		return boton;
	}
	
	private JButton get_boton2(){
		JButton boton = new JButton("F2-LUGAR",
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/apartamento.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				log_action.setLog("Acceso Adm_lugares");
    			set_log();
    			new Adm_lugares();
    			
			}
		});
		return boton;
	}
	
	private JButton get_boton3(){
		JButton boton = new JButton("F3-VISITA",
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/visita.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				log_action.setLog("Acceso Gestor_visitas");
    			set_log();
    			new Gestor_visitas();
    			
			}
		});
		return boton;
	}
	
	private JButton get_boton4(){
		JButton boton = new JButton("F4-PAQUETE",
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/paquete.png", 50, 50)));
     	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
    			log_action.setLog("Acceso Gestor_paquetes");
    			set_log();
    			new Gestor_paquetes();

			}
		});
		return boton;
	}
	

	private JButton get_boton5(){
		JButton boton = new JButton("F9-SALIR",
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/salir.png", 50, 50)));
     	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
    			log_action.setLog("SALIO");
    			set_log();
				Ventana_Principal.this.dispose();
			}
		});
		return boton;
	}
	
	private JButton get_boton6(){
		 
     JButton boton_betados = new JButton("F5-BETADOS", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/beto2.png", 50, 50)));
    	boton_betados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
    			log_action.setLog("Gestor_Betados");
    			set_log();
    			new Gestor_Betados();
			}
		});
    	
    	return boton_betados;
   }
	
	private JButton get_boton7(){
		 
	     JButton boton_betados = new JButton("F6-RESERVAS", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/reserva.png", 50, 50)));
	    	boton_betados.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	    			log_action.setLog("RESERVAS");
	    			set_log();
	    			new Adm_Reservas();
				}
			});
	    	
	    	return boton_betados;
	   }
	
	
	private JButton get_boton8(){
		 
	     JButton boton_averias = new JButton("F7-AVERIAS", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/reparacion.jpg", 50, 50)));
	    	boton_averias.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	    			log_action.setLog("AVERIAS");
	    			set_log();
	    			new Adm_averias();
				}
			});
	    	
	    	return boton_averias;
	   }
	

	
	
	private class MyDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent(KeyEvent e) {
        	int opcion = e.getKeyCode();
        	log_action.setFecha(new Fecha().get_fecha_sql());
			log_action.setHora(new Fecha().get_hora_sql());
			
        	if (e.getID() == KeyEvent.KEY_PRESSED) {
        		switch (opcion) {
        		case KeyEvent.VK_F1:
        			log_action.setLog("Acceso Adm_usuarios");
        			set_log();
        			if(Usuario.getLlave()==0){
        				new Adm_usuarios();
        			}
        			break;
        		case KeyEvent.VK_F2:
        			log_action.setLog("Acceso Adm_lugares");
        			set_log();
        			if(Usuario.getLlave()==0){
        			   new Adm_lugares();
        			}
        			break;
        		case KeyEvent.VK_F3:
        			log_action.setLog("Acceso Gestor_visitas");
        			set_log();
        			new Gestor_visitas();
        			
        			break;
        		case KeyEvent.VK_F4:
        			log_action.setLog("Acceso Gestor_paquetes");
        			set_log();
        			new Gestor_paquetes();
        		
        			break;
        		case KeyEvent.VK_F5:
        			log_action.setLog("Gestor_Betados");
        			set_log();
        			if(Usuario.getLlave()==0){
        				new Gestor_Betados();
        			}
        			break;
        		case KeyEvent.VK_F6:
        			log_action.setLog("Reservas");
        			set_log();
        			if(Usuario.getLlave()==0){
        				new Adm_Reservas();
        			}
        			break;
        			
        		case KeyEvent.VK_F7:
        			log_action.setLog("AVERIAS");
        			set_log();
        			if(Usuario.getLlave()==0){
        				new Adm_averias();
        			}
        			break;
        			
        		case KeyEvent.VK_F9:
        			log_action.setLog("SALIO");
        			set_log();
        			Ventana_Principal.this.dispose();
        			
        			break;
        		}
            }
    		
            return false;
        }
    }//fin de la clase alterna

	protected void set_log(){
		new Action_log().set_insert(log_action);
	}
}
