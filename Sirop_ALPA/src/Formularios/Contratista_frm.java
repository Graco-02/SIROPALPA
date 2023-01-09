package Formularios;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controladores.Contratista_control;
import Entidades.Contratista;

public class Contratista_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_descripcion;
	private Contratista contratista;
	
	public Contratista_frm(Contratista contratista) {	
		setLayout(new GridLayout(1, 2));
		add(get_txt_descripcion());
		if (contratista!=null) {
			this.contratista = contratista;
			this.txt_descripcion.setText(this.contratista.getDescripcion());
		}
			
	}
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		
		return etiqueta;
	}
	
	private JTextField get_txt_descripcion(){
		this.add(get_etiqueta("Descripcion"));
		
		txt_descripcion = new JTextField();
		this.txt_descripcion.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,15));

		txt_descripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			
				
			}
		});
		return txt_descripcion;
	}

	public void set_contratista(){
		txt_descripcion.setText(contratista.getDescripcion());
	}
	
	public Contratista get_contratista(){
		if(this.contratista == null){
			this.contratista = new Contratista();
			this.contratista.setId(new Contratista_control().get_NextNum());
		}
		
		this.contratista.setDescripcion(txt_descripcion.getText());
		System.out.println(txt_descripcion.getText());
		return this.contratista;
	}
	
}
