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

import Controladores.Averias_control;
import Entidades.Averias;
import Formularios.Averias_frm;
import Utilidades.Gestor_Imagenes;

public class Averias_formulario extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Averias averia;
	private Averias_frm averia_frm;
	
	public Averias_formulario(Averias averia) {
		if (averia!=null) {
			this.averia = averia;
		}
		
		averia_frm = new Averias_frm(this.averia);
		averia_frm.set_averia();
		
		this.setTitle("FORMULARIO CONTRATISTA");
        this.setSize(700,300);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.add(get_contend());
        this.setVisible(true);
	
	}
	
	private JPanel get_contend(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(averia_frm, BorderLayout.CENTER);
		panel.add(get_Funciones(),BorderLayout.EAST);
		return panel;
	}
	
	private JPanel get_Funciones(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBackground(Color.DARK_GRAY);
		
		panel.add(get_boton_add());
		panel.add(get_boton_Reparaciones());
		return panel;
	}
	
	
	private JButton get_boton_add(){
		JButton boton = new JButton("ADD", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/adduser.png", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(averia == null && averia_frm.get_validar()){
					averia = averia_frm.get_averia();//cargo los datos del formulario a la variable instancia
					new Averias_control().set_insert(averia);//envio la variable al controlador para ser ingresada
				}else if(averia!= null && averia_frm.get_validar()){
					if(JOptionPane.showConfirmDialog(null, "Esta seguro de modificar el estatus de la averia?") == 0){
						averia = averia_frm.get_averia();//cargo los datos del formulario a la variable instancia
						new Averias_control().set_modificar(averia);//modifico el estado en caso de haber seleccionado una averia existente
					}
				}
				
				averia_frm.set_limpiar();
				averia = null;
				dispose();
			}
		});
		return boton;
	}
	
	private JButton get_boton_Reparaciones(){
		JButton boton = new JButton("REPARACIONES", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/reparacion.jpg", 50, 50)));
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Reparaciones_formulario(averia);
			}
		});
		return boton;
	}

}
