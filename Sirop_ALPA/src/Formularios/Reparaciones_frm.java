package Formularios;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Ventanas_S.Date_Time_GUI;
import Ventanas_S.Persona_selector;
import Ventanas_S.selector_lugar;
import Controladores.Contratista_control;
import Controladores.Reparaciones_control;
import Entidades.Averias;
import Entidades.Contratista;
import Entidades.Persona;
import Entidades.Reparaciones;

public class Reparaciones_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Contratista contratista;

	private JTextField txt_contratista;
	private Persona datos_personales;

	private JTextField txt_fecha_ini;
	protected Date fecha_ini;
	private JComboBox estado;
	protected Date fecha_fin;
	private Reparaciones reparacion;
	private Averias averia;

	private JTextField txt_fecha_fin;
	
	public Reparaciones_frm(Reparaciones reparaciones,Averias averia) {
		
		
		this.averia = averia;
		
		this.setLayout(new GridLayout(0, 2));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.add(get_txt_contratista());
		this.add(get_txtFecha_Ini());
		this.add(get_Estado());
		
		if(reparaciones!=null){
			this.reparacion = reparaciones;
			set_reparacion();
		}
	}

	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		
		return etiqueta;
	}
	
	private JTextField get_txt_contratista(){
		this.add(get_etiqueta("Contratista"));
		
		txt_contratista = new JTextField();
		txt_contratista.setEditable(false);
		
	    txt_contratista.addMouseListener(new MouseAdapter() {


				@Override
				public void mouseClicked(MouseEvent arg0) {
					datos_personales = new Persona_selector(2).get_persona();
					txt_contratista.setText(datos_personales.get_nombre_apellidos());
					contratista = new Contratista_control().get_contratista_by_persona(datos_personales.getId());
				}
		});
		
		return txt_contratista;
	}
	
	private JTextField get_txtFecha_Ini(){
		this.add(get_etiqueta("FECHA INICIO"));
		
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
	
	private JTextField get_txtFecha_fin(){
		this.add(get_etiqueta("FECHA"));
		
	 txt_fecha_fin = new JTextField();
		txt_fecha_fin.setEditable(false);
		txt_fecha_fin.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				fecha_fin = new Date_Time_GUI().get_fecha_sql();
				txt_fecha_fin.setText(fecha_ini.toString());
				txt_fecha_fin.setBackground(Color.WHITE);
				
			}
			
		});
		
		return txt_fecha_fin;
	}
	
	private JComboBox get_Estado(){
		add(get_etiqueta("ESTADO"));
		String bloqueo_op[] = {"REPORTADA","REPARANDO","TERMINADA"};
		estado = new JComboBox(bloqueo_op);
		
		return estado;
	}

	
	public void set_reparacion(){
		if(reparacion!=null){
			contratista = reparacion.getContratista();
			fecha_ini = reparacion.getFecha_ini();
			fecha_fin = reparacion.getFecha_fin();
			estado.setSelectedItem(reparacion.getEstado());
			
			txt_contratista.setText(contratista.getDatos_personales().get_nombre_apellidos());
			txt_fecha_ini.setText(fecha_ini.toString());
		}
	}
	
	public Reparaciones get_reparacion(){
		if(reparacion == null){
			reparacion = new Reparaciones();
			reparacion.setId(new Reparaciones_control().get_NextNum());
		}
		System.out.println("reparacion..Seleccionada..:"+reparacion.getId());
		
		reparacion.setContratista(contratista);
		reparacion.setEstado(estado.getSelectedIndex());
		reparacion.setFecha_ini(fecha_ini);
		reparacion.setFecha_fin(fecha_fin);
		reparacion.setAveria(averia);
		
		System.out.println("Estado.Seleccionada..:"+reparacion.getEstado());
		
		return reparacion;
	}
	
	public void set_limpiar(){
		averia = null;
		reparacion = null;
		fecha_ini = null;
		contratista = null;
		
	}
	
}
