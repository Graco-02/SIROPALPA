package Ventanas_S;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controladores.Correo_control;
import Controladores.Reg_habitantes_control;
import Controladores.Telefono_Control;
import Entidades.Contacto;
import Entidades.Correo;
import Entidades.Telefono;
import Utilidades.Gestor_Imagenes;

public class Email_Tele_Ventana extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_telefono;
	private JPanel panel;
	private Telefono telefono;
	private Correo email;
	private JButton boton_del;
	private JPanel panel_opc;
	private JTextField txt_correo;
	private int opcion;
	private int cont_id;
	
	public Email_Tele_Ventana(int opc,Telefono tel,Correo email,int cont) {
		this.opcion = opc;
		this.cont_id = cont;
		if(opc == 1){
			this.setTitle("Telefono");
			this.add(set_Jpanel());
			panel.add(get_txt_telefono(),BorderLayout.CENTER);
			panel.add(set_Jpanel_opciones(),BorderLayout.SOUTH);
			
			if(tel!=null){
				this.telefono = tel;
				txt_telefono.setText(tel.getTelefono());
				panel_opc.add(get_boton_DEL());
			}
			
			set_inicializar();
		}else{
			this.setTitle("Correo");
			this.add(set_Jpanel());
			panel.add(get_txt_correo(),BorderLayout.CENTER);
			panel.add(set_Jpanel_opciones(),BorderLayout.SOUTH);
			
			if(email!=null){
				this.email = email;
				txt_correo.setText(this.email.get_correo());
				panel_opc.add(get_boton_DEL());
			}
			set_inicializar();
		}
		this.setVisible(true);
	}
	
	private void set_inicializar(){
		this.setSize(350, 200);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private JPanel set_Jpanel(){
		panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setLayout(new BorderLayout());
		return panel;
	}
	
	private JPanel set_Jpanel_opciones(){
		panel_opc = new JPanel();
		panel_opc.setBorder(BorderFactory.createTitledBorder(""));
		panel_opc.setLayout(new GridLayout(1,2));
		panel_opc.add(get_add());
		return panel_opc;
	}	
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		return etiqueta;
	}
	
	private JTextField get_txt_telefono(){
		txt_telefono = new JTextField();
		txt_telefono.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
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
		txt_correo = new JTextField();
		txt_correo.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		return txt_correo;
	}		
	
	
	private JButton get_add(){		
		JButton boton = new JButton( "Add",
				new ImageIcon( new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50) ) );
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(telefono!=null && email == null && cont_id !=0){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de modificar los datos?") == 0){
					    telefono.setTelefono(txt_telefono.getText());
						new Telefono_Control().set_modificar(telefono);
					}	
				}
				
				if(telefono==null && email != null && cont_id !=0){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de modificar los datos?") == 0){
					    email.set_correo(txt_correo.getText());
						new Correo_control().set_modificar(email);
					}	
				}
				
				if (telefono==null && email == null && cont_id !=0){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de agregar los datos?") == 0){
					    if(opcion == 1){
					    	telefono = new Telefono();
					    	telefono.setId_contacto(cont_id);
					    	telefono.setTelefono(txt_telefono.getText());
							new Telefono_Control().set_insert(telefono);
					    }else{
					    	email = new Correo();
					    	email.setId_contacto(cont_id);
					    	email.set_correo(txt_correo.getText());
							new Correo_control().set_insert(email);
					    }
					}
				}else{
					
				}
				

				JOptionPane.showMessageDialog(null, "Acccion Realizada");
				dispose();
			}
		});
		
		return boton;
	}	

	private JButton get_boton_DEL(){
		boton_del = new JButton("ELIMINAR", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/delete.png", 50, 50)));
		boton_del.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(telefono!=null){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de ELIMINAR los datos?") == 0){
					   new Telefono_Control().set_eliminar(telefono);
					   JOptionPane.showMessageDialog(null, "Acccion Realizada");
					}	
				}else{
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de ELIMINAR los datos?") == 0){
						   new Correo_control().set_eliminar(email);
						   JOptionPane.showMessageDialog(null, "Acccion Realizada");
					}
				}
				
				dispose();
			}
		});
		return boton_del;
	}	
	
	
	public String get_dato(){
		if(opcion==1){
			return txt_telefono.getText();
		}else{
			return txt_correo.getText();
		}
	}
	
}
