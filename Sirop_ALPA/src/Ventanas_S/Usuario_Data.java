/**
 * 
 */
package Ventanas_S;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import Controladores.Usuario_control;
import Entidades.Usuario;
import Formularios.Usuario_form;
import Utilidades.Gestor_Imagenes;

/**
 * @author FFerr
 *
 */
public class Usuario_Data extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario_form usuario_frm;
	private Usuario user;
	
	/**
	 * 
	 */
	public Usuario_Data() {
		usuario_frm = new Usuario_form();
		
		this.setTitle("Usuario Data");
		this.setSize(600, 300);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}
	
	public Usuario_Data(Usuario user) {
		this.user = user; // inicializo la instancia del usuario recivido
		usuario_frm = new Usuario_form();//inicializa la instancia del formulario de usuario
		usuario_frm.set_usuario(user);//agrego la instancia de usuario al formulario para accesar a sus atributos
		
		this.setTitle("Usuario Data");
		this.setSize(600, 300);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_conten());
		this.setVisible(true);
	}

	private JPanel get_conten(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(usuario_frm, BorderLayout.CENTER);
		panel.add(get_panel_funcion(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel get_panel_funcion(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_boton_G(), BorderLayout.CENTER);
		return panel;
	}
	
	 private JButton get_boton_G(){
		 JButton boton = new JButton("Add",
	    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
	    	
		 boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(usuario_frm.get_validar() && user == null){//valido que los datos esten completos y que la instancia de usuario este vacia
					
					user = usuario_frm.get_usuario();
					
					new Usuario_control().set_insert(user);
					dispose();
					
				}else if(user != null){//valido que la instacia de usuario este inicializada y que los datos esten completos
					//modificar
					user = usuario_frm.get_usuario();
					
					new Usuario_control().set_modificar(user);
					
					user = null;//limpio la instancia
					
					dispose();
				}
			
			}
		});
		 
		 return boton;
	 }
	
}
