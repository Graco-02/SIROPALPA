package Formularios;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Utilidades.Gestor_Imagenes;
import Ventanas_S.Persona_selector;
import Ventanas_S.selector_lugar;
import Controladores.Bloqueo_control;
import Controladores.Lugar_control;
import Controladores.Persona_control;
import Controladores.Reg_Visitas_control;
import Entidades.Lugar;
import Entidades.Persona;
import Entidades.Usuario;
import Entidades.visita;
import Entidades.bloqueo;

public class Add_BL_frm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private visita visita;
	private Persona_frm indesedeable;
	private JTextField txt_lugar;
	private Lugar lugar;
	private Persona reportado;
	private JTextPane txt_motivo;
	private JComboBox bloqueo;
	private JComboBox estado;
	private bloqueo betado;
	
	public Add_BL_frm(visita visita) {
		this.visita = visita;
		this.setLayout(new BorderLayout());
		this.add(get_panel_indeseable(),BorderLayout.NORTH);
		this.add(get_panel_querella(),BorderLayout.CENTER);
	}
	
	public Add_BL_frm() {
		this.setLayout(new BorderLayout());
		this.add(get_panel_indeseable(),BorderLayout.NORTH);
		this.add(get_panel_querella(),BorderLayout.CENTER);
	}
	
	private JPanel get_panel_indeseable(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		if(visita!=null){
			indesedeable = new Persona_frm();
			indesedeable.set_rezise_foto(200, 200);
			indesedeable.set_persona(visita.getPersona());
		}else{
			indesedeable = new Persona_frm();
			indesedeable.set_rezise_foto(200, 200);
			indesedeable.set_add(get_boton_select());
		}
		
		panel.add(indesedeable);
		return panel;
	}
	
	private JPanel get_panel_querella(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Datos de la Incidencia"));
		panel.setLayout(new GridLayout(0,2));
		
		panel.add(get_etiqueta("Lugar"));
		panel.add(get_txt_lugar());
		
		panel.add(get_etiqueta("Motivo"));
		panel.add(get_txt_motivo());
		
		panel.add(get_etiqueta("Bloqueo"));
		panel.add(get_Bloqueo());
		
		panel.add(get_etiqueta("Estado"));
		panel.add(get_estado());
		
		panel.add(new JLabel());
		panel.add(get_bt_guardar());
		
		if(Usuario.getLlave() == 0){
			estado.setEnabled(true);
		}
		
		return panel;
	}

	private JLabel get_etiqueta(String text){
		JLabel etiqueta = new JLabel(text);
		etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
		return etiqueta;
	}
	
	private JTextField get_txt_lugar(){
		txt_lugar = new JTextField();
		txt_lugar.setEditable(false);
		
		if(visita!=null){
			txt_lugar.setText( new Lugar_control().get_lugar(visita.getId_lugar()).getTitulo() );
		}else{
			txt_lugar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {

					lugar = new selector_lugar().get_lugar();
					txt_lugar.setText(lugar.getTitulo());
					
				}
			});
		}
		
		return txt_lugar;
	}
	
	private JButton get_boton_select(){
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/select_p.png", 25, 25)));
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				reportado = new Persona_selector().get_persona();
				indesedeable.set_persona(reportado);
			}
		});
    	return boton;
     }
	
	
	private JScrollPane get_txt_motivo(){
		txt_motivo = new JTextPane();
		txt_motivo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txt_motivo.getText().length() >= 500){
					e.consume();
				}
			}
		});
		
		JScrollPane cs = new JScrollPane(txt_motivo);
		
		return cs;
	}
	
	private JComboBox get_Bloqueo(){
		String bloqueo_op[] = {"Unico","Total"};
		bloqueo = new JComboBox(bloqueo_op);
		
		return bloqueo;
	}
	
	private JComboBox get_estado(){
		String estado_op[] = {"Reportado","Retencion","Libre"};
		estado = new JComboBox(estado_op);
		estado.setSelectedIndex(0);
		estado.setEnabled(false);
		
		return estado;
	}
	
	private JButton get_bt_guardar(){
		JButton boton = new JButton("Guardar", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/bloqueo_P.png", 50, 50)));
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
                if(reportado == null && indesedeable.get_validar() ){
    				reportado = indesedeable.get_Persona();
    				new Persona_control().set_insert(reportado);
                }
				
				if(validar() ){
				   if(betado == null){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de aplicar el cambio") == 0){
				
							betado = new bloqueo();
							
							betado.setApt(lugar);
							betado.setReportado(reportado);
							betado.setMotivo(txt_motivo.getText());
							betado.setEstado(estado.getSelectedIndex());
							betado.setBloqueo(bloqueo.getSelectedIndex());
						
							new Bloqueo_control().set_insert(betado);
							set_limpiar();
					}
				   }else{
					//modificacion de atributos
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de aplicar La Modificacion") == 0){
						
						betado.setMotivo(txt_motivo.getText());
						betado.setEstado(estado.getSelectedIndex());
						betado.setBloqueo(bloqueo.getSelectedIndex());
						
						new Bloqueo_control().set_modificar(betado);
						
						set_limpiar();
					}
				 }
			}
			}
		});
		
		return boton;
	}
	
	public void set_betado(bloqueo betado){
		this.betado = betado;
		
		this.lugar = this.betado.getApt();
		indesedeable.set_persona(this.betado.getReportado());
		this.txt_motivo.setText(this.betado.getMotivo());
		estado.setSelectedIndex(this.betado.getEstado());
		bloqueo.setSelectedIndex(this.betado.getBloqueo());
		
		txt_lugar.setText(lugar.getTitulo());
	}
	
	
	private boolean validar(){
		if(txt_motivo.getText().length() < 10){
			JOptionPane.showMessageDialog(null, "Motivo incoherente");
			txt_motivo.requestFocus();
			return false;
		}else if(reportado==null){
			JOptionPane.showMessageDialog(null, "Debe elejir un objetibo");
			return false;
		}else if(lugar==null){
			JOptionPane.showMessageDialog(null, "Debe elejir un querellante");
			return false;
		}
		
		return true;
	}
	
	public void set_limpiar(){
		this.reportado = null;
		this.lugar = null;
		this.txt_motivo.setText("");
		this.indesedeable.set_limpiar();
		betado = null;
	}
	
}
