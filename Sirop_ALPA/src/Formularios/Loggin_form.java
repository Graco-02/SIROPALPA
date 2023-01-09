package Formularios;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Entidades.Usuario;
import Utilidades.Fecha;
import Ventanas_S.Date_Time_GUI;

public class Loggin_form extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_User;
	private JPasswordField txt_Clave;
	private JTextField txt_fecha;

	public Loggin_form() {
		this.setLayout(new GridLayout(7, 0));
		this.add(get_txtUser());
		this.add(get_txtPasword());
		this.add(get_txtFecha());
	}
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,16));
		etiqueta.setForeground(Color.BLUE);
		return etiqueta;
	}
	
	private JTextField get_txtUser(){
		this.add(get_etiqueta("Usuario"));
		txt_User = new JTextField();
		return txt_User;
	}
	
	private JPasswordField get_txtPasword(){
		this.add(get_etiqueta("Clave"));
		txt_Clave = new JPasswordField();
		return txt_Clave;
	}
	
	private JTextField get_txtFecha(){
		this.add(get_etiqueta("Fecha"));
		
		txt_fecha = new JTextField(new Fecha().getFechaSystemaYYMMDD());
		txt_fecha.setEditable(false);
		
		txt_fecha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txt_User.requestFocus();
				txt_fecha.setText(new Date_Time_GUI().get_fechaAAAAMMDD());
			}
		});
		
		return txt_fecha;
	}
	
	@SuppressWarnings("deprecation")
	public boolean validar_campos(){
		
		if(txt_User.getText().length()<=0){
			txt_User.requestFocus();
			JOptionPane.showMessageDialog(null, "Faltan Datos");
			return false;
		}else if(txt_Clave.getText().length()<=0){
			txt_Clave.requestFocus();
			JOptionPane.showMessageDialog(null, "Faltan Datos");
			return false;
		}
		
		return true;
	}
	
	public Usuario get_usuario(){
		Usuario user = new Usuario();
		
		user.setUser(txt_User.getText());
		user.setClave(txt_Clave.getText());
		user.setFecha(txt_fecha.getText());
		
		return user;
	}

}
