package Formularios;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Ventanas_S.Date_Time_GUI;
import Ventanas_S.Persona_selector;
import Ventanas_S.selector_lugar;
import Controladores.Foto_control;
import Controladores.Persona_control;
import Entidades.Foto;
import Entidades.Paquete;
import Entidades.Persona;

public class reg_paquete_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Persona receptor;
	private Paquete paquete;
	private JTextField txt_fecha_ent;
	private Date fecha_ent,fecha_sal;
	private JTextField txt_fecha_sal;
	private JTextPane txt_nota;
	private JLabel foto_jlb;
	protected String ruta_foto;
	private Foto foto;
	private JPanel panel_data;
	private JTextField txt_receptor;
	private JTextField txt_depositante;
	private boolean selector = false;
	
	private foto_frm ft_frm;
	
	public reg_paquete_frm() {
		fecha_ent = fecha_ent.valueOf(new Fecha().getFechaSystemaYYMMDD());
		fecha_sal = fecha_sal.valueOf(new Fecha().getFechaSystemaYYMMDD());
		ft_frm = new foto_frm();
		
		this.setLayout(new BorderLayout());
		this.add(ft_frm, BorderLayout.EAST);
		this.add(get_panel(), BorderLayout.CENTER);
	}
	
	private JPanel get_panel(){
		panel_data = new JPanel();
		panel_data.setLayout(new GridLayout(5, 2));
		panel_data.add(get_txt_fecha_entrada());
		panel_data.add(get_txt_fecha_salida());
		panel_data.add(get_receptor());
		panel_data.add(get_txt_Nota());
		return panel_data;
	}
	
	private JLabel get_etiqueta(String text){
        JLabel etiqueta = new JLabel(text);
        etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
        return etiqueta;
    }
	
	private JTextField get_txt_fecha_entrada(){
		this.panel_data.add(get_etiqueta("Fecha Entrada"));
		
		txt_fecha_ent = new JTextField(fecha_ent.toString());
		txt_fecha_ent.setEditable(false);
		txt_fecha_ent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(selector==false){
					fecha_ent = new Date_Time_GUI().get_fecha_sql();
					txt_fecha_ent.setText(fecha_ent.toString());
				}
				
			}
		});
		return txt_fecha_ent;
	}
	
	private JTextField get_txt_fecha_salida(){
		this.panel_data.add(get_etiqueta("Fecha Entrega"));
		
		txt_fecha_sal = new JTextField(fecha_sal.toString());
		txt_fecha_sal.setEditable(false);
		txt_fecha_sal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selector == false){
					fecha_sal = new Date_Time_GUI().get_fecha_sql();
					txt_fecha_sal.setText(fecha_sal.toString());
				}
			}
		});
		return txt_fecha_sal;
	}
	
	private JTextField get_receptor(){
		this.panel_data.add(get_etiqueta("Receptor"));
		
		txt_receptor = new JTextField();
		txt_receptor.setEnabled(false);
		txt_receptor.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
				receptor = new Persona_selector(1).get_persona();
				
				if(receptor!=null && selector == false){
					txt_receptor.setText(receptor.getNombres()+","+receptor.getApellidos());
					paquete.setDepositante(receptor);
				}
				
			}
		});
		
		return txt_receptor;
	}
	
	private JTextField get_depositante(){
		this.panel_data.add(get_etiqueta("Depositante"));
		
		txt_depositante = new JTextField(paquete.getDepositante().getNombres()+
				","+paquete.getDepositante().getApellidos());
		
		txt_depositante.setEnabled(false);
		
		return txt_depositante;
	}
	
	private JScrollPane get_txt_Nota(){
		this.panel_data.add(get_etiqueta("Nota"));
		
		txt_nota = new JTextPane();
		txt_nota.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txt_nota.getText().length() > 500){
					e.consume();
				}
				
			}
		});
		
		return new JScrollPane(txt_nota);
	}
	
	
	public Paquete get_paquete(){
		
		if(this.paquete == null){
			paquete = new Paquete();
		}
		
		paquete.setDescripcion(txt_nota.getText());
		paquete.setFecha_entra(fecha_ent);
		paquete.setFecha_sale(fecha_sal);
		paquete.setId_receptor(receptor.getId());
		
		foto = ft_frm.get_foto();
		
		paquete.setFoto(foto);
		
		
		return paquete;
	}//
	
	public void set_paquete(Paquete paq){
		
		this.paquete = paq;
		receptor = new Persona_control().get_persona(paquete.getId_receptor());
		
		this.txt_fecha_ent.setText(paquete.getFecha_entra().toString());
		
		if(paquete.getFecha_sale() == null){
			paquete.setFecha_sale(fecha_sal);
		}
		
		this.txt_nota.setText(paquete.getDescripcion());
		this.txt_receptor.setText(receptor.getNombres()+","+receptor.getApellidos());
		
		ft_frm.set_foto(paquete.getFoto());
		
		this.selector = true;
		
		panel_data.add(get_depositante());
	}
	
	public void set_limpiar(){
		this.paquete = null;
		this.foto = null;
		this.txt_nota.setText("");
		this.receptor = null;
		
		ft_frm.set_limpiar();
	}
	

}
