package Ventanas_S;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controladores.Reg_Visitas_control;
import Controladores.Reparaciones_control;
import Entidades.Averias;
import Entidades.Reparaciones;
import Formularios.Reparaciones_frm;
import Utilidades.Gestor_Imagenes;

public class Reparaciones_formulario extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Reparaciones_frm rep_frm;
	private Averias averia;
	private Reparaciones rep;
	
	public Reparaciones_formulario(Averias averia) {
		this.averia = averia;
		rep = new Reparaciones_control().get_reparaciones(this.averia.getId());
		rep_frm = new Reparaciones_frm(rep, averia);
		System.out.println("Averia seleccionada.:"+averia.getId());
		
		this.setTitle("FORMULARIO CONTRATISTA");
        this.setSize(700,500);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_contend());
        this.setVisible(true);
	}

	private JPanel get_contend() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(rep_frm, BorderLayout.CENTER);
		panel.add(get_funciones(), BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_funciones() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBackground(Color.DARK_GRAY);
		panel.add(get_boton_add());
		panel.add(get_boton_Limpiar());
		return panel;
	}
	
	private JButton get_boton_add(){
		JButton boton = new JButton("ADD", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/adduser.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rep == null){
					
					rep = rep_frm.get_reparacion();
					new Reparaciones_control().set_insert(rep);
					
					if(JOptionPane.showConfirmDialog(null, "Desea informar visita al lobby") == 0){
						new Reg_Visitas_control().set_insert(rep);
					}
					
					set_limpiar();
				}else{
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de cambiar el estado?") == 0){
						rep = rep_frm.get_reparacion();
						new Reparaciones_control().set_modificar(rep);
						set_limpiar();
					}
				}
				
			}
		});
		return boton;
	}
	
	private JButton get_boton_Limpiar(){
    	JButton boton = new JButton("Limpiar",
    			new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/limpiar1.png", 50, 50)));
    	
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					set_limpiar();
			}
		});
    	
    	return boton;
    }
	
	private void set_limpiar(){
			averia = null;
			rep = null;
			rep_frm.set_limpiar();
			dispose();
		
	}
	

}
