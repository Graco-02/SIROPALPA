package Formularios;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utilidades.Fecha;
import Ventanas_S.Date_Time_GUI;
import Ventanas_S.selector_lugar;
import Controladores.Averias_control;
import Controladores.Contratista_control;
import Controladores.Lugar_control;
import Entidades.Averias;
import Entidades.Contratista;
import Entidades.Lugar;

public class Averias_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_descripcion;
	private Averias averia;
	private JTextField txt_lugar;
	protected Lugar apt;
	private JTextField txt_titulo;
	private JComboBox estado;
	private JTextField txt_fecha_ini;
	protected Date fecha_ini;
	
	public Averias_frm(Averias averia) {
		if (averia!=null) {
			this.averia = averia;
		}
		
		setLayout(new GridLayout(5, 2));
		add(get_txt_lugar());
		add(get_txt_subject());
		add(get_txt_descripcion());
		add(get_txtFecha_Ini());
		add(get_Estado());
	}
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		
		return etiqueta;
	}
	
	private JTextField get_txt_lugar(){
		this.add(get_etiqueta("SOLICITANTE"));
		
		txt_lugar = new JTextField();
		txt_lugar.setEditable(false);
		
	    txt_lugar.addMouseListener(new MouseAdapter() {


				@Override
				public void mouseClicked(MouseEvent arg0) {

					apt = new selector_lugar().get_lugar();
					txt_lugar.setText(apt.getTitulo());
					
				}
		});
		
		return txt_lugar;
	}
	
	private JTextField get_txt_subject(){
		this.add(get_etiqueta("TITULO"));
		
		txt_titulo = new JTextField();
		txt_titulo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			
				
			}
		});
		return txt_titulo;
	}
	
	private JTextField get_txt_descripcion(){
		this.add(get_etiqueta("Descripcion"));
		
		txt_descripcion = new JTextField();
		txt_descripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			
				
			}
		});
		return txt_descripcion;
	}

	private JTextField get_txtFecha_Ini(){
		this.add(get_etiqueta("FECHA"));
		
	    txt_fecha_ini = new JTextField();
		txt_fecha_ini.setEditable(false);
		txt_fecha_ini.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				fecha_ini = new Date_Time_GUI().get_fecha_sql();
				txt_fecha_ini.setText(fecha_ini.toString());
				txt_fecha_ini.setBackground(Color.WHITE);
				
			}
			
		});
		
		return txt_fecha_ini;
	}
	
	private JComboBox get_Estado(){
		add(get_etiqueta("ESTADO"));
		String bloqueo_op[] = {"REPORTADA","REPARANDO","TERMINADA"};
		estado = new JComboBox(bloqueo_op);
		
		return estado;
	}
	
	public void set_averia(){
		if(averia!=null){
			txt_descripcion.setText(averia.getDescripcion());
			fecha_ini = averia.getFecha();
			apt = averia.getSolicitante();
			txt_titulo.setText(averia.getTitulo());
			estado.setSelectedIndex(averia.getEstado());
			txt_fecha_ini.setText(fecha_ini.toString());
			txt_lugar.setText(apt.getTitulo());
			
		}
	}
	
	public Averias get_averia(){
		if(averia == null){
			averia = new Averias();
			averia.setId(new Averias_control().get_NextNum());
		}
		
		averia.setDescripcion(txt_descripcion.getText());
		averia.setEstado(estado.getSelectedIndex());
		averia.setFecha(fecha_ini);
		averia.setSolicitante(apt);
		averia.setTitulo(txt_titulo.getText());
		
		return averia;
	}
	
	public boolean get_validar(){
		if(txt_descripcion.getText().length() < 10){
			txt_descripcion.requestFocus();
			JOptionPane.showMessageDialog(null, "DEBE ESCRIBIR UNA DESCRIPCION COHERENTE");
			return false;
		}else if(apt == null){
			JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN SOLICITANTE");
			return false;
		}else if(fecha_ini == null){
			JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UNA FECHA");
			return false;
		}
		
		return true;
	}
	
	public void set_limpiar(){
		averia = null;
		apt = null;
		fecha_ini = null;
		txt_descripcion.setText("");
		txt_titulo.setText("");
		txt_lugar.setText("");
	}
	
}
