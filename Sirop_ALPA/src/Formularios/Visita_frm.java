package Formularios;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Entidades.visita;
import Utilidades.Fecha;
import Ventanas_S.Date_Time_GUI;

public class Visita_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_hora_sal;
	private JTextField txt_hora_ent;
	private JTextField txt_fecha;
	private Date fecha_sql;
	private Time hora_ent,hora_sal;
	private JTextPane txt_nota;
	private JComboBox tipo;
	private visita visita;
	
	
	public Visita_frm(){
		fecha_sql = fecha_sql.valueOf(new Fecha().getFechaSystemaYYMMDD());
		hora_ent = hora_ent.valueOf(new Fecha().getHora());
		hora_sal = hora_sal.valueOf(new Fecha().getHora());
		
		this.setLayout(new GridLayout(5, 2));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.add(get_txt_fecha());
		this.add(get_txt_Hora_ent());
		this.add(get_txt_Hora_Sal());
		this.add(get_tipo());
		
		this.add(new JScrollPane(get_txt_Nota()));
	}
	
	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		return etiqueta;
	}
	
	private JTextField get_txt_fecha(){
		this.add(get_etiqueta("Fecha"));
		
		txt_fecha = new JTextField(fecha_sql.toString());
		txt_fecha.setEditable(false);
		txt_fecha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fecha_sql = new Date_Time_GUI().get_fecha_sql();
				txt_fecha.setText(fecha_sql.toString());
			}
		});
		return txt_fecha;
	}
	
	private JTextField get_txt_Hora_ent(){
		this.add(get_etiqueta("Hora Entrada"));
		
		txt_hora_ent = new JTextField(hora_ent.toString());
		txt_hora_ent.setEditable(false);
		txt_hora_ent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				hora_ent = new Date_Time_GUI().get_hora();
				txt_hora_ent.setText(hora_ent.toString());
			}
		});
		return txt_hora_ent;
	}
	
	private JTextField get_txt_Hora_Sal(){
		this.add(get_etiqueta("Hora Salida"));
		
		txt_hora_sal = new JTextField(new Fecha().getHora());
		txt_hora_sal.setEditable(false);
		txt_hora_sal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				hora_sal = new Date_Time_GUI().get_hora();
				txt_hora_sal.setText(hora_sal.toString());
			}
		});
		return txt_hora_sal;
	}
	
	private JComboBox get_tipo(){
		this.add(get_etiqueta("Tipo Visita"));
		
		String[] opc = {"Contratista","Privada"};
		tipo = new JComboBox(opc);
		
		return tipo;
		
	}
	
	private JTextPane get_txt_Nota(){
		this.add(get_etiqueta("Nota"));
		
		txt_nota = new JTextPane();
		txt_nota.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txt_nota.getText().length() > 500){
					e.consume();
				}
				
			}
		});
		return txt_nota;
	}

	
	public visita get_Visita(){
		
		if(visita == null){
			visita = new visita();
		}
		
		visita.setFecha(fecha_sql);
		visita.setHora_ent(hora_ent);
		visita.setHora_sal(hora_sal);
		visita.setTipo(tipo.getSelectedIndex());
		visita.setNota(txt_nota.getText());
		
		return visita;
	}
	
	public void set_Visita(visita visita){
		this.visita = visita;
		
		try{
			txt_fecha.setText(this.visita.getFecha().toString());
			txt_hora_ent.setText(this.visita.getHora_ent().toString());
			txt_hora_sal.setText(this.visita.getHora_sal().toString());
			txt_nota.setText(this.visita.getNota());
			tipo.setSelectedIndex(this.visita.getTipo());
		}catch(NullPointerException np){
			txt_nota.setText(this.visita.getNota());
			tipo.setSelectedIndex(this.visita.getTipo());
		}
	}
	
	public int get_tipo_int(){
		return tipo.getSelectedIndex();
	}

	public void set_limpiar(){
		this.visita = null;
	}
	
}
