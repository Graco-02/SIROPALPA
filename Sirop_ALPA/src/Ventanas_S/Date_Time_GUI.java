/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas_S;

import Utilidades.Fecha;
import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.Time;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author FGFH
 */
public class Date_Time_GUI extends JDialog{
    
    private JCalendar calendario;
    private Date fecha_sql;
    private Time hora_sql;
    private JTextField txt_hora;
    
    
    public Date_Time_GUI(){
        calendario = new JCalendar();
		
	this.setSize(400, 300);
	this.setModal(true);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.add(get_panel());
	this.setVisible(true);
    }
    
    private JPanel get_panel(){
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	panel.add(calendario,BorderLayout.CENTER);
        panel.add(get_panel_2(),BorderLayout.SOUTH);
	panel.setBackground(Color.DARK_GRAY);
	return panel;
    }
    
    
    private JPanel get_panel_2(){
        JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(1, 2));
        panel.add(get_txt_hora());
	panel.add(get_slecButon());
	panel.setBackground(Color.DARK_GRAY);
	return panel;
    }
    
    
    private JTextField get_txt_hora(){
        txt_hora = new JTextField(new Fecha().getHora());
        txt_hora.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                
                try{
                    Integer.parseInt(String.valueOf(caracter));
                    if(txt_hora.getText().length() == 2){
                        txt_hora.setText(txt_hora.getText() + ":");
                    }else if(txt_hora.getText().length() == 5){
                        txt_hora.setText(txt_hora.getText() + ":");
                    }else if(txt_hora.getText().length() > 7){
                        e.consume();
                    }
                    
                }catch(Exception ex){
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Solo se admiten digitos");
                }
            }
        });
        
        return txt_hora;
    }
    
    private JButton get_slecButon(){
	JButton bt = new JButton("Select", new ImageIcon("imagenes/"));
	bt.addActionListener(new ActionListener() {
			
	@Override
		public void actionPerformed(ActionEvent arg0) {
			get_fechaAAAAMMDD();
			dispose();
		}
        });
		
        return bt;
    }

    public String get_fechaAAAAMMDD(){
		 String a = Integer.toString(calendario.getCalendar().get(java.util.Calendar.YEAR));
		 String m = Integer.toString(calendario.getCalendar().get(java.util.Calendar.MONTH) + 1);
		 String d = Integer.toString(calendario.getCalendar().get(java.util.Calendar.DATE));
		
		 return a+"-"+m+"-"+d;
    }
        
    public Date get_fecha_sql(){
            /*
             este metodo convierte el string que representa la fecha a Date sql
            y lo devuelve
            */
           return fecha_sql.valueOf(get_fechaAAAAMMDD());
            
    }
    
    public Time get_hora(){
        return Time.valueOf(txt_hora.getText());
    }
    
}
