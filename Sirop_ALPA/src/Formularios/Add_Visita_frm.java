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

import Controladores.Bloqueo_control;
import Controladores.Lugar_control;
import Controladores.Persona_control;
import Controladores.Reg_Visitas_control;
import Entidades.Lugar;
import Entidades.Persona;
import Entidades.Usuario;
import Entidades.bloqueo;
import Entidades.visita;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Utilidades.Visita_stick;
import Ventanas_S.Persona_selector;
import Ventanas_S.selector_lugar;

public class Add_Visita_frm extends JPanel{

	private visita visita;
	private Persona_frm visitante_frm;
	private JTextField txt_lugar;
	private Lugar apt;
	private Visita_frm visita_frm;
	private ResultSetTableModel resultado;
	
	public Add_Visita_frm(visita visita,ResultSetTableModel resultado) {
		this.resultado = resultado;
		
		visita_frm = new Visita_frm();
		this.visita = visita;
		this.setLayout(new BorderLayout());
		this.add(get_panel_visitante(),BorderLayout.NORTH);
		this.add(get_panel_visita_data(),BorderLayout.CENTER);
	}

	public Add_Visita_frm(ResultSetTableModel resultado) {
		this.resultado = resultado;
		
		visita_frm = new Visita_frm();
		this.setLayout(new BorderLayout());
		this.add(get_panel_visitante(),BorderLayout.NORTH);
		this.add(get_panel_visita_data(),BorderLayout.CENTER);
	
	}
	
	private JPanel get_panel_visitante(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		if(visita!=null){
			visitante_frm = new Persona_frm();
			visitante_frm.set_persona(visita.getPersona());
		}else{
			visitante_frm = new Persona_frm();
			visitante_frm.set_add(get_boton_select());
		}
		
		panel.add(visitante_frm);
		return panel;
	}
	
	private JPanel get_panel_visita_data(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Datos de la Visita"));
		panel.setLayout(new GridLayout(2,0));
		
		panel.add(visita_frm);
		panel.add(get_panel_Lugar());
		
		return panel;
	}
	
	private JPanel get_panel_Lugar(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setLayout(new GridLayout(0,2));
		
		panel.add(get_etiqueta("Lugar"));
		panel.add(get_txt_lugar());
		
		
		panel.add(new JLabel());
		panel.add(get_bt_guardar());
	
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

					apt = new selector_lugar().get_lugar();
					txt_lugar.setText(apt.getTitulo());
					
				}
			});
		}
		
		return txt_lugar;
	}
	
	private JButton get_boton_select(){
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/select_p.png", 25, 25)));
    	boton.addActionListener(new ActionListener() {
			
			private Persona visitante;

			@Override
			public void actionPerformed(ActionEvent e) {
				visitante = new Persona_selector().get_persona();
				System.out.println(visitante.getId());
				new Bloqueo_control().get_validar(visitante.getId());
				visitante_frm.set_persona(visitante);
			}
		});
    	return boton;
     }
	
	


	
	private JButton get_bt_guardar(){
		JButton boton = new JButton("Guardar", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*Lo primero es validar que los datos esten completos para proseguir con el proceso 
				 * de insercion de datos a la DDBB
				 * */
				if(visitante_frm.get_validar()){
					//una vez validados prosigo con el proceso
					
					if(visita == null && JOptionPane.showConfirmDialog(null, "Esta seguro de guardar esta visita?") == 0){
						//valido que todos los datos esten completos y que sea una nueva visita ademas valido que este visitante
						//tenga permitido accesar al lugar seleccionado
						try{
							
							if( new Bloqueo_control().get_validar(visitante_frm.get_Persona().getId()) ){//valido que no tenga bloqueos el visitante
								
								visita = visita_frm.get_Visita();
								visita.setPersona(visitante_frm.get_Persona());
								visita.setId_lugar(apt.getId());
							
								new Persona_control().set_persona_validar_identificacion(visita.getPersona());
								new Reg_Visitas_control().set_insert(visita);
			            		//envio los datos de la visita al generador de tiquet
			            		new Visita_stick(visita);
								set_limpiar();
								JOptionPane.showMessageDialog(null, "Accion Realizada");
							}else{
							
							}
							
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "Error Guardando la visita \n"+ex.getMessage());
						}
						
					}else{
						//valido que el usuario este seguro de dar la salida a esta visita
						if(JOptionPane.showConfirmDialog(null, "Esta seguro de dar salida a esta visita?")==0){
							
							visita = visita_frm.get_Visita();
							
							new Reg_Visitas_control().set_hora_salida(visita);
							
							set_limpiar();
							
							JOptionPane.showMessageDialog(null, "Accion Realizada");
							
							set_limpiar();//reinicio los valores
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Faltan Datos");
				}
				
				
			}
		});
		
		return boton;
	}
	
	public void set_visita(visita visita){
		this.visita = visita;
		this.apt = new Lugar_control().get_lugar(visita.getId_lugar());
		this.visitante_frm.set_persona(visita.getPersona());
		this.visita_frm.set_Visita(visita);
		txt_lugar.setText(apt.getTitulo());
	}
	
	public void set_limpiar(){
		try{
			this.resultado.setQuery(new ManejadorReportes().get_query_visitas());
			this.visita = null;
			this.apt = null;
			this.visitante_frm.set_limpiar();
			visita_frm.set_limpiar();
		}catch(Exception e){
			
		}
	}
}//fin de la clase
