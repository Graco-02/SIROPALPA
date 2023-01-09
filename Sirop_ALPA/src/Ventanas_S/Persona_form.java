package Ventanas_S;

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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import Controladores.Contacto_control;
import Controladores.Foto_control;
import Controladores.Persona_control;
import Controladores.Reg_habitantes_control;
import Entidades.Lugar;
import Entidades.Persona;
import Formularios.Contactos_frm;
import Formularios.Persona_frm;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;

public class Persona_form extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Persona persona;
	private Persona_frm persona_frm;
	private Contactos_frm cont_frm;
	private Lugar lugar;
	private ResultSetTableModel resultado;
	private JTable tabla;
	private JButton boton_del;
	
	public Persona_form(Lugar lugar){
		this.lugar = lugar;
		
		this.setTitle("PERSONA FRM         "+lugar.getTitulo());
		this.setSize(900, 500);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(get_content());
		this.setVisible(true);
	}
	
	private JPanel get_content(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_content_2(),BorderLayout.CENTER);
		panel.add(getTabla(), BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_content_2(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(get_pestanas(),BorderLayout.CENTER);
		panel.add(get_funciones(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel get_funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_add());
		panel.add(get_boton_DEL());
		return panel;
	}
	
	private JTabbedPane get_pestanas(){
		persona_frm = new Persona_frm();
		persona_frm.set_add(get_boton_select());
		//persona_frm.set_add(get_boton_add());
		//persona_frm.set_add(get_boton_DEL());
		cont_frm = new Contactos_frm();
		
		JTabbedPane pestanas = new JTabbedPane();
		pestanas.add("PERSONA DATA", persona_frm);
		pestanas.add("CONTACTO DATA", cont_frm);
		
		return pestanas;
	}
	
	
	 private Box getTabla()
	  {
	    try
	    {
	      resultado = new ManejadorReportes().getReporte_habitantes(lugar.getId());
	      tabla = new JTable(this.resultado);
	      tabla.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
	      tabla.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e)
	        {
	          if (e.getClickCount() == 2){
	        	  
	            JTable target = (JTable)e.getSource();
	            int row = target.getSelectedRow();
	            
	            	try {
		            	//selecciono una persona segun su id
	            		persona = new Persona_control().get_persona( Integer.parseInt(String.valueOf(tabla.getValueAt(row, 2))) );
		            	
	            		
	            		//valido el estado del habitante en caso de ser distino a cero pregunto sei se desea habilitar
	            		if(new Reg_habitantes_control().get_valida_estado(persona.getId())){
		            		if(JOptionPane.showConfirmDialog(null, "Desea habilitar Nuebamente") == 0){
		            			new Reg_habitantes_control().set_modificar(persona.getId(), 0);
		            		}
		            	}
	            		
	            		persona_frm.set_persona(persona);//seteo los datos al formulario
		            	cont_frm.set_limpiar();
		            	cont_frm.set_contacto(persona.getContacto());//seteo los datos del contacto de esa persona
		            	
		                
	            	} catch (IllegalStateException e1) {
						e1.printStackTrace();
					}
	          }//
	        }
	      });
	    }
	    catch (IllegalStateException e)
	    {
	      e.printStackTrace();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    
	    Box box = Box.createHorizontalBox(); 
	    JScrollPane barra = new JScrollPane(tabla);
	    box.add(barra);
	    box.setBorder(BorderFactory.createTitledBorder(""));
	    
	    return box;
	  }
	
	
	private JButton get_boton_add(){
		JButton boton = new JButton("ADD", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/adduser.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				get_persona();
				set_limpiar();
			}
		});
		return boton;
	}
	
	
	
	private void get_persona(){
		if(persona_frm.get_validar() && persona == null){
			persona = persona_frm.get_Persona();
			persona.setContacto(cont_frm.get_contacto());
			
			try{
				
				new Foto_control().set_insert(persona.getFoto());
				new Contacto_control().set_insert(persona.getContacto());
				new Persona_control().set_insert(persona);
				new Reg_habitantes_control().set_insert(persona.getId(), lugar.getId());
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Persona_form.this.dispose();
		}else if(persona!=null && persona_frm.get_validar()){
			if(JOptionPane.showConfirmDialog(null, "Esta seguro de modificar los datos?") == 0){
				persona = persona_frm.get_Persona();//obtengo los nuevos datos de la persona
				persona.setContacto(cont_frm.get_contacto());
				System.out.println("contacto a modificar=="+persona.getContacto().get_id());
				
				//new Persona_control().set_modificar(persona);//envio los datos para ser modificados
			   
				//new Foto_control().set_modificar(persona.getFoto());//envio la instancia de la foto para ser modificada
		  
		    	new Contacto_control().set_tel_corre(persona.getContacto());
			}else if(JOptionPane.showConfirmDialog(null, "Desea registrar este habitante?") == 0){
				new Foto_control().set_insert(persona.getFoto());
				new Contacto_control().set_insert(persona.getContacto());
				new Persona_control().set_insert(persona);
				new Reg_habitantes_control().set_insert(persona.getId(), lugar.getId());
			}
		}else{
			JOptionPane.showMessageDialog(null, "Faltan datos");
		}
	}
	
	private JButton get_boton_DEL(){
		boton_del = new JButton("ELIMINAR", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/delete.png", 50, 50)));
		boton_del.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(persona!=null){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de desactivar este habitante?") == 0){
						new Reg_habitantes_control().set_modificar(persona.getId(), 1);
					}
				}	
				set_limpiar();
			}
		});
		return boton_del;
	}
	
	private JButton get_boton_select(){
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/", 50, 50)));
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				persona = new Persona_selector().get_persona();
				
				//valido el estado del habitante en caso de ser distino a cero pregunto sei se desea habilitar
        		if(new Reg_habitantes_control().get_valida_estado(persona.getId())){
            		if(JOptionPane.showConfirmDialog(null, "Desea habilitar Nuebamente") == 0){
            			new Reg_habitantes_control().set_modificar(persona.getId(), 0);
            		}
            	}
        		
        		persona_frm.set_persona(persona);//seteo los datos al formulario
            	
            	cont_frm.set_contacto(persona.getContacto());//seteo los datos del contacto de esa persona
            	
			}
		});
    	return boton;
    }
	
	private void set_limpiar(){
		try {
			persona = null;
			persona_frm.set_limpiar();
			cont_frm.set_limpiar();
			resultado.setQuery(new ManejadorReportes().set_queryReporte_Habitantes_lug(lugar.getId()));
		} catch (IllegalStateException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
