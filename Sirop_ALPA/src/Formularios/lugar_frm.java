/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Controladores.Lugar_control;
import Entidades.Lugar;
import Utilidades.Fecha;
import Utilidades.Gestor_Imagenes;
import Utilidades.ManejadorReportes;
import Utilidades.ResultSetTableModel;
import Ventanas_S.Date_Time_GUI;
import Ventanas_S.selector_lugar;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author FGFH
 */
public class lugar_frm extends JPanel{

    private JTextField txt_titulo;
    private JComboBox opc_acceso;
    private JTextField txt_hora_ini;
    private JTextField txt_hora_fin;
    private Lugar lugar;
    private Time hora_ini,hora_fin;
    private ResultSetTableModel resultado;
    
    public lugar_frm(){
        
        this.setLayout(new GridLayout(5, 2));
        this.setBorder(BorderFactory.createTitledBorder(""));
        
        this.add(get_txt_titulo());
        this.add(get_opc_acesso());
        this.add(get_txt_hora_ini());
        this.add(get_txt_hora_fin());
        this.add(new JLabel());
        this.add(get_boton_select());
        
    }
    
    public lugar_frm(ResultSetTableModel resultado){
        this.resultado = resultado;
    	
        this.setLayout(new GridLayout(4, 2));
        this.setBorder(BorderFactory.createTitledBorder(""));
        
        this.add(get_txt_titulo());
        this.add(get_opc_acesso());
        this.add(get_txt_hora_ini());
        this.add(get_txt_hora_fin());
    }
    
    private JLabel get_etiqueta(String text){
        JLabel etiqueta = new JLabel(text);
        etiqueta.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,20));
        return etiqueta;
    }
    
    private JTextField get_txt_titulo(){
        this.add(get_etiqueta("Titulo"));
        
        txt_titulo = new JTextField();
        txt_titulo.addKeyListener(new KeyAdapter() {
               @Override
            public void keyTyped(KeyEvent e) {
                
                if(txt_titulo.getText().length() > 50){
                    e.consume();
                    
                }else{
                	try {
						resultado.setQuery(new ManejadorReportes().set_queryReporte_lugares(txt_titulo.getText(),opc_acceso.getSelectedIndex()));
					} catch (IllegalStateException | SQLException e1) {
						e1.printStackTrace();
					}
                }
                
            }

        });
        return txt_titulo;
    }
    
    private JComboBox get_opc_acesso(){
        this.add(get_etiqueta("Acceso"));
        
        String[] opc = {"COMUN","PRIVADO"};
        
        opc_acceso = new JComboBox(opc);
        
        opc_acceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /*
                * determino que nivel de acceso a sido seleccionado 
                * si es comun las horas de uso deben ser completadas
                * de lo contrario no deben ser habilitadas
                * */
            	if(opc_acceso.getSelectedIndex() == 1){
                	swich(false);
                }else{
                	swich(true);
                }//fin del if
            	
            }
        });
        
        return opc_acceso;
    }
    
    private JTextField get_txt_hora_ini(){
        this.add(get_etiqueta("Hora Inicio"));
        
        txt_hora_ini = new JTextField(new Fecha().getHora());
        txt_hora_ini.setEnabled(true);
        txt_hora_ini.setEditable(false);
        
        txt_hora_ini.addMouseListener(new MouseAdapter() {
               @Override
            public void mouseClicked(MouseEvent e) {
                if(txt_hora_ini.isEnabled()){
                    hora_ini = new Date_Time_GUI().get_hora();
                    txt_hora_ini.setText(hora_ini.toString());
                }
            }

        });
        
        return txt_hora_ini;
    }
    
    private JTextField get_txt_hora_fin(){
        this.add(get_etiqueta("Hora Fin"));
        
        txt_hora_fin = new JTextField(new Fecha().getHora());
        txt_hora_fin.setEnabled(true);
        txt_hora_fin.setEditable(false);
        txt_hora_fin.addMouseListener(new MouseAdapter() {
               @Override
            public void mouseClicked(MouseEvent e) {
                if(txt_hora_fin.isEnabled()){
                    hora_fin = new Date_Time_GUI().get_hora();
                    txt_hora_fin.setText(hora_fin.toString());
                }
            }

        });
        
        return txt_hora_fin;
    }
    
    private JButton get_boton_select(){
    	JButton boton = new JButton("Select", new ImageIcon(new Gestor_Imagenes().get_Imagen_Redimencionada("imagenes/select_p.png", 25, 25)));
    	boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				set_lugar(new selector_lugar().get_lugar());
			}
		});
    	return boton;
    }
    
    private void swich(boolean opc){
        this.txt_hora_ini.setEnabled(opc);
        this.txt_hora_fin.setEnabled(opc);
    }
    
    public void swich_2(boolean opc){
    	
    	this.txt_hora_ini.setEnabled(opc);
        this.txt_hora_fin.setEnabled(opc);
        this.txt_titulo.setEnabled(opc);
        this.opc_acceso.setEnabled(opc);
    
    }
    
    public Lugar get_lugar(){
    	
    	if(lugar == null){
    		 this.lugar = new Lugar();
    		 this.lugar.setId(new Lugar_control().get_NextNum());
    	}
        
        this.lugar.setTitulo(txt_titulo.getText());
        this.lugar.setAcceso(opc_acceso.getSelectedIndex());
        this.lugar.setHora_ini(hora_ini);
        this.lugar.setHora_fin(hora_fin);
        
        return this.lugar;
    }
    
    public void set_lugar(Lugar lugar){
        this.lugar = lugar;
        
        txt_titulo.setText(lugar.getTitulo());
        opc_acceso.setSelectedIndex(lugar.getAcceso());
        
        if(opc_acceso.getSelectedIndex() == 0){
        	
        	hora_ini=lugar.getHora_ini();
        	hora_fin=lugar.getHora_fin();
        	
        	txt_hora_ini.setText(hora_ini.toString());
            txt_hora_fin.setText(hora_fin.toString());
        }
        
    }
    
    public int get_nivel(){
    	return opc_acceso.getSelectedIndex();
    }
    
    public void set_limpiar(){
        txt_titulo.setText("");
        opc_acceso.setSelectedIndex(0);
        this.lugar = null;
    }
    
    public boolean get_validar(){
    	if(txt_titulo.getText().length() < 1){
    		txt_titulo.requestFocus();
    		JOptionPane.showMessageDialog(null, "DEBE COMPLETAR");
    		return false;
    	}
    	
    	if(opc_acceso.getSelectedIndex()==0){
    	if(hora_ini == null){
    		txt_hora_ini.requestFocus();
    		JOptionPane.showMessageDialog(null, "DEBE COMPLETAR");
    		 hora_ini = new Date_Time_GUI().get_hora();
             txt_hora_ini.setText(hora_ini.toString());
    		return false;
    		
    	}if(hora_fin == null){
    		txt_hora_fin.requestFocus();
    		JOptionPane.showMessageDialog(null, "DEBE COMPLETAR");
    		 hora_fin = new Date_Time_GUI().get_hora();
             txt_hora_fin.setText(hora_ini.toString());
    		return false;
    		
    	}
    	}
    	
    	return true;
    }
}
