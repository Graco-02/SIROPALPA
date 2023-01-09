/**
 * 
 */
package Formularios;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controladores.Foto_control;
import Controladores.Persona_control;
import Entidades.Foto;
import Entidades.Persona;
import Utilidades.Gestor_Imagenes;
import Ventanas_S.Persona_selector;
import Ventanas_S.selector_lugar;

/**
 * @author FFerr
 *
 */
public class Persona_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ruta_foto = "";
	private Persona persona;
	private JTextField txt_nombres;
	private JTextField txt_apellidos;
	private JTextField txt_identificacion;
	private JComboBox tipo_identificacion;
	private Foto foto;
	private JLabel foto_jlb;
	private JPanel panel_data;
	private foto_frm ft_frm;
	
	public Persona_frm(){
		ft_frm = new foto_frm();
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(""));
		
		this.add(get_panel(), BorderLayout.CENTER);
		this.add(ft_frm, BorderLayout.EAST);
	}

	private JPanel get_panel(){
		panel_data = new JPanel();
		panel_data.setBorder(BorderFactory.createTitledBorder(""));
		panel_data.setLayout(new GridLayout(7, 2));
		
		panel_data.add(get_txt_nombres());
		panel_data.add(get_txt_Apellidos());
		panel_data.add(get_txt_Identificacion());
		panel_data.add(get_tipo_identificacion());
		
		panel_data.add(new JLabel());
		
		return panel_data;
	}
	
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		
		return etiqueta;
	}
	
	private JTextField get_txt_nombres(){
		this.panel_data.add(get_etiqueta("Nombres"));
		
		txt_nombres = new JTextField();
		txt_nombres.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(txt_nombres.getText().length() > 500){
					e.consume();
				}
				
			}
			
		});
		
		return txt_nombres;
	}
	
	private JTextField get_txt_Apellidos(){
		this.panel_data.add(get_etiqueta("Apellidos"));
		
		txt_apellidos = new JTextField();
		txt_apellidos.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(txt_apellidos.getText().length() > 500){
					e.consume();
				}
				
			}
			
		});
		
		return txt_apellidos;
	}
	
	
	private JTextField get_txt_Identificacion(){
		this.panel_data.add(get_etiqueta("Identificacion"));
		
		txt_identificacion = new JTextField();
		txt_identificacion.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				switch (tipo_identificacion.getSelectedIndex()) {
				case 1:
					
					if(txt_identificacion.getText().length()>12){
						e.consume();
					}
					
					break;

				default:
					try{
						Integer.parseInt(String.valueOf(e.getKeyChar()));
						
						if(txt_identificacion.getText().length() > 12){
							e.consume();
						}else if(txt_identificacion.getText().length()==3){
							txt_identificacion.setText(txt_identificacion.getText()+"-");
						}else if(txt_identificacion.getText().length()==11){
							txt_identificacion.setText(txt_identificacion.getText()+"-");
						}
						
					}catch (NumberFormatException num) {
						e.consume();
						JOptionPane.showMessageDialog(null, "Solo se admiten digitos");
					}
					
					break;
				}
				
				
			}
			
		});
		
		return txt_identificacion;
	}
	
	
	private JComboBox get_tipo_identificacion(){
		this.panel_data.add(get_etiqueta("Tipo Identificacion"));
		String[] opc = {"CEDULA","PASAPORTE"};
		
		tipo_identificacion = new JComboBox(opc);
		
		return tipo_identificacion;
	}
	
	public boolean get_validar(){
		if(txt_nombres.getText().length() < 3){
			txt_nombres.requestFocus();
			return false;
		}else if(txt_apellidos.getText().length() < 3){
			txt_apellidos.requestFocus();
			return false;
		}else if(txt_identificacion.getText().length() < 11){
			txt_identificacion.requestFocus();
			return false;
		}
		
		return true;
	}
	
	public Persona get_Persona(){
		
		if(persona == null){
			
			persona = new Persona();
			persona.setId(new Persona_control().get_NextNum());
			
			foto = ft_frm.get_foto();
			
    		persona.setFoto(foto);
		}
		
		persona.setNombres(txt_nombres.getText());
		persona.setApellidos(txt_apellidos.getText());
		persona.setIdentificacion(txt_identificacion.getText());
		persona.setTipo_ident(tipo_identificacion.getSelectedIndex());
		
		persona.setFoto(ft_frm.get_foto());
		
		return persona;
	}
	
	public void set_persona(Persona persona){
		this.persona = persona;
		
		this.txt_nombres.setText(this.persona.getNombres());
		this.txt_apellidos.setText(this.persona.getApellidos());
		this.txt_identificacion.setText(this.persona.getIdentificacion());
		this.tipo_identificacion.setSelectedIndex(this.persona.getTipo_ident());
		
		if(persona.getFoto()!=null && persona.getFoto().get_ruta_foto().length() > 0){//valido que se haya seleccionado una foto
			this.foto = this.persona.getFoto();
			
			ft_frm.set_foto(foto);
		}//fin del if para las fotos
	}
	
	
	
	public void set_limpiar(){
		this.persona = null;
		this.foto = null;
		
		this.txt_nombres.setText("");
		this.txt_apellidos.setText("");
		this.txt_identificacion.setText("");
		this.tipo_identificacion.setSelectedIndex(0);
		
		ft_frm.set_limpiar();
	}
	
	private JButton get_boton_select(){
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/", 50, 50)));
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				set_persona(new Persona_selector().get_persona());
			}
		});
    	return boton;
    }
	
	public void set_add_buton_select(){
		panel_data.add(get_boton_select());
	}
	
	public void set_add(JButton boton){
		panel_data.add(boton);
	}
	
	public void set_rezise_foto(int w,int h){
		ft_frm.resize_img(w, h);
	}
	
}//fin de la clase
