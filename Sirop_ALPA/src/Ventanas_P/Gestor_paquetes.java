/**
 * 
 */
package Ventanas_P;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import Controladores.Action_log;
import Controladores.Contacto_control;
import Controladores.Foto_control;
import Controladores.Lugar_control;
import Controladores.Persona_control;
import Controladores.Reg_Visitas_control;
import Controladores.Reg_paquetes_control;
import Entidades.Contacto;
import Entidades.Paquete;
import Entidades.Persona;
import Entidades.log_accion;
import Formularios.Persona_frm;
import Formularios.Visita_frm;
import Formularios.busqueda_paquetes_frm;
import Formularios.busqueda_visita_frm;
import Formularios.lugar_frm;
import Formularios.reg_paquete_frm;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.Notificacion_paq;
import Utilidades.Paquete_stick;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Form_Paquetes;
import Ventanas_S.Persona_selector;

/**
 * @author FFerr
 *
 */
public class Gestor_paquetes extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private Persona_frm persona_frm;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private reg_paquete_frm paquete_frm;
	private Paquete paquete;
	protected log_accion log_action;
	
	
	public Gestor_paquetes() {
		log_action = new log_accion();
		
		this.setTitle("GESTOR DE PAQUETES");
        this.setSize(1000,700);
       // this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_content());
        this.setVisible(true);
	}
	
	private JPanel get_content(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(get_Tabla(), BorderLayout.CENTER);
		panel.add(get_funciones(), BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_centro(){
		persona_frm = new Persona_frm();
    	persona_frm.set_add(get_boton_select());
		
    	paquete_frm = new reg_paquete_frm();
    	paquete_frm.setBorder(BorderFactory.createTitledBorder(""));
    	
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(persona_frm,BorderLayout.WEST);
		panel.add(paquete_frm,BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel get_Tabla(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.add(getTabla(), BorderLayout.CENTER);
		
		busqueda_paquetes_frm busq = new busqueda_paquetes_frm(resultado);
		busq.setBackground(Color.DARK_GRAY);
		panel.add(busq,BorderLayout.NORTH);
		
		return panel;
	}
	
	private JPanel get_funciones(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(5, 0));
		panel.setBackground(Color.DARK_GRAY);
		
		panel.add(get_boton_guardar());
		
		return panel;
	}
	
	private JTabbedPane get_pestanas(){
		
    	JTabbedPane pestanas = new JTabbedPane();
    	
		persona_frm = new Persona_frm();
    	persona_frm.set_add(get_boton_select());
		
    	paquete_frm = new reg_paquete_frm();
    	
    	pestanas.setBorder(BorderFactory.createTitledBorder(""));
    	pestanas.add("DEPOSITANTE", persona_frm);
    	pestanas.add("PAQUETE", paquete_frm);
    	
    	
    	return pestanas;
	} 

	private Box getTabla(){
			try{
				resultado = new ManejadorReportes().getReporte_paquetes();
				tabla = new JTable(this.resultado);
				tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
				tabla.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						if (e.getClickCount() == 2){
        	  
							JTable target = (JTable)e.getSource();
							int row = target.getSelectedRow();
            
							try {
								
								paquete = new Reg_paquetes_control().get_paquete_by_ID(
										Integer.parseInt(String.valueOf(tabla.getValueAt(row, 0))));
								
								//paquete_frm.set_paquete(paquete);
								//persona_frm.set_persona(paquete.getDepositante());
								
								new Form_Paquetes(paquete);//seteo los datos del paquete seleccionado en una nueva ventana
								set_limpiar();
							} catch (IllegalStateException e1) {
	        		
							}
						}//
					}
				});
			}catch (IllegalStateException e){
				e.printStackTrace();
			}catch (SQLException e){
				e.printStackTrace();
			}
   
			Box box = Box.createHorizontalBox(); 
			JScrollPane barra = new JScrollPane(tabla);
			box.add(barra);
			box.setBorder(BorderFactory.createTitledBorder(""));
    
			return box;
  
	}
	
	
	private JButton get_boton_select(){
		
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/select_p.png", 25, 25)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Persona per = new Persona_selector().get_persona();
				
				if( new Reg_Visitas_control().valida_estado( per.getId() ) ){
					JOptionPane.showMessageDialog(null, "Esta persona no puede ser seleccionada!");
				}else{
					persona_frm.set_persona(per);
				}
			}
		});
    	return boton;
	}
	
	 private JButton get_boton_guardar(){
		 
		 JButton boton_g = new JButton("Nuevo", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
		 
		 boton_g.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				new Form_Paquetes(null);
				set_limpiar();
				
			}
			
		 });
		 
		 return boton_g;
	 }
	 
	
	
	private void set_limpiar(){
		
		try {
			
			this.paquete = null;
			//this.paquete_frm.set_limpiar();
			///this.persona_frm.set_limpiar();
			
			this.resultado.setQuery(new ManejadorReportes().get_query_paquetes());
			
		} catch (IllegalStateException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JOptionPane.showMessageDialog(null, "Accion Completada");
		}
	}

	protected void set_log(){
		log_action.setFecha(new Fecha().get_fecha_sql());
		log_action.setHora(new Fecha().get_hora_sql());
		
		new Action_log().set_insert(log_action);
	}
}
