package Formularios;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controladores.Lugar_control;
import Controladores.Reservas_control;
import Entidades.Lugar;
import Entidades.Reservas;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorFicheros;
import Ventanas_S.Date_Time_GUI;
import Ventanas_S.selector_lugar;

public class Reserva_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Reservas reserva;
	private Date fecha_ini;
	private Time hora_inicio;
	private Time hora_fin;
	private Lugar apt;
	private JTextField txt_hora_fin;
	private JTextField txt_hora_ini;
	private JTextField txt_fecha_ini;
	private JTextField txt_solicitante;
	private String ruta;
	private JTextField txt_lista_inv;
	private selecion_lugares ss;

	private JTextField txt_nombre;
	
	
	public Reserva_frm(Reservas reserva,selecion_lugares ss) {
		
		this.setBorder(BorderFactory.createTitledBorder(null, "Datos De La Reserva", 1, 1, new Font(Font.SANS_SERIF,Font.BOLD,20), Color.BLUE));
		this.setLayout(new GridLayout(8, 2));
		this.add(get_solicitante());
		this.add(get_txtNombre());
		this.add(get_txtFecha_Ini());
		this.add(get_txtHora_Ini());
		this.add(get_txtHora_Fin());
		this.add(get_set_lista_invitados());
	
		if(reserva!=null){
			this.reserva = reserva;
			set_reserva(reserva);
			
		}
		
		this.ss = ss;
		
	}
	
	private JLabel get_etiqueta(String text){
	        JLabel etiqueta = new JLabel(text);
	        etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
	        return etiqueta;
	}
	
	private JTextField get_solicitante(){
		this.add(get_etiqueta("Solicitante"));
		
		txt_solicitante = new JTextField();
		txt_solicitante.setEnabled(false);
		txt_solicitante.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {

					apt = new selector_lugar(1).get_lugar();
					
					if(apt!=null){
						txt_solicitante.setText(apt.getTitulo());
						txt_solicitante.setBackground(Color.WHITE);
					}
			}
		});
		
		return txt_solicitante;
	}
	
	private JTextField get_txtNombre(){
		this.add(get_etiqueta("QUIEN SOLICITA"));
		
	    txt_nombre = new JTextField();
		txt_nombre.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txt_nombre.getText().length() > 100){
					e.consume();
				}
			}
			
		});
		
		return txt_nombre;
	}
	
	
	private JTextField get_txtFecha_Ini(){
		this.add(get_etiqueta("FECHA ACTVIDAD"));
		
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
	
	private JTextField get_txtHora_Ini(){
		this.add(get_etiqueta("HORA INICIO"));
		
		txt_hora_ini = new JTextField("HH:MM");
		txt_hora_ini.setEditable(false);
		txt_hora_ini.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				hora_inicio = new Date_Time_GUI().get_hora();
				txt_hora_ini.setText(hora_inicio.toString());
				txt_hora_ini.setBackground(Color.WHITE);
				
			}
			
		});
		
		return txt_hora_ini;
	}
	
	private JTextField get_txtHora_Fin(){
		this.add(get_etiqueta("HORA FIN"));
		
		txt_hora_fin = new JTextField("HH:MM");
		txt_hora_fin.setEditable(false);
		txt_hora_fin.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				hora_fin = new Date_Time_GUI().get_hora();
				txt_hora_fin.setText(hora_fin.toString());
				txt_hora_fin.setBackground(Color.WHITE);
			}
			
		});
		
		return txt_hora_fin;
	}
	
	
	private JTextField get_set_lista_invitados(){
		this.add(get_etiqueta("LISTA INVITADOS"));
		txt_lista_inv = new JTextField();
		txt_lista_inv.setEditable(false);
		txt_lista_inv.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser archivo = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				archivo.setFileFilter(filter);
			    
			    int returnVal = archivo.showOpenDialog(null);
			    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	ruta = archivo.getSelectedFile().toString();
			    	
			    	txt_lista_inv.setText(ruta);
			    	txt_lista_inv.setBackground(Color.YELLOW);
			    }//compruebe que se haya seleccionado un objeto filtrado 
			
			}
			
		});
		
		return txt_lista_inv;
	}
	
	public void set_reserva(Reservas rs){
		this.reserva = rs;
		
		
		this.hora_inicio = rs.getHora_inicio();
		this.hora_fin = rs.getHora_fin();
		this.fecha_ini = rs.getFecha_reserva();
		this.apt = new Lugar_control().get_lugar(rs.getId_solicitante());
		
		txt_fecha_ini.setText(fecha_ini.toString());
		txt_hora_fin.setText(hora_fin.toString());
		txt_hora_ini.setText(hora_inicio.toString());
		txt_solicitante.setText(apt.getTitulo());
		txt_nombre.setText(rs.getNombre_solicitante());
		
	}
	
	public Reservas get_reserva(){
		if(reserva==null){
			reserva = new Reservas();
			reserva.setId(new Reservas_control().get_NextNum());
			reserva.setFecha_solicitud(new Date(new Fecha().getTime()));
		}
		
		reserva.setFecha_reserva(fecha_ini);
		reserva.setFecha_solicitud(new Date(new Fecha().getTime()));
		reserva.setHora_fin(hora_fin);
		reserva.setHora_inicio(hora_inicio);
		reserva.setId_solicitante(apt.getId());
		
		if(ss!=null){
			reserva.set_lista_seleeciones(ss.get_lista());
		}
		
		reserva.setNombre_solicitante(txt_nombre.getText());
		
		if(ruta!=null && ruta.length() > 1){
			reserva.setLista_invitados(new ManejadorFicheros().get_file_byte(ruta));
		}
		
		return reserva;
	}
	
	public boolean get_validar(){
		if(apt == null){
			txt_solicitante.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Faltan datos");
			return false;
		}else if(fecha_ini == null){
			txt_fecha_ini.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Faltan datos");
			return false;
		}else if(hora_inicio == null){
			txt_hora_ini.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Faltan datos");
			return false;
		}else if(hora_fin == null){
			txt_hora_fin.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Faltan datos");
			return false;
		}else if(txt_nombre.getText().isEmpty()){
			txt_nombre.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Faltan datos");
			return false;
		}
		
		return true;
	}
	
	public void set_limpiar(){
		this.reserva = null;
		this.txt_fecha_ini.setText("");
		this.txt_hora_fin.setText("");
		this.txt_hora_ini.setText("");
		this.txt_solicitante.setText("");
		this.txt_nombre.setText("");
		
		apt = null;
		fecha_ini = null;
		hora_inicio= null;
		hora_fin = null;
		
		txt_solicitante.setBackground(Color.WHITE);
		txt_fecha_ini.setBackground(Color.WHITE);
		txt_hora_ini.setBackground(Color.WHITE);
		txt_hora_fin.setBackground(Color.WHITE);
		
	}

}
