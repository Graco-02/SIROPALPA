package Ventanas_S;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.Imp_reserva;
import Controladores.Action_log;
import Controladores.Reservas_control;
import Entidades.Reservas;
import Entidades.Usuario;
import Entidades.log_accion;
import Formularios.Reserva_frm;
import Formularios.selecion_lugares;

public class reserva_formulario extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Reserva_frm reserva_frm;
	protected log_accion log_action;
	private Reservas rs;
	private JComboBox estado_solicitud;
	private selecion_lugares ss_lugares;
	
	public reserva_formulario(Reservas rs) {
		if(rs!=null){
			this.rs = rs;
			this.reserva_frm = new Reserva_frm(rs,ss_lugares);
			
			String[] ss = new String[rs.get_lista_reservaciones().size()];
			for(int i=0;i<ss.length;i++){
				ss[i]=rs.get_seleccion(i);
			}
			
			ss_lugares = new selecion_lugares(ss);
			
		}else{
			this.reserva_frm = new Reserva_frm(null,ss_lugares);
			ss_lugares = new selecion_lugares();
		}
		
		
		log_action.setFecha(new Fecha().get_fecha_sql());
		log_action.setHora(new Fecha().get_hora_sql());
	
		
		this.setTitle("Datos De Reserva");
		this.setSize(700, 400);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_panel_contend());
		
		
		
		
		this.setVisible(true);
	}

	private JPanel get_panel_contend(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.add(reserva_frm,BorderLayout.CENTER);
		
		panel.add(get_panel_funciones(),BorderLayout.SOUTH);
		panel.add(ss_lugares,BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_panel_funciones(){
		JPanel panel = new JPanel(new GridLayout(0, 4));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_estado_solicitud());
		panel.add(get_boton_add());
		panel.add(get_boton_limpiar());
		
		if( rs!=null){
			panel.add(get_boton_imp());	
		}
		return panel;
	}
	
	private JComboBox get_estado_solicitud(){
        String[] opc = {"SOLICITUD","APROBADA","RECHAZADA"};
        
        estado_solicitud = new JComboBox(opc);
        
        if(Usuario.getLlave() != 0){//solo si el usuario es adminisrador pude usar las funciones
			estado_solicitud.setEnabled(false);
		}
        
        if(rs != null){//solo si el usuario es adminisrador pude usar las funciones
			estado_solicitud.setSelectedIndex(rs.getEstado());
		}
        
        estado_solicitud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(rs!=null){
            	   get_reserva();
            	   rs.setEstado(estado_solicitud.getSelectedIndex());
               }
            }
        });
        
        return estado_solicitud;
    }
	
	private JButton get_boton_add(){
		JButton boton = new JButton("ADD", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/adduser.png", 50, 50)));
		
		if(Usuario.getLlave() != 0){//solo si el usuario es administrador puede usar las funciones
			boton.setEnabled(false);
		}
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				
				if (rs == null && reserva_frm.get_validar()) {
					get_reserva();
					
					rs.set_lista_seleeciones(ss_lugares.get_lista());
					
					new Reservas_control().set_insert(rs);
					JOptionPane.showMessageDialog(null, "Reserva Realizada");
					dispose();
					log_action.setLog("Guardo Reserva");
				}else{
					if (reserva_frm.get_validar() && JOptionPane.showConfirmDialog(null, "Desea modificar los datos?") == 0) {
						new Reservas_control().set_modificar(rs);
						JOptionPane.showMessageDialog(null, "Reserva Modificada");
						dispose();
						log_action.setLog("Modifico Reserva");
					}
				}
				
				set_log();
			}
		});
		return boton;
	}
	
	private JButton get_boton_limpiar(){
    	
		JButton boton = new JButton("Limpiar", 
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/limpiar1.png", 50, 50)));
		
		if(Usuario.getLlave() != 0){//solo si el usuario es adminisrador pude usar las funciones
			boton.setEnabled(false);
		}
		
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				reserva_frm.set_limpiar();
				rs = null;
			}
		});
    	
    	return boton;
	}

	private JButton get_boton_imp(){
    	
		JButton boton = new JButton("Imp", 
				new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/imprimir.png", 50, 50)));
		
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			  if (reserva_frm.get_validar() && JOptionPane.showConfirmDialog(null,
					  "Desea Imprimir los datos de la reserva") == 0) {
					new Imp_reserva(rs);
					JOptionPane.showMessageDialog(null, "Impresion Realizada");
			  }
			}
		});
    	
    	return boton;
	}
	
	protected void set_log(){
		new Action_log().set_insert(log_action);
	}
	
	private Reservas get_reserva(){
		this.rs = reserva_frm.get_reserva();
		
		return rs;
	}
}
