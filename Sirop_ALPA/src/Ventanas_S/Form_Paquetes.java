package Ventanas_S;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controladores.Action_log;
import Controladores.Contacto_control;
import Controladores.Foto_control;
import Controladores.Persona_control;
import Controladores.Reg_Visitas_control;
import Controladores.Reg_paquetes_control;
import Entidades.Contacto;
import Entidades.Paquete;
import Entidades.Persona;
import Entidades.log_accion;
import Formularios.Persona_frm;
import Formularios.reg_paquete_frm;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.Notificacion_paq;
import Utilidades.Paquete_stick;

public class Form_Paquetes extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Paquete paquete;
	private reg_paquete_frm paquete_frm;
	private Persona_frm persona_frm;
	protected log_accion log_action;
	private JCheckBox notificar;
	private JCheckBox imprimir;
	
	public Form_Paquetes(Paquete paquete) {
		log_action = new log_accion();
		
		if(paquete != null && paquete.getEstado() == 1){
			//si se recive los datos de un paque para visulaizar se cargan en una variable working
			this.paquete = paquete;
			
			//seteo los datos del paquete seleccionado en la instancia del formulario del paquete
			paquete_frm = new reg_paquete_frm();
			paquete_frm.set_paquete(paquete);
			
			//seteo los datos del paquete seleccionado en la instancia del formulario del depositante
			persona_frm = new Persona_frm();
			persona_frm.set_persona(paquete.getDepositante());
			persona_frm.set_add(get_boton_select());
		}else{
		
			persona_frm = new Persona_frm();
			persona_frm.setBorder(BorderFactory.createTitledBorder(null, "Depositante", 1, 1,
					new Font(Font.SANS_SERIF,Font.BOLD,15), Color.BLUE));
			
			persona_frm.set_add(get_boton_select());
			paquete_frm = new reg_paquete_frm();
			
			if(paquete != null){
				this.paquete = paquete;
				System.out.println(this.paquete.getDescripcion());
				
				persona_frm.set_persona(this.paquete.getDepositante());
				paquete_frm.set_paquete(this.paquete);
			}
			
		}
		
		this.setTitle("Recibir Paquete");
		this.setSize(900, 300);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_contend());
		this.setVisible(true);
	}
	
	private JPanel get_contend(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(""));
		
		panel.add(paquete_frm, BorderLayout.CENTER);
		
		if(paquete==null || paquete.getEstado() == 0){
			panel.add(get_funciones(), BorderLayout.EAST);
			panel.add(persona_frm,BorderLayout.NORTH);
			this.setSize(900, 450);
		}
		
		return panel;
	}
	
	private JPanel get_funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.DARK_GRAY);
		
		panel.add(get_boton_guardar());
		panel.add(get_boton_limpiar());
		panel.add(get_opciones());
		return panel;
	}
	
	
	private JPanel get_opciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setBackground(Color.BLUE);
		
		panel.add(get_opcion_notificar());
		panel.add(get_opcion_imprimir());
		
		return panel;
	}
	
	
 private JButton get_boton_guardar(){
		 
		 JButton boton_g = new JButton("Guardar", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/agregar.png", 50, 50)));
		 
		 boton_g.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				/*Lo primero es validar que los datos esten completos para proseguir con el proceso 
				 * de insercion de datos a la DDBB
				 * */
				if(persona_frm.get_validar()){
					//una vez validados prosigo con el proceso
					
					if(paquete == null && JOptionPane.showConfirmDialog(null, "Esta seguro de guardar este paquete?") == 0){
						
						try{
							
							if(persona_frm.get_validar()){
								
								paquete = paquete_frm.get_paquete();
								
								paquete.setDepositante(persona_frm.get_Persona());
								paquete.getDepositante().setContacto(new Contacto());
								paquete.getDepositante().getContacto().set_id(new Contacto_control().get_NextNum());
								
								new Foto_control().set_insert(paquete.getFoto());
								new Persona_control().set_persona_validar_identificacion(paquete.getDepositante());
								new Reg_paquetes_control().set_insert(paquete);
								
								if(notificar.isSelected()){
									new Notificacion_paq(paquete);//envio la notificacion por correo
								}
								
								//pregunto si se desea imprimir un identificador para el paquete
								if(imprimir.isSelected()){
									new Paquete_stick(paquete);// imprimo el sticker para identificar el paquete
								}
								
								log_action.setLog("Recibio un paquete");
								set_log();
								
								dispose();
							}
						}catch(Exception ex){
							
						}
						
					}else{
						//valido que el usuario este seguro de dar la salida a esta paquete
						if(JOptionPane.showConfirmDialog(null, "Esta seguro entregar este paquete?")==0){
							
							log_action.setLog("Entrego un paquete");
							set_log();
							
							paquete = paquete_frm.get_paquete();
							new Reg_paquetes_control().set_cerrar_caso(paquete);
							
							if(notificar.isSelected()){
								try {
									new Notificacion_paq(paquete);
								} catch (MessagingException e1) {
									e1.printStackTrace();
								}//envio la notificacion por correo
							}
							
							//pregunto si se desea imprimir un identificador para el paquete
							if(imprimir.isSelected()){
								new Paquete_stick(paquete);// imprimo el sticker para identificar el paquete
							}
							
							dispose();
						}
						
					}
					
					
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Faltan Datos");
				}
				
			}
			
		 });
		 
		 return boton_g;
	 }
	
 private JButton get_boton_limpiar(){
 	
		JButton boton = new JButton("Limpiar", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/limpiar1.png", 50, 50)));
 	
 	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				set_limpiar();
			}
		});
 	
 	return boton;
	}
 
 private void set_limpiar(){
		
		try {
			
			this.paquete = null;
			this.paquete_frm.set_limpiar();
			this.persona_frm.set_limpiar();
			
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JOptionPane.showMessageDialog(null, "Accion Completada");
		}
	}
 
 
   private JCheckBox get_opcion_notificar(){
	   //notificar = new JCheckBox(new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/inbox-logo.png", 50, 50)), true);
	   notificar = new JCheckBox("Notificar",
			   new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/inbox-logo.png", 50, 50)));
	   notificar.setSelected(true);
	   notificar.setForeground(Color.BLUE);
	   notificar.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(notificar.isSelected()){
				notificar.setForeground(Color.BLUE);
			}else{
				notificar.setForeground(Color.RED);
			}
		}
	});
	   return notificar;
   }
   
   
	private JButton get_boton_select(){
		
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/select_p.png", 25, 25)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Persona per = new Persona_selector().get_persona();
				
				if( new Reg_Visitas_control().valida_estado( per.getId() ) ){
					JOptionPane.showMessageDialog(null, "Esta persona no puede ser seleccionada! segun el registro de visitas esta se encuentra en un apartamento, cierre la visita");
				}else{
				//if(per!=null){
					persona_frm.set_persona(per);	
				}
			}
		});
    	return boton;
	}
   
   private JCheckBox get_opcion_imprimir(){
	   //imprimir = new JCheckBox(new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/imprimir.png", 50, 50)), true);
	   imprimir = new JCheckBox("Imprimir",
			   new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/imprimir.png", 50, 50)));
	   
	   imprimir.setSelected(true);
	   imprimir.setForeground(Color.BLUE);
	   
	   imprimir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(imprimir.isSelected()){
					imprimir.setForeground(Color.BLUE);
				}else{
					imprimir.setForeground(Color.RED);
				}
			}
		});
	   return imprimir;
   }
 
	protected void set_log(){
		log_action.setFecha(new Fecha().get_fecha_sql());
		log_action.setHora(new Fecha().get_hora_sql());
		
		new Action_log().set_insert(log_action);
	}

}
