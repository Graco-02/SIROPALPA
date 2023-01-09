package Formularios;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Ventanas_S.Persona_selector;
import Entidades.Usuario;

public class Usuario_form extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_usuario;
	private JPasswordField txt_psw;
	private JPasswordField txt_psw2;
	private JComboBox llave;
	private Usuario usuario;
	private int id_apartamento;
	
	public Usuario_form() {
		this.setLayout(new GridLayout(0, 2));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.add(get_txt_usuario());
		this.add(get_clave());
		this.add(get_R_clave());
		this.add(get_llave());
	}
	
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		return etiqueta;
	}
	
	private JTextField get_txt_usuario(){
		this.add(get_etiqueta("Usuario"));
		
		txt_usuario = new JTextField();
		txt_usuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(txt_usuario.getText().length() > 8) arg0.consume();
			}
		});
		return txt_usuario;
	}
	
	private JPasswordField get_clave(){
		this.add(get_etiqueta("Clave"));
		
		txt_psw = new JPasswordField();
		txt_psw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(txt_psw.getText().length() > 8){
					arg0.consume();
				}
			}
		});
		return txt_psw;
	}

	private JPasswordField get_R_clave(){
		this.add(get_etiqueta("Confirmar Clave"));
		
		
		txt_psw2 = new JPasswordField();
		txt_psw2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(txt_psw2.getText().length() > 8){
					arg0.consume();
				}
			}
		});
		return txt_psw2;
	}
	
	private JComboBox get_llave(){
		this.add(get_etiqueta("Tipo Acceso"));
		
		String[] opc = {"Administrador","Recepcion","Condomino"};
		
		llave = new JComboBox(opc);
		llave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(llave.getSelectedIndex() == 2){
					//obtengo el id del apartamento a asignar segun el habitante seleccionado
					//Persona_selector() puede recibir 3 valores 
					//1 consulta habitantes, 2 consulta contratistas, 3 global
					new Persona_selector(1);
				}
			}
		});
		return llave;
	}
	
	
	
	
	public Usuario get_usuario(){
		if(this.usuario == null){
			usuario = new Usuario();
		}
		
		usuario.setUser(txt_usuario.getText());
		
		if(txt_psw.getText().equals(txt_psw2.getText())){
			
			usuario.setClave(txt_psw.getText());
			
		}else{
			JOptionPane.showMessageDialog(null, "La claves deben coincidir");
			txt_psw2.requestFocus();
			return null;
		}
		
		usuario.setLlave(llave.getSelectedIndex());
		
		return usuario;
	}
	
	public void set_usuario(Usuario user){
		this.usuario = user;
		
		txt_usuario.setText(usuario.getUser());
		txt_psw.setText(user.getClave());
		
		llave.setSelectedIndex(user.getLlave());
	}
	
	public boolean get_validar(){
		if(txt_usuario.getText().length() < 3){
			JOptionPane.showMessageDialog(null, "El nombre de usuario debe contener 3 caracteres");
			txt_usuario.requestFocus();
			return false;
		}else if(llave.getSelectedIndex() != 2 && (txt_psw.getText().length() < 4 || !txt_psw.getText().equals(txt_psw2.getText()))){
			JOptionPane.showMessageDialog(null, "Hay un problema con las claves deben ser iguales y tener como minimo 4 caracteres");
			txt_usuario.requestFocus();
			return false;
		}
		return true;
	}
	
	public void set_limpiar(){
		this.usuario = null;
		txt_psw.setText("");
		txt_psw2.setText("");
		txt_usuario.setText("");
	}
	
}
