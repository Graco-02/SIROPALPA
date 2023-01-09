/**
 * 
 */
package Formularios;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utilidades.Gestor_Imagenes;
import Ventanas_S.Email_Tele_Ventana;
import Controladores.Contacto_control;
import Entidades.Contacto;
import Entidades.Correo;
import Entidades.Telefono;
import Entidades.Usuario;

/**
 * @author FFerr
 *
 */
public class Contactos_frm extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contacto contacto;
	private JTextField txt_telefono;
	private JTextField txt_correo;
	private JComboBox telefonos_list;
	private JComboBox correos_list;
	private int seleccion_T = 0;
	private int seleccion_C = 0;
	private ArrayList<Telefono> lista_telefonos;
	private ArrayList<Correo> lista_correo;
	
	public Contactos_frm(){
		lista_telefonos = new ArrayList<Telefono>();
		lista_correo = new ArrayList<Correo>();
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.setLayout(new GridLayout(2, 3));
		this.add(get_etiqueta("Telefono"));
		this.add(get_telefonos());
		this.add(get_boton_add_tel());
		this.add(get_etiqueta("Correo"));
		this.add(get_correos());
		this.add(get_boton_add_Email());
	//	this.add(get_boton());
	}
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		return etiqueta;
	}
	
	private JTextField get_txt_telefono(){
		this.add(get_etiqueta("Telefono"));
		
		txt_telefono = new JTextField();
		txt_telefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
				try{
					
					Integer.parseInt(String.valueOf(caracter));
					if(txt_telefono.getText().length()==3){
						txt_telefono.setText(txt_telefono.getText() + "-");
					}else if(txt_telefono.getText().length()==7){
						txt_telefono.setText(txt_telefono.getText() + "-");
					}else if(txt_telefono.getText().length() > 11){
						e.consume();
					}
					
					
				}catch(NumberFormatException a){
					e.consume();
					JOptionPane.showMessageDialog(null, "Solo se admiten digitos en este campo");
				}
			}
		});
		return txt_telefono;
	}
	
	private JTextField get_txt_correo(){
		this.add(get_etiqueta("Correo"));
		
		txt_correo = new JTextField();
		txt_correo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txt_correo.getText().length() > 200){
					e.consume();
				}
			}
		});
		return txt_correo;
	}
	
	private JComboBox get_telefonos(){
		
		telefonos_list = new JComboBox();

		return telefonos_list;
	}
	
	private JComboBox get_correos(){
		
		correos_list = new JComboBox();
		return correos_list;
	}
	
	private JButton get_boton(){
		this.add(new JLabel());this.add(new JLabel());
		
		JButton boton = new JButton( "Add",
				new ImageIcon( new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50) ) );
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txt_correo.getText().length() > 10){
					if(seleccion_C > 0){
						correos_list.removeItemAt(seleccion_C);//remueve el registro en la posicion dada
						correos_list.insertItemAt(txt_correo.getText(), seleccion_C);//agrega un registro en una posicio dada
						
						if(contacto!=null){//valido que se este modificando el registro al validar que la instancia sea diferente de null
							//cambio el atributo  por el nuebo insertado
							contacto.get_Correo(seleccion_C-1).set_correo(
									String.valueOf(correos_list.getItemAt(seleccion_C)));
						}
						
					}else{//si no se esta cambiando el registro se agrega normal a la cola
						correos_list.addItem(txt_correo.getText());
					}
					
					txt_correo.setText("");
				
				} if(txt_telefono.getText().length() > 11){
					if(seleccion_T > 0){
						telefonos_list.removeItemAt(seleccion_T);
						telefonos_list.insertItemAt(txt_telefono.getText(), seleccion_T);
						
						if(contacto!=null){//valido que se este modificando el registro al validar que la instancia sea diferente de null
							//cambio el atributo  por el nuebo insertado
							contacto.get_Telefono(seleccion_T-1).set_telefono(
									String.valueOf(telefonos_list.getItemAt(seleccion_T)));
						}
						
					}else{
						telefonos_list.addItem(txt_telefono.getText());
					}
					
					txt_telefono.setText("");
				
				}
				
				
			}
		});
		
		return boton;
	}

	private JButton get_boton_add_tel(){		
		JButton boton = new JButton( "Add",
				new ImageIcon( new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50) ) );
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (contacto!=null && telefonos_list.getSelectedIndex()!=0){
					contacto =  new Contacto_control().get_contacto(contacto.get_id());
					
					new Email_Tele_Ventana(1,
							contacto.get_Telefono(telefonos_list.getSelectedIndex()-1)
							,null,0);
					
				}else{
					String dato = new Email_Tele_Ventana(1,null,null,0).get_dato();
					telefonos_list.addItem(dato);
				    Telefono tel = new Telefono();
				    tel.set_telefono(dato);
					lista_telefonos.add(tel);
				}	
			}
		});
		
		return boton;
	}
	
	private JButton get_boton_add_Email(){		
		JButton boton = new JButton( "Add",
				new ImageIcon( new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50) ) );
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (contacto!=null && correos_list.getSelectedIndex()!=0){
					new Email_Tele_Ventana(2,
							null,contacto.get_Correo(correos_list.getSelectedIndex()-1),0);
					
				}else{
					String dato = new Email_Tele_Ventana(2,null,null,0).get_dato();
					correos_list.addItem(dato);
				    Correo correo = new Correo();
				    correo.set_correo(dato);
					lista_correo.add(correo);		
					
				}
			}
		});
		
		return boton;
	}
	
	
	public Contacto get_contacto(){
		
		if(contacto == null){
			
			contacto = new Contacto();
			contacto.set_id(new Contacto_control().get_NextNum());
			contacto.set_lista_Telefonos(lista_telefonos);
			contacto.set_lista_correos(lista_correo);
			
			for(int i = 0;i<lista_telefonos.size();i++){
				lista_telefonos.get(i).setId_contacto(contacto.get_id());
			}
			
			for(int i = 0;i<lista_correo.size();i++){
				lista_correo.get(i).setId_contacto(contacto.get_id());
			}
			
			/*
			for(int i=0;i<telefonos_list.getItemCount();i++){
				Telefono telefono = new Telefono();
				telefono.set_telefono(String.valueOf(telefonos_list.getItemAt(i)));
				contacto.add_Telefono(telefono);
			}
			
			
			for(int k=0;k<correos_list.getItemCount();k++){
				Correo correo = new Correo();
				correo.set_correo(String.valueOf(correos_list.getItemAt(k)));
				contacto.add_correo(correo);
			}*/
			
		}else{
			Usuario.setCorreo_ini(correos_list.getItemCount());
			Usuario.setTelefono_ini(telefonos_list.getItemCount());
			if(telefonos_list.getItemCount()>contacto.get_lista_telefonos().size()){
				for(int k=contacto.get_lista_telefonos().size();k<telefonos_list.getItemCount();k++){
					Telefono telefono = new Telefono();
					telefono.set_telefono(String.valueOf(telefonos_list.getItemAt(k)));
					contacto.add_Telefono(telefono);
				}
			}
			
			if(correos_list.getItemCount()>contacto.get_lista_correos().size()){
				for(int k=contacto.get_lista_correos().size();k<correos_list.getItemCount();k++){
					Correo correo = new Correo();
					correo.set_correo(String.valueOf(correos_list.getItemAt(k)));
					contacto.add_correo(correo);
				}
			}
		}
		return contacto;
	}//
	
	public void set_contacto(Contacto contacto){
		this.contacto = contacto;
		
		try{
				telefonos_list.addItem("Nuevo");
			
			for(int i=0;i<contacto.get_lista_telefonos().size();i++){
				telefonos_list.addItem(contacto.get_Telefono(i).getTelefono());
			}
		
				correos_list.addItem("Nuevo");
			
			for(int k=0;k<contacto.get_lista_correos().size();k++){
				correos_list.addItem(contacto.get_Correo(k).get_correo());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}//
	
	public void set_limpiar(){
		this.contacto = null;
		//this.txt_correo.setText("");
		//this.txt_telefono.setText("");
		this.telefonos_list.removeAllItems();
		this.correos_list.removeAllItems();
		lista_telefonos.clear();
		lista_correo.clear();
	}
	
}
